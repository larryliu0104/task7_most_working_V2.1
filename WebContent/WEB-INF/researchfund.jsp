<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="customer-top.jsp" />

		<div class="panel-heading">
			<h3 class="panel-title"><b>Find fund</b></h3>
		</div>
<div class="panel-body">
	<div class="search">
		<h4>Please search the fund you want to buy</h4>
		<jsp:include page="template-result.jsp" />
		<br>
		<form class="form-inline">
			<div class="col-sm-8">
				<input type="text" class="form-control" name="keyword"
					placeholder="Search for...">
			</div>
			<button class=btn btn-success" type="submit">Search</button>
		</form>
		<br>		
	</div>
	<div class="search_result">
	
		<c:choose>
			<c:when test="${fundList !=null}">
			<hr>
				<h3>Search Result:</h3>
				<br>
				<c:if test="${fn:length(fundList) == 0}">
					No result matched.
				</c:if>
				<c:if test="${fn:length(fundList) != 0}">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Fund Name</th>
								<th>Fund Ticker</th>
								<th>Operation</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="fund" items="${fundList}">
								<tr>
									<td><a href="customer_fund_detail.do?fundId=${fund.getId()}">${fund.getName()}</a></td>
									<td>${fund.getTicker()}</td>
									<td><a class="btn btn-success" role="button"
										href="customer-buy-fund.do?fundId=${fund.getId()}">Buy</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</c:when>
		</c:choose>
	</div>
</div>

<jsp:include page="template-bottom.jsp" />

