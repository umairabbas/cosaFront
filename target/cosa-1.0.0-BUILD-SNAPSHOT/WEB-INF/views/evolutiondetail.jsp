<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.google.gson.Gson"%>
<%@ page import="java.util.*" %>
<%@ page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
	String json = new Gson().toJson(request.getAttribute("codesmells"));
%>

<spring:url value="/resources/images/swc.png" var="swclogo"/>
<spring:url value="/resources/images/cosa.ico" var="cosaico"/>
<spring:url value="/resources/images/excel.svg" var="excelsvg"/>
<spring:url value="/resources/images/activityexample.png" var="activityexample"/>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>CoSA</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="icon" href="${cosaico}">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
  	<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-route.js"></script>
	<style>
		.navbar{
			margin-bottom: 0;
		}

		.well{
			margin-top: 50px;
		}
		
		.panel-group{
			margin-bottom: 80px;
		}
		
		.harmfulnessmapping{
			height:150px;
			text-align:center;
			padding-top:30px;
			color:white;
		}
		
		#activenessinfo{
			position:absolute;
			right:0;
			bottom:0;
			-webkit-transform: rotate(-90deg);
			transform: rotate(-90deg);
		}
		
		#svgButton {
		    background-color: Transparent;
		    background-repeat:no-repeat;
		    border: none;
		    overflow: hidden;
		    outline:none;
		}
		
		.harmfulnesslegend{
			color:white;
			border: 1px solid;
			border-color:black;
		}
		/* #codesmellresult {
		  background-image: linear-gradient(180deg, #FF0000, #00FF00);
		} */
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
	<div class="container">
		<!-- content -->
		<h3>Analysis Result</h3>
		
		<div class="panel-group">
			<!-- first panel -->
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="row">
						<div class="col-sm-6">Analysis Detail</div>
						<div class="col-sm-6" style="text-align:right;">
							<a data-toggle="collapse" data-target="#analysisdetailpanel">
								<span class="glyphicon glyphicon-resize-vertical"></span>
							</a>
						</div>
					</div>
				</div>
				<div id="analysisdetailpanel" class="panel-collapse collapse">
					<div class="panel-body">
						<table class="table table-hover table-bordered">
							<tr>
								<td><strong>VCS Root</strong></td>
								<td><%= request.getAttribute("cvsroot") %></td>
							</tr>
							<tr>
								<td><strong>Last Analysis</strong></td>
								<td><%= request.getAttribute("analyzedDate") %></td>
							</tr>
							<tr>
								<td><strong>Commit Date</strong></td>
								<td><%= request.getAttribute("commitDate") %></td>
							</tr>
							<tr>
								<td><strong>Total Version</strong></td>
								<td><%= request.getAttribute("totalVersion") %></td>
							</tr>
							<tr>
								<td><strong>Number Of Problem</strong></td>
								<td><%= request.getAttribute("numOfProblem") %></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			
			<div class="panel panel-info">
				<div class="panel-heading">
					Harmfulness Mapping
				</div>
				<div class="panel-body">
					<p>Click the classification below to get the list of corresponding class</p>
					<div id="container">
						<div class="row">
							<div class="col-sm-3"></div>
							<div id="high" class="col-sm-3 harmfulnessmapping" data-ng-click="classificationType = 'High'" style="background-color:#FF4A0D; ">
								<h3>${numOfHigh}</h3>
								<h4>HIGH</h4>	
							</div>
							<div id="harmful" class="col-sm-3 harmfulnessmapping" data-ng-click="classificationType = 'VeryHigh'" style="background-color:#FF000D;">
								<h3>${numOfVeryHigh}</h3>
								<h4>VERY HIGH</h4>	
							</div>
							<div class="col-sm-3">
								<h4>Legend:</h4>
								<div class="row" style="margin-left:5px;">
									<div class="col-sm-4 harmfulnesslegend" data-toggle="tooltip" data-placement="right" title="High Activity and High Severity" style="background-color:#FF000D;">
										Very High
									</div>
								</div>
								<div class="row" style="margin-left:5px;">
									<div class="col-sm-4 harmfulnesslegend" data-toggle="tooltip" data-placement="right" title="High Activity and Low Severity" style="background-color:#FF4A0D;">
										High
									</div>
								</div>
								<div class="row" style="margin-left:5px;">
									<div class="col-sm-4 harmfulnesslegend" data-toggle="tooltip" data-placement="right" title="Low Activity and High Severity" style="background-color:#FFA10D;">
										Medium
									</div>
								</div>
								<div class="row" style="margin-left:5px;">
									<div class="col-sm-4 harmfulnesslegend" data-toggle="tooltip" data-placement="right" title="Low Activity and Low Severity" style="background-color:#FFDC0D;">
										Low
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-3">
								<div id="activenessinfo">
									<p>Activity Level <span class="glyphicon glyphicon-chevron-right"></span> </p>
								</div>
							</div>
							<div id="harmless" class="col-sm-3 harmfulnessmapping" data-ng-click="classificationType = 'Low'" style="background-color:#FFDC0D;">
								<h3>${numOfLow}</h3>
								<h4>LOW</h4>	
							</div>
							<div id="normal" class="col-sm-3 harmfulnessmapping" data-ng-click="classificationType = 'Medium'" style="background-color:#FFA10D;">
								<h3>${numOfMedium}</h3>
								<h4>MEDIUM</h4>	
							</div>
							<div class="col-sm-3">
								<h4>Filter:</h4>
								<p>You choose <strong>{{classificationType}}</strong></p>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-3"></div>
							<div class="col-sm-6" style="text-align:center;padding-top:10px;">
								<p>Severity Level <span class="glyphicon glyphicon-chevron-right"></span> </p>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="panel panel-info">
				<div class="panel-heading">
					Download Document
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-1">
							Export as 
						</div>
						<div class="col-sm-11">
							<form method="POST" action="/cosa/downloadExcel/">
								<input type="hidden" name="excelinfo" value='{{codesmells}}'>
								<button type="submit" id="svgButton">
									<img src="${excelsvg }" style="width:25px;height:25px;">
								</button>
							</form>
						</div>
					</div>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-1">
							Export All Version
						</div>
						<div class="col-sm-11">
							<form method="POST" action="/cosa/downloadExcelAllVersion/">
								<input type="hidden" name="rootId" value='<%= request.getAttribute("id")%>'>
								<button type="submit" id="svgButton">
									<img src="${excelsvg }" style="width:25px;height:25px;">
								</button>
							</form>
						</div>
					</div>
				</div>
			</div>
			
			<!-- third panel -->
			<div class="panel panel-info">
				<div class="panel-heading">
					Column Filter
				</div>
				<div class="panel-body">
					<label class="checkbox-inline"><input type="checkbox" data-ng-click="showProg = !showProg" checked>Programming Language</label>
					<label class="checkbox-inline"><input type="checkbox" data-ng-click="showCodesmell = !showCodesmell" checked>Codesmell Type</label>
					<label class="checkbox-inline"><input type="checkbox" data-ng-click="showActivity = !showActivity" checked>Activity</label>
					<label class="checkbox-inline"><input type="checkbox" data-ng-click="showHarmfulnessLevel = !showHarmfulnessLevel" checked>Harmfulness Level</label>
					<label class="checkbox-inline"><input type="checkbox" data-ng-click="showActivenessLevel = !showActivenessLevel" checked>Activeness Level</label>
				</div>
			</div>
			
			<!-- fourth panel -->
			<div class="panel panel-info">
				<div class="panel-heading">
					Code Smell Detail
				</div>
				<div class="panel-body">
					<table id="codesmellresult" class="table table-hover table-bordered">
						<thead style="background-color:white;">
							<tr>
								<th>Class Name</th>
								<th data-ng-show="showProg">Programming Language</th>
								<th data-ng-show="showCodesmell">Codesmell Type <span data-toggle="modal" data-target="#codesmellmodal"><span data-toggle="tooltip" data-placement="right" title="What is?" class="glyphicon glyphicon-question-sign"></span></span></th>
								<th data-ng-show="showActivity">Activity <span data-toggle="modal" data-target="#activitymodal"><span data-toggle="tooltip" data-placement="right" title="What is?" class="glyphicon glyphicon-question-sign"></span></span></th>
								<th data-ng-show="showHarmfulnessLevel">Harmfulness Level <span data-toggle="modal" data-target="#harmfulnesslevelmodal"><span data-toggle="tooltip" data-placement="right" title="What is?" class="glyphicon glyphicon-question-sign"></span></span></th>
								<th data-ng-show="showActivenessLevel">Activeness Level <span data-toggle="modal" data-target="#activenesslevelmodal"><span data-toggle="tooltip" data-placement="right" title="What is?" class="glyphicon glyphicon-question-sign"></span></span></th>
								<th>View Evolution</th>
							</tr>
						</thead>
						<tbody>
							<tr data-ng-repeat="codesmell in codesmells | filter : classificationType">
								<td>{{codesmell.className}}</td>
								<td data-ng-show="showProg">{{codesmell.programmingLanguage}}</td>
								<td data-ng-show="showCodesmell">{{codesmell.codesmellType}}</td>
								<td data-ng-show="showActivity">{{codesmell.activityType}}</td>
								<td data-ng-show="showHarmfulnessLevel">{{codesmell.harmfulnessLevel}}</td>
								<td data-ng-show="showActivenessLevel">{{codesmell.activenessLevel}} %</td>
								<td>
									<form action="/cosa/viewEvolve" method="POST">
										<input type="hidden" name="cvsrootid" value="<%= request.getAttribute("id") %>">
										<input type="hidden" name="classname" value="{{codesmell.className}}">
										<input type="hidden" name="codesmelltype" value="{{codesmell.codesmellType}}">
										<button type="submit" class="btn btn-primary btn-sm">View</button>
									</form>
								</td>
							</tr>
						</tbody>
					</table>
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

