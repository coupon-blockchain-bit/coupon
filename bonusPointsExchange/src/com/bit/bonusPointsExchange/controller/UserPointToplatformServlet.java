package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Point;
import com.bit.bonusPointsExchange.bean.ShowBindInfo;
import com.bit.bonusPointsExchange.bean.Transfer;
import com.bit.bonusPointsExchange.json.GetJsonStr;
import com.bit.bonusPointsExchange.manager.BindShopManager;
import com.bit.bonusPointsExchange.manager.UserPointToplatfromManger;
import com.bit.bonusPointsExchange.utils.HttpUtils;

public class UserPointToplatformServlet extends HttpServlet {

	//用于处理用户选择转移积分到平台
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//有个前提，假设用户在商家注册的用户名和用户在平台注册的用户名相同，因为目前无法知道商家的数据库
		//解决方案：需要用户其在商家的名称和密码（待解决）
		//完成转移积分数据库相关操作
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		BindShopManager bindShopManger = new BindShopManager();
		//通过session获取平台用户名
		//这里还应该获取用户在商家的用户名和账号，进行判断，待解决，（我们这里假设平台用户名和商家用户名相同，当然这不科学F）
		String userName =(String)request.getSession().getAttribute("userName");	
		int points = Integer.parseInt(request.getParameter("points"));//获取用户积分数量
		String userNameAtShop = (String)request.getParameter("userName");//获取用户在商家注册的用户名
		//System.out.println(userNameAtShop);
		//获取用户想要转移的积分数量,并且转换整数
		String transfer_points = (String)request.getParameter("transfer_points");
		int wantTransfer_points = Integer.parseInt(transfer_points);
		//获取用户选择的商家，用户选择的商家必须在我们平台进行过注册（待解决）
		String shopName = request.getParameter("shop");		
		//更新数据库
		UserPointToplatfromManger dbManger = new UserPointToplatfromManger();
		if(points == 0 || wantTransfer_points == 0 || wantTransfer_points > points) {
			request.setAttribute("pointTranRes", "N"); 
			request.setAttribute("shopPoints", points);
		}
		else {
			Point point = bindShopManger.findBindedShop(userName, shopName);
			String str="{'jsonrpc': '2.0','method': 'invoke','params': {'type': 1,'chaincodeID':{'name':'6ef62a4eb59238a25fedcb50cc873f90f9d3fe0053888620f9011e25947fa85c9d411ac7193572732cf11987f2f8423d9a77d18332cf6f7dc4c2fa4821136099'},'ctorMsg': {'function':'transfer','args':['"+point.getPointID()+"','1','"+wantTransfer_points+"']}},'id': 3}";
			HttpUtils httputils = new HttpUtils();
			String conRes = httputils.getHttpConnection();
			if(conRes.equals("开启请求连接成功")){
				GetJsonStr result = httputils.postJsonToBlockChain(str);
				if(result.getResult().getStatus().equals("OK")){
				
					//执行相关操作
					//1.平台数据库中增加积分
					boolean res1 = dbManger.updatePointsPlatform(userName, shopName, wantTransfer_points);
					//查询pointID
					int pointID = dbManger.queryPointID(userName, shopName);
					//2.商家数据库中减少积分
					boolean res2 = dbManger.updatePointsShop(userNameAtShop, shopName, wantTransfer_points);
					//在transfer表中记录这笔交易
					Transfer transfer = new Transfer(pointID, 0, wantTransfer_points);
					int res3 = dbManger.insertTransfer(transfer);
				
					if (res1 && res2 && (0 != res3)) {//这里是逻辑有问题的，如果两个数据库更新失败一个，需要将数据库回滚到没有更新的状态，暂时不解决
						int userPoints1 =  dbManger.ownPointsAtPlatform(userName, shopName);//用户在平台的积分
						int shopPoints1 = dbManger.ownPoints(userNameAtShop, shopName);//用户在商家的积分
						String userPoints = String.valueOf(userPoints1);
						String shopPoints = String.valueOf(shopPoints1);
						request.setAttribute("userPoints", userPoints);//弹框提示
						request.setAttribute("shopPoints", shopPoints);//弹框提示
						request.setAttribute("pointTranRes", "Y"); 
					}
					else {
						request.setAttribute("shopPoints", point);
						request.setAttribute("pointTranRes", "N"); 
						
					}
				}else{
					request.setAttribute("shopPoints", point);
					request.setAttribute("pointTranRes", "N"); 
				}
			}else{						
				request.setAttribute("shopPoints", point);
				request.setAttribute("pointTranRes", "连接blockchain失败，请检查网络");
			}
			
		}
		
		//查询用户绑定的商家信息，显示在select中
		
		List< ShowBindInfo> list = bindShopManger.bingShopInfo(userName);
		request.setAttribute("bindInfo", list);
		request.setAttribute("index", "3");
		request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
	}
}
