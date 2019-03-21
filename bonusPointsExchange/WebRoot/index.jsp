<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%	String LaunchTranscationRes = (String)request.getAttribute("LaunchTranscationRes"); 
 	 if(LaunchTranscationRes=="unBindShop"){
 %>
	<script type="text/javascript" language="javascript">
		alert("您还未绑定商家，请先去绑定商家！");                            
	</script>
<%	} %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>主页</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
<link href="css/footer.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="css/index.css">
</head>
<body>

<!--这是top-->
	<%@ include file="header.jsp" %>
<!--这是main_page-->
<div class="index clearfix">
  <div class="index1">
    <ul>
      <li>银行</li>
      <li>航空公司</li>
      <li>超市</li>
      <li>餐饮</li>
      <li>商场</li>
      <li style="border:none">其他</li>
    </ul>
  </div>
  <div class="index2">
    <div>
      <p class="title">商家<span class="more title1"><a href="#">更多</a></span></p>
    </div>
    <div id="shop-list"  class="clearfix">
      <ul>
        <li><a><img src="images/shopLogo/beifanghangkong.jpg"/>
          <p class="shopName">北方航空</p>
          </a></li>
        <li><a><img src="images/shopLogo/changanhangkong.jpg"/>
          <p class="shopName">长安航空</p>
          </a></li>
        <li><a><img src="images/shopLogo/changchenghangkong.jpg"/>
          <p class="shopName">长城航空</p>
          </a></li>
        <li><a><img src="images/shopLogo/dongfanghangkong.jpg"/>
          <p class="shopName">东方航空</p>
          </a></li>
        <li><a><img src="images/shopLogo/shanghaihangkong.jpg"/>
          <p class="shopName">上海航空</p>
          </a></li>
        <li><a><img src="images/shopLogo/shenzhenhangkong.jpg"/>
          <p class="shopName">深圳航空</p>
          </a></li>
        <li><a><img src="images/shopLogo/sichuanhangkong.jpg"/>
          <p class="shopName">四川航空</p>
          </a></li>
        <li><a><img src="images/shopLogo/wuhanhangkong.jpg"/>
          <p class="shopName">武汉航空</p>
          </a></li>
        <li><a><img src="images/shopLogo/xiamenhangkong.jpg"/>
          <p class="shopName">厦门航空</p>
          </a></li>
        <li><a><img src="images/shopLogo/zhongguoguoji.jpg"/>
          <p class="shopName">中国国际航空</p>
          </a></li>
      </ul>
    </div>
    <div>
      <p class="title">最新发布<span class="more title1"><a href="#">更多</a></span></p>
    </div>
    <div id="order-list" class="clearfix"> 
      <!---- 事例1------>
      <div class="order-info clearfix">
        <ul >
          <li class="shop-logo"><img src="images/shopLogo/dongfanghangkong.jpg"/></li>
          <li class="info">
            <table>
              <tr>
                <td>商家：东方航空</td>
                <td>目标商家：厦门航空</td>
              </tr>
              <tr>
                <td>积分数量：100</td>
                <td>目标积分数量：120</td>
              </tr>
              <tr>
                <td>截止日期：2016-11-20</td>
                <td></td>
              </tr>
            </table>
          </li>
          <li class="operate">
            <input name="exchange" type="button" class="submitBtn" id="exchange" value="交易">
          </li>
        </ul>
      </div>
      <!-----事例2------>
      <div class="order-info clearfix " >
        <ul>
          <li class="shop-logo"><img src="images/shopLogo/changanhangkong.jpg"/></li>
          <li class="info">
            <table>
              <tr>
                <td>商家：东方航空</td>
                <td>目标商家：厦门航空</td>
              </tr>
              <tr>
                <td>积分数量：100</td>
                <td>目标积分数量：120</td>
              </tr>
              <tr>
                <td>截止日期：2016-11-20</td>
                <td></td>
              </tr>
            </table>
          </li>
          <li class="operate">
            <input name="exchange" type="button" class="submitBtn" id="exchange" value="交易">
          </li>
        </ul>
      </div>
      <!------事例3------->
      <div class="order-info clearfix " >
        <ul>
          <li class="shop-logo"><img src="images/shopLogo/changchenghangkong.jpg"/></li>
          <li class="info">
            <table>
              <tr>
                <td>商家：东方航空</td>
                <td>目标商家：厦门航空</td>
              </tr>
              <tr>
                <td>积分数量：100</td>
                <td>目标积分数量：120</td>
              </tr>
              <tr>
                <td>截止日期：2016-11-20</td>
                <td></td>
              </tr>
            </table>
          </li>
          <li class="operate">
            <input name="exchange" type="button" class="submitBtn" id="exchange" value="交易">
          </li>
        </ul>
      </div>
            <!------事例4------->
      <div class="order-info clearfix " >
        <ul>
          <li class="shop-logo"><img src="images/shopLogo/dongfanghangkong.jpg"/></li>
          <li class="info">
            <table>
              <tr>
                <td>商家：东方航空</td>
                <td>目标商家：厦门航空</td>
              </tr>
              <tr>
                <td>积分数量：100</td>
                <td>目标积分数量：120</td>
              </tr>
              <tr>
                <td>截止日期：2016-11-20</td>
                <td></td>
              </tr>
            </table>
          </li>
          <li class="operate">
            <input name="exchange" type="button" class="submitBtn" id="exchange" value="交易">
          </li>
        </ul>
      </div>
            <!------事例5------->
      <div class="order-info clearfix " >
        <ul>
          <li class="shop-logo"><img src="images/shopLogo/shanghaihangkong.jpg"/></li>
          <li class="info">
            <table>
              <tr>
                <td>商家：东方航空</td>
                <td>目标商家：厦门航空</td>
              </tr>
              <tr>
                <td>积分数量：100</td>
                <td>目标积分数量：120</td>
              </tr>
              <tr>
                <td>截止日期：2016-11-20</td>
                <td></td>
              </tr>
            </table>
          </li>
          <li class="operate">
            <input name="exchange" type="button" class="submitBtn" id="exchange" value="交易">
          </li>
        </ul>
      </div>
    </div>
  </div>
</div>
<!--这是bottom-->
	<%@ include file="footer.jsp" %>
</body>
</html>
