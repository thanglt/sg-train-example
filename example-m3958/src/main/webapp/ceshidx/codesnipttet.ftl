<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<#macro buildNode child parent> 
    <#if child?? && child?size gt 0> 
        <#list child as t> 
            //to build tree node 
            <@buildNode child=t.child parent=t/> 
        </#list> 
    </#if> 
</#macro> 

<@buildNode child=data.treeRoot.child parent=data.treeRoot/>
<!--
Default value operator ${mouse!"No mouse."}
Missing value test operator unsafe_expr?? or (unsafe_expr)??



 -->
</body>
</html>