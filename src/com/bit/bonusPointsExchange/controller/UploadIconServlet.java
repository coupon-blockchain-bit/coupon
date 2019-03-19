package com.bit.bonusPointsExchange.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lxh.smart.SmartUpload;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.manager.LoginShopManger;
import com.bit.bonusPointsExchange.manager.UploadHeadIconManger;
import com.bit.bonusPointsExchange.utils.IpTimeStamp;

public class UploadIconServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		try {
			//上传操作
			SmartUpload smart = new SmartUpload();
			smart.initialize(this.getServletConfig(),request,response); // 初始化上传操作
			smart.upload(); // 上传准备
			IpTimeStamp its = new IpTimeStamp(request.getRemoteAddr());//获取请求的主机的ip
			String ext = smart.getFiles().getFile(0).getFileExt();//取得文件后缀
			String fileName = its.getIPTimeRand() + "." + ext;//拼凑新的名称
			String filePath = this.getServletContext().getRealPath("/");
			//filePath.replaceAll("\\\\", "\\\\\\\\");
			//fuck，路径存储到Mysql数据库中，路径中的斜杠消失，不知道为什么replaceAll函数不管用，只有自己拼凑路径了
			String str =  "images\\\\shopLogo" + "\\\\" + fileName;
			String []str1 =  filePath.split("\\\\");
			String str2 = "";
			for(int i = 0 ; i < str1.length; i++) {
				str2 += str1[i] + "\\\\";
			}
			//System.out.println("str2="+str2);	
			filePath = str2 + str;//拼凑成的文件路径		
			//System.out.println("fielPath="+filePath);//打印文件存储路径
			String shopName = (String)request.getSession().getAttribute("shopName");// 获取登录的商家的名称
			if (fileName.matches("^\\w+\\.(jpg|gif|png|bmp)$")) { //检查文件类型是否符合要求
				//上传到文件夹中
				smart.getFiles()
						.getFile(0)
						.saveAs(filePath);// 文件保存
				//若用户已经上传过头像，需要将先前上传的头像删除，防止用户多次上传，占用硬盘资源
				String oldFileName = UploadHeadIconManger.queryImgURL(shopName);//原先数据库中的图片的名字，拼凑成地址，还要判断其是否和默认地址一样
				String imageURL = str2 + "images\\\\shopLogo" + "\\\\" + oldFileName;//旧头像文件路径
				//若一样则不能删除，否则删除,默认图标为defaultIcon.jpg
				String str3 = str2 + "images\\\\shopLogo" + "\\\\" + "defaultIcon.jpg";//默认头像的地址
				if(!(str3.equals(imageURL))) {
					// 删除文件夹中的图片
					File imgFile = new File(imageURL);
					//System.out.println(path);
					if (imgFile.exists()) {
						imgFile.delete();
					}
				}
				//更新数据库,将文件存储的路径保存到数据库中
				boolean ret = UploadHeadIconManger.updateImgURL(fileName, shopName);//更新数据库
				if (ret == false) {
					request.setAttribute("uploadRes", "N");
				}
				else {
					request.setAttribute("uploadRes", "Y");
				}
			} else {
				request.setAttribute("uploadTypeErr", "N");
			}
			
			LoginShopManger loginShopManger = new LoginShopManger();
			Shop shop = loginShopManger.getShopInfo(shopName);
			request.setAttribute("email", shop.getEmail());//传递给personal_shop页面进行显示
			request.setAttribute("imageURL", shop.getImgUrl());//传递给personal_shop页面进行显示
			request.setAttribute("telephone", shop.getTelephone());//传递给personal_shop页面进行显示
			request.setAttribute("shopDec", shop.getShopDec());//传递给personal_shop页面进行显示
			request.getRequestDispatcher("personal_shop.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
