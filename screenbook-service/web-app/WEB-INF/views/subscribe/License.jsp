<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ include file="/WEB-INF/partials/common.taglibs.jsp"%>

<c:set var="previousPage" value="/subscribe/license/" scope="session" />

<stripes:layout-render name="/WEB-INF/layout/customer.jsp">
  <stripes:layout-component name="titleKey">title.new.subscription</stripes:layout-component>
  <stripes:layout-component name="bodyClasses">subscribe license</stripes:layout-component>
  <stripes:layout-component name="page">license</stripes:layout-component>
  <stripes:layout-component name="content">





<stripes:form action="/subscribe/license/" id="subscribe">


    <h1><fmt:message key="contact.license" /></h1>
    <p>Jag handlar-abonnemanget g&auml;ller f&ouml;r 15 m&aring;nader och kan enkelt f&ouml;rnyas (vi p&aring;minner).</p>
    <p>Abonnemanget kan inneh&aring;lla upp till &aring;tta webb&ouml;cker &mdash; varje elev beh&ouml;ver sin egen Jag handlar. Antalet
    ing&aring;ende webb&ouml;cker best&auml;mmer kostnaden f&ouml;r abonnemanget.</p>

    <p class="label">Markera hur m&aring;nga Jag handlar-webb&ouml;cker som ska ing&aring; i ditt abonnemang:</p>


    <ul>
    <c:forEach var="license" items="${licenseOptions}">
        <li class="text" style="margin-bottom:10px;">
        <input type="radio" name="chooseLicenseForm.license" value="${license.users}" id="license_${license.users}"
               ${actionBean.chooseLicenseForm.license eq license.users ? 'checked="checked"' : ''} />
        <label for="license_${license.users}">
            <fmt:message key="${license.resourceKey}">
              <fmt:param  value="${license.users}" />
            </fmt:message>&nbsp;&mdash;&nbsp;
            <fmt:formatNumber type="number" value="${license.price.amount * 4 / 5}" maxFractionDigits="0" />&nbsp;${license.price.currency.label}
            &nbsp;(${license.price.amount}&nbsp;${license.price.currency.label} inkl moms)
        </label>
        </li>
        </c:forEach>
    </ul>


<div class="backnext">
    <a href="/subscribe/info" class="back"><fmt:message key="common.back" /></a>
    <button type="submit" class="next" name="chooseLicense">
        <fmt:message key="common.next" />
    </button>

</div>

</stripes:form>

  </stripes:layout-component>
</stripes:layout-render>

