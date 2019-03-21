<%@page import="com.bit.bonusPointsExchange.bean.ShowBindInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
	String bindRes = (String)request.getAttribute("bindRes"); 
	if(bindRes == "Y") {
%>
	<script type="text/javascript" language="javascript">
		alert("成功绑定商家！");                                    // 弹出错误信息
	</script>		
<% } else if(bindRes == "N") {%>
	<script type="text/javascript" language="javascript">
		alert("绑定商家失败，您可能未在该商家注册或您已绑定过该商家！");                                    // 弹出错误信息
	</script>
<% }else if(bindRes == "连接blockchain失败，请检查网络") {%>
	<script type="text/javascript" language="javascript">
		alert("连接blockchain失败，请检查网络");                                    // 弹出错误信息
	</script>		
<% }%>


<%
	String userChangeResult = (String)request.getAttribute("userChangeResult");  //获取修改用户信息是否成功
	if(userChangeResult == "Y") {
%>
	<script type="text/javascript" language="javascript">
		alert("修改信息成功！");                                    // 弹出错误信息
	</script>	
<% } else if(userChangeResult == "N") {%>
	<script type="text/javascript" language="javascript">
		alert("修改信息失败！");                                    
	</script>	
<% }%>

<%
	String pointTranRes = (String)request.getAttribute("pointTranRes"); 
	if(pointTranRes == "Y") {
%>
	<script type="text/javascript" language="javascript">
		alert("成功将积分从商家转移到平台！您在平台拥有"+<%=request.getAttribute("userPoints")%>+"积分，在商家拥有"+<%=request.getAttribute("shopPoints")%>+"积分" );                                    // 弹出错误信息
	</script>	
<% } else if(pointTranRes == "N") {%>
	<script type="text/javascript" language="javascript">
		alert("转移失败！");                                    
	</script>	
<% }else if(pointTranRes == "连接blockchain失败，请检查网络") {%>
	<script type="text/javascript" language="javascript">
		alert("连接blockchain失败，请检查网络！");                                    
	</script>	
<% }%>


<%
	String pointToPshopRes = (String)request.getAttribute("pointToPshopRes"); 
	if(pointToPshopRes == "Y") {
%>
	<script type="text/javascript" language="javascript">
		alert("成功将积分从平台转移到商家！您在平台拥有"+<%=request.getAttribute("userPoints")%>+"积分，在商家拥有"+<%=request.getAttribute("shopPoints")%>+"积分" );                                    // 弹出错误信息
	</script>	
<% } else if(pointToPshopRes == "N") {%>
	<script type="text/javascript" language="javascript">
		alert("转移失败！");                                    
	</script>	
<% }%>

<%
	String userChangePasswd = (String)request.getAttribute("userChangePasswd");  //获取修改用户密码是否成功
	
	if(userChangePasswd == "N") {%>
	<script type="text/javascript" language="javascript">
		alert("修改密码失败！您输入的旧密码不正确");                                    
	</script>	
<% }else if(userChangePasswd == "errorPasswd"){%>
	<script type="text/javascript" language="javascript">
		alert("您输入的旧密码不正确，请重新输入");
	</script>
<% } %>
<!-- 显示查询到的绑定信息 -->
<% 
	List<ShowBindInfo> list = (List<ShowBindInfo>)request.getAttribute("bindInfo");
%>
 


<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>个人中心</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
<link href="css/footer.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="css/personal.css">
</head>
<body>
<!--这是top-->
	<%@ include file="header.jsp" %>
