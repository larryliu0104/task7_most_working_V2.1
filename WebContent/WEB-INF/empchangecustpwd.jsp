

<jsp:include page="employee-top.jsp" />

<div class="panel-heading">
	<h3 class="panel-title">Change Customer Password</h3>

</div>
<jsp:include page="template-result.jsp" /><br>
<div class="panel-body">
	<div class="col-sm-6" style="margin-left: 180px">
		<h3>
			Change Password for <a id="${customer.getUserName() }-ID"
				data-toggle="modal" data-target="#${customer.getUserName() }">${customer.getUserName()}</a>

		</h3>
		<br></br>
		<form role="form">
			<div class="form-group">
				<input type="hidden" name="userName"
					value="${customer.getUserName()}"> <label
					for="exampleInputEmail1"> New password </label> <input
					type="password" class="form-control" name="newPassword">
			</div>
			<div class="form-group">
				<label for="exampleInputPassword1">Confirm New Password</label> <input
					type="password" class="form-control" name="confirmNewPassword">
			</div>
			<button type="submit" class="btn btn-primary">Save Changes</button>
		</form>
	</div>
</div>



<!-- Modal -->
<div class="modal fade" id="${customer.getUserName() }" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">View Profile</h4>
			</div>
			<div class="modal-body">
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th width="50%">Key</th>
							<th>Value</th>
					</thead>
					<tbody>
						<tr>
							<td>Customer Name</td>
							<td>${customer.getFirstName()}&nbsp;${customer.getLastName()}</td>
						</tr>
						<tr>
							<td>Address</td>
							<td><c:if test="${customer.getAddrLine1() !=null}">${customer.getAddrLine1()}
                                </c:if> <c:if
									test="${customer.getAddrLine2() !=null && customer.getAddrLine2().length() != 0}">
									<br></br>${customer.getAddrLine2()}
                                </c:if> ${customer.getCity()}<br>
								${customer.getState()}&nbsp;${customer.getZip()}</td>
						</tr>
						<tr>
							<td>Cash Balance</td>
							<td>${customer.getCash()}</td>
						</tr>
					</tbody>
				</table>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</div>

