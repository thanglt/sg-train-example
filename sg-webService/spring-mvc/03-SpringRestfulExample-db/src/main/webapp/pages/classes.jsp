<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<p>Classroom of students</p>

${classroomOutput.test}
<br/>
<c:forEach items="${classroomOutput.classes}" var="item">
	${item.classId} <br/>
</c:forEach>
