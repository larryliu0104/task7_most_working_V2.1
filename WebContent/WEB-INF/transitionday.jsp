

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:include page="employee-top.jsp" />

<div class="panel-heading">
	<h3 class="panel-title">Update Transaction Information</h3>
</div>
<div class="panel-body">
	<jsp:include page="template-result.jsp" /><br> <br>
	<form role="form">
		<div class="form-group">
			<label for="exampleInputEmail1">
				Last Transaction Day is ${lastTransactionDay }</label>
			<p>	
			Please pick a new transaction day</p> 
			<div class="col-sm-4" style="margin-left:-16px">
			
			<input type="date" class="form-control col-xs-4" name="date"
				min="${minDate}" max="2050-01-01"></div>
		</div>

		<table class="table table-striped table-hover" id="transaction">
			<thead>
				<tr>
					<th width="15%">Date</th>
					<th>Fund Ticker</th>
					<th>Fund Name</th>

					<th style="text-align: right">Current Price</th>
					<th width="30%">Update Price
					<br>
					 (at most 2 decimal digits)</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="fund" items="${funds}">
					<tr>
						<td>${fund.getDate()}</td>
						<td>${fund.getTicker()}</td>
						<td>${fund.getFundName()}</td>


						<td align="right">${fund.getPrice()}</td>
						<td><input type="hidden" name="ids"
							value="${fund.getFundId()}" /> <input type="text"
							name="prices" value="1.00" placeholder="1.00"
							pattern="\d+(\.\d{1,2})?" class="form-control" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<!-- <p style="color: grey"> *Please enter numbers with no more than 2 digits after the decimal </p> -->
		<br> <br>
		<button type="submit" class="btn btn-primary">Update</button>
	</form>
</div>


