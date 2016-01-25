

<jsp:include page="employee-top.jsp" />


		<div class="panel-heading">
			<h3 class="panel-title">View Customer Account</h3>
		</div>
		<div class="panel-body">
			<h3>Account information of ${customer.getUserName()}</h3>
			<jsp:include page="template-result.jsp" />
			<a
				href="employee-change-customer-pwd.do?userName=${customer.getUserName()}">change
				password</a><br>
				 <a
				href="employee-view-transactions.do?userName=${customer.getUserName()}">transaction
				history</a><br>
				<a
				href="employee-deposit-check.do?userName=${customer.getUserName()}">deposit
				check</a>
			<jsp:include page="customer-profile.jsp" />
			<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
			<c:choose>
				<c:when test="${shareList ==null}">
					<h5>No funds currently.</h5>
				</c:when>
				<c:otherwise>
					<h5>Last trading day: ${lastTransactionDay}</h5>
					<table class="table">
						<thead>
							<tr>
								<th>Fund name</th>
								<th>Ticker</th>
								<th style="text-align:right">Shares</th>
								<th style="text-align:right">Estimated value</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="share" items="${shareList}">
								<tr>
									<td>${share.getFundName() }</td>
									<td>${share.getFundTicker() }</td>
									<td align="right">${share.getShare()}</td>
									<td align="right">${share.getAmount()}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>

<jsp:include page="bottom.jsp" />

