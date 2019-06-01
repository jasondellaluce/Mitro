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
		<title>Registra utente</title>
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
								<a href="/amministratore-nuovo-utente">
									<i class="fa big-icon fa-pie-chart"></i>
									<span class="mini-dn">Registra Utente</span>
									<span class="indicator-right-menu mini-dn"></span>
								</a>
							</li>							
							<li class="nav-item">
								<a href="/amministratore-nuova-comunicazione">
									<i class="fa big-icon fa-envelope"></i>
									<span class="mini-dn">Registra Comunicazione</span>
									<span class="indicator-right-menu mini-dn"></span>
								</a>
							</li>
							<li class="nav-item">
								<a>
									<i>*</i>
									<span class="mini-dn">Registra Classe(WIP..)</span>
									<span class="indicator-right-menu mini-dn"></span>
								</a>
							</li>
							<li class="nav-item">
								<a>
									<i>*</i>
									<span class="mini-dn">Modifica Classe(WIP..)</span>
									<span class="indicator-right-menu mini-dn"></span>
								</a>
							</li>
							<li class="nav-item">
								<a>
									<i>*</i>
									<span class="mini-dn">Calendario(WIP..)</span>
									<span class="indicator-right-menu mini-dn"></span>
								</a>
							</li>
							<li class="nav-item">
								<a>
									<i>*</i>
									<span class="mini-dn">Modifica Voto(WIP..)</span>
									<span class="indicator-right-menu mini-dn"></span>
								</a>
							</li>
							<li class="nav-item">
								<a>
									<i>*</i>
									<span class="mini-dn">Modifica Utente(WIP..)</span>
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

				</div>
				
				<!-- Main container -->
				<div class="welcome-adminpro-area">
					<div class="row">			
						<div class="col-lg-8" style="margin-left: 50px;">
							<div class="alert-title">
								<h2>Registra comunicazione</h2>
							</div> 	
							<div>
							<form id="adminpro-form" class="adminpro-form" action='/amministratore-nuova-comunicazione' method="post">
							<div class="row">
								<div style="">	
								<% 
								String classiSel="";
								String professoriSel="";
								String studentiSel="";
									if(request.getParameter("classiSelected")!=null&&
									   request.getParameter("studentiSelected")!=null&&
									   request.getParameter("professoriSelected")!=null){
										String[] classiSelected=request.getParameterValues("classiSelected");
										String[] professoriSelected=request.getParameterValues("professoriSelected");
										String[] studentiSelected=request.getParameterValues("studentiSelected");
										for(int i=0;i<classiSelected.length;i++){
											if(i==classiSelected.length-1) classiSel+=classiSelected[i];
											else{
												classiSel+= classiSelected[i]+",";
											}
										} 
										
										for(int i=0;i<studentiSelected.length;i++){
											if(i==studentiSelected.length-1) studentiSel+=studentiSelected[i];
											else{
												studentiSel+= studentiSelected[i]+",";
											}
										} 
										
										for(int i=0;i<professoriSelected.length;i++){
											if(i==professoriSelected.length-1) professoriSel+=professoriSelected[i];
											else{
												professoriSel+= professoriSelected[i]+",";
											}
									}

									} %>

										<div class="col-lg-4">
											<div class="note-dropzone"> 												
												<div>
													<p>Classi:</p>
													<select name="classiSelected" multiple>
													<%	if(request.getAttribute("classi") != null) {
														List<Classe> classi = (List<Classe>) request.getAttribute("classi");
														for(int i = 0; i < classi.size(); i++) {
														%>
															<option value='<%= classi.get(i).getId()%>'><%= classi.get(i).getNome()+"-"+classi.get(i).getDescrizione() %></option>
														<% 		}
														}
														%>			
													</select>	
												</div>																															
											</div> 
										</div>
										<div class="col-lg-4">
											<div class="note-dropzone"> 												
												<div>
													<p>Studenti:</p>
													<select name="studentiSelected" multiple>
													<%	if(request.getAttribute("studenti") != null) {
														List<Studente> studenti = (List<Studente>) request.getAttribute("studenti");
														for(int i = 0; i < studenti.size(); i++) {
														%>
															<option value='<%= studenti.get(i).getId()%>'><%= studenti.get(i).getNome()+" "+studenti.get(i).getCognome() %></option>
														<% 		}
														}
														%>			
													</select>	
												</div>																															
											</div> 
										</div>	
										<div class="col-lg-4">
											<div class="note-dropzone"> 												
												<div>
													<p>Professori:</p>
													<select name="professoriSelected" multiple>
													<%	if(request.getAttribute("professori") != null) {
														List<Professore> professori = (List<Professore>) request.getAttribute("professori");
														for(int i = 0; i < professori.size(); i++) {
														%>
															<option value='<%= professori.get(i).getId()%>'><%= professori.get(i).getNome()+" "+professori.get(i).getCognome() %></option>
														<% 		}
														}
														%>			
													</select>	
												</div>																															
											</div> 
										</div>										
									</div>	
								</div>
								<div class="row">
									<div class="col-lg-4">
										<div style="">
											<p>Oggetto:</p>
											<input type="text" name="oggetto">
											<p>Comunicazione:</p>
											<div class="note-editor note-frame panel panel-default" style=" width:450px; height: 200px;">
												<div class="note-dropzone"> 
													<textarea name="comunicazione" style=" width:350px; height: 150px;"></textarea>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-lg-4">
										<div style="">
											<div class="compose-email">   
												<button name="scrivi" type="submit" class="btn btn-custon-rounded-three btn-default" style="width:100%;">Scrivi comunicazione</button>
											</div> 				
										</div>
									</div>
								</div>
							</form>
							
							<%/*
								if(request.getParameter("scrivi")!=null){
									List<String> classiSelected=(List<String>)request.getAttribute("classiSelected");
									List<String> professoriSelected=(List<String>)request.getAttribute("professoriSelected");
									List<String> studentiSelected=(List<String>)request.getAttribute("studentiSelected");
									String classiSel="";
									String professoriSel="";
									String studentiSel="";
									for(int i=0;i<classiSelected.size();i++){
										if(i==classiSelected.size()-1) classiSel+=classiSelected.get(i);
										else{
											classiSel+= classiSelected.get(i)+",";
										}
									} 
									
									for(int i=0;i<studentiSelected.size();i++){
										if(i==studentiSelected.size()-1) studentiSel+=studentiSelected.get(i);
										else{
											studentiSel+= studentiSelected.get(i)+",";
										}
									} 
									
									for(int i=0;i<professoriSelected.size();i++){
										if(i==professoriSelected.size()-1) professoriSel+=professoriSelected.get(i);
										else{
											professoriSel+= professoriSelected.get(i)+",";
										}
									} 
									request.setAttribute("classiSelected",classiSel);
									request.setAttribute("studentiSelected",studentiSel);
									request.setAttribute("professoriSelected",professoriSel);
								}
							*/
							%>
							
							</div>
														
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