<%@ include file="/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta http-equiv="Cache-Control" content="no-Cache">
		<meta http-equiv="PRAGMA" content="NO-CACHE">
		<title>Stub Web - Test Harness app</title>
		<%@ include file="/include/style.jspf" %>
		
		<script type="text/javascript">
		    window.onload = maxWindow;
		
		    function maxWindow() {
		        window.moveTo(0, 0);
		        
		        if (document.all) {
		            top.window.resizeTo(screen.availWidth, screen.availHeight);
		        }
		        else if (document.layers || document.getElementById) {
		            if (top.window.outerHeight < screen.availHeight || top.window.outerWidth < screen.availWidth) {
		                top.window.outerHeight = screen.availHeight;
		                top.window.outerWidth = screen.availWidth;
		            }
		        }
		    }
		</script>		
		<%@ include file="/include/script.jspf" %>
	</head>
	
	<body>
		<div id="container">
			<div id="title">
				CS1x - Test Harness to setup data	
			</div>
			<div class="ui-layout-north">
				<div id="ribbonContainer">			
					<div id="ribbonContent" class="ribbonContent">
						<div id="dataInfoContainer" class="dataContainer" style="clear: both; ">
							<table cellpadding="5" cellspacing="5" width="100%">
								<tr>
									<td>How to use this UI:<br/>
									<hr/>
									<i><b>Load Data</b></i><br/>
									1. Either choose a single clinic id or a range of clinic ids for which you intend to setup/teardown data<br/>
									2. Either choose a single shift or a range of shifts (for each of the clinics selected in step 1) for which you intend to setup/teardown data<br/>
									3. Enter a shift date in the format <b>mm/dd/yyyy</b><br/>
									4. Select an event<br/>
									5. Click Submit
									<hr/>
									<i><b>Extract Data</b></i><br/>
									This tab's features have not yet been tested/constructed
									<hr/>
									<i><b>Transform Data</b></i><br/>
									This tab's features have not yet been tested/constructed
									</td>
								</tr>
								<tr>
									<td><i><b>NOTE:</b> Check your admin server console (datasource listing page) to determine the target database from which data would be extracted and to which data would be loaded. Look for a datasource whose <b>jndi-name=jdbc/tss</b></i></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="ui-layout-center" id="centerDiv">
				<ul>
					<li class="mainTab"><a id="loadData" href="#tabs-center-1">Load Data</a></li>
					<li class="mainTab"><a id="extractData" href="#tabs-center-2">Extract Data</a></li>
					<li class="mainTab"><a id="transformData" href="#tabs-center-3">Transform Data</a></li>
				</ul>
				<div class="ui-layout-content">
					<div id="tabs-center-1">
						<div id="loadDataDiv" class="container">
							<%@ include file="WEB-INF/jsp/loadData.jsp"%>
						</div>
					</div>
					<div id="tabs-center-2">
						<div id="extractDataDiv" class="container">
							<%@ include file="WEB-INF/jsp/extractData.jsp"%>
						</div>
					</div>
					<div id="tabs-center-3">
						<div id="transformDataDiv" class="container">
							<%@ include file="WEB-INF/jsp/underConstruction.jsp"%>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="exceptionDiv" style="display: none;font-size:13px; WORD-BREAK:BREAK-ALL;"></div>
		<div id="progressLayer" align="center"><div id="progressHeader" style="cursor: auto;"></div>
			<div id="progressBody">
				<img src="images/loading.gif" />
			</div>
		</div>
	</body>
</html>
