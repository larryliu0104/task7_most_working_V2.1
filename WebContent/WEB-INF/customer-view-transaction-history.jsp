<jsp:include page="customer-top.jsp" />
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
	<div class="panel-heading">
		<h3 class="panel-title"><b>Transaction history</b></h3>
	</div>
			
			<jsp:include page="template-result.jsp" />
			
			<div class="panel-body">
		<div class="col-sm-12">
			
			<c:choose>
				<c:when test="${transactions ==null}">
					<h5>No transactions.</h5>
				</c:when>
				<c:otherwise>
					<table class="table table-striped">
						<thead >
							<tr >
								<th >Date</th>
								<th >Fund name</th>
								<th style="text-align:right">Shares</th>
								<th style="text-align:right">Price</th>
								<th style="text-align:right">Amount</th>
								<th></th>
								<th >Operation</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="transaction" items="${transactions}">
								<tr>
									<td >${transaction.getExecuteDay()}</td>
									<td >${transaction.getFundName()}</td>
									<td align="right">${transaction.getShares()}</td>
									<td align="right">${transaction.getPrice()}</td>
									<td align="right">${transaction.getAmount()}</td>
									<td></td>
									<td >${transaction.getTransactionType()}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="col-md-4 column"></div>
	</div>


