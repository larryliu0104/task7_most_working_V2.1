<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="employee-top.jsp" />

<div class="panel-heading">
	<h3 class="panel-title">Create Employee Account</h3>
</div>
<br>
<div class="panel-body">
	<div>
		<c:if test="${errors !=null && fn:length(errors) > 0}">
			<div class="alert alert-warning">
				<h4>
					
					Error Input
				</h4>
				<c:forEach var="error" items="${errors}">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${error}<br>
				</c:forEach>
			</div>
		</c:if>
	</div>
	<div class="col-sm-8" style="margin-left:">
		<form role="form" class="form-horizontal">
			<div class="form-group">
				<label for="exampleInputEmail1" class="col-sm-6 control-label">User
					Name</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" name="userName"
						value=${form.userName}>
				</div>
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1" class="col-sm-6 control-label">First
					Name</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" name="firstName"
						value=${form.firstName}>
				</div>
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1" class="col-sm-6 control-label">Last
					Name</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" name="lastName"
						value=${form.lastName}>
				</div>
			</div>
			<div class="form-group">
				<label for="exampleInputPassword1" class="col-sm-6 control-label">Password</label>
				<div class="col-sm-6">
					<input type="password" class="form-control" name="password">
				</div>
			</div>
			<div class="form-group">
				<label for="exampleInputPassword1" class="col-sm-6 control-label">Confirm
					Password</label>
				<div class="col-sm-6">
					<input type="password" class="form-control" name="confirm">
				</div>
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1" class="col-sm-6 control-label">
				</label>
				<div class="col-sm-6">
					<button type="submit" class="btn btn-primary"
						class="col-sm-2 control-label" name="action" value="create">Create
						Employee Account</button>
				</div>
			</div>
		</form>
	</div>

</div>

<%-- <jsp:include page="bottom.jsp" /> --%>

