<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="previousPage" value="/subscribe/login" scope="session" />

<stripes:layout-render name="/WEB-INF/layout/customer.jsp">
  <stripes:layout-component name="titleKey">title.renew.subscription</stripes:layout-component>
  <stripes:layout-component name="bodyClasses">subscribe</stripes:layout-component>
  <stripes:layout-component name="page">login-renew</stripes:layout-component>

  <stripes:layout-component name="content">



<h1 class="largeheading"><fmt:message key="title.renew.subscription"/></h1>

<p class="text" ><fmt:message key="subscribe.renew.login"/> </p>

<p style="margin-top: 10px;" class="text">
<fmt:message key="subscribe.renew.no.account.1" />&nbsp;<a href="/subscribe/info"><fmt:message key="subscribe.renew.no.account.2"/></a>.
</p>



<stripes:form action="/subscribe/login/checkLogin" id="account">

    <table class="form-fields-right">
        <tr>
            <th><label class="subscribe-label"><fmt:message key="common.username" /></label></th>
            <td><stripes:text class="subscribe-text" id="subscribeUsername" name="subscribeCheckLoginForm.subscribeUsername" /></td>
            <td><div class="error-messages"><stripes:errors field="subscribeCheckLoginForm.subscribeUsername" /></div></td>
        </tr>

        <tr>
            <th><label class="subscribe-label"><fmt:message key="common.password" /></label></th>
            <td><stripes:password id="verifyEmail" class="subscribe-text" name="subscribeCheckLoginForm.subscribePassword" /></td>
            <td>
                <div class="error-messages"><stripes:errors field="subscribeCheckLoginForm.subscribePassword" /></div>
            </td>
        </tr>

    </table>


    <div class="backnext" style="background: none;">
      <img src="/images/subscribe/long-gradient.png" alt="" />
      <button type="submit" class="next" name="checkLogin">
          <fmt:message key="common.next" />
      </button>
    </div>


</stripes:form>




</stripes:layout-component>


<stripes:layout-component name="bottomJavascript">

<script type="text/javascript">
$("subscribeUsername").focus();
</script>

</stripes:layout-component>

</stripes:layout-render>
