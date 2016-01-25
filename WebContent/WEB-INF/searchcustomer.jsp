<jsp:include page="employee-top.jsp" />


<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="panel-heading">
	<h3 class="panel-title">Search Customer</h3>
</div>
<div class="panel-body">
	<div class="search">
		<jsp:include page="template-result.jsp" />
		<br>
		<!-- <form class="form-inline"> -->
		<!-- <form class="">
			<div class="form-group col-md-6" style="margin-left: -15px;">
				<div class="input-group">
					<div class="input-group-addon">@</div>
					<input type="text" class="form-control" name="keyword"
						id="searchbox" placeholder="Search Customer">
				</div>
			</div>
			<button type="submit" class="btn btn-primary">Search</button>
		</form> -->
		<!-- 		<form class="form-inline">
			<div class="form-group">
									<label class="control-label col-sm-3" for="inputSuccess3">search
						customer</label>
				<div class="input-group ">
					<div class="input-group-addon">@</div>
					<input type="text" class="form-control" name="keyword"
						placeholder="search customer">

					<button class="btn btn-primary" type="submit">Search</button>
				</div>
			</div>
		</form> -->
	</div>
	<div class="search_result">
		<c:choose>
			<c:when test="${customers !=null}">
				
				<br>
				<c:if test="${fn:length(customers) == 0}">
					No results matched
				</c:if>
				<c:if test="${fn:length(customers) != 0}">
					<table class="table table-striped table-hover" id="example">
						<thead>
							<tr>
								<th>User Name</th>
								<th>First Name</th>
								<th>Last Name</th>
								<th>Operation</th>
								<!-- <th>Details</th> -->
								
							</tr>
						</thead>
						<tbody>
							<c:forEach var="customer" items="${customers}">
								<tr>
									<td>${customer.getUserName() }</td>
									<td>${customer.getFirstName() }</td>
									<td>${customer.getLastName() }</td>
									<td>
										<div class="dropdown">
											<button class="btn btn-primary dropdown-toggle" type="button"
												id="dropdownMenu1" data-toggle="dropdown"
												aria-haspopup="true" aria-expanded="true">
												Manage Account <span class="caret"></span>
											</button>
											<ul class="dropdown-menu" aria-labelledby="dropdownMenu1"
												style="font-size: 16px">
												<li><a
                                                    href="employee_view_customer_profile.do?userName=${customer.getUserName()}">View Profile
                                                        </a></li>
                                                        
												<li><a
													href="employee-change-customer-pwd.do?userName=${customer.getUserName()}">Change
														Password</a></li>
												<li role="separator" class="divider"></li>
												<li><a
													href="employee-view-transactions.do?userName=${customer.getUserName()}">Transaction
														History</a></li>


												<li><a
													href="employee-deposit-check.do?userName=${customer.getUserName()}">Deposit
														Check</a></li>
											</ul>
										</div>
									</td>

									<%-- <td><a id="${customer.getUserName() }-ID"
										data-toggle="modal" data-target="#${customer.getUserName() }">View</a>
										<!-- Modal -->
										<div class="modal fade" id="${customer.getUserName() }"
											tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
											<div class="modal-dialog" role="document">
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal"
															aria-label="Close">
															<span aria-hidden="true">&times;</span>
														</button>
														<h4 class="modal-title" id="myModalLabel">View
															Profile</h4>
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
																	<td>${customer.getCashTwoDecimal()}</td>
																</tr>
															</tbody>
														</table>
														<div class="modal-footer">
															<button type="button" class="btn btn-default"
																data-dismiss="modal">Close</button>
														</div>
													</div>
												</div>
											</div>
										</div></td> --%>

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</c:when>
			<%-- <c:when test=" ">
					<div class="alert alert-dismissable alert-warning">
				 		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
						<h4><strong>No Results Found!</strong>Please Try Again...</h4>
					</div>
				</c:when>
				<c:otherwise>
				</c:otherwise> --%>
		</c:choose>
	</div>
</div>


<%-- href="employee_view_customer.do?username=${customer.getUserName()}"> --%>

<!-- Button trigger modal -->



<%-- <jsp:include page="bottom.jsp" /> --%>