<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<jsp:include page="customer-top.jsp" />

<div class="col-md-2 column"></div>
<div class="col-md-8 column"></div>
<!-- <div class="panel-heading"> -->
<!-- 	<h3 class="panel-title">Your Account Information</h3> -->
<!-- </div> -->


<div class="panel-body">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">Your Profile</h3>
		</div>
		<div class="panel-body">
			<jsp:include page="template-result.jsp" />
			<jsp:include page="customer-profile.jsp" />
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">Your Fund</h3>
		</div>
		<div class="panel-body">
			<c:choose>
				<c:when test="${shareList ==null}">
					<h5>You don't have any funds currently.</h5>
				</c:when>
				<c:otherwise>
					<h5>Last trading day: ${lastTransactionDay}</h5>
					<table class="table">
						<thead>
							<tr>
								<th>Fund name</th>
								<th>Ticker</th>
								<th style="text-align: right">Shares</th>
								<!--<th style="text-align:right">Estimated value</th> -->
								<th>Operation</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="share" items="${shareList}">
								<tr>
									<td><a
										href="customer_research_fund.do?fundId=${share.getFundId()}">${share.getFundName() }</a></td>
									<td>${share.getFundSymbol() }</td>
									<td align="right">${share.getSharesThreeDecimal()}</td>
									<!--  <td align="right">${share.getAmountTwoDecimal()}</td>  -->
									<td>
									<div class="dropdown">
                                        <button
											class="btn btn-primary dropdown-toggle" type="button"
											id="dropdownMenu1" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="true">
                                            Manage Account <span
												class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu"
											aria-labelledby="dropdownMenu1" style="font-size: 16px">
                                            <li><a
												href="customer-sell-fund.do?fundId=${share.getFundId()}">Sell
                                            </a></li>

                                            <li><a
												href="customer_buy_fund.do?fundId=${share.getFundId()}">Buy</li>
                                        </ul>
                                    </div>
                                </td>
								</tr>
								
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>

<div class="col-md-2 column"></div>


