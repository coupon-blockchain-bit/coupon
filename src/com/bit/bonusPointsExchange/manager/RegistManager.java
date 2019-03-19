package com.bit.bonusPointsExchange.manager;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import com.bit.bonusPointsExchange.bean.User;
import com.bit.bonusPointsExchange.utils.DBUtils;



public class RegistManager {
	/*
	 * 注册
	 */
	private Connection conn=null;
	private Statement stmt=null;
	private String sql=null; 
	
	public int registUser(User user){
		int count =0;//注册是否成功标志， 0为失败
		conn=DBUtils.getConnection();
		try {
			stmt=conn.createStatement();
			sql="insert into user(userName,passwd,email,fullName,phone) values('"+user.getUserName()+"','"+user.getPasswd()+"','"+user.getEmail()+"','"+user.getFullName()+"','"+user.getPhone()+"')";
			//sql="insert into user(userName,passwd,email,fullName,phone,activated,randomCode) values('"+user.getUserName()+"','"+user.getPasswd()+"','"+user.getEmail()+"','"+user.getFullName()+"','"+user.getPhone()+"','"+user.getActivated()+"','"+user.getRandomCode()+"')";
			count=stmt.executeUpdate(sql);//执行插入语句，并返回插入数据的个数	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(null, stmt, conn);
		}
		return count;			
		
	}
	
	public int isRegist(String userName){//查询账户是否已注册
		Connection conn=DBUtils.getConnection();
		int result=0;						
		Statement stmt=null;
		try {
			stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("select * from user where userName='"+userName+"'");			
			System.out.println();
			if(rs.next())
				result=1;//用户账号存在  					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBUtils.close(null, stmt, conn);
		}		
		return result;
	}

}
