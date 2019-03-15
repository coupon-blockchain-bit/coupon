package fabric_method.method;

public class Config {
	public static String Org1AdminPrivate = "/Users/zhangyulong/Documents/gopath/src/github.com/hyperledger/bonusPointsExchange/first-network/crypto-config/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/keystore/439911603e6aec36e1edd60230914d12d6c679c5be782ac33935cf38117ee2c7_sk";
	public static String Org1AdminPem = "/Users/zhangyulong/Documents/gopath/src/github.com/hyperledger/bonusPointsExchange/first-network/crypto-config/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/signcerts/Admin@org1.example.com-cert.pem";
	public static String Org1AdminTls = "/Users/zhangyulong/Documents/gopath/src/github.com/hyperledger/bonusPointsExchange/first-network/crypto-config/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/server.crt";
	public static String PeerGrpcUrl = "grpcs://localhost:7051";
	public static String OrdererGrpcUrl = "grpcs://localhost:7050";
	public static String ChaincodeName = "mycc";
	public static String ChaincodeVersion = "1.0";
}
