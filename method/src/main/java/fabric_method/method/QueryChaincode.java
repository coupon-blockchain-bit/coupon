package fabric_method.method;

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
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.json.JSONException;
import org.json.JSONObject;

public class QueryChaincode {

	public static String queryChaincode(String fun, String[] args, String filePath) throws JSONException, Exception {
		String resultString = null;

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

		// Creating proposal for query
		System.out.println("Creating proposal for query a");

		final QueryByChaincodeRequest query = client.newQueryProposalRequest();
		query.setChaincodeID(invokeChaincodeID);
		query.setFcn(fun);
		query.setProposalWaitTime(TimeUnit.SECONDS.toMillis(10));
		query.setArgs(args);
		// Send proposal and wait for responses

		final Collection<ProposalResponse> queryRes = myChannel.queryByChaincode(query, myChannel.getPeers());

		for (ProposalResponse resp1 : queryRes) {
			System.out.println("Response from peer " + resp1.getPeer().getName() + " is " + "==="
					+ resp1.getProposalResponse().getResponse());
			resultString = resp1.getProposalResponse().getResponse().toString();
			System.out.println(resultString);
		}
		return resultString;

	}
}
