<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="employee-top.jsp" />

<div class="panel-heading">
	<h3 class="panel-title">Create Fund</h3>
</div>
<br>
<div class="panel-body">
	<div>
		<c:if test="${errors !=null && fn:length(errors) > 0}">
			<div class="alert alert-warning">
				<h4>
					Error In Input
				</h4>

				<c:forEach var="error" items="${errors}">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${error}<br>
				</c:forEach>
			</div>
		</c:if>
	</div>

	<div class="col-sm-12" style="margin-left: 0px">
		<!-- <form role="form">
			<div class="form-group">
				<label for="exampleInputEmail1">Fund Name</label><input type="text"
					class="form-control" name="name">
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">Ticker</label><input type="text"
					class="form-control" name="ticker">
			</div>
			<button type="submit" class="btn btn-primary">Create New
				Fund</button>
		</form> -->
		<form class="form-horizontal">
			<div class="form-group">
				<label for="exampleInputEmail1" class="col-sm-3 control-label">Fund Name</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="inputEmail3" name="name"
						placeholder="Please input fund name">
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword3" class="col-sm-3 control-label">Ticker</label>
				<div class="col-sm-8">
					<input type="text"  name="ticker" class="form-control" id="inputPassword3"
						placeholder="Please input Ticker">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-3 col-sm-10">
					<button type="submit" class="btn btn-primary">Create New
				Fund</button>
				</div>
			</div>
		</form>

	</div>
</div>