<!--这是main_page-->
<div class="personal">
  <div class="span7" id="Accordion1">
    <div  class="nav">
      <h3>个人信息</h3>
      <div class="index">
        <p><a href="javascript:showDiv(1)">修改信息</a></p>
        <p><a href="javascript:showDiv(2)">修改密码</a></p>
      </div>
      <h3>积分转移</h3>
      <div class="index">
        <p><a href="javascript:showDiv(3)">积分转移到平台</a></p>
        <p><a href="javascript:showDiv(4)">积分转移到商家</a></p>
      </div>
      <h3>绑定商家</h3>
      <div class="index">
        <p><a href="javascript:showDiv(5)">绑定新商家</a></p>
        <p><a href="javascript:showDiv(6)">显示已绑定商家</a></p>
      </div>
    </div>
  </div>
  <div class="span8">
    <div id="div1">
      <p class="title">个人资料 <span class="title1">USER INFOMATION</span></p>
      <form action="/bonusPointsExchange/actionServlet" method="post">
        <table>
        <!--  <tr>
            <td>账&nbsp;号：</td>
            <td><input name="userName" type="text" id="userName" maxlength="40"></td>
          </tr>  -->
          <tr>
            <td>邮&nbsp;箱：</td>
            <td><input name="email" type="text" id="email" maxlength="40" value=<%=request.getAttribute("email") %> readonly></td>
          </tr>
          <tr>
            <td>姓&nbsp;名：</td>
            <td><input name="fullName" type="text" id="name" maxlength="40" value=<%=request.getAttribute("fullName") %>></td>
          </tr>
          <tr>
            <td>电&nbsp;话：</td>
            <td><input name="phone" type="text" id="phone" maxlength="40" value=<%=request.getAttribute("phone") %>></td>
          </tr>
          <tr>
            <td colspan="2" class="mid"><input name="submit" type="submit" class="submitBtn" id="submit" value="提交"></td>
              </td>
          </tr>
        </table>
        <input type="hidden" name="actionCode" value="user" >
  		<input type="hidden" name="methodCode" value="alter_user_info">
      </form>
    </div>
    <div id="div2">
      <p class="title">修改密码 <span class="title1">USER　PASSWORD</span></p>
      <form action="/bonusPointsExchange/actionServlet" onsubmit="return checkInputPasswd();">
        <table>
        <tr>
            <td>用户名：</td>
            <td><input name="name" readonly="readonly"  type="text" id="name" value="<%=session.getAttribute("userName")%>" maxlength="40" style="border:none;"></td>
          </tr>
          <tr>
            <td>旧密码：</td>
            <td><input name="oldPassword" type="password" id="oldPassword" maxlength="20"></td>
          </tr>
          <tr>
            <td>新密码：</td>
            <td><input name="newPassword" type="password" id="newPassword" maxlength="20"></td>
          </tr>
          <tr>
            <td>再次输入新密码：</td>
            <td><input name="reNewPassword" type="password" id="reNewPassword" maxlength="20" onblur="checkInputPasswd()"></td>
          </tr>
          <tr>
            <td colspan="2" class="mid"><input name="submit" type="submit" class="submitBtn" id="submit" value="提交"></td>
              </td>
          </tr>
        </table>
        <input type="hidden" name="actionCode" value="user">
        <input type="hidden" name="methodCode" value="alter_user_passwd">
      </form>
    </div>
    <div id="div3">
      <p class="title">积分转移到平台 <span class="title1">POINTS TRANSFER TO PLATFORM</span></p>
      <form action="/bonusPointsExchange/UserPointToplatformServlet"  method="post" onsubmit="return checkForm();">
        <table>
          <tr>
            <td>选择商家：</td>
            <td><select  class="normal-font" name="shop" id="shop">
            <option selected="selected" ></option>
             <% if(null != list) {
             	System.out.println(list.size());
        		for(int i = 0; i < list.size(); i++) {
        		ShowBindInfo bindInfo = (ShowBindInfo)list.get(i);
      		 %>
             	<option><%=bindInfo.getShopName() %></option>
               <%}
        	 }%>
              </select></td>
          </tr>
           <tr>
            <td>在商家注册的用户名：</td>
            <td><input name="userName" onblur="queryUserPoints()" type="text" value="" id="userName">
            </td>
          </tr>
          <tr>
            <td>商家积分：</td>
            <td><input name="points" type="text" value="" readonly id="points" style="border:none;"> 
            </td>
          </tr>
          <tr>
            <td>转移积分：</td>
            <td><input name="transfer_points"  type="number" value="0" id="transfer_points">
            </td>
          </tr>
          <tr>
            <td colspan="2" class="mid"><input name="submit" type="submit" class="submitBtn" id="submit" value="提交"></td>
              </td>
          </tr>
        </table>
      </form>
    </div>
    <div id="div4">
      <p class="title">积分转移到商家 <span class="title1">POINTS TRANSFER TO SHOP</span></p>
      <form action="/bonusPointsExchange/PlatformToUserServlet"  method="post" onsubmit="return checkForm();">
        <table>
          <tr>
            <td>选择商家：</td>
            <td><select  class="normal-font" onchange="queryPointsAtPlatform()" name="shop2" id="shop2">
            <option selected="selected"></option>
              <% if(null != list) {
        			for(int i = 0; i < list.size(); i++) {
        				ShowBindInfo bindInfo = (ShowBindInfo)list.get(i);
      		 %>     			
             	<option><%=bindInfo.getShopName() %></option>
               <%}
        	 }%>
              </select></td>
          </tr>
           <tr>
            <td>在商家注册的用户名：</td>
            <td><input name="userName2" type="text" value="" id="userName2">
            </td>
          </tr>
          <tr>
            <td>平台积分：</td>
            <td><input name="platformPoints" type="text" value="" readonly id="platformPoints" style="border:none;"></td>
          </tr>
          <tr>
            <td>转移积分：</td>
            <td><input name="transfer_points" type="number" value="0" id="transfer_points"></td>
          </tr>
          <tr>
            <td colspan="2" class="mid"><input name="submit" type="submit" class="submitBtn" id="submit" value="提交"></td>
              <td></td>
          </tr>
        </table>
      </form>
    </div>
    <div id="div5">
      <p class="title">商家绑定 <span class="title1">SHOP BIND</span></p>
      <form action="/bonusPointsExchange/BindShopQueryInfoServlet" method="post"  onsubmit="return checkBindForm();">
      <br/>
      <div> &nbsp;<span class="normal-font">商家名称：</span>
      	<input name="search"  type="text" id="search" placeholder="请输入商家名称"> &nbsp;&nbsp;&nbsp;
      	<input name="submit2" type="submit" class="submitBtn" id="submit2" value="搜索">
      </div>
      <!------------table 中为查询结果--------每一行是一个商家---------------->
       <%
	  String shopName1 = request.getParameter("search");
	  if(shopName1 != null) {%>
      <table>
      	<tr><span id="hint" style="color:#FF0000"></span></tr>
          <tr class="normal-font">
            <td class="shop-logo"><img src="images/shopLogo/${imgURL }" alt="商家商标"/><p>${shopName}</p></td><td>${shopDec}</td>
            <td class="bindBtn"><a href="bindShop.jsp?shopName=${shopName}"><input name="bind" type="button" id="bind" class="buttonStyle1" value="绑定"></a></td>
          </tr>
       </table>
       <%} %>
      </form>
    </div>
    
    <div id="div6">
      <p class="title">查看商家 <span class="title1">BOUND　SHOP</span></p>
      <form>
        <table>
        <% 	if(null != list) {
        	for(int i = 0; i < list.size(); i++) {
        		ShowBindInfo bindInfo = (ShowBindInfo)list.get(i);
        %>
          <tr>
            <td class="shop-logo"><img src="images/shopLogo/<%=bindInfo.getImgURL() %>" alt="商家商标"/></td><td><%=bindInfo.getShopName() %></td>
            <td><input name="points" type="text" class="inputNum" id="points" value="<%=bindInfo.getPlatformPoints() %>" maxlength="20" readonly></td>
          </tr>
         <%}
          }%>
         
        </table>
      </form>
    </div>
  </div>