<!-- Activity Modal -->
<div class="modal fade" id="activitymodal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
         	 	<h4 class="modal-title">Activity Level</h4>
			</div>
			<div class="modal-body">
				<ul class="nav nav-tabs">
          			<li class="active"><a data-toggle="tab" href="#explanationtab">Explanation</a></li>
          			<li><a data-toggle="tab" href="#exampletab">Example</a></li>
          		</ul>
          		
          		<div class="tab-content">
          			<div id="explanationtab" class="tab-pane fade in active">
          				<h4>Activity</h4>
		   				Activity Level is the classification of code smell changes from one version to another. 
		   				It is categorized as:
		   				<div class="list-group">
		   					<div class="list-group-item">
		   						<h5 class="list-group-item-heading">Strong Active</h5>
		   						<p class="list-group-item-text">It indicates any changes that slightly increase the intensity of a code smell.
		   						Strong Active is labeled as 3. It should be noted that, the more occurrence of Strong Active in a class, it means
		   						it changed frequently in the past as well as it increases its intensity.
		   						</p>
		   					</div>
		   					<div class="list-group-item">
		   						<h5 class="list-group-item-heading">Stable Active</h5>
		   						<p class="list-group-item-text">It indicates any changes has been occurred from one version to another, but it does not really impact the intensity of that code smell.
		   						Stable Active is labeled as 2.
		   						</p>
		   					</div>
		   					<div class="list-group-item">
		   						<h5 class="list-group-item-heading">Ameliorate Active</h5>
		   						<p class="list-group-item-text">It indicates any changes has been occurred from one version to another and it decreases the intensity of that code smell.
		   						Ameliorate Active is labeled as 1. It should be noted that Ameliorate Active might gives some hint to refactoring (when developers notice about a certain smell or bugs and 
		   						decide to improve the code).
		   						</p>
		   					</div>
		   					<div class="list-group-item">
		   						<h5 class="list-group-item-heading">Dormant</h5>
		   						<p class="list-group-item-text">It indicates there is no changes from one version to another. It is labeled as 0. In this approach, it is believed that a code smell that 
		   						does not change frequently, will not be changed as well in the future. Therefore in the future, a class that has a lot of Dormant state, will have lower maintenance impact than a class that frequently changes.
		   						</p>
		   					</div>
		   				</div>
          			</div>
          			<div id="exampletab" class="tab-pane fade">
          				<div class="row">
          					<div class="col-sm-2"></div>
          					<div class="col-sm-8">
          						<img src="${activityexample}" style="width:100%;height:200px;">
          					</div>
          					<div class="col-sm-2"></div>
          				</div>
          				<div class="row">
          					<div class="col-sm-1"></div>
          					<div class="col-sm-10">
	          					<h4>Explanation:</h4>
	          					<dl class="dl-horizontal">
	          						<dt>Version 1</dt><dd>Strong Active</dd>
	          						<dt>Version 2</dt><dd>Stable Active</dd>
	          						<dt>Version 3</dt><dd>Strong Active</dd>
	          						<dt>Version 4</dt><dd>Stable Active</dd>
	          						<dt>Version 5</dt><dd>Ameliorate Active</dd>
	          						<dt>Version 6</dt><dd>Dormant</dd>
	          					</dl>
          					</div>
          				</div>
          			</div>
          		</div>
        	</div>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	</div>
		</div>
	</div>
