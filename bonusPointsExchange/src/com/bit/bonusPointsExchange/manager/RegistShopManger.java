package com.bit.bonusPointsExchange.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.utils.DBUtils;

//商家注册数据库相关操作类
public class RegistShopManger {
	//查询商家用户名是否存在
	public boolean isShopNameExit(Shop shop){
		String shopName = shop.getShopName();//获取商家的名称
		System.out.println(shopName);
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select *from shop where shopName='"+shopName+"'");
			if(rs.next()) {
				//System.out.println(rs.getString("userPoint"));
				rs.close();
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, stmt, conn);
		}
		return true;
	}
	//新注册用户插入数据到数据库
	public int insertShop(Shop shop){
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		ResultSet rs = null;
		int count =0;//注册是否成功标志， 0为失败
		conn=DBUtils.getConnection();
		try {
			stmt=conn.createStatement();
			String sql="insert into shop(shopName,imageURL,password,email,number,shopProperty,shopDec,telephone) values('"+shop.getShopName()+"','"+shop.getImgUrl()+"','"+shop.getPassword()+"','"+shop.getEmail()+"','"+shop.getNumber()+"','"+shop.getShopProperty()+"','"+shop.getShopDec()+"','"+shop.getTelephone()+"')";
			count=stmt.executeUpdate(sql);//执行插入语句，并返回插入数据的个数	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, stmt, conn);
		}
		return count;		
	}
}
