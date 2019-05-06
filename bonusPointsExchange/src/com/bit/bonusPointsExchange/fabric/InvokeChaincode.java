package com.bit.bonusPointsExchange.fabric;

import static java.lang.String.format;

import java.io.File;
import java.security.Security;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.json.JSONObject;

public class InvokeChaincode {
	static void out(String format, Object... args) {

		System.err.flush();
		System.out.flush();

		System.out.println(format(format, args));
		System.err.flush();
		System.out.flush();

	}

	public static String invokeChaincode(String funcs, String[] args, String filePath)
			throws RuntimeException, Exception {

		Security.addProvider(new BouncyCastleProvider());
		final CryptoSuite crypto = CryptoSuite.Factory.getCryptoSuite();
		// Create client and set default crypto suite
		System.out.println("Creating client");
		final HFClient client = HFClient.createNewInstance();
		client.setCryptoSuite(crypto);

		Collection<ProposalResponse> successful = new LinkedList<>();
		Collection<ProposalResponse> failed = new LinkedList<>();

		// Loading org1 User1 from disk
		System.out.println("Loading org1 User1 from disk");

		final String privateKeyFileName = JsonUtils.getOrgJsonObject(filePath).getJSONObject("Org1")
				.getString("adminPrivateKey");

		System.out.println("========adminPrivateKey=======" + privateKeyFileName);
		final String certificateFileName = JsonUtils.getOrgJsonObject(filePath).getJSONObject("Org1")
				.getString("signedCert");
		System.out.println("========signedCert========" + certificateFileName);
		final User User1 = Utils.getUser("PeerAdmin", "Org1MSP", privateKeyFileName, certificateFileName);
		client.setUserContext(User1);

		// Accessing channel, should already exist
		System.out.println("Accessing channel");
		JSONObject channelObject = JsonUtils.getChannelJsonObect(filePath);
		String channelName = channelObject.getString("name");
		Channel myChannel = client.newChannel(channelName);

		System.out.println("Setting channel configuration");
		final List<Peer> peers = new LinkedList<>();
		Properties peerProperties = new Properties();
		JSONObject zyl_object = JsonUtils.getPeerJsonObject(filePath);
		String tmpPath = "./tmp" + File.separator + System.currentTimeMillis() + File.separator + "tlsCACerts.pem";

		Utils.writeStringToFile(zyl_object.getString("tlsCACerts"), tmpPath);

		peerProperties.setProperty("pemFile", tmpPath);
		peerProperties.setProperty("hostnameOverride",
				zyl_object.getJSONObject("grpcOptions").getString("ssl-target-name-override"));
		peerProperties.setProperty("sslProvider", "openSSL");
		peerProperties.setProperty("negotiationType", "TLS");
		peerProperties.setProperty("trustServerCertificate", "true"); // testing // // PRODUCTION!
		peerProperties.put("grpc.NettyChannelBuilderOption.maxInboundMessageSize", 9000000);
		peers.add(client.newPeer(zyl_object.getJSONObject("grpcOptions").getString("ssl-target-name-override"),
				zyl_object.getString("url"), peerProperties));
		myChannel.addPeer(peers.get(0));
		// orderer是必不可少的

		JSONObject zylOrderObject = JsonUtils.getOrdererJsonObject(filePath);
		String tmpOrderPath = "./tmp" + File.separator + System.currentTimeMillis() + File.separator + "tlsCACerts.pem";
		System.out.println("=====tmpOrderPath====" + tmpOrderPath);
		System.out.println("=======tlsCACerts======" + zylOrderObject.getString("tlsCACerts"));
		Utils.writeStringToFile(zylOrderObject.getString("tlsCACerts"), tmpOrderPath);
		Properties ordererProperties = new Properties();
		ordererProperties.setProperty("pemFile", tmpOrderPath);
		ordererProperties.setProperty("trustServerCertificate", "true");
		ordererProperties.setProperty("hostnameOverride",
				zylOrderObject.getJSONObject("grpcOptions").getString("ssl-target-name-override"));
		ordererProperties.setProperty("sslProvider", "openSSL");
		ordererProperties.setProperty("negotiationType", "TLS");
		ordererProperties.put("grpc.NettyChannelBuilderOption.keepAliveTime", new Object[] { 5L, TimeUnit.MINUTES });
		ordererProperties.put("grpc.NettyChannelBuilderOption.keepAliveTimeout", new Object[] { 8L, TimeUnit.SECONDS });
		myChannel.addOrderer(
				client.newOrderer(zylOrderObject.getJSONObject("grpcOptions").getString("ssl-target-name-override"),
						zylOrderObject.getString("url"), ordererProperties));

		myChannel.initialize();
		final ChaincodeID invokeChaincodeID = ChaincodeID.newBuilder()
				.setName(JsonUtils.getChannelJsonObect(filePath).getJSONObject("chaincode").getString("name"))
				.setVersion(JsonUtils.getChannelJsonObect(filePath).getJSONObject("chaincode").getString("version"))
				.build();

		String flag = "0";
		System.out.println("Creating proposal for invoke(a,b,10)");

		final TransactionProposalRequest invokeProposalRequest = client.newTransactionProposalRequest();
		invokeProposalRequest.setChaincodeID(invokeChaincodeID);
		invokeProposalRequest.setFcn(funcs);
		invokeProposalRequest.setProposalWaitTime(TimeUnit.SECONDS.toMillis(10));
		invokeProposalRequest.setArgs(args);

		System.out.println("============");

		final Collection<ProposalResponse> transactionPropResp = myChannel
				.sendTransactionProposal(invokeProposalRequest, myChannel.getPeers());
		System.out.println("============");
		for (ProposalResponse response : transactionPropResp) {
			if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
				flag = "SUCCESS";
				out("Successful transaction proposal response Txid: %s from peer %s", response.getTransactionID(),
						response.getPeer().getName());
				successful.add(response);
				System.out.println("=====getChaincodeID=======" + response.getChaincodeID().toString());
			} else {
				failed.add(response);
				flag = "FAILED";
			}
		}
		System.out.println("------------");
		ProposalResponse resp = successful.iterator().next();
		byte[] x = resp.getChaincodeActionResponsePayload(); // This is the data returned by the chaincode.
		String resultAsString = null;
		if (x != null) {
			resultAsString = new String(x, "UTF-8");
		}

		myChannel.sendTransaction(successful);
		System.out.println("=========" + resultAsString);
		return resultAsString;

	}

}
