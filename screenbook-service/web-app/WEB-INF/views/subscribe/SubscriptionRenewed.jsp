<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="previousPage" value="/subscription/renewed"	scope="session" />

<stripes:layout-render name="/WEB-INF/layout/customer.jsp">
  <stripes:layout-component name="titleKey">title.inactive.account</stripes:layout-component>
  <stripes:layout-component name="bodyClasses">subscribe</stripes:layout-component>
  <stripes:layout-component name="content">
      
    <h1><fmt:message key="title.subscription.renewed" /></h1>
      
    <p class="text"><fmt:message key="customer.subscription.renewed.text" /></p>
    <p><fmt:message key="customer.subscription.renewed.login" /></p>

  </stripes:layout-component>
</stripes:layout-render>
