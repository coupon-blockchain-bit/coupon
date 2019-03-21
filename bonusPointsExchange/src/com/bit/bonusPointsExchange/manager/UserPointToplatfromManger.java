package com.bit.bonusPointsExchange.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.bean.Transfer;
import com.bit.bonusPointsExchange.utils.DBUtils;


//用户积分到平台数据库处理
public class UserPointToplatfromManger {
	//查询用户在平台数据库中的积分
	public int ownPointsAtPlatform(String userName, String shopName){
		Connection conn=DBUtils.getConnection();
		int points = 0;//用户实际拥有的积分,返回0表示没有积分
		Statement stmt=null;
		//System.out.println(userName);
		//System.out.println(shopName);
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select platformPoint from point where userName='"+userName+"'and shopName='"+shopName+"'");
			if(rs.next()) {
				//System.out.println(rs.getString("userPoint"));
				points = Integer.parseInt(rs.getString("platformPoint"));
				rs.close();
				return points;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, stmt, conn);
		}
		return points;
	}
	//查询用户在商家的数据库中的积分
	public int ownPoints(String userName, String shopName){
		Connection conn=DBUtils.getConnection();
		int points = 0;//用户实际拥有的积分,返回0表示没有积分
		Statement stmt=null;
		//System.out.println(userName);
		//System.out.println(shopName);
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select userPoint from userpoint where userName='"+userName+"'and shopName='"+shopName+"'");
			if(rs.next()) {
				//System.out.println(rs.getString("userPoint"));
				points = Integer.parseInt(rs.getString("userPoint"));
				rs.close();
				return points;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, stmt, conn);
		}
		return points;
	}
	//更新平台的数据库
	public boolean updatePointsPlatform(String userName, String shopName, int wantTransfer_points ){
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		try {
			stmt = conn.createStatement();
			String sql="update point set platformPoint=platformPoint+'"+wantTransfer_points+"' where userName='"+userName+"' and shopName='"+shopName+"'";
			//System.out.println(sql);
			int res = stmt.executeUpdate(sql);
			if(res != 0) 
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBUtils.close(null, stmt, conn);
		}
		return false;
	}
	//更新商家的数据库（模拟）
	public boolean updatePointsShop(String userName, String shopName,int wantTransfer_points){
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		try {
			stmt = conn.createStatement();
			String sql="update userpoint set userpoint=userpoint-'"+wantTransfer_points+"' where userName='"+userName+"' and shopName='"+shopName+"'";
			//System.out.println(sql);
			int res = stmt.executeUpdate(sql);
			if(res != 0) 
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBUtils.close(null, stmt, conn);
		}
		return false;
	}
	
	//在transfer表中记录交易
	public int insertTransfer(Transfer transfer){
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		int count =0;
		conn=DBUtils.getConnection();
		try {
			stmt=conn.createStatement();
			String sql="insert into transfer(pointID,status,point) values('"+transfer.getPointID()+"','"+transfer.getStatus()+"','"+transfer.getPoint()+"')";
			count=stmt.executeUpdate(sql);//执行插入语句，并返回插入数据的个数	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBUtils.close(null, stmt, conn);
		}
		return count;		
	}
	
	//查询交易的pointID
	//查询用户在平台数据库中的积分
		public int queryPointID(String userName, String shopName){
			Connection conn=DBUtils.getConnection();
			Statement stmt=null;
			int pointID = -1;
			ResultSet rs = null;
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select pointID from point where userName='"+userName+"'and shopName='"+shopName+"'");
				if(rs.next()) {
					//System.out.println(rs.getString("userPoint"));
					pointID = rs.getInt("pointID");
					rs.close();
					return pointID;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				DBUtils.close(rs, stmt, conn);
			}
			return pointID;
		}
}
