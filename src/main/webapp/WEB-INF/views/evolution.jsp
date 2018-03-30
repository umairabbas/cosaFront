<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>
<%@ page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url value="/resources/images/swc.png" var="swclogo"/>
<spring:url value="/resources/slider/bootstrap-slider.min.css" var="slidercss"/>
<spring:url value="/resources/slider/bootstrap-slider.min.js" var="sliderjs"/>
<spring:url value="/resources/images/cosa.ico" var="cosaico"/>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>CoSA</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="${slidercss}">
	<link rel="icon" href="${cosaico}">
	<script src="${sliderjs}"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<style>
		.navbar{
			margin-bottom: 0;
		}

		.well{
			margin-top: 50px;
		}
		
		#slider .slider-track-high {
			background: #FF0000;
		}
		
		#slider .slider-selection {
			background: #E8770C;
		}
		
		#slider2 .slider-track-high {
			background: #FF0000;
		}
		
		#slider2 .slider-selection {
			background: #E8770C;
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

 
<body> 
	<div class="well">
		<div class="container">
 			<h1>CoSA</h1>
 			<p>Mining and Visualizing <strong>Co</strong>de <strong>S</strong>mell <strong>A</strong>ctivity</p>
 		</div>
 	</div>
	<div class="container">
		<!-- content -->
		<!-- <div contenteditable="true">Edit me!</div> -->
		<h2>Find Analyzed VCS</h2>
		<form class="form-horizontal" method="POST" action="/cosa/doEvolve">
			<div class="form-group">
				<label class="control-label col-sm-3" for="cvsrootdiv">VCS Root : </label>
				<div id="cvsrootdiv" class="dropdown col-sm-9">
					<%
						List<Map<String, String>> alldata = (List<Map<String, String>>)request.getAttribute("idAndVcsroot");
						if(alldata.size() > 0){
					%>
					<button id="cvsrootbtn" class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Analyzed VCS Root <span class="caret"></span></button>
					<%
						} else{
					%>
					<button id="cvsrootbtn" class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">No Analyzed VCS Root Found <span class="caret"></span></button>
					<%
						}
					%>
					
					<ul id="csvrootul" class="dropdown-menu">
						<% 
							for(Map<String, String> temp : alldata ){
						%>
							<li value="<%= temp.get("id")%>"><a href="#"><%= temp.get("vcsroot")%></a></li>
						<%
							}
						%>
					</ul>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-3" for="codesmelltypediv">Code Smell Types : </label>
				<div id="codesmelltypediv" class="dropdown col-sm-9">
					<button id="codesmelltypebtn" class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">All <span class="caret"></span></button>
					<ul id="codesmelltypeul" class="dropdown-menu">
						<li value="1"><a href="#">All</a></li>
						<li value="2"><a href="#">God Class</a></li>
						<li value="3"><a href="#">Brain Class</a></li>
						<li value="4"><a href="#">Data Class</a></li>
					</ul>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-3" for="activitytypediv">Code Smell Activity Types : </label>
				<div id="activitytypediv" class="dropdown col-sm-9">
					<button id="activitytypebtn" class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">All <span class="caret"></span></button>
					<ul id="activitytypeul" class="dropdown-menu">
						<li value="1"><a href="#">All</a></li>
						<li value="2"><a href="#">Strong Active</a></li>
						<li value="3"><a href="#">Stable Active</a></li>
						<li value="4"><a href="#">Ameliorate Active</a></li>
						<li value="5"><a href="#">Dormant</a></li>
					</ul>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-3" for="activityrangediv">ACT Proportion : </label>
				<div id="activityrangediv">
					<input id="activityrange" type="text"/><br/>
					<input name="activityrangeresult" id="activityrangeresult" type="hidden" value="50"/>
				</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-sm-3" for="severityrangediv">IMP Proportion : </label>
				<div id="severityrangediv">
					<input id="severityrange" type="text"/><br/>
					<input name="severityrangeresult" id="severityrangeresult" type="hidden" value="50"/>
				</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-sm-3"></label>
				<div>
					<span class="glyphicon glyphicon-stop" style="color: #E8770C;"></span> Low <span class="glyphicon glyphicon-stop" style="color: red;"></span> High
				</div>
			</div>
			
			<div class="form-group">
				<%
					if(alldata.size() > 0){
				%>
				<input type="hidden" id="csvroot" name="cvsroot" value="<%= alldata.get(0).get("id") %>">
				<%
					}
				%>
				<input type="hidden" id="codesmelltype" name="codesmelltype" value="1">
				<input type="hidden" id="activitytype" name="activitytype" value="1">
				<div class="col-sm-offset-3 col-sm-9">
					<%
						if(alldata.size() > 0){
					%>
					<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> Find</button>
					<%
						}else{
					%>
					<button id="cannotfind" type="button" class="btn btn-default disabled"><span class="glyphicon glyphicon-search"></span> Find</button>
					<%
						}
					%>
					
				</div>
			</div>
		</form>
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
	$(document).ready(function() {
		$(".well").css("margin-top", $(".navbar").height());
		
		$(window).resize(function(){
			$(".well").css("margin-top", $(".navbar").height());
		});
		
		$("#csvrootul li").click(function(){
			$("#cvsrootbtn").html($(this).text() + " <span class='caret'></span>");
			$("#csvroot").val($(this).attr("value"));
		});

		$("#codesmelltypeul li").click(function(){
			$("#codesmelltypebtn").html($(this).text() + " <span class='caret'></span>");
			$("#codesmelltype").val($(this).attr("value"));
		});

		$("#activitytypeul li").click(function(){
			$("#activitytypebtn").html($(this).text() + " <span class='caret'></span>");
			$("#activitytype").val($(this).attr("value"));
		});
		
		$("#cannotfind").click(function(){
			alert("You have to analyze your local repository first!");
		});
		
		var slider = new Slider("#activityrange", { id: "slider", min: 0, max: 100, value: 50 });
		
		slider.on("slide", function(sliderVal){
			document.getElementById("activityrangeresult").value = sliderVal;
		});
		
		var slider2 = new Slider("#severityrange", { id: "slider2", min: 0, max: 100, value: 50 });
		
		slider2.on("slide", function(sliderVal){
			document.getElementById("severityrangeresult").value = sliderVal;
			document.getElementById("res").value = sliderVal;
		});
	});
</script>
</html>
