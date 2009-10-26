<%@ include file="/WEB-INF/partials/common.taglibs.jsp"%>

<c:set var="previousPage" value="/subscribe/license/" scope="session" />

<stripes:layout-render name="/WEB-INF/layout/customer.jsp">
  <stripes:layout-component name="titleKey">title.new.subscription</stripes:layout-component>
  <stripes:layout-component name="bodyClasses">subscribe</stripes:layout-component>
  <stripes:layout-component name="content">


<div class="upperblock">


<stripes:form action="/subscribe/license/" id="subscribe">

<table>
    <tr>
        <td valign="top" style="width: 240px;"><span class="text header"><fmt:message
            key="contact.license" /></span>
        <p class="note">
        <c:choose>
        <c:when test="${ees}">
            <fmt:message key="contact.license.info.with.vat" />
        </c:when>
        <c:otherwise>
            <fmt:message key="contact.license.info.export"/>
        </c:otherwise>
        </c:choose>
        </p>

            <p class="note" style="padding-right: 25px; margin-top: 20px;"><fmt:message
            key="contact.license.details" /></p>
            </td>

        <td>

        <ul>
        <c:forEach var="license" items="${licenseOptions}">
        <li class="text" style="margin-bottom:10px;">
        <input type="radio" name="chooseLicenseForm.license" value="${license.users}" id="license_${license.users}"
               ${actionBean.chooseLicenseForm.license eq license.users ? 'checked="checked"' : ''} />
        <label for="license_${license.users}">
            <fmt:message key="${license.resourceKey}">
              <fmt:param  value="${license.users}" />
            </fmt:message>&nbsp;&mdash;&nbsp;
            ${license.price.amount}&nbsp;${license.price.currency.label}
        </label>
        </li>
        </c:forEach>

        </ul>
        </td>
    </tr>
</table>


<div class="backnext">
<button type="submit" class="next" name="chooseLicense"><fmt:message
        key="common.next" /></button>

</div>

</stripes:form></div>

  </stripes:layout-component>
</stripes:layout-render>

