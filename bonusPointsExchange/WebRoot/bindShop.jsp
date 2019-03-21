<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
    String shopName = request.getParameter("shopName"); 
    if(null != shopName) 
   		shopName = new String(shopName.getBytes("ISO-8859-1"),"utf-8"); 
   	else 
   		shopName = (String)request.getAttribute("shopName");	
%>



<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>绑定商家</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/personal.css">
<link href="css/footer.css" rel="stylesheet" type="text/css">
</head>

<body>
<!--这是top-->
	<%@ include file="header.jsp" %>
<!--这是main_page-->
<div class="main">
  <div class="bind-form">
    <p class="title">绑定商家&nbsp;&nbsp;<span class="title1">BIND SHOP</span></p>
    <form action="/bonusPointsExchange/BindShopServlet" method="post" onsubmit="return checkForm();">
      <table cellspacing="2">
        <tr>
          <td>商家名：</td>
          <td><input name="ShopName" type="text" id="ShopName" class="no-border" value="<%=shopName %>" maxlength="20" readonly ></td>
        </tr>
        <tr>
          <td>用户名：</td>
          <td><input name="userName" type="text" id="userName" maxlength="20"></td>
        </tr>
        <tr>
          <td>密&nbsp;码：</td>
          <td><input name="password" type="password" id="password" maxlength="20"></td>
        </tr>
        <tr>
          <td class="mid"><input name="submit" type="submit" class="submitBtn" id="submit" value="提交"></td>
          <td class="mid"><input name="reset" type="reset" class="submitBtn" id="reset" value="重置"></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<!--这是bottom-->
	<%@ include file="footer.jsp" %>
</body>


<script type="text/javascript">
function checkForm() {
	// alert("ada");
	var userName = document.getElementById("userName").value;
	if (userName == "") {
		alert("用户名不能为空！");
		return false;
	}
	
	var password = document.getElementById("password").value;
	if (password == "") {
		alert("密码不能为空！");
		return false;
	}
}	
</script>
</html>


