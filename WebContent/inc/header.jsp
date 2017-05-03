<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title><c:out value="${ tittle }" /></title>
        <link type="text/css" rel="stylesheet" href="<c:url value="inc/style.css"/>"/>
        <link href="<c:url value="bootstrap/css/bootstrap.css"/>" rel="stylesheet"/>
    </head>
    <body>
    	<div class="container">
    		<div class="starter-template">
    		<div class="row">
    			<a href="<c:url value="index"/>">
    				<div class="col-lg-1"><img width="50" alt="Logo BSA" src="<c:url value="/inc/images/logoBSA.jpg"/>"></div>
    			</a>
    			<div class="col-lg-11"><h1><c:out value="${ tittle }" /></h1></div>
    		</div>
    			<div class="row">
    				<c:import url="/inc/menu.jsp"/>
    				<ul class="col-lg-9">