package com.bit.bonusPointsExchange.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * ���ݿ�����
 * @author gmx
 */
public class DBUtils {

	private Connection conn=null;//����Connection������ʵ��
	private Statement stmt=null;//����Statement������ʵ��
	public ResultSet rs=null;//����ResultSet������ʵ��
	private static String propFileName="/com/bit/bonusPointsExchange/DBUtils.properties";//ָ����Դ�ļ�������λ��
	private static Properties prop = new Properties(); // ������ʵ����Properties������ʵ��

	/*���屣�����ݿ������ı���*/
	private static String dbClassName = null;
	private static String dbUrl = null;
	private static String dbUser = null;
	private static String dbPwd = null;

	/*��̬�����飬����ʼ��ʱ�������ݿ����� */
	static{
		try {			//��׽�쳣
			//��Properties�ļ���ȡ��InputStream������
			InputStream in =DBUtils.class.getClassLoader().getResourceAsStream(propFileName);
			prop.load(in); // ͨ����������������Properties�ļ�
			dbClassName = prop.getProperty("DB_CLASS_NAME"); // ��ȡ���ݿ�����
			dbUrl = prop.getProperty("DB_URL", dbUrl);		//��ȡURL
			dbUser = prop.getProperty("DB_USER", dbUser);	//��ȡ��¼�û�
			dbPwd = prop.getProperty("DB_PWD", dbPwd);		//��ȡ����
		} catch (Exception e) {
			e.printStackTrace(); // �����쳣��Ϣ
		}
	}


	/**�������ݿ�**/
	public static Connection getConnection(){
		Connection conn=null;
		try {			 //�������ݿ�ʱ���ܷ����쳣������Ҫ��׽���쳣
			Class.forName(dbClassName).newInstance();//װ�����ݿ�����
//			conn=DriverManager.getConnection(dbUrl,dbUser,dbPwd);//���������ݿ�URL�ж��������ݿ�������
conn=DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bonusbit123","bonusbit123","12345678");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();//�����쳣��Ϣ
		}
		if(conn==null){
			System.out.println("���棺DbConnectionManager.getConnection()�������ݿ�����ʧ��,\r\n\r\n�������ͣ�"
					+dbClassName
					+"\r\n������λ�ã�"
					+dbUrl
					+"\r\n�û�/����"
					+dbUser+"/"+dbPwd);//�ڿ���̨��������ʾ��Ϣ
		}
		return conn;//�������ݿ����Ӷ���
	}

	/**	���ܣ�ִ�в�ѯ����**/
	public ResultSet executeQuery(String sql){
		try { // ��׽�쳣
			conn = getConnection(); // ����getConnection()��������Connection������һ��ʵ��conn
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
		} catch (SQLException ex) {
			System.err.println(ex.getMessage()); // �����쳣��Ϣ
		}
		return rs; // ���ؽ���������
	}

	/**���ܣ�ִ�и��� ����**/
	public int executeUpdate(String sql) {
		int result = 0; // ���屣�淵��ֵ�ı���
		try { // ��׽�쳣
			conn = getConnection(); // ����getConnection()��������Connection������һ��ʵ��conn
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeUpdate(sql); // ִ�и��²���
		} catch (SQLException ex) {
			result = 0; // �����淵��ֵ�ı�����ֵΪ0
		}
		return result; // ���ر��淵��ֵ�ı���
	}

	/**����:�ر����ݿ������ӣ��ͷ���Դ**/
	public static void close(ResultSet rs,Statement stmt,Connection conn){
		try {//��׽�쳣
			if(rs!=null){ // ��ResultSet������ʵ��rs��Ϊ��ʱ
				rs.close();// �ر�ResultSet����
			}
			if(stmt!=null){ // ��Statement������ʵ��stmt��Ϊ��ʱ
				stmt.close(); // �ر�Statement����
			}
			if (conn != null) { // ��Connection������ʵ��conn��Ϊ��ʱ
				conn.close(); // �ر�Connection����
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err); // �����쳣��Ϣ
		}
	}
}
