<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:include page="customer-top.jsp" />
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="css/style.css" type="text/css" rel="stylesheet">
<style type="text/css">
.box {
	background: #fff;
}

.box>h3 {
	color: #333;
	font-size: 17px;
	font-weight: 400;
	padding-left: 20px;
	padding-top: 20px;
}

.box>p {
	color: #919599;
	font-size: 1.4rem;
	font-weight: 400;
	line-height: 18px;
	padding-bottom: 20px;
	padding-left: 20px;
}

.bdt {
	
}

.bdt thead {
	
}

.bdt>thead>tr>th, .bdt>tbody>tr>td {
	font-size: 13px;
}

.bdt tr:last-child td, .bdt tr:last-child th {
	border-bottom: 1px solid #f0f1f5;
}

.bdt>tbody>tr>td, .bdt>tbody>tr>th, .bdt>tfoot>tr>td, .bdt>tfoot>tr>th,
	.bdt>thead>tr>td, .bdt>thead>tr>th {
	border-color: #e6e7ed;
	padding: 17px 25px;
}

.search-form .form-control {
	border-color: #e6e7ed;
	border-radius: 0;
	box-shadow: none;
	font-weight: 400;
	height: 56px;
	margin-left: 20px
}

#page-rows-form {
	padding-left: 20px;
}

#page-rows-form label {
	line-height: 40px;
	padding-right: 10px;
}

#page-rows-form select {
	background-clip: padding-box;
	border-color: #e6e7ed;
	border-radius: 0;
	box-shadow: none;
	font-weight: 400;
	height: 56px;
	margin: 0;
	outline: 0 none;
	padding: 13px;
}

#table-footer {
	margin-bottom: 20px;
}

.pagination {
	padding-right: 20px
}

.pagination>.disabled>span, .pagination>.disabled>span:hover,
	.pagination>.disabled>span:focus, .pagination>.disabled>a, .pagination>.disabled>a:hover,
	.pagination>.disabled>a:focus {
	border-color: #fff;
}

.pagination>li {
	display: inline-block;
	margin: 0 2px;
}

.pagination>li>a, .pagination>li>span {
	border: none;
}

.pagination>li>a, .pagination>li>span {
	background: #ebecf2;
	height: 56px;
	line-height: 56px;
	padding: 0;
	text-align: center;
	width: 56px;
	color: #333;
}

.pagination>li>a:hover, .pagination>li>span:hover {
	background: #E2E3EC;
}

.pagination>li:last-child>a, .pagination>li:last-child>span {
	border-bottom-right-radius: 0;
	border-top-right-radius: 0;
	color: #363f45;
}

.pagination>li:first-child>a, .pagination>li:first-child>span {
	border-bottom-left-radius: 0;
	border-top-left-radius: 0;
}

.pagination>li.active>a, .pagination>li.active>span {
	font-weight: 700;
}

.table-info {
	font-size: 12px;
	line-height: 50px;
	margin-left: 40px;
}
</style>
<div class="search_result">
	<div class="container">
		<div class="row">
			<div class="col-lg-8">
				<div class="box clearfix">
					<h3>Search a fund and choose operation</h3>
					<p></p>
					<table class="table table-hover" id="bootstrap-table"">
						<thead>
							<tr>
								<th>Name</th>
								<th>Ticker</th>
								<th>Operation</th>
							</tr>
						</thead>
						<tbody>
							<tr>
	                                             <c:forEach var="fund" items="${funds}">
                     <tr>
                     <td><a href="customer_research_fund.do?fundId=${fund.getId()}">${fund.getName()}</a>"</td>
                     <td>${fund.getTicker()}</td>
                     <td>					
                     <div class="dropdown">
                     <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                     Operation
                     <span class="caret"></span>
                     </button>
                     <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                     <li><a href="customer_buy_fund.do?fundId=${fund.getId()}">
                     Buy fund
                     </a></li>
                     </ul>
                     </div>
                     </td>
                     </tr>
                     </c:forEach>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="http://code.jquery.com/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script src="Bootstrap table/js/vendor/jquery.sortelements.js"
	type="text/javascript"></script>
<script src="Bootstrap table/js/jquery.bdt.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		$('#bootstrap-table').bdt();
	});
</script>