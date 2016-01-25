<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:include page="customer-top.jsp" />
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="css/style.css" type="text/css" rel="stylesheet">
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Line Chart</title>
		<script src="js/Chart.js"></script>
	</head>
	<body>
		<span style="font-size: 30px;">Price changes for the last 7 transitions</span>
		<br/>
		<span style="font-size: 20px;">Fund Name: &nbsp; <span style="font-weight: bolder;">${fund.getName()}</span></span>
		<br/>
		<span style="font-size: 20px;">Fund Ticker: &nbsp; <span style="font-weight: bolder;">${fund.getTicker()}</span></span>
		<div>
		</div>
		<div style="width:50%">
			<div>
				<canvas id="canvas" height="450" width="600"></canvas>
			</div>
		</div>


	<script>
		var randomScalingFactor = function(){ return Math.round(Math.random()*100)};
		var lineChartData = {
			labels : 
			[
				<c:forEach var="price" items="${prices}">
				"${price.getPriceDate()}" ${!items.last ? ',' : ''}
				</c:forEach>
			 ],
			datasets : [
				{
					label: "Price changes of the fund",
					fillColor : "rgba(220,220,220,0.2)",
					strokeColor : "rgba(220,220,220,1)",
					pointColor : "rgba(220,220,220,1)",
					pointStrokeColor : "#fff",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgba(220,220,220,1)",
					data : //[randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
			[
				<c:forEach var="price" items="${prices}">
				${price.getPrice()} ${!items.last ? ',' : ''}
				</c:forEach>
			 ]
				}
			]

		}

	window.onload = function(){
		var ctx = document.getElementById("canvas").getContext("2d");
		window.myLine = new Chart(ctx).Line(lineChartData, {
			responsive: true
		});
	}


	</script>
	</body>
</html>
