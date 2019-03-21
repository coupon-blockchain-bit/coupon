<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	String errorMsg =  (String)request.getAttribute("errorMsg");//忘记密码
	if(errorMsg == "您输入的账号或邮箱不存在！") {
%>
	<script type="text/javascript" language="javascript">
		alert("您输入的账号或邮箱不存在！请重新输入");                            
	</script>	
<% } %>
<%
	String resetPasswdMeg =  (String)request.getAttribute("resetPasswdMeg");//忘记密码重置密码
	if(resetPasswdMeg == "fail") {
%>
	<script type="text/javascript" language="javascript">
		alert("重置密码失败，请重新操作");                            
	</script>	
<% } %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>忘记密码</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
<link href="css/footer.css" rel="stylesheet" type="text/css">
<link href="css/regist.css" rel="stylesheet" type="text/css">
<script type="text/javascript">

//表单验证
function checkForm() {
	// alert("ada");
	var userName = document.getElementById("userName").value;
	if (userName == "" || userName == null) {
		alert("用户名不能为空！");
		return false;
	}
	
	var email = document.getElementById("email").value;
	if (email == "") {
		alert("邮箱不能为空！");
		return false;
	}
}
</script>
</head>

<body>
<!--header -->
	<%@ include file="header.jsp" %>
<!--header -->
<div class="repsw-form">
<div class="retrieve">
  <p class="title">找回密码&nbsp;&nbsp;&nbsp;&nbsp;<span class="title1">RETRIEVE　PASSWORD</span> <span class="title1 right"><a href="login.jsp">立即登录</a>&nbsp;&nbsp;&nbsp;</span></p>
  <div><span id="step-title1">STEP1:安全验证</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="step-title2">STEP2:重置密码</span></div>
    <div class="span5">
    <div id="step1">
      <form action="/bonusPointsExchange/actionServlet" method="post"  onsubmit="return checkForm();">
        <table>
          <tr>
            <td>账&nbsp;号：</td>
            <td><input name="userName" type="text" id="userName" maxlength="20"></td>
          </tr>
       <!--   <tr>
            <td>密&nbsp;码：</td>
            <td><input name="password" type="password" id="password" maxlength="20"></td>
          </tr>
          <tr>
            <td>确认密码：</td>
            <td><input name="repassword" type="password" id="repassword" maxlength="20"></td>
          </tr>   ---->
          <tr>
            <td>邮&nbsp;箱：</td>
            <td><input name="email" type="text" id="email" maxlength="40"></td>
          </tr>
          <tr>
            <td colspan="2" class="mid"><input name="next" type="submit" class="submitBtn" id="next" value="提交" ></td>
          </tr> 
        </table>
        <input type="hidden" name="actionCode" value="forgetPasswd">
        <input type="hidden" name="methodCode" value=<%=request.getParameter("method") %>>
      </form>
      </div>
      <div id="step2">

      </div>
    </div>
</div>
</div>
<!--footer -->
	<%@ include file="footer.jsp" %>
</body>

</html>
