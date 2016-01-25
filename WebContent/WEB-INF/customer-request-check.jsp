
<jsp:include page="customer-top.jsp" />

	<div class="panel-heading">
		<h3 class="panel-title"><b>Request Check</b></h3>
	</div>
	<jsp:include page="template-result.jsp" />
	<br>
	<div class="panel-body">
		<div class="col-sm-10">
			<p>
				Your Valid Balance:  ${validAmount}
			</p>
			<p>
				Your Current Balance:  ${currentAmount}
			</p>
			<br>
			<form role="form">
			<div class="form-group">
				 <label
					class="col-sm-2 control-label"> Amount($): 
					</label>					
				<div class="col-sm-10">
					<input type="text" class="form-control" name="amount">
				</div>
			</div>
			<br></br>
				<button type="submit" class="btn btn btn-primary" name="action"
					value="request">Submit</button>
			</form>
			<br></br>
			<br>
		</div>
	</div>
