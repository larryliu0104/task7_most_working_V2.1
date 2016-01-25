<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>T3 Mutual Funds</title>
<link rel="shortcut icon" type="image/x-icon" href="img/logo-new.png" />

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!--link rel="stylesheet/less" href="less/bootstrap.less" type="text/css" /-->
<!--link rel="stylesheet/less" href="less/responsive.less" type="text/css" /-->
<!--script src="js/less-1.3.3.min.js"></script-->
<!--append â#!watchâ to the browser URL, then refresh the page. -->

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
    color: #51626f;
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

.panel-default>.panel-heading {
    color: #333;
    background-color: #d9edf7;
    border-color: #ddd;
}
</style>
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
  <![endif]-->

<script type="text/javascript"
    src="https://code.jquery.com/jquery-2.1.4.js"></script>
<script type="text/javascript"
    src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.5/js/bootstrap.js"></script>
<script type="text/javascript"
    src="https://cdn.datatables.net/1.10.10/js/jquery.dataTables.js"></script>
<script type="text/javascript"
    src="https://cdn.datatables.net/1.10.10/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
    src="https://cdn.datatables.net/autofill/2.1.0/js/dataTables.autoFill.js"></script>
<script type="text/javascript"
    src="https://cdn.datatables.net/autofill/2.1.0/js/autoFill.bootstrap.js"></script>
<script type="text/javascript"
    src="https://cdn.datatables.net/buttons/1.1.0/js/dataTables.buttons.js"></script>
<script type="text/javascript"
    src="https://cdn.datatables.net/buttons/1.1.0/js/buttons.bootstrap.js"></script>

<script>
    /*     $(function() {
     $("#view").click(function() {
     $("#myModal").modal();
     });
     }); */
    $(document).ready(function() {

        $('#example').DataTable({
            "lengthMenu" : [ [ 5, 10, -1 ], [ 5, 10, "All" ] ],
            /* "dom": '<"top"i>rt<"bottom"flp><"clear">', */
            "dom" : '<"top"f>rt<"bottom"lip><"clear">',
            "aoColumnDefs" : [ {
                "bSortable" : false,
                "aTargets" : [ 3, 4 ]
            } ]
        });
        $('#transaction').DataTable({
            "lengthMenu" : [ [ 5, 10, -1 ], [ 5, 10, "All" ] ],
            /* "dom": '<"top"i>rt<"bottom"flp><"clear">', */
            "dom" : '<"top"f>rt<"bottom"lip><"clear">',
            "aoColumnDefs" : [ {
                "bSortable" : false,
                "aTargets" : [ 4 ]
            } ]
        });
        /*      $("#searchbox").keyup(function() {
        
         filterGlobal();
         });    */
    });
</script>
</head>

<body>
    <div class="container">
        <div class="clearfix">
            <div class="col-md-12 column">
                <div class="container-fluid clearfix">
                    <div class="col-md-6 column whitefont"
                        style="margin-top: 50px; font-weight: 900; font-size: 20px;">
                        T3 Mutual Funds Management Platform</div>
                    <div class="col-md-6 column whitefont"
                        style="text-align: right; margin-top: 60px">
                        <c:if test="${employee !=null}">
                                Welcome, ${employee.getUserName()}! &nbsp;
                                <a type="button" href="logout.do" class="btn btn-primary"
                                role="button">Logout</a>
                        </c:if>
                    </div>

                </div>
                <div class="container-fluid clearfix ">
                    <div class="col-md-3 btn-left column whitefont"
                        style="margin-top: 10px">

                        <div class="btn-group-vertical btn-group-lg" role="group"
                            aria-label="...">
                            <button type="button" class="btn btn-default ">
                                <a href="employee_register.do">Create Employee Account</a>
                            </button>
                            <button type="button" class="btn btn-default">
                                <a href="employee_change_pwd.do">Change Your Password</a>
                            </button>
                        </div>


                        <div class="btn-group-vertical btn-group-lg" role="group"
                            aria-label="..." style="margin-top: 20px">
                            <button type="button" class="btn btn-default">
                                <a href="employee_create_customer.do">Create Customer
                                    Account</a>
                            </button>
                            <button type="button" class="btn btn-default">
                                <a href="employee_search_customer.do">View Customer Account</a>
                            </button>
                        </div>
                        <div class="btn-group-vertical btn-group-lg" role="group"
                            aria-label="..." style="margin-top: 20px">
                            <button type="button" class="btn btn-default"
                                style="width: 237px">
                                <a href="employee_create_fund.do">Create New Fund </a>
                            </button>
                            <button type="button" class="btn btn-default"
                                style="width: 237px">
                                <a href="employee-transition-day.do">Transaction Day </a>
                            </button>
                        </div>

                        <!-- <ul class="nav nav-pills nav-stacked" style="font-size: 16px">
                            <li role="presentation" class="active"><a
                                href="employee_register.do">Create Employee Account</a></li>
                            <li role="presentation" class="active"><a
                                href="employee_change_pwd.do">Change Your password</a></li>
                            <li role="separator" class="divider"></li>
                                
                            <li role="presentation" class="active"><a
                                href="employee_create_customer.do">Create Customer Account</a></li>
                            <li role="presentation" class="active"><a
                                href="employee_search_customer.do">View Customer Account</a></li>
                            <li role="presentation" class="active"><a
                                href="employee_create_fund.do">Create New Fund</a></li>
                            <li role="presentation" class="active"><a
                                href="employee-transition-day.do">Transition Day</a></li>
                            <li role="presentation"></li>
                        </ul> -->
                    </div>
                    <div class="col-md-9 column">
                        <div class="panel panel-default"
                            style="margin-top: 10px; font: #51626f；">