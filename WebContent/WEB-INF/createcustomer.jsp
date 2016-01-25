<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="employee-top.jsp" />

<div class="panel-heading">
    <h3 class="panel-title">Create Customer Account</h3>
</div>
<br>
<div class="panel-body">
    <div>
        <c:if test="${errors !=null && fn:length(errors) > 0}">
            <div class="alert alert-warning">
                <h4>Sorry, you have invalid input.</h4>

                <c:forEach var="error" items="${errors}">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${error}<br>
                </c:forEach>
            </div>
        </c:if>
    </div>

    <form role="form" style="font: #51626f">
        <div class="col-sm-6">
            <div class="form-group">
                <label for="exampleInputEmail1">User Name</label><input type="text"
                    class="form-control" name="userName" value=${form.userName} >
            </div>
            <div class="form-group">
                <label for="exampleInputEmail1">First Name</label><input type="text"
                    class="form-control" name="firstName" value=${form.firstName}>
            </div>
            <div class="form-group">
                <label for="exampleInputEmail1">Last Name</label><input type="text"
                    class="form-control" name="lastName" value=${form.lastName}>
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">Password</label><input
                    type="password" class="form-control" name="password"
                    value=${form.password}>
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">Confirm Password</label><input
                    type="password" class="form-control" name="confirm"
                    value=${form.confirm}>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
                <label for="exampleInputEmail1">Address Line 1</label><input
                    type="text" class="form-control" name="addressLine1"
                    value=${form.addressLine1}>
            </div>
            <div class="form-group">
                <label for="exampleInputEmail1">Address Line 2</label><input
                    type="text" class="form-control" name="addressLine2"
                    value=${form.addressLine2}>
            </div>
            <div class="form-group">
                <label for="exampleInputEmail1">City</label><input type="text"
                    class="form-control" name="city" value=${form.city}>
            </div>
            <div class="form-group">
                <label for="exampleInputEmail1">State</label>
                <!-- <input type="text"
                            class="form-control" name="state" value=${form.state}> -->
                <select class="form-control" name="state">
                    <option value="AL">Alabama</option>
                    <option value="AK">Alaska</option>
                    <option value="AZ">Arizona</option>
                    <option value="AR">Arkansas</option>
                    <option value="CA">California</option>
                    <option value="CO">Colorado</option>
                    <option value="CT">Connecticut</option>
                    <option value="DE">Delaware</option>
                    <option value="DC">District Of Columbia</option>
                    <option value="FL">Florida</option>
                    <option value="GA">Georgia</option>
                    <option value="HI">Hawaii</option>
                    <option value="ID">Idaho</option>
                    <option value="IL">Illinois</option>
                    <option value="IN">Indiana</option>
                    <option value="IA">Iowa</option>
                    <option value="KS">Kansas</option>
                    <option value="KY">Kentucky</option>
                    <option value="LA">Louisiana</option>
                    <option value="ME">Maine</option>
                    <option value="MD">Maryland</option>
                    <option value="MA">Massachusetts</option>
                    <option value="MI">Michigan</option>
                    <option value="MN">Minnesota</option>
                    <option value="MS">Mississippi</option>
                    <option value="MO">Missouri</option>
                    <option value="MT">Montana</option>
                    <option value="NE">Nebraska</option>
                    <option value="NV">Nevada</option>
                    <option value="NH">New Hampshire</option>
                    <option value="NJ">New Jersey</option>
                    <option value="NM">New Mexico</option>
                    <option value="NY">New York</option>
                    <option value="NC">North Carolina</option>
                    <option value="ND">North Dakota</option>
                    <option value="OH">Ohio</option>
                    <option value="OK">Oklahoma</option>
                    <option value="OR">Oregon</option>
                    <option value="PA">Pennsylvania</option>
                    <option value="RI">Rhode Island</option>
                    <option value="SC">South Carolina</option>
                    <option value="SD">South Dakota</option>
                    <option value="TN">Tennessee</option>
                    <option value="TX">Texas</option>
                    <option value="UT">Utah</option>
                    <option value="VT">Vermont</option>
                    <option value="VA">Virginia</option>
                    <option value="WA">Washington</option>
                    <option value="WV">West Virginia</option>
                    <option value="WI">Wisconsin</option>
                    <option value="WY">Wyoming</option>
                </select>

            </div>
            <div class="form-group">
                <label for="exampleInputEmail1">Zip code</label> <input type="text"
                    class="form-control" name="zip" value="${form.zip}"
                    pattern="\b\d{5}\b" placeholder="5 digit number">
            </div>
        </div>
        <div class="col-sm-6"></div>
        <div class="col-sm-6">
            <button type="submit" class="btn btn-primary" name="action"
                value="create">Create Customer Account</button>
    </form>
</div>

</div>
</div>

<%-- <jsp:include page="bottom.jsp" /> --%>

