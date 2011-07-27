<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>诗篇在线内容管理系统 Works!</h1>
<p>You are :<#if user??> ${user.loginName}  <#else> anonymous </#if></p>
<#list section.getArticles(5) as ar>
<a href="${ar.url}}">${ar.title}</a>
</#list>

</body>
</html>