</div>

<!-- Code Smell Modal -->
<div class="modal fade" id="codesmellmodal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
         	 	<h4 class="modal-title">Code Smell Types</h4>
			</div>
			<div class="modal-body">
				<h4>Data Class</h4>
   				<blockquote>
   					Data-classes are dumb data holders and almost certainly other classes are strongly relying on them. The lack of
					"functional" methods may indicate that related data and behaviour are not kept in one place, this is a sign of a
					non object-oriented conception
					<footer>
						D. Rapu, S. Ducasse, T. Girba and R. Marinescu, "Using history information to improve design flaws detection," Eighth European Conference on Software Maintenance and Reengineering, 2004. CSMR 2004. Proceedings., 2004, pp. 223-232.
					</footer>
   				</blockquote>
   				
   				<h4>Brain Class</h4>
   				<blockquote>
   					Similar to God Classes, Brain Classes tend to be complex and centralize the functionality of the system. It is therefore assumed that they are difficult to understand and
					maintain. However, contrary to God Classes, Brain Classes do not use much data from foreign classes and are slightly more cohesive.
					<footer>
						Lanza, M., & Marinescu, R. (2011). Object-oriented metrics in practice (1st ed.). Berlin: Springer.
					</footer>
   				</blockquote>
   				
          		<h4>God Class</h4>
          		<blockquote>
	   				God Class "refers to those classes that tend to centralize the intelligence of the system. An instance
					of a god-class performs most of the work, delegating only minor details to a set of trivial classes and
					using the data from other classes"
					<footer> 
						R. Marinescu. Measurement and Quality in Object-Oriented Design. Ph.D. thesis, Department of Computer
						Science, "Politehnica" University of Timi¸soara, 2002.
					</footer>
				</blockquote>
        	</div>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	</div>
		</div>
	</div>