</div>
<!--这是bottom-->
	<%@ include file="footer.jsp" %>

<!-- 根据返回的index显示div -->
<script type="text/javascript" language="javascript">
	var index = ${index};
	//alert(index);
	var show=parseInt(index);
	//alert(show);
	for(i=1;i<=6;i++){
		document.getElementById('div'+i).style.display = "none";
	}
	document.getElementById('div'+index).style.display = "block";
</script>


<script type="text/javascript">
/****
$(function() {
	$( "#Accordion1" ).accordion(); 
});
****/
var xmlHttp;
// 对象的创建
function createXMLHttp() {
	//alert("sasdad");//调试代码
	if (window.XMLHttpRequest) { // firefox
		xmlHttp = new XMLHttpRequest();
	} else { // ie
		xmlHttp = new ActiveXObject("microsoft.XMLHTTP");
	}
}
//=================================================//
//查询用户在商家那里有多少积分
function queryUserPoints() {
	//alert("sasdad");//调试代码
	var shopName = document.getElementById("shop").value;//此处应该是用户所属商家，后面可能要改代码
	var userName = document.getElementById("userName").value;//用户在商家注册的用户名
	//alert(userName);
	var url = "/bonusPointsExchange/QueryUserPoints?shop="+encodeURI(encodeURI(shopName))+"&userNameAtShop="+encodeURI(encodeURI(userName));
	createXMLHttp();
	xmlHttp.onreadystatechange = queryUserPointsBack;
	xmlHttp.open("get", url, true);
	xmlHttp.send(null);
}
// 回调函数,处理服务器返回结果
function queryUserPointsBack() {
	// alert(xmlHttp.readyState);
	// 响应已完成
	if (xmlHttp.readyState == 4) {
		// 服务器正常的响应
		// alert(xmlHttp.status);
		if (xmlHttp.status == 200) {
			var returnMsg = xmlHttp.responseText; // 收取服务器端的响应信息(String)
			//alert(returnMsg);
			document.getElementById("points").value = returnMsg;
		}
	}
}

