package com.bit.bonusPointsExchange.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bit.bonusPointsExchange.bean.Point;
import com.bit.bonusPointsExchange.bean.User;
import com.bit.bonusPointsExchange.utils.DBUtils;

public class PointManager {
	
	private Connection conn = null;
	private Statement stmt = null;
	private String sql = null; 
	private ResultSet rs = null;
	private User user = null;
	
	public int updatePoint(Point points){
		int result =0;
		
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();			
			sql= "update bonusPointsExchange.point set platformPoint='"+points.getPlatformPoint()+"' where userName='"+points.getUserName()+"' and shopName='"+points.getShopName()+"'";
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(null, stmt, conn);
		}

		return result;
		
	}
	
	public int findPointByUserName(Point points){
		int platfromPoint=0;
		
		conn = DBUtils.getConnection();
		try {
			stmt = conn.createStatement();
			sql="select platformPoint from bonusPointsExchange.point where userName='"+points.getUserName()+"' and shopName='"+points.getShopName()+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				platfromPoint = rs.getInt("platformPoint");
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, stmt, conn);
		}		
		return platfromPoint;
	}
	
	

}
