<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<dl>
	<dt>Profile</dt>
	<dd>First Name:&nbsp;${customer.getFirstName()}
	<dd>Last Name:&nbsp;${customer.getLastName()}</dd>
	<dd>
		<c:if test="${customer.getAddrLine1() !=null}">Address:&nbsp;${customer.getAddrLine1()}
		</c:if>
		<c:if test="${customer.getAddrLine2() !=null && customer.getAddrLine2().length() != 0}">
			<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${customer.getAddrLine2()}
		</c:if>
	</dd>
	<dd>City:&nbsp;${customer.getCity()}&nbsp;State:&nbsp;${customer.getState()}&nbsp;</dd>
	<dd>Zip:&nbsp;${customer.getZip()}</dd>
	<dt>Cash Balance</dt>
	<dd>${currentAmount}</dd>
	<dt>Available Balance</dt>
	<dd>${validAmount}</dd>
</dl>