</div>

<!-- Harmfulness Level Modal -->
<div class="modal fade" id="harmfulnesslevelmodal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
         	 	<h4 class="modal-title">Harmfulness Level</h4>
			</div>
			<div class="modal-body">
				<h4>Harmfulness Level</h4>
   				Harmfulness Level shows the accumulation of a code smell activity as well as its severity in the past. Harmfulness Level
   				gives a hint to which code smell that has change-proneness or which class that might be hard to deal with or even both.
   				It is categorized to 4 levels:
   				<div class="list-group">
   					<div class="list-group-item">
   						<h5 class="list-group-item-heading">Harmful</h5>
   						<p class="list-group-item-text">If a class is categorized in this level, it indicates that this class has high change-proneness and high severity impact.
   						The severity impact shows the complexity to maintain or change a class. It also indicates that this class has higher intensity among other code smells. Therefore,
   						Harmful has highest priority. 
   						</p>
   					</div>
   					<div class="list-group-item">
   						<h5 class="list-group-item-heading">High</h5>
   						<p class="list-group-item-text">If a class is categorized in this level, it indicates that this class has a high change-proneness. We believe that when a class
   						has a high change-proneness it might be change frequently which may impact the maintenance effort. Therefore, we believe this level has higher priority to be refactored.
   						</p>
   					</div>
   					<div class="list-group-item">
   						<h5 class="list-group-item-heading">Normal</h5>
   						<p class="list-group-item-text">It indicates that the corresponding class is hard to be maintain, since it has high complexity and more smelly among other code smells.
   						However, a class that is categorized in this level tends to be not change frequently. Therefore, we give a lower priority to this class (compare to Harmful and High level).
   						</p>
   					</div>
   					<div class="list-group-item">
   						<h5 class="list-group-item-heading">Harmless</h5>
   						<p class="list-group-item-text">As the semantic of "Harmless", a class that is categorized in this level is harmless to the system. It is not change frequently and has low complexity as well as its severity among other
   						code smels. Therefore, harmless has the lowest priority among other levels. 
   						 
   						</p>
   					</div>
   				</div>
        	</div>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	</div>
		</div>
	</div>
