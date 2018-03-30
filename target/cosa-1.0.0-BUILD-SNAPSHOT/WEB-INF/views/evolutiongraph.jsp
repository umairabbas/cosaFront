<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.google.gson.Gson"%>
<%@ page import="java.util.*" %>
<%@ page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
	String json = new Gson().toJson(request.getAttribute("evolutionresult"));
%>

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
	<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
  	<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-route.js"></script>
  	<script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
	<style>
		.navbar{
			margin-bottom: 0;
		}

		.well{
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
			<li><a href="/cosa">Home</a></li>
			<li class="active"><a href="/cosa/evolution">Evolution</a></li>
			<!-- <li class="active"><a href="/cosa/dsl">Custom DSL</a></li> -->
			<li><a href="/cosa/newdsl">DSL</a></li>
		</ul>
	</div>
</nav>

 
<body data-ng-app="main" data-ng-controller="mainCtl" data-ng-init="init()"> 
	<div class="well">
		<div class="container">
 			<h1>CoSA</h1>
 			<p>Mining and Visualizing <strong>Co</strong>de <strong>S</strong>mell <strong>A</strong>ctivity</p>
 		</div>
 	</div>
	<div class="container" style="margin-bottom:75px;">
		<!-- content -->
		<h3>Class ${codesmell} Evolution <span data-toggle="modal" data-target="#evolutionmodal"><span data-toggle="tooltip" data-placement="right" title="How to intepret the data" class="glyphicon glyphicon-question-sign"></span></span></h3>
			<hr>
		<div class="well well-lg" style="color:black;">
	  		<div id="viewevolve3" id="plotdiv"></div>
	 	</div>
	 	<div class="well well-lg" style="color:black;">
	  		<div id="viewevolve" id="plotdiv"></div>
	 	</div>
	 	<div class="well well-lg" style="color:black;">
	  		<div id="viewevolve2" id="plotdiv"></div>
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

<!-- Evolution Modal -->
<div class="modal fade" id="evolutionmodal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
         	 	<h3 class="modal-title">How to intepret</h3>
			</div>
			<div class="modal-body">
          		<ul class="nav nav-tabs">
          			<li class="active"><a data-toggle="tab" href="#severitytab">Severity Level</a></li>
          			<li><a data-toggle="tab" href="#activitytab">Activity Level</a></li>
          		</ul>
          		
          		<div class="tab-content">
          			<div id="severitytab" class="tab-pane fade in active">
          				<h4>Severity Level</h4>
          				<p>
          					The value of Severity Level is in the interval of 0 to 3. 3 indicates that the corresponding class has the most severe code smell among other classes, vice versa.
          				</p>
          				<br/>
          				<h4>What should to interpret</h4>
          				<p>
          					When a class has the highest severity, it means that this class will be hard to be maintain (due to its complexity and metrics value) compare to other classes.
          				</p>
          			</div>
          			<div id="activitytab" class="tab-pane fade">
          				<h4>Actvity Level</h4>
          				<p>
          					The value of Activity Level is either 0, 1, 2, 3, in which:
          				</p>
          				<ul>
          					<li>0 = Dormant</li>
          					<li>1 = Ameliorate Active</li>
          					<li>2 = Stable Active</li>
          					<li>3 = Strong Active</li>
          				</ul>
          				<br/>
          				<h4>What should to interpret</h4>
          				<ol>
          				<li>
          					When a class has a lot of Strong Active point, it means that this class change frequently in the past and for each changes, it increase the code smell intensity. The higher the intensity, will make the code smell harder to maintain.
          				</li>
          				<li>
          					When a class has a lot of Stable Active, it means that this class change frequently but most probably just a slight or little changes.
          				</li>
          				<li>
          					When a class has a lot of Ameliorate Active, it means that this class may be being the subject of refactoring, which can indicates a good evolution
          				</li>
          				<li>
          					When a class has a Dormant, it means there is no changes occurred to this class.
          				</li>
          				</ol>
          			</div>
          		</div>
        	</div>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		$('[data-toggle="tooltip"]').tooltip();
		
		$(".well").css("margin-top", $(".navbar").height());
		$("#plotdiv").css("height", (window.outerHeight - $(".navbar").height() - 50) * 75 / 100);
		
		$(window).resize(function(){
			$(".well").css("margin-top", $(".navbar").height());
			$("#plotdiv").css("height", (window.outerHeight - $(".navbar").height() - 50) * 75 / 100);
		});
	});
	
	var app = angular.module("main",[]);
	app.controller('mainCtl', function($scope){
		$scope.init = function(){
			var res = JSON.stringify(<%= json %>);
			var obj = JSON.parse(res);
			$scope.data = obj;
			
			var evolution = obj.evolution;
			
			//setting for x-axis
			var datax = new Array();
			//setting for y-axis
			var datay = new Array();
			var datay2 = new Array();
			var datay3 = new Array();
			
			datax[0] = 0;
			datay[0] = 0;
			datay2[0] = 0;
			datay3[0] = 0;
			
			for(var i = 0 ; i < evolution.length; i++){
				datax[i+1] = (i+1);
				datay[i+1] = evolution[i].severityLevel;
				datay2[i+1] = evolution[i].activityDegree;
				datay3[i+1] = evolution[i].severityImpact;
			}
			
			//first plot
			var evolvedata = {
				x	: datax,
				y	: datay,
				mode: 'lines+markers',
				name: String(evolution[0].className)
			};
			
			var data = [evolvedata];
			
			var evolvelayout = {
				title: evolution[0].className + ' Evolution',
				xaxis: {
					title: 'Commit'
				},
				yaxis: {
					title: 'Impact'
				}
			};
			
			Plotly.newPlot('viewevolve', data, evolvelayout);
			
			//second plot
			
			var evolvedata2 = {
				x	: datax,
				y	: datay2,
				mode: 'lines+markers',
				name: String(evolution[0].className)
			};
			
			var data2 = [evolvedata2];
			
			var evolvelayout2 = {
				title: evolution[0].className + ' Evolution',
				xaxis: {
					title: 'Commit'
				},
				yaxis: {
					title: 'Activeness'
				}
			};
			
			Plotly.newPlot('viewevolve2', data2, evolvelayout2);
			
			//severity impact plot
			var evolvedata3 = {
				x	: datax,
				y	: datay3,
				mode: 'lines+markers',
				name: String(evolution[0].className)
			};
			
			var data3 = [evolvedata3];
			
			var evolvelayout3 = {
				title: evolution[0].className + ' Evolution',
				xaxis: {
					title: 'Commit'
				},
				yaxis: {
					title: 'Impact Value'
				}
			};
			
			Plotly.newPlot('viewevolve3', data3, evolvelayout3);
		}
	});
</script>
</html>
