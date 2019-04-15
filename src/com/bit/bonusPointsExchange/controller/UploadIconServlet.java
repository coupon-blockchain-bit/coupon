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
			//�ϴ�����
			SmartUpload smart = new SmartUpload();
			smart.initialize(this.getServletConfig(),request,response); // ��ʼ���ϴ�����
			smart.upload(); // �ϴ�׼��
			IpTimeStamp its = new IpTimeStamp(request.getRemoteAddr());//��ȡ�����������ip
			String ext = smart.getFiles().getFile(0).getFileExt();//ȡ���ļ���׺
			String fileName = its.getIPTimeRand() + "." + ext;//ƴ���µ�����
			String filePath = this.getServletContext().getRealPath("/");
			//filePath.replaceAll("\\\\", "\\\\\\\\");
			//fuck��·���洢��Mysql���ݿ��У�·���е�б����ʧ����֪��ΪʲôreplaceAll���������ã�ֻ���Լ�ƴ��·����
			String str =  "images\\\\shopLogo" + "\\\\" + fileName;
			String []str1 =  filePath.split("\\\\");
			String str2 = "";
			for(int i = 0 ; i < str1.length; i++) {
				str2 += str1[i] + "\\\\";
			}
			//System.out.println("str2="+str2);	
			filePath = str2 + str;//ƴ�ճɵ��ļ�·��		
			//System.out.println("fielPath="+filePath);//��ӡ�ļ��洢·��
			String shopName = (String)request.getSession().getAttribute("shopName");// ��ȡ��¼���̼ҵ�����
			if (fileName.matches("^\\w+\\.(jpg|gif|png|bmp)$")) { //����ļ������Ƿ����Ҫ��
				//�ϴ����ļ�����
				smart.getFiles()
						.getFile(0)
						.saveAs(filePath);// �ļ�����
				//���û��Ѿ��ϴ���ͷ����Ҫ����ǰ�ϴ���ͷ��ɾ������ֹ�û�����ϴ���ռ��Ӳ����Դ
				String oldFileName = UploadHeadIconManger.queryImgURL(shopName);//ԭ�����ݿ��е�ͼƬ�����֣�ƴ�ճɵ�ַ����Ҫ�ж����Ƿ��Ĭ�ϵ�ַһ��
				String imageURL = str2 + "images\\\\shopLogo" + "\\\\" + oldFileName;//��ͷ���ļ�·��
				//��һ������ɾ��������ɾ��,Ĭ��ͼ��ΪdefaultIcon.jpg
				String str3 = str2 + "images\\\\shopLogo" + "\\\\" + "defaultIcon.jpg";//Ĭ��ͷ��ĵ�ַ
				if(!(str3.equals(imageURL))) {
					// ɾ���ļ����е�ͼƬ
					File imgFile = new File(imageURL);
					//System.out.println(path);
					if (imgFile.exists()) {
						imgFile.delete();
					}
				}
				//�������ݿ�,���ļ��洢��·�����浽���ݿ���
				boolean ret = UploadHeadIconManger.updateImgURL(fileName, shopName);//�������ݿ�
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
			request.setAttribute("email", shop.getEmail());//���ݸ�personal_shopҳ�������ʾ
			request.setAttribute("imageURL", shop.getImgUrl());//���ݸ�personal_shopҳ�������ʾ
			request.setAttribute("telephone", shop.getTelephone());//���ݸ�personal_shopҳ�������ʾ
			request.setAttribute("shopDec", shop.getShopDec());//���ݸ�personal_shopҳ�������ʾ
			request.getRequestDispatcher("personal_shop.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
