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
import com.bit.bonusPointsExchange.fabric.InvokeChaincode;
import com.bit.bonusPointsExchange.manager.BindShopManager;
import com.bit.bonusPointsExchange.manager.PlatformPointToUserManger;
import com.bit.bonusPointsExchange.manager.UserPointToplatfromManger;
import com.bit.bonusPointsExchange.utils.HttpUtils;

public class PlatformToUserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		BindShopManager bindShopManger = new BindShopManager();
		// 通过session获取平台用户名
		String userName = (String) request.getSession().getAttribute("userName");
		// 获取用户在平台积分数量
		int points = Integer.parseInt(request.getParameter("platformPoints"));
		// 获取用户想要转移的积分数量,并且转换整数
		String transfer_points = (String) request.getParameter("transfer_points");
		int wantTransfer_points = Integer.parseInt(transfer_points);
		// 获取用户在商家注册的用户名
		String userNameAtShop = (String) request.getParameter("userName2");
		// 获取用户所在商家
		String shopName = request.getParameter("shop2");
		// 更新数据库
		PlatformPointToUserManger dbManger = new PlatformPointToUserManger();
		if (points == 0 || wantTransfer_points == 0 || wantTransfer_points > points) {
			request.setAttribute("pointToPshopRes", "N");
			request.setAttribute("userPoints", points);
		} else {

			Point point = bindShopManger.findBindedShop(userName, shopName);
			String str = "{'jsonrpc': '2.0','method': 'invoke','params': {'type': 1,'chaincodeID':{'name':'6ef62a4eb59238a25fedcb50cc873f90f9d3fe0053888620f9011e25947fa85c9d411ac7193572732cf11987f2f8423d9a77d18332cf6f7dc4c2fa4821136099'},'ctorMsg': {'function':'transfer','args':['"
					+ point.getPointID() + "','0','" + wantTransfer_points + "']}},'id': 3}";
			HttpUtils httputils = new HttpUtils();
			String conRes = httputils.getHttpConnection();
//			if(conRes.equals("开启请求连接成功")){
//				GetJsonStr result = httputils.postJsonToBlockChain(str);
//				if(result.getResult().getStatus().equals("OK")){
			String[] args = { point.getPointID() + "", "0", wantTransfer_points + "" };
			String filePath = "/Users/zhangyulong/eclipse-workspace/bonusPointsExchange/network-config.json";
			try {
				String status = InvokeChaincode.invokeChaincode("transfer", args, filePath);
				System.out.println("==platformtouser==status=======" + status);
				if (status.equals("ok")) {
					// 执行相关操作
					// 1.平台数据库中减少积分
					boolean res1 = dbManger.updatePointsPlatform(userName, shopName, wantTransfer_points);
					// 2.商家数据库中增加积分
					boolean res2 = dbManger.updatePointsShop(userNameAtShop, shopName, wantTransfer_points);
					UserPointToplatfromManger pointToplatfromManger = new UserPointToplatfromManger();
					// 查询pointID
					int pointID = dbManger.queryPointID(userName, shopName);
					// 在transfer表中记录这笔交易
					Transfer transfer = new Transfer(pointID, 1, wantTransfer_points);
					int res3 = dbManger.insertTransfer(transfer);
					if (res1 && res2 && (0 != res3)) //// 这里是逻辑有问题的，如果两个数据库更新失败一个，需要将数据库回滚到没有更新的状态，要分别去判断，决定回滚哪个数据库
					{
						int userPoints1 = pointToplatfromManger.ownPointsAtPlatform(userName, shopName);// 用户在平台的积分
						int shopPoints1 = pointToplatfromManger.ownPoints(userNameAtShop, shopName);// 用户在商家的积分
						String userPoints = String.valueOf(userPoints1);
						String shopPoints = String.valueOf(shopPoints1);
						request.setAttribute("userPoints", userPoints);
						request.setAttribute("shopPoints", shopPoints);
						request.setAttribute("pointToPshopRes", "Y");
					} else {
						request.setAttribute("userPoints", point);
						request.setAttribute("pointTranRes", "N");

					}

				} else {
					request.setAttribute("userPoints", point);
					request.setAttribute("pointTranRes", "N");
				}
			} catch (RuntimeException e) {
				request.setAttribute("userPoints", point);
				request.setAttribute("pointTranRes", "连接blockchain失败，请检查网络");
			} catch (Exception e) {
				request.setAttribute("userPoints", point);
				request.setAttribute("pointTranRes", "连接blockchain失败，请检查网络");
			}

//				}else{
//					request.setAttribute("userPoints", point);
//					request.setAttribute("pointTranRes", "N"); 
//				}
//			}else{						
//				request.setAttribute("userPoints", point);
//				request.setAttribute("pointTranRes", "连接blockchain失败，请检查网络");
//			}

		}
		// 查询用户绑定的商家信息，显示在select中
		List<ShowBindInfo> list = bindShopManger.bingShopInfo(userName);
		request.setAttribute("bindInfo", list);
		request.setAttribute("index", "4");
		request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);

	}

}
