<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ include file="/WEB-INF/partials/common.taglibs.jsp"%>


<c:set var="previousPage" value="/subscribe/info" scope="session" />

<stripes:layout-render name="/WEB-INF/layout/customer.jsp">
  <stripes:layout-component name="title">
    <fmt:message key="title.subscribe" />
  </stripes:layout-component>
  <stripes:layout-component name="bodyClasses">subscribe</stripes:layout-component>
  <stripes:layout-component name="page">info</stripes:layout-component>    
  <stripes:layout-component name="content">

  <h1><fmt:message key="subscribe.whatis.header" /></h1>
  <p><fmt:message key="subscribe.whatis.text" /></p>

  <div class="backnext" style="background: none;">
    <img src="/images/subscribe/long-gradient.png" alt="" />
    <a href="/subscribe/license/" class="next"><fmt:message key="common.next" /></a>
  </div>

  </stripes:layout-component>
</stripes:layout-render>