//=================================================//
//查询用户在品台数据库有多少积分
function queryPointsAtPlatform() {
	var shopName = document.getElementById("shop2").value;
	//alert(shopName);//调试代码
	var url = "/bonusPointsExchange/QueryPointsAtPlatform?shop="+encodeURI(encodeURI(shopName));
	createXMLHttp();
	xmlHttp.onreadystatechange = queryPointsAtPlatformBack;
	xmlHttp.open("get", url, true);
	xmlHttp.send(null);
}
// 回调函数,处理服务器返回结果
function queryPointsAtPlatformBack() {
	//alert("aaaaaa");
	// 响应已完成
	if (xmlHttp.readyState == 4) {
		// 服务器正常的响应
		// alert(xmlHttp.status);
		if (xmlHttp.status == 200) {
			var returnMsg = xmlHttp.responseText; // 收取服务器端的响应信息(String)
			//alert(returnMsg);
			document.getElementById("platformPoints").value = returnMsg;
		}
	}
}

function showDiv(index) {   
var show=parseInt(index);
for(i=1;i<=6;i++){
		document.getElementById('div'+i).style.display = "none";
	}
	document.getElementById('div'+index).style.display = "block";
	if(show == 1){
		//查询用户个人信息
		location.href="/bonusPointsExchange/actionServlet?actionCode=user&methodCode=query_user_info&index="+1;
	}
	if(show == 3) {
		//查询用户绑定的商家的相关信息，显示在select中
		location.href = "/bonusPointsExchange/QueryBindedShopNameServlet?index="+3;
	}
	if(show == 4) {
		//查询用户绑定的商家的相关信息，显示在select中
		location.href = "/bonusPointsExchange/QueryBindedShopNameServlet?index="+4;
	}
	if(show == 5) {
		//location.href = "/bonusPointsExchange/QueryBindInfo";
	}
	if(show == 6) {
		location.href = "/bonusPointsExchange/QueryBindInfo";
	}
}
function checkInputPasswd(){
	var newPassword = document.getElementById("newPassword").value;
	var reNewPassword = document.getElementById("reNewPassword").value;
	if(newPassword!=reNewPassword){
		alert("您输入的两次新密码不一致");
		return false;
	}else return true;
	
}

function checkForm() {
	
	var shop = document.getElementById("shop").value;
	var shop2 = document.getElementById("shop2").value;
	//alert(shopName);
	if (shop == "" && shop2 == "") {
		alert("请选择商家！");
		return false;
	}
	
	//商家名不能空
	var userName = document.getElementById("userName").value;
	var userName2 = document.getElementById("userName2").value;
	//alert(shopName);
	if (userName == "" && userName2=="" ) {
		alert("在商家注册的名称不能为空！");
		return false;
	}
	
	var transfer_points = document.getElementById("transfer_points").value;
	if (transfer_points == "") {
		alert("转移积分不能为空！");
		return false;
	}
 }
 
 function checkBindForm() {
	var shop2 = document.getElementById("search").value;
	//alert(shopName);
	if (shop2 == "") {
		alert("请输入商家名称！");
		return false;
	}
}
</script>
</body>
</html>
