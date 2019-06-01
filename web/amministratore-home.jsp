<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="java.time.format.*" %>
<%@ page import="mitro.model.*" %>
<%@ page import="mitro.deployment.Configurazione" %>
<%@ page import="java.util.EnumSet" %>
<!DOCTYPE html>
<html class=" js flexbox canvas canvastext webgl touch geolocation postmessage no-websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients no-cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths js flexbox canvas canvastext webgl touch geolocation postmessage no-websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients no-cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths" style="" lang="en">
	<head>
		<title>Home</title>
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
						<br>
						<br>
						<span style="color:black"><%= Configurazione.getInstance().getDescrizioneApplicazione() %></span>
					</div>
					<div class="left-custom-menu-adp-wrap">
						<ul class="nav navbar-nav left-sidebar-menu-pro">
							<li class="nav-item">
								<a href="/amministratore?azione=disconnetti">
									<i class="fa big-icon fa-sign-out"></i>
									<span class="mini-dn">Disconnettiti</span>
									<span class="indicator-right-menu mini-dn"></span>
								</a>
							</li>
							</br>
							<li class="nav-item">
								<a href="/amministratore">
									<i class="fa big-icon fa-home"></i>
									<span class="mini-dn">Vai alla Home</span>
									<span class="indicator-right-menu mini-dn"></span>
								</a>
							</li>
							<li class="nav-item">
								<a href="/amministratore-comunicazioni">
									<i class="fa big-icon fa-envelope"></i>
									<span class="mini-dn">Registra Comunicazione</span>
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
				
				<!-- Breadcome start-->
				<div class="breadcome-area mg-b-30 small-dn">
					<div class="container-fluid">
						<div class="row">
							<div class="col-lg-12">
								<div class="breadcome-list map-mg-t-40-gl shadow-reset">
									<div class="row">
										<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
											<div class="breadcome-heading" style="font-size: 12pt;">
												<ul>
													<li>Bentornato, <%= request.getAttribute("nomeAmministratore") %></li>
												</ul>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<!-- Main container -->
				<div class="welcome-adminpro-area">
					<div class="row">

						
						<div class="col-lg-10" >
							<div class="alert-title">
								<h2>Registra utente</h2>
							</div> 	
							<div class="note-editor note-frame panel panel-default" style=" width:450px; height: 450px;">
									<form id="adminpro-form" class="adminpro-form" action='/amministratore?selAtt=<%= request.getParameter("selAtt") %>' method="post">
										<div class="note-dropzone"> 
											<div class="note-editable panel-body" style=" width:450px;height: 450px;" contenteditable="true">
												<div class="col-lg-3" style="margin-bottom: 20px;">
												<p>Ruolo:</p></br>
												<select class="form-control" name="ruolo" >
													<%
														ArrayList<String> ruoli = (ArrayList<String>) request.getAttribute("ruoli");
														for(int i=0;i<ruoli.size();i++) {
													%>
															<option value='<%= i %>' <%= i == (int) request.getAttribute("ruolo") ? "selected" : "" %>><%= ruoli.get(i)%></option>
													<% 	
														}
													%>			
												</select>
												</div>
												Nome:<input type="text" name="nome" style=" width:100%; height:100%; border-color: white; border-width: 0px 0px 0px 0px; padding: 0px 0px 0px 0px;">
												Cognome:<input type="text" name="cognome" style=" width:100%; height:100%; border-color: white; border-width: 0px 0px 0px 0px; padding: 0px 0px 0px 0px;">
												<div class="row">
													<div class="col-lg-3" style="margin-left: 15px; margin-bottom: 20px; ">
														Classe(*):
														<select class="form-control" name="ruolo" >
														<%	if(request.getAttribute("listaMaterie") != null) {
															List<Classe> classi = (List<Classe>) request.getAttribute("classi");
															for(int i = 0; i < classi.size(); i++) {
															%>
																<option value='<%=classi.get(i).getNome() %>'><%= classi.get(i).getNome() %></option>
															<% 		}
															}
															%>			
														</select>	
													</div>													
												</div>
												<p>(*): solo se si deve registrare uno studente</p>
											</div>
											<div class="compose-email"> 
												<br>   
												<button type="submit" class="btn btn-custon-rounded-three btn-default" style="width:100%;" <%= request.getParameter("selAtt") == null ? "disabled" : "" %> >Crea utente</button>
											</div> 
										</div>  
									</form>
								</div>


						</div>                   
						
					</div>
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
		<script src="js/main.js"></script>
	</body>
</html>