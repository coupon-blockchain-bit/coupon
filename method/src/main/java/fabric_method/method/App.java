package fabric_method.method;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
//		String[] args1 = { "a", "b", "10", "20" };
//		String status = InvokeChaincode.invokeChaincode("invoke", args1);
//		System.out.println("=====status=====" + status);

//		QueryChaincodeInfo.queryChaincodeInfo();
		// 使用fastjson解析
		String[] args1 = { "Bob", "John", "10", "20" };
		String filePath = "/Users/zhangyulong/eclipse-workspace/method/network-config.json";
		JsonUtils.getOrgJsonObject(filePath);
//		InvokeChaincode.invokeChaincode("invoke", args1, filePath);

		String[] args2 = { "Bob" };
		QueryChaincode.queryChaincode("query", args2, filePath);
//		InputStream inputStream = new FileInputStream(filePath);
//		System.out.println(filePath);
//		String text = IOUtils.toString(inputStream, "utf8");
//		JSONObject jsonObject = new JSONObject(text).getJSONObject("organizations").getJSONObject("Org1");
//		System.out.println(jsonObject.getString("adminPrivateKey"));
//
//		final String privateKeyFileName = JsonUtils.getOrgJsonObject(filePath).getJSONObject("Org1")
//				.getString("adminPrivateKey");
//		System.out.println(privateKeyFileName);
	}
}
