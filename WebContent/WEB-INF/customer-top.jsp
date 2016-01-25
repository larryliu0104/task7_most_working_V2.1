<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>T3 Mutual Funds</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">

<style>
body {
	font-family: Tahoma, Geneva, sans-serif;
	font-weight: normal;
	background-color: #51626f !important;
	/* rgb(35, 31, 32) */
	color: #51626f;
}

a {
	text-decoration: none;
}

h1, h2, h3, h4 {
	font-family: Tahoma, Geneva, sans-serif;
	font-weight: normal;
}

.whitefont {
	color: white;
}

.dataTables_filter { //
	display: none;
}

.btn-left .btn {
	border-color: #51626f;
	background-color: #d9edf7;
}

.btn-left a {
	color: #51626f;
}

.panel-title {
	margin-top: 0;
	margin-bottom: 0;
	font-size: 20px;
	/* color: #51626f; */
}

.table>thead>tr>th, .table>thead>tr>td, .table>tbody>tr>th, .table>tbody>tr>td
	{
	vertical-align: middle;
}

.input-group-addon {
	background-color: #d9edf7;
}

.table-striped>tr:nth-child(odd) {
	background-color: red;
}

#transaction_filter {
	display: none;
}

.table-striped>tbody>tr:nth-child(odd)>td, .table-striped>tbody>tr:nth-child(odd)>th
	{
	background-color: #d9edf7;
}

.panel-default .panel-heading {
	color: #333;
	background-color: #d9edf7;
	border-color: #ddd;
}
</style>


<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>
<script>
	$(function() {
		$("#view").click(function() {
			$("#myModal").modal();
		});
	});
</script>
</head>

<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="row clearfix">
					<div class="col-md-1 column"></div>
					<div class="col-md-10 column">
						<div class="col-md-6 column whitefont"
							style="margin-top: 50px; font-weight: 900; font-size: 18px;">
							T3 Mutual Funds Management Platform</div>
						<div class="col-md-6 column whitefont"
							style="text-align: right; margin-top: 60px">
							<c:if test="${customer !=null}">
								Welcome, ${customer.getUserName()}! &nbsp;
								<a type="button" href="logout.do" class="btn btn-primary"
									role="button">Logout</a>
							</c:if>
						</div>
					</div>
				</div>

				<div class="container-fluid clearfix ">
					<div class="col-md-3 column whitefont">
						<div class="btn-group-vertical btn-group-lg" role="group"
							aria-label="...">
							<button type="button" class="btn btn-default "
								style="width: 224px">
								<a href="customer_viewaccount.do">View my account</a>
							</button>
							<button type="button" class="btn btn-default"
								style="width: 224px">
								<a href="customer_change_password.do">Change my password</a>
							</button>
						</div>
						<div class="btn-group-vertical btn-group-lg" role="group"
							aria-label="..." style="margin-top: 10px">
							<button type="button" class="btn btn-default ">
								<a href="customer-view-transactions.do">View transaction
									history</a>
							</button>
							<button type="button" class="btn btn-default">
								<a href="customer_search_fund.do">Buy/Research fund</a>
							</button>
							<button type="button" class="btn btn-default">
								<a href="customer-request-check.do">Request check</a>
							</button>
						</div>

					</div>
					<div class="col-md-9 column">
						<div class="panel " style="margin-top: 10px; font: #51626f">