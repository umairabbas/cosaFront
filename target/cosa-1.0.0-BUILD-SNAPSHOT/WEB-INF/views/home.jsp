<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url value="/resources/images/swc.png" var="swclogo"/>
<spring:url value="/resources/images/cosa.ico" var="cosaico"/>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>CoSA</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="icon" href="${cosaico}">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<style>
		.navbar{
			margin-bottom: 0;
		}
		
		#wellheader{
			margin-top: 50px;
		}
	</style>
</head>

<!-- header -->
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="/cosa/">CoSA</a>
		</div>
		<ul class="nav navbar-nav">
			<li class="active"><a href="/cosa">Home</a></li>
			<li><a href="/cosa/evolution">Evolution</a></li>
			<!-- <li class="active"><a href="/cosa/dsl">Custom DSL</a></li> -->
			<li><a href="/cosa/newdsl">DSL</a></li>
		</ul>
	</div>
</nav>

 
<body> 
	<div id="wellheader" class="well">
		<div class="container">
 			<h1>CoSA</h1>
 			<p>Mining and Visualizing <strong>Co</strong>de <strong>S</strong>mell <strong>A</strong>ctivity</p>
 		</div>
 	</div>
	<div class="container" style="margin-bottom:50px;">
		<!-- content -->
		<!-- <div contenteditable="true">Edit me!</div> -->
		<div class="row">
			<div class="col-sm-6">
				<div class="well well-lg" style="text-align:center;">
					<h1><span class="glyphicon glyphicon-search"></span></h1>
					<hr>
					<h3>Evolution</h3>
					<p style="text-align:justify;">Find code smells in the current repository's version and view the evolution of that code smells.</p>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="well well-lg" style="text-align:center;">
					<h1><span class="glyphicon glyphicon-pencil"></span></h1>
					<hr>
					<h3>Custom DSL</h3>
					<p style="text-align:justify;">Analyze the code smells in your local repository by using your own custom DSL (Domain Specific Language)</p>
				</div>
			</div>
		</div>
		<!-- end of content -->
	</div>
</body>

<!-- footer -->
<nav class="navbar navbar-inverse navbar-fixed-bottom">
	<div class="container-fluid" style="text-align:center; padding:10px;">
		<p style="color:white;display:inline;">Copyright <span class="glyphicon glyphicon-copyright-mark"></span> 2016 by <img src="${swclogo}" alt="SWC RWTH Aachen"></p>
	</div>
</nav>

<script>
	$(document).ready(function(){
		$("#wellheader").css("margin-top", $(".navbar").height());
		$(window).resize(function(){
			$("#wellheader").css("margin-top", $(".navbar").height());
		});
	});
</script>

</html>
