<jsp:include page="customer-top.jsp" />
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<div class="panel-heading">
			<h3 class="panel-title"><b>Sell Fund</b></h3>
		</div><jsp:include page="template-result.jsp" />
		<div class="panel-body">
<% 
				String currentShare = request.getAttribute("currentShare").toString();
				out.println("Your total shares: <b>" + currentShare + "</b>");
%>
			</p>
			<p>
<% 
				String pendingShare = request.getAttribute("pendingShare").toString();
				out.println("Your pending shares: <b>" + pendingShare + "</b>");
%>
			</p>

			<p>
<% 
				String validShare = request.getAttribute("validShare").toString();
				out.println("Your available shares: <b>" + validShare + "</b>");
%>
			</p>
			<br></br>
<%-- 					       <c:if test="${fund ==null}"> --%>
<!-- 								You have no fund now!!! &nbsp; -->
<!-- 								<a type="button" href="logout.do" class="btn btn-primary btn-sm" -->
<!-- 									role="button">Logout</a> -->
<%-- 							</c:if> --%>
<%-- 		<c:if test="${fund !=null}"> --%>
			<form class="form-horizontal">
				<div class="form-group">
					<label class="col-sm-2 control-label">
						Fund Name: 
					</label>
					<div class="col-sm-10">
						<a href="customer_fund_detail.do?fundId=${fund.getId()}">${fund.getName() }</a>
					</div>
				</div>
				<div class="form-group">
					<input type="hidden" style="width: 30%" name="fundId" value="${fund.getId()}">
					<label class="col-sm-2 control-label">
						Share: 
					</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="share">
					</div>
				</div>
			<br>
				<div class="form-group">
    				<div class="col-sm-offset-2 col-sm-10">
      					<button type="submit" class="btn btn-primary" name="action" 
						value="sell">Sell</button>
   					</div>
  				</div>
			</form>
			<br></br>
		</div>
<%--     </c:if> --%>
<jsp:include page="bottom.jsp" />

