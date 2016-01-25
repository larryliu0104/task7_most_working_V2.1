<jsp:include page="customer-top.jsp" />

	<div class="panel-heading">
		<h3 class="panel-title"><b>Buy Fund</b></h3>
	</div>
	<jsp:include page="template-result.jsp" />
	<div class="panel-body">
		<p>
<% 
            String validAmount = request.getAttribute("validAmount").toString();
			out.print("Your Available Balance: $ <b>" + validAmount + "</b>");
%>
		</p>
		<p>
<% 
			String currentAmount = request.getAttribute("currentAmount").toString();
			out.print("Your Cash Balance: $ <b>" + currentAmount + "</b>");
			
%>
		</p>
		<br>
		<form class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-2 control-label"> Fund Name: </label>
				<div class="col-sm-10">
					<a href="customer_fund_detail.do?fundId=${fund.getId()}">${fund.getName() }</a>
				</div>
			</div>
			<div class="form-group">
				<input type="hidden" style="width: 30%" name="fundId"
					value="${fund.getId()}"> <label
					class="col-sm-2 control-label"> Amount(USD): </label>
					
				<div class="col-sm-8">
					<input type="text" class="form-control" name="amount">
				</div>
			</div>
			<br>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary" name="action"
						value="buy">Buy</button>
				</div>
			</div>
		</form>
		<br></br>
		<br>
	</div>
