
<jsp:include page="employee-top.jsp" />
<div class="panel-heading">
	<h3 class="panel-title">Transaction History</h3>
</div>
<br>
<div class="panel-body">


	<h3>
		Transaction history for <a id="${customer.getUserName() }-ID"
                data-toggle="modal" data-target="#${customer.getUserName() }">${customer.getUserName()}</a>
	</h3>
	<jsp:include page="template-result.jsp" />
	<br></br>
	<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
	<c:choose>
		<c:when test="${transactions ==null}">
			<h5>No transactions.</h5>
		</c:when>
		<c:otherwise>
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>Date</th>
						<th>Fund name</th>
						<th>Fund Ticker</th>
						<th style="text-align: right">Shares</th>
						<th style="text-align: right">Price</th>
						<th style="text-align: right">Amount</th>
						<th>Type</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="transaction" items="${transactions}">
                        <tr>
                            <td>${transaction.getExecuteDay()}</td>
                            <td>${transaction.getFundName()}</td>
                            <td>${transaction.getFundTicker()}</td>
                            <td style="text-align:right">${transaction.getShares()}</td>
                            <td style="text-align:right">${transaction.getPrice()}</td>
                            <td style="text-align:right">${transaction.getAmount()}</td>
                            <td>${transaction.getTransactionType()}</td>
                            <td>${transaction.getStatus()}</td>
                         </tr>
                    </c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
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

