<%@ include file="/WEB-INF/partials/common.taglibs.jsp"%>


<c:set var="previousPage" value="/subscribe/info" scope="session" />

<stripes:layout-render name="/WEB-INF/layout/customer.jsp">
  <stripes:layout-component name="title">
    <fmt:message key="title.subscribe" />
  </stripes:layout-component>
  <stripes:layout-component name="bodyClasses">subscribe</stripes:layout-component>
  <stripes:layout-component name="content">

  <div class="centeredcontent">

    <h1><fmt:message key="title.subscribe" /></h1>
    <h2><fmt:message key="subscribe.whatis.header" /></h2>

    <p><fmt:message key="subscribe.whatis.text" /></p>


    <div style="margin: 30px 0px;">
        <a href="/subscribe/license/" class="order large-button" style="margin:0"> <fmt:message key="subscribe.button" /></a>
    </div>

  </div>

  </stripes:layout-component>
</stripes:layout-render>

