<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="stripes-dyn" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://combine.intem.se" prefix="combine" %>

<combine:requires requires="customer" />
<stripes:layout-definition>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title><stripes:layout-component name="title"/></title>


        <combine:layout-css />
        <%--Move to bottom when rest of javascript has been migrated. --%>
        <combine:layout-script />
        <script type="text/javascript">
          var _gaq = _gaq || [];
          _gaq.push(['_setAccount', 'UA-19116328-1']);
          _gaq.push(['_trackPageview']);

          (function() {
            var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
            var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
          })();

        </script>
    </head>

    <body id="${bodyId}" class="yui-skin-sam ${bodyClasses}">


    <div id="root-container">

    <ul class="header">
        <li class="${page eq 'info' ? 'selected' : ''}"><span>Vad är Jag handlar?</span></li>
        <li class="${page eq 'license' ? 'selected' : ''}"><span>Välj antal</span></li>
        <li class="${page eq 'account' ? 'selected' : ''}"><span>Välj användarnamn</span></li>
        <li class="${page eq 'invoice' ? 'selected' : ''}"><span>Kunduppgifter</span></li>
        <li class="${page eq 'invoice' ? 'selected' : ''}"><span>Skicka beställningen</span></li>
    </ul>

    <div class="logo">
    </div>


    <div class="content">
        <stripes:layout-component name="content"/>
    </div>

            
    </div>

    <script type="text/javascript">
        Event.observe(window, "load", function() {
            $$("ul.header li").each(function(li) {
                if (li.hasClassName("selected")) {
                    li.setOpacity(1);
                } else {
                    li.setOpacity(0.2);
                }
            });

        });
    </script>

    <stripes:layout-component name="bottomJavascript"/>
    </body>

    </html>

</stripes:layout-definition>
