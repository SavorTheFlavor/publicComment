<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
		<title></title>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/all.css">
		<link rel="stylesheet" type="text/css" href="${basePath}/css/pop.css">
		<link rel="stylesheet" type="text/css" href="${basePath}/css/main.css">
		<script type="text/javascript" src="${basePath}/js/common/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="${basePath}/js/content/orderList.js"></script>
	</head>
	<body style="background: #e1e9eb;">
		<form action="${basePath}/orders/search" id="mainForm" method="post">
			<input id="id" name="id" type="hidden">
			<input id="basePath" value="${basePath}" type="hidden">
			<input name="page.currentPage" id="currentPage" value="1" type="hidden">
			<div class="right">
				<div class="current">当前位置：<a href="#">内容管理</a> &gt; 订单查询</div>
				<div class="rightCont">
					<p class="g_title fix">订单列表</p>
					<table class="tab1">
						<tbody>
							<tr>
								<td width="80" align="right">手机号：</td>
								<td>
									<input name="title" id="title" value="" class="allInput" type="text">
								</td>
	                            <td style="text-align: right;" width="150">
	                            	<input class="tabSub" value="查询" onclick="search('1');" type="button">&nbsp;&nbsp;&nbsp;&nbsp;
	                            </td>
	       					</tr>
						</tbody>
					</table>
					<div class="zixun fix">
						<table class="tab2" width="100%">
							<tbody>
								<tr>
								    <th>序号</th>
								    <th>标题</th>
								    <th>手机号</th>
								    <th>订单号</th>
								    <th>金额(元)</th>
								</tr>
								<c:forEach items="${ordersList}" var="item" varStatus="s">
									<tr>
										<td>${s.index+1}</td>
										<td>${item.title}</td>
										<td>${item.member.phone}</td>
										<td>${item.id}</td>
										<td>${item.price*item.count}</td>
										<td>
												<a href="javascript:void(0);" onclick="remove2('${item.id}')">删除</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						
						<!-- 分页 -->
						



<script type="text/javascript">
	function transCurrentPage(currentPage) {
		var rule = /^[0-9]*[1-9][0-9]*$/;
		if(!rule.test(currentPage)) {
			currentPage = 1;
		}
		eval("search(currentPage)");
	}
</script>

<div class="page fix">
	<a href="javascript:transCurrentPage('1');" class="first">首页</a>
	<a href="javascript:transCurrentPage('0');" class="pre">上一页</a>
	当前第<span>1/1</span>页
	<a href="javascript:transCurrentPage('2');" class="next">下一页</a>
	<a href="javascript:transCurrentPage('1');" class="last">末页</a>
	跳至 &nbsp;<input id="currentPageText" value="1" class="allInput w28" type="text">&nbsp;页 &nbsp;
	<a href="javascript:transCurrentPage($('#currentPageText').val());" class="go">GO</a>
</div>
					</div>
				</div>
			</div>
		</form>
	
</body></html>