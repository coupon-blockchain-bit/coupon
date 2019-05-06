package com.bit.bonusPointsExchange.service;

import com.bit.bonusPointsExchange.bean.Order;
import com.bit.bonusPointsExchange.bean.Point;
import com.bit.bonusPointsExchange.fabric.InvokeChaincode;
import com.bit.bonusPointsExchange.manager.BindShopManager;
import com.bit.bonusPointsExchange.manager.OrderManager;
import com.bit.bonusPointsExchange.manager.PointManager;
import com.bit.bonusPointsExchange.manager.UserPointToplatfromManger;
import com.bit.bonusPointsExchange.utils.TimeUtils;

public class OrderService {
	private int orderStatus_unfinished_valid = 0;// 0代表未完成且未超过有效期（有效）
	private int orderStatus_finished = 1;// 1代表完成
	private int orderStatus_cancel_invalid = 2;// 2代表撤销或超过有效期（无效)
	OrderManager om = new OrderManager();
	PointManager pm = new PointManager();
	BindShopManager bsm = new BindShopManager();
	UserPointToplatfromManger uptm = new UserPointToplatfromManger();
	BindShopManager bindShopManger = new BindShopManager();

	public String finishOrder(String exchangeUserName, int orderID) {
		// String exchangeUserName = (String)
		// request.getSession().getAttribute("userName");
		String finishOrderRes = null;
		Point pointsFromRelease = new Point();// 订单发布者的积分
		Point pointsFromReleaseWanted = new Point();// 订单发布者想要交换的积分
		Point pointsFromExchange = new Point();// 发起交易者的积分
		Point pointsFromExchangeWanted = new Point();// 发起交易者想要交换的积分

		Order orderInfo = om.findOrderByID(orderID);

		boolean isBindWantedShop = bsm.isBindThisShop(exchangeUserName, orderInfo.getShopName());// 验证发起交易者是否已绑定该商家
		boolean isBindShopName = bsm.isBindThisShop(exchangeUserName, orderInfo.getWantedShop());// 验证发起交易者是否已绑定该商家

		if (!isBindWantedShop) {// 验证发起交易者是否已绑定该商家
			finishOrderRes = "您未绑定目标商家！";
			return finishOrderRes;
		}
		if (!isBindShopName) {// 验证发起交易者是否已绑定该商家
			finishOrderRes = "您未绑定商家！";
			return finishOrderRes;
		}

		int pointsAtPlatByExchanger = uptm.ownPointsAtPlatform(exchangeUserName, orderInfo.getWantedShop());// 查询交易发起者在商家的平台积分
		if (pointsAtPlatByExchanger < orderInfo.getWantedPoint()) {// 如果交易发起者在商家的平台积分小于订单发起者所期望商家的平台积分，返回交易页面显示交易发起者的积分不足
			finishOrderRes = "您在商家的积分不够！";
			return finishOrderRes;
		}

		Point point1 = bindShopManger.findBindedShop(exchangeUserName, orderInfo.getWantedShop());// 交易发起者在商家中的积分
		Point point2 = bindShopManger.findBindedShop(exchangeUserName, orderInfo.getShopName());// 交易发起者在期望商家中的积分
		Point point3 = bindShopManger.findBindedShop(orderInfo.getUserName(), orderInfo.getShopName());// 订单发布者在商家中的积分
		Point point4 = bindShopManger.findBindedShop(orderInfo.getUserName(), orderInfo.getWantedShop());// 订单发布者在期望商家中的积分

		String str1 = "{'jsonrpc': '2.0','method': 'invoke','params': {'type': 1,'chaincodeID':{'name':'6ef62a4eb59238a25fedcb50cc873f90f9d3fe0053888620f9011e25947fa85c9d411ac7193572732cf11987f2f8423d9a77d18332cf6f7dc4c2fa4821136099'},'ctorMsg': {'function':'invoke','args':['"
				+ String.valueOf(point1.getPointID()) + "','" + String.valueOf(point2.getPointID()) + "','"
				+ String.valueOf(orderInfo.getWantedPoint()) + "','" + String.valueOf(orderInfo.getPoint())
				+ "']}},'id': 3}";
		String str2 = "{'jsonrpc': '2.0','method': 'invoke','params': {'type': 1,'chaincodeID':{'name':'6ef62a4eb59238a25fedcb50cc873f90f9d3fe0053888620f9011e25947fa85c9d411ac7193572732cf11987f2f8423d9a77d18332cf6f7dc4c2fa4821136099'},'ctorMsg': {'function':'invoke','args':['"
				+ String.valueOf(point3.getPointID()) + "','" + String.valueOf(point4.getPointID()) + "','"
				+ String.valueOf(orderInfo.getPoint()) + "','" + String.valueOf(orderInfo.getWantedPoint())
				+ "']}},'id': 3}";
		System.out.println(str1);
		System.out.println(str2);
		String[] args1 = { String.valueOf(point1.getPointID()), String.valueOf(point2.getPointID()),
				String.valueOf(orderInfo.getWantedPoint()), String.valueOf(orderInfo.getPoint()) };
		String[] args2 = { String.valueOf(point3.getPointID()), String.valueOf(point4.getPointID()),
				String.valueOf(orderInfo.getPoint()), String.valueOf(orderInfo.getWantedPoint()) };
		String filePath = "/Users/zhangyulong/eclipse-workspace/bonusPointsExchange/network-config.json";
//		HttpUtils httputils = new HttpUtils();
//		String conRes = httputils.getHttpConnection();
		try {
			String status = InvokeChaincode.invokeChaincode("invoke", args1, filePath);
			if (status.equals("ok")) {
				String status1 = InvokeChaincode.invokeChaincode("invoke", args1, filePath);
				if (status1.equals("ok")) {
					/* 订单发布者的积分 */
					pointsFromRelease.setUserName(orderInfo.getUserName());
					pointsFromRelease.setShopName(orderInfo.getShopName());
					pointsFromRelease
							.setPlatformPoint(pm.findPointByUserName(pointsFromRelease) - orderInfo.getPoint());

					/* /订单发布者想要交换的积分 */
					pointsFromReleaseWanted.setUserName(orderInfo.getUserName());
					pointsFromReleaseWanted.setShopName(orderInfo.getWantedShop());
					pointsFromReleaseWanted.setPlatformPoint(
							pm.findPointByUserName(pointsFromReleaseWanted) + orderInfo.getWantedPoint());

					/* 发起交易者的积分 */
					pointsFromExchange.setUserName(exchangeUserName);
					pointsFromExchange.setShopName(orderInfo.getWantedShop());
					pointsFromExchange
							.setPlatformPoint(pm.findPointByUserName(pointsFromExchange) - orderInfo.getWantedPoint());

					/* 发起交易者想要交换的积分 */
					pointsFromExchangeWanted.setUserName(exchangeUserName);
					pointsFromExchangeWanted.setShopName(orderInfo.getShopName());
					pointsFromExchangeWanted
							.setPlatformPoint(pm.findPointByUserName(pointsFromExchangeWanted) + orderInfo.getPoint());

					int pointRes1 = pm.updatePoint(pointsFromRelease);
					int pointRes2 = pm.updatePoint(pointsFromReleaseWanted);
					int pointRes3 = pm.updatePoint(pointsFromExchange);
					int pointRes4 = pm.updatePoint(pointsFromExchangeWanted);

					String orderDate = TimeUtils.getNowTime();
					int orderStatus = orderStatus_finished;// 订单完成

					Order order = new Order();
					order.setOrderID(orderID);
					order.setExchangeUserName(exchangeUserName);
					order.setOrderDate(orderDate);
					order.setOrderStatus(orderStatus);
					int orderRes = om.finshOrder(order);// 完成订单

					if (orderRes > 0 && pointRes1 > 0 && pointRes2 > 0 && pointRes3 > 0 && pointRes4 > 0) {
						finishOrderRes = "积分兑换成功！";
						return finishOrderRes;

					}
				} else {
					finishOrderRes = "积分兑换失败！";
					return finishOrderRes;
				}
			} else {
				finishOrderRes = "积分兑换失败！";
				return finishOrderRes;
			}
		} catch (RuntimeException e) {
			finishOrderRes = "连接blockchain失败，请检查网络！";
			return finishOrderRes;
		} catch (Exception e) {
			finishOrderRes = "连接blockchain失败，请检查网络！";
			return finishOrderRes;
		}
//		if (conRes.equals("开启请求连接成功")) {
//			GetJsonStr result1 = httputils.postJsonToBlockChain(str1);
//			if (result1.getResult().getStatus().equals("OK")) {
//				HttpUtils httputils2 = new HttpUtils();
//				httputils2.getHttpConnection();
//				GetJsonStr result2 = httputils2.postJsonToBlockChain(str2);
//				if (result2.getResult().getStatus().equals("OK")) {
//					/* 订单发布者的积分 */
//					pointsFromRelease.setUserName(orderInfo.getUserName());
//					pointsFromRelease.setShopName(orderInfo.getShopName());
//					pointsFromRelease
//							.setPlatformPoint(pm.findPointByUserName(pointsFromRelease) - orderInfo.getPoint());
//
//					/* /订单发布者想要交换的积分 */
//					pointsFromReleaseWanted.setUserName(orderInfo.getUserName());
//					pointsFromReleaseWanted.setShopName(orderInfo.getWantedShop());
//					pointsFromReleaseWanted.setPlatformPoint(
//							pm.findPointByUserName(pointsFromReleaseWanted) + orderInfo.getWantedPoint());
//
//					/* 发起交易者的积分 */
//					pointsFromExchange.setUserName(exchangeUserName);
//					pointsFromExchange.setShopName(orderInfo.getWantedShop());
//					pointsFromExchange
//							.setPlatformPoint(pm.findPointByUserName(pointsFromExchange) - orderInfo.getWantedPoint());
//
//					/* 发起交易者想要交换的积分 */
//					pointsFromExchangeWanted.setUserName(exchangeUserName);
//					pointsFromExchangeWanted.setShopName(orderInfo.getShopName());
//					pointsFromExchangeWanted
//							.setPlatformPoint(pm.findPointByUserName(pointsFromExchangeWanted) + orderInfo.getPoint());
//
//					int pointRes1 = pm.updatePoint(pointsFromRelease);
//					int pointRes2 = pm.updatePoint(pointsFromReleaseWanted);
//					int pointRes3 = pm.updatePoint(pointsFromExchange);
//					int pointRes4 = pm.updatePoint(pointsFromExchangeWanted);
//
//					String orderDate = TimeUtils.getNowTime();
//					int orderStatus = orderStatus_finished;// 订单完成
//
//					Order order = new Order();
//					order.setOrderID(orderID);
//					order.setExchangeUserName(exchangeUserName);
//					order.setOrderDate(orderDate);
//					order.setOrderStatus(orderStatus);
//					int orderRes = om.finshOrder(order);// 完成订单
//
//					if (orderRes > 0 && pointRes1 > 0 && pointRes2 > 0 && pointRes3 > 0 && pointRes4 > 0) {
//						finishOrderRes = "积分兑换成功！";
//						return finishOrderRes;
//
//					}
//				}
//
//			} else {
//				finishOrderRes = "积分兑换失败！";
//				return finishOrderRes;
//			}
//		} else {
//			finishOrderRes = "连接blockchain失败，请检查网络！";
//			return finishOrderRes;
//		}

		return finishOrderRes;
	}

}
