<%@ include file="/WEB-INF/partials/common.taglibs.jsp"%>

<c:set var="previousPage" value="/subscribe/received" scope="session" />

<stripes:layout-render name="/WEB-INF/layout/customer.jsp">
  <stripes:layout-component name="title"><fmt:message key="title.inactive.account"/></stripes:layout-component>
  <stripes:layout-component name="bodyClasses">subscribe</stripes:layout-component>
  <stripes:layout-component name="content">

    <div class="centeredcontent">
    <h1><fmt:message key="title.inactive.account" /></h1>
    <p><fmt:message key="customer.order.not.activated.text" /></p>
    </div>

  </stripes:layout-component>
</stripes:layout-render>

