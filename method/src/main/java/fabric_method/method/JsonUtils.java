package fabric_method.method;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class JsonUtils {
	public static JSONObject getChannelJsonObect(String filePath) throws IOException {
		InputStream inputStream = new FileInputStream(filePath);
		String text = IOUtils.toString(inputStream, "utf8");
		JSONObject jsonObject = new JSONObject(text).getJSONObject("channels");
		Iterator keys = jsonObject.keys();
		String channel = null;
		JSONObject channelJsonObject = new JSONObject();
		while (keys.hasNext()) {
			channel = String.valueOf(keys.next());
			channelJsonObject.put(channel, jsonObject);
		}

		System.out.println("====channel====" + channel);
		return jsonObject;

	}

	public static JSONObject getOrgJsonObject(String filePath) throws IOException {
		InputStream inputStream = new FileInputStream(filePath);
		System.out.println(filePath);
		String text = IOUtils.toString(inputStream, "utf8");
		JSONObject jsonObject = new JSONObject(text).getJSONObject("organizations");
		Iterator keys = jsonObject.keys();
		JSONObject orgJsonObject = new JSONObject();
		String orgName = null;
		while (keys.hasNext()) {
			orgName = String.valueOf(keys.next());
			orgJsonObject.put(orgName, jsonObject);
			System.out.println("======orgName=====" + orgName);

		}
		return jsonObject;
	}

	public static JSONObject getOrdererJsonObject(String filePath) throws IOException {
		InputStream inputStream = new FileInputStream(filePath);
		String text = IOUtils.toString(inputStream, "utf8");
		JSONObject jsonObject = new JSONObject(text).getJSONObject("orderers").getJSONObject("zyl.orderer");

		return jsonObject;
	}

	public static JSONObject getPeerJsonObject(String filePath) throws IOException {
		InputStream inputStream = new FileInputStream(filePath);
		String text = IOUtils.toString(inputStream, "utf8");
		JSONObject jsonObject = new JSONObject(text).getJSONObject("peers").getJSONObject("zyl.peer0.org1");

		return jsonObject;
	}

	public static JSONObject getCaJsonObject(String filePath) throws IOException {
		InputStream inputStream = new FileInputStream(filePath);
		String text = IOUtils.toString(inputStream, "utf8");
		JSONObject jsonObject = new JSONObject(text).getJSONObject("certificateAuthorities");
		Iterator keys = jsonObject.keys();
		JSONObject caJsonObject = new JSONObject();
		String caName = null;
		while (keys.hasNext()) {
			caName = String.valueOf(keys.next());
			caJsonObject.put(caName, jsonObject);
			System.out.println("======caName=====" + caName);

		}
		return caJsonObject;
	}

}
