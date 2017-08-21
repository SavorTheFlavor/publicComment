<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "publicComment";
    request.setAttribute("basePath", basePath);
%>