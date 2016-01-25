<!-- output errors if there is any-->
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test="${message !=null}">
	<p style="font-size: medium">${message}</p>
</c:if>
<div>
	<c:if test="${errors !=null && fn:length(errors) > 0}">
		<div class="alert alert-warning ">
			<h4>Invalid Input</h4>
			<c:forEach var="error" items="${errors}">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${error}<br>
			</c:forEach>
		</div>
	</c:if>
</div>
