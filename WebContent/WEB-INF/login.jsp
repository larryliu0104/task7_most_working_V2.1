<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>T3 Mutual Funds</title>
<link rel="shortcut icon" type="image/x-icon" href="img/logo-new.png" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Expires" content="0">
<meta http-equiv="kiben" content="no-cache">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<link href="js/fullPage/jquery.fullPage.css" rel="stylesheet">

<style>
body {
	font-family: Tahoma, Geneva, sans-serif;
	font-weight: normal;
	background-color: #51626f;
	/* rgb(35, 31, 32) */
	color: white;
}

h1, h2, h3, h4 {
	font-family: Tahoma, Geneva, sans-serif;
	font-weight: normal;
}
</style>
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
  <![endif]-->

<!-- Fav and touch icons -->
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>
<script type="text/javascript" src="js/fullPage/jquery.fullPage.js"></script>
<script>
	/* 	$(document).ready(function() {
	 $('#fullpage').fullpage();
	 }); */
</script>
</head>

<body>
	<!-- 	<div class="container">
		<ul id="menu">
			<li data-menuanchor="customerPage" class="active"><a
				href="#firstPage">Customer Login</a></li>
			<li data-menuanchor="employeePage" class=""><a
				href="#secondPage">Employee Login</a></li>
		</ul>
	</div> -->

	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column" style="margin-left: 90px">
				<div class="col-md-3 column"></div>
				<div class="col-md-6 column" class="header"
					style="margin-top: 50px; font-weight: 900; font-size: 20px">
					<img src="img/logo-new.png" style="width: 200px"> T3 Mutual
					Funds
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<br> <br> <br>

			</div>
		</div>
		<div class="container">
			<div class="row clearfix">
				<!-- error message -->
				<div class="col-md-12 column">
					<div class="row clearfix">
						<div class="col-md-12 column">
							<c:if test="${errors !=null && fn:length(errors) > 0}">
								<div class="col-md-4 column"></div>
								<div class="col-md-4 column">
									<br>
									<div class="panel panel-default" style="color: #51626f">
										<div class="panel-heading" style="color: #51626f">
										Error Input</div>
										<div class="panel-body">
											<c:forEach var="error" items="${errors}">
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${error}<br>
											</c:forEach>
										</div>
									</div>

								</div>
								<div class="col-md-4 column"></div>

							</c:if>



						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="container" id="fullpage">
			<div class="row clearfix">
				<div class="col-md-12 column section" id="firstPage">
					<div class="row clearfix">
						<div class="col-md-6 column">
							<div class="form">
								<form role="form" action="login.do" class="form-horizontal">
									<div class="form-group">
										<label for="exampleInputEmail1" class="col-sm-6 control-label">User
											Name/Email</label>
										<div class="col-sm-6">
											<input type="text" class="form-control" name="userName">
										</div>
									</div>
									<div class="form-group">
										<label for="exampleInputPassword1"
											class="col-sm-6 control-label">Password</label>
										<div class="col-sm-6">
											<input type="password" class="form-control" name="password">
										</div>
									</div>
									<div class="form-group">
										<label for="exampleInputPassword1"
											class="col-sm-6 control-label"></label>
										<div class="col-sm-6">
											<button type="submit" class="btn btn-primary" name="action"
												value="customer_login">Customer Login</button>
										</div>
								</form>
							</div>
						</div>
					</div>
					<div class="col-md-6 column">
						<form role="form" action="login.do" class="form-horizontal">
							<div class="form-group">
								<label for="exampleInputEmail1" class="col-sm-6 control-label">User
									Name/Email</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" name="userName">
								</div>
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1"
									class="col-sm-6 control-label">Password</label>
								<div class="col-sm-6">
									<input type="password" class="form-control" name="password">
								</div>
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1" class="col-sm-6 control-label"></label>
								<div class="col-sm-6">
									<button type="submit" class="btn btn-primary"
										class="control-label" name="action" value="employee_login">Employee
										Login</button>
								</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!--  bottom copyright-->
	<!-- 	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<p style="text-align: center">
					<small>Copyright &copy; 2016 T3 Group, Inc </small>
				</p>
			</div>
		</div>
	</div> -->

</body>
</html>