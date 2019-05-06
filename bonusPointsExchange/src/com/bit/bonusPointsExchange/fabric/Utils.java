package com.bit.bonusPointsExchange.fabric;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

public class Utils {
	static public User getUser(final String name, final String mspId, String privatestr, final String certificatestr)
			throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		try {
//			final String certificate = new String(IOUtils.toByteArray(new FileInputStream(certificatestr)), "UTF-8");
			final PrivateKey privateKey = getPrivateKeyFromBytes(privatestr);
			User user = new User() {
				public String getName() {
					return name;
				}

				public Set<String> getRoles() {
					return null;
				}

				public String getAccount() {
					return null;
				}

				public String getAffiliation() {
					return null;
				}

				public Enrollment getEnrollment() {
					return new Enrollment() {
						public PrivateKey getKey() {
							return privateKey;
						}

						public String getCert() {
							return certificatestr;
						}
					};
				}

				public String getMspId() {
					return mspId;
				}
			};
			return user;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw e;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			throw e;
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassCastException e) {
			e.printStackTrace();
			throw e;
		}
	}

	static PrivateKey getPrivateKeyFromBytes(String data)
			throws IOException, NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
		final Reader pemReader = new StringReader(data);

		PEMParser pemParser = new PEMParser(pemReader);
		final PrivateKeyInfo pemPair = (PrivateKeyInfo) pemParser.readObject();

		PrivateKey privateKey = new JcaPEMKeyConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME)
				.getPrivateKey(pemPair);
		return privateKey;
	}

	public static void writeStringToFile(String str, String filePath) throws Exception {
		File file = new File(filePath);
		int index = filePath.lastIndexOf(File.separator);
		File dir = new File(filePath.substring(0, index));
		// File die = new File(;
		if (file.isDirectory()) {
			Exception exception = new Exception("writeStringToFile error: " + filePath + " is a dir.");
			exception.printStackTrace();
			throw exception;
		}
		if (file.exists()) {
			file.delete();
		}
		if (!dir.exists()) {
			dir.mkdirs();
		}
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(filePath);
		fos.write(str.getBytes());
		fos.close();
	}

}
