<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<p>Classroom of students</p>

<br/>
<c:forEach items="${classesAll}" var="item">
	${item.classId} <br/>
</c:forEach>
