<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="java.time.format.*" %>
<%@ page import="mitro.model.*" %>
<!DOCTYPE html>
<html class=" js flexbox canvas canvastext webgl touch geolocation postmessage no-websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients no-cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths js flexbox canvas canvastext webgl touch geolocation postmessage no-websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients no-cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths" style="" lang="en">
	<head>
		<title>Storico</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<meta http-equiv="x-ua-compatible" content="ie=edge">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,700,700i,800" rel="stylesheet">
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<link rel="stylesheet" href="css/font-awesome.min.css">
		<link rel="stylesheet" href="css/adminpro-custon-icon.css">
		<link rel="stylesheet" href="css/meanmenu.min.css">
		<link rel="stylesheet" href="css/jquery.mCustomScrollbar.min.css">
		<link rel="stylesheet" href="css/animate.css">
		<link rel="stylesheet" href="css/data-table/bootstrap-table.css">
		<link rel="stylesheet" href="css/data-table/bootstrap-editable.css">
		<link rel="stylesheet" href="css/normalize.css">
		<link rel="stylesheet" href="css/c3.min.css">
		<link rel="stylesheet" href="css/form/all-type-forms.css">
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="css/responsive.css">
		
		<script src="js/vendor/modernizr-2.8.3.min.js"></script>
		
		<!-- Styles -->
		<style type="text/css">  .JColResizer{/*table-layout:fixed;*/} .JColResizer td, .JColResizer th{overflow:hidden;padding-left:0!important; padding-right:0!important;}  .JCLRgrips{ height:0px; position:relative;} .JCLRgrip{margin-left:-5px; position:absolute; z-index:5; } .JCLRgrip .JColResizer{position:absolute;background-color:red;filter:alpha(opacity=1);opacity:0;width:10px;height:100%;cursor: e-resize;top:0px} .JCLRLastGrip{position:absolute; width:1px; } .JCLRgripDrag{ border-left:1px dotted black;	} .JCLRFlex{width:auto!important;}</style>
		<style type="text/css">.jqstooltip { position: absolute;left: 0px;top: 0px;visibility: hidden;background: rgb(0, 0, 0) transparent;background-color: rgba(0,0,0,0.6);filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000);-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000)";color: white;font: 10px arial, san serif;text-align: left;white-space: nowrap;padding: 5px;border: 1px solid white;z-index: 10000;}.jqsfield { color: white;font: 10px arial, san serif;text-align: left;}</style>
		<style type="text/css">  .JColResizer{/*table-layout:fixed;*/} .JColResizer td, .JColResizer th{overflow:hidden;padding-left:0!important; padding-right:0!important;}  .JCLRgrips{ height:0px; position:relative;} .JCLRgrip{margin-left:-5px; position:absolute; z-index:5; } .JCLRgrip .JColResizer{position:absolute;background-color:red;filter:alpha(opacity=1);opacity:0;width:10px;height:100%;cursor: e-resize;top:0px} .JCLRLastGrip{position:absolute; width:1px; } .JCLRgripDrag{ border-left:1px dotted black;	} .JCLRFlex{width:auto!important;}</style>
		<style type="text/css">.jqstooltip { position: absolute;left: 0px;top: 0px;visibility: hidden;background: rgb(0, 0, 0) transparent;background-color: rgba(0,0,0,0.6);filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000);-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000)";color: white;font: 10px arial, san serif;text-align: left;white-space: nowrap;padding: 5px;border: 1px solid white;z-index: 10000;}.jqsfield { color: white;font: 10px arial, san serif;text-align: left;}</style>
	</head>

	<body class="materialdesign">
		<div class="wrapper-pro">
			<div class="left-sidebar-pro">
				<nav id="sidebar">
					<div class="sidebar-header">
						<img src="img/logo/logo-small.png"> 
					</div>
					<div class="left-custom-menu-adp-wrap">
						<ul class="nav navbar-nav left-sidebar-menu-pro">
							<li class="nav-item">
								<a href="/studente?azione=disconnetti">
									<i class="fa big-icon fa-sign-out"></i>
									<span class="mini-dn">Disconnettiti</span>
									<span class="indicator-right-menu mini-dn"></span>
								</a>
							</li>
							</br>
							<li class="nav-item">
								<a href="/studente">
									<i class="fa big-icon fa-home"></i>
									<span class="mini-dn">Vai alla Home</span>
									<span class="indicator-right-menu mini-dn"></span>
								</a>
							</li>
							<li class="nav-item">
								<a href="/studente-storico">
									<i class="fa big-icon fa-pie-chart"></i>
									<span class="mini-dn">Storico</span>
									<span class="indicator-right-menu mini-dn"></span>
								</a>
							</li>
							<li class="nav-item">
								<a href="/studente-comunicazioni">
									<i class="fa big-icon fa-envelope"></i>
									<span class="mini-dn">Comunicazioni</span>
									<span class="indicator-right-menu mini-dn"></span>
								</a>
							</li>
						</ul>
					</div>
				</nav>
			</div>
			
			<div class="content-inner-all">
				<!-- Top Header -->
				<div class="header-top-area">
					<div class="fixed-header-top">
						<div class="container-fluid">
							<div class="row">
								<div class="col-lg-1 col-md-6 col-sm-6 col-xs-12">
									<button type="button" id="sidebarCollapse" class="btn bar-button-pro header-drl-controller-btn btn-info navbar-btn">
										<i class="fa fa-bars"></i>
									</button>
									<div class="admin-logo logo-wrap-pro">
										<a href="#"><img src="" alt="">
										</a>
									</div>
								</div>
								<div class="col-lg-6 col-md-1 col-sm-1 col-xs-12">

								</div>
								<div class="col-lg-5 col-md-5 col-sm-6 col-xs-12">
                                       
                                </div>
                            </div>
                        </div>
                    </div>
				</div>
				
				<div class="breadcome-area mg-b-30 small-dn">

				</div>
				
				<!-- Main container -->
				<div class="welcome-adminpro-area" style="margin:50px">
					<div class="row">
						<div class="col-lg-11" style="margin-left: 15px; margin-bottom: 20px; ">
                            <div class="sparkline16-hd" >
                                    <div class="main-sparkline9-hd">
                                        <h1>Seleziona una materia</h1>
                                    </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="row">
						<form action='/studente-storico' method="post">
							<div class="col-lg-3" style="margin-left: 15px; margin-bottom: 20px; ">
								<select class="form-control" name="selectionMaterie" >
									<%	if(request.getAttribute("listaMaterie") != null) {
											List<String> listaMaterie = (List<String>) request.getAttribute("listaMaterie");
											for(int i = 0; i < listaMaterie.size(); i++) {
									%>
											<option value='<%= i %>' <%= i == (int) request.getAttribute("selectMateria") ? "selected" : "" %>><%= listaMaterie.get(i) %></option>
									<% 		}
										}
									%>			
								</select>
							</div>
							
							<div class="col-lg-3" style="margin-bottom: 20px;">
								<input class="pull-left" type="radio" value="voto" name="tipoRicerca" <%= ((boolean) request.getAttribute("selectVoto")) ? "checked" : "" %> > Voti<br>
								<input class="pull-left" type="radio" value="presenza" name="tipoRicerca" <%= !((boolean) request.getAttribute("selectVoto")) ? "checked" : "" %>> Presenze
							</div>
							
							<div class="col-lg-3" style="margin-bottom: 20px;">
								<button type='submit' class="btn btn-custon-rounded-three btn-default" style="width:100%;" >Ricerca</button>
							</div>	
						</form>
                    </div>
					
					<%	if(request.getAttribute("selectMateria") != null) {
							int selectMateria = (int) request.getAttribute("selectMateria");
							boolean selectVoto = (boolean) request.getAttribute("selectVoto");
							if(selectMateria >= 0) {
					%>
						<div class="row">
	                        <div class="col-lg-6" style="margin-left: 15px;">
	                            <div class="sparkline9-list shadow-reset mg-tb-30" style="margin-top: 0px;">
									<div class="sparkline9-graph dashone-comment">
										<div class="datatable-dashv1-list custom-datatable-overright dashtwo-project-list-data">
											<h4>Ultime <%= selectVoto ? "valutazioni" : "presenze" %> personali nella materia</h4>
											<div class="fixed-table-container" style="padding-bottom: 0px; height: 500px;">
												<table class="table">
													<tbody>
														<tr>
															<td>Nome Studente</td>
															<td><%= selectVoto ? "Voto" : "Presenza" %></td>
														</tr>
														<%	List<? extends Archiviazione> listaArch = (List<? extends Archiviazione>) request.getAttribute("listaArchiviazioni");
															if(selectVoto) {						
																for(int i = 0; i < listaArch.size(); i++) {
														%>
															<tr>
																<td><%= listaArch.get(i).getStudente().getNome() + " " + listaArch.get(i).getStudente().getCognome()%></td>
																<td><input type="text" disabled value='<%= ((Voto) listaArch.get(i)).getValore() %>'></td>
															</tr>
														<%		}
															}
														%>
														<%	if(!selectVoto) {
																for(int i = 0; i < listaArch.size(); i++) {
														%>
															<tr>
																<td><%= listaArch.get(i).getStudente().getNome() + " " + listaArch.get(i).getStudente().getCognome()%></td>
																<td><input type="checkbox" disabled <%= ((Presenza) listaArch.get(i)).isValore() ? "checked" : "" %> ></td>
															</tr>
														<%		}
															}
														%>
													<tbody>        
												</table>
											</div>
										</div>
										<div class="fixed-table-footer" style="display: none;">
											<table>
												<tbody>
													<tr>
													</tr>
												</tbody>
											</table>
										</div>
										<div class="clearfix">
										</div>
									</div>
								</div>
	                        </div>
							
							<% if(selectVoto) { %>
								<div class="col-lg-2" > 	
									<div class="note-editor note-frame panel panel-default" style=" width:660px; height: 400px;"> 
										<div id="basic-chart1" style="margin-left:30px;margin-top:20px;">
											<h4>Andamento personale nella materia</h4>
											<canvas id="linechart" width="600" height="295"></canvas>
										</div>
									</div>
								</div>   
							<% } %>
	                    </div>		
                    <%		}
                    	}
                    %>
				</div>
			</div>
		</div>
		
		<!-- JQuery Scripts -->
		<script src="js/vendor/jquery-1.11.3.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/jquery.meanmenu.js"></script>
		<script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
		<script src="js/jquery.sticky.js"></script>
		<script src="js/jquery.scrollUp.min.js"></script>
		<script src="js/counterup/jquery.counterup.min.js"></script>
		<script src="js/counterup/waypoints.min.js"></script>
		<script src="js/modal-active.js"></script>
		<script src="js/touchspin/jquery.bootstrap-touchspin.min.js"></script>
		<script src="js/touchspin/touchspin-active.js"></script>
		<script src="js/colorpicker/jquery.spectrum.min.js"></script>
		<script src="js/colorpicker/color-picker-active.js"></script>
		<script src="js/datapicker/bootstrap-datepicker.js"></script>
		<script src="js/datapicker/datepicker-active.js"></script>
		<script src="js/input-mask/jasny-bootstrap.min.js"></script>
		<script src="js/chosen/chosen.jquery.js"></script>
		<script src="js/chosen/chosen-active.js"></script>
		<script src="js/select2/select2.full.min.js"></script>
		<script src="js/select2/select2-active.js"></script>
		<script src="js/ionRangeSlider/ion.rangeSlider.min.js"></script>
		<script src="js/ionRangeSlider/ion.rangeSlider.active.js"></script>
		<script src="js/rangle-slider/jquery-ui-1.10.4.custom.min.js"></script>
		<script src="js/rangle-slider/jquery-ui-touch-punch.min.js"></script>
		<script src="js/rangle-slider/rangle-active.js"></script>
		<script src="js/knob/jquery.knob.js"></script>
		<script src="js/knob/knob-active.js"></script>
		<script src="js/charts/Chart.js"></script>
		<script>
			(function ($) {
				"use strict";			 
				let draw = Chart.controllers.line.prototype.draw;
				Chart.controllers.line.prototype.draw = function() {
					draw.apply(this, arguments);
					let ctx = this.chart.chart.ctx;
					let _stroke = ctx.stroke;
					ctx.stroke = function() {
						ctx.save();
						ctx.shadowColor = '#07C';
						ctx.shadowBlur = 20;
						ctx.shadowOffsetX = 2;
						ctx.shadowOffsetY = 20;
						_stroke.apply(this, arguments);
						ctx.restore();
					}
				};
				<%	if(request.getAttribute("selectMateria") != null) {
						int selectMateria = (int) request.getAttribute("selectMateria");
						boolean selectVoto = (boolean) request.getAttribute("selectVoto");
						if(selectMateria >= 0 && selectVoto) {
							List<? extends Archiviazione> listaArch = (List<? extends Archiviazione>) request.getAttribute("listaArchiviazioni");
				%>
							var ctx = document.getElementById("linechart");
							let myChart = new Chart(ctx, {
								type: 'line',		
								data: {
									labels: ['<%= listaArch.get(listaArch.size() - 1).getAttivita().getData() %>', <% for(int i = listaArch.size() - 2; i > 0; i--) {%> <%= "''," %> <% }%> '<%= listaArch.get(0).getAttivita().getData() %>'],
									datasets: [{
										data: [<% for(int i = listaArch.size() - 1; i >= 0; i--) {%> <%= ((Voto) listaArch.get(i)).getValore() + (i == 0 ? " " : ", ") %> <% } %>],
										borderColor: '#07C',
										pointBackgroundColor: "#FFF",
										pointBorderColor: "#07C",
										pointHoverBackgroundColor: "#07C",
										pointHoverBorderColor: "#FFF",
										pointRadius: 4,
										pointHoverRadius: 4,
										fill: false,
										tension: 0.15
									}]
								},
								options: {
									responsive: false,
									tooltips: {
										displayColors: false,
										callbacks: {
											label: function(e, d) {
												return;
											},
											title: function() {
												return;
											}
										}
									},
									legend: {
										display: false
									},
									scales: {
										yAxes: [{
											ticks: {
												max: 10
											}
										}]
									}
								}
							});
				<% 		}
					}%>
			})(jQuery); 
		</script>
		<script src="js/main.js"></script>
	</body>
</html>