</div>

<!-- Activeness Level Modal -->
<div class="modal fade" id="activenesslevelmodal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
         	 	<h4 class="modal-title">Activeness Level</h4>
			</div>
			<div class="modal-body">
				<h4>Activeness Level</h4>
				<p>Activeness Level is a percentage that shows the accumulation of activity level from the very first version divided by the highest possible activity level in that version, for example:</p>
				<div class="row">
   					<div class="col-sm-2"></div>
   					<div class="col-sm-8">
   						<img src="${activityexample}" style="width:100%;height:200px;">
   					</div>
   					<div class="col-sm-2"></div>
   				</div>
   				<p>The activeness level of above graph is <strong>61.11%</strong> ([3+2+3+2+1+0]/18)</p>
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
		
		$(window).resize(function(){
			$(".well").css("margin-top", $(".navbar").height());
		});
		
		/* var c = document.getElementById("riskMappingCanvas");
		var ctx = c.getContext("2d");

		var hig = ${high};
		var med = ${med};
		var low = ${low};

		for(var x = 0 ; x < 10; x++){
			for(var y = 0 ; y < 10; y++){
				//set color
				var cmp = (x+1) * (y+1);
				if(cmp < low){
					ctx.fillStyle="#FFD900";
				}else if(cmp>= low && cmp < (med+low)){
					ctx.fillStyle="#E8770C";
				}else{
					ctx.fillStyle="#FF0000";
				}

				//make rectangle
				var sx=x*50;
				var sy=(9-y)*50;
				ctx.fillRect(sx,sy,50,50);

				====commment====
				ctx.fillStyle="#000000";
				ctx.font = "8px Arial";
				ctx.fillText((x+1)+","+(y+1), (sx), (sy+10));
				====end of comment====
			}
		}
		
		ctx.fillStyle = "#FFFFFF";
		ctx.fillRect(215, 478, 95,15);
		ctx.fillStyle = "#000000";
		
		ctx.font = "12px Arial";
		ctx.fillText("Severity Impact",220,490);
		
		ctx.rotate(-90*Math.PI/180);
		ctx.fillStyle = "#FFFFFF";
		ctx.fillRect(-285, 10, 100,18);
		ctx.fillStyle = "#000000";
		ctx.fillText("Activeness Level",-280,22);
		
		ctx.rotate(90*Math.PI/180); */
	});
	
	var app = angular.module('main', []);
	app.controller('mainCtl', function($scope){
		$scope.init = function(){
			$scope.showProg = true;
			$scope.showCodesmell = true;
			$scope.showActivity = true;
			$scope.showPriority = true;
			$scope.showHarmfulnessLevel = true;
			$scope.showActivenessLevel = true;
			
			var res = JSON.stringify(<%= json %>);
			var obj = JSON.parse(res);
			
			$scope.codesmells = obj;
		}
		
		/* $scope.drawMe = function(id, className, accSL, accAL){
			classInitial = "";
			
			for(var i = 0 ; i < className.length; i++){
				tempClass = className.charAt(i);
				if(tempClass == tempClass.toUpperCase()){
					classInitial += tempClass;
				}
			}
			var docchecked = document.getElementById("check"+id).checked;
			
			//severity level
			coorx = accSL * 50;
			//activity level
			coory = (10-accAL) * 50;
			
			var c = document.getElementById("riskMappingCanvas");
			var ctx = c.getContext("2d");
			
			if(docchecked == true){
				//draw
				ctx.fillStyle="#000000";
				ctx.font = "12px Arial";
				
				ctx.fillText(classInitial,coorx ,coory );
				
			}else{
				//erase
				coortimes = accSL * accAL;
				if(coortimes < ${low}){
					ctx.fillStyle="#FFD900";
				}else if(coortimes>= ${low} && coortimes < (${low}+${med})){
					ctx.fillStyle="#E8770C";
				}else{
					ctx.fillStyle="#FF0000";
				}
				
				ctx.font = "12px Arial";
				ctx.fillText(classInitial,coorx ,coory );

			}
		} */
		
	});
</script>
</html>
