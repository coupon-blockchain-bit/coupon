package com.bit.bonusPointsExchange.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bit.bonusPointsExchange.utils.DBUtils;

//上传头像数据库操作类
public class UploadHeadIconManger {
	//更新数据库中的imageURL字段
	public static boolean updateImgURL(String imageURL, String shopName) {
		System.out.println(imageURL);//打印路	
		//待解决1，2 
		//1.数据库中存储的路径斜杠不见了，（已解决）自己拼凑路径。。。。有点晕
		//2.同一个用户只能有一张头像，在上传头像之前应该将原来的头像删除（从数据库中读出原始路径，删除）
		
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		//System.out.println(wantTransfer_points);
		try {
			stmt = conn.createStatement();
			String sql="update shop set imageURL='"+imageURL+"' where shopName='"+shopName+"'";
			//System.out.println(sql);
			int res = stmt.executeUpdate(sql);
			if(res != 0) 
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(null, stmt, conn);
		}
		return false;
	}
	
	
	//查询数据库中的imageURL字段
	public static String queryImgURL(String shopName) {
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		String imageURL = null;
		//System.out.println(shopName);
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select imageURL from shop where shopName='"+shopName+"'");
			if(rs.next()) {
				imageURL = rs.getString("imageURL");
				rs.close();
				return imageURL;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, stmt, conn);
		}
		return imageURL;
	}
}
