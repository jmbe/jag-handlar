<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="stripes-dyn" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://packtag.sf.net" prefix="pack" %>

<stripes:layout-definition>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title><stripes:layout-component name="title"/></title>


        <pack:style minify="false">
            <src>${pageContext.request.contextPath}/css/jag-handlar.css</src>
            <src>${pageContext.request.contextPath}/css/subscribe.css</src>
        </pack:style>


    </head>

    <body id="${bodyId}" class="yui-skin-sam ${bodyClasses}">

    <div class="logo">
        Plats f&ouml;r logotyp.
    </div>

    <div class="menu">
        <div class="box" style="background-color:#33CCFF "></div>
        <div class="box" style="background-color:#3366FF "></div>
        <div class="box" style="background-color:#003DF5 "></div>
        <div class="box" style="background-color:#002EB8 "></div>
    </div>

    <div class="content">
        <stripes:layout-component name="content"/>
    </div>
    </body>

    </html>

</stripes:layout-definition>
