package com.bit.bonusPointsExchange.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.utils.DBUtils;

//商家修改信息
public class ShopChangeInfoManger {
	//更新平台的数据库
	public boolean updateShopInfo(Shop shop, String oldShopName){
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		try {
			stmt = conn.createStatement();
			String sql="update shop set shopName='"+shop.getShopName()+"',imageURL='"+shop.getImgUrl()+"',email='"+shop.getEmail()+"',shopDec='"+shop.getShopDec()+"',telephone='"+shop.getTelephone()+"' where shopName='"+oldShopName+"'";
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
}
