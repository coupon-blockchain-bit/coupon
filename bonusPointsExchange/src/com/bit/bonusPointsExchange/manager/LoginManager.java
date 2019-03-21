package com.bit.bonusPointsExchange.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bit.bonusPointsExchange.bean.User;
import com.bit.bonusPointsExchange.utils.DBUtils;


public class LoginManager {
	/*
	 * µ«¬Ω
	 */
	private ResultSet rs;
	
	public int isValid(User user){ //≤È—Ø’À∫≈ «∑Ò¥Ê‘⁄
		Connection conn=DBUtils.getConnection();
		int result=0;						
		Statement stmt=null;
		try {
			stmt = conn.createStatement();
			rs=stmt.executeQuery("select *from user where userName='"+user.getUserName()+"'and passwd='"+user.getPasswd()+"'");					
			if(rs.next())
				result=1;  					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, stmt, conn);
		}		
		return result;
	}
	

}
