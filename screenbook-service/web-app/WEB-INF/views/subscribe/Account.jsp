<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/partials/common.taglibs.jsp"%>

<c:set var="previousPage" value="/subscribe/account/" scope="session" />

<stripes:layout-render name="/WEB-INF/layout/customer.jsp">
  <stripes:layout-component name="titleKey">title.new.subscription</stripes:layout-component>
  <stripes:layout-component name="bodyClasses">subscribe</stripes:layout-component>
  <stripes:layout-component name="page">account</stripes:layout-component>
  <stripes:layout-component name="content">


<stripes:form action="/subscribe/account/" id="account">

    <h1>V&auml;lj anv&auml;ndarnamn</h1>
    <label class="subscribe-label">Skriv vilket anv&auml;ndarnamn du vill ha (t ex ditt namn, namnet p&aring; skolan, klassen/gruppen, boendet). </label>

    <div class="error-messages"><stripes:errors field="createAccountForm.newUsername" /></div>
    <div>
        <stripes:text id="newUsername" class="subscribe-text" name="createAccountForm.newUsername" />
    </div>
    <div class="note" style="margin-bottom: 25px;"><fmt:message key="createAccountForm.newUsername.hint" /></div>


    <label class="subscribe-label" style="padding-left: 3px;">Din e-postadress (hit skickas dina personliga inloggningsuppgifter).</label>
    <table class="form-fields-right">
        <tr>
            <th><stripes:label class="subscribe-label" for="createAccountForm.email" /></th>
            <td><stripes:text class="subscribe-text" name="createAccountForm.email" /></td>
            <td><div class="error-messages"><stripes:errors field="createAccountForm.email" /></div></td>
        </tr>

        <tr>
            <th><stripes:label class="subscribe-label" for="createAccountForm.verifyEmail" /></th>
            <td><stripes:text id="verifyEmail" class="subscribe-text" name="createAccountForm.verifyEmail" /></td>
            <td>
                <div class="error-messages"><stripes:errors field="createAccountForm.verifyEmail" /></div>
            </td>
        </tr>

    </table>




    <div class="notify"><tag:checkbox name="createAccountForm.notify"
        checked="${empty notify ? true : notify}"
        key="contact.mailinglist.subscribe" /></div>


    <div class="backnext">
      <a href="/subscribe/license/" class="back"><fmt:message key="common.back" /></a>
      <button type="submit" class="next" name="createAccount">
          <fmt:message key="common.next" />
      </button>
    </div>


</stripes:form>
  </stripes:layout-component>

  <stripes:layout-component name="bottomJavascript">

  <script type="text/javascript">
var verifyEmail = $("verifyEmail");
/* prevent context menu */
verifyEmail.observe("contextmenu", function(event) {
  Event.stop(event);
});

/* prevent Ctrl+V */
verifyEmail.observe("keydown", function(event) {
   var insert = 45;
   var v = 86;

    if ((event.ctrlKey && event.keyCode === v) ||
        (event.shiftKey && event.keyCode === insert)) {
        Event.stop(event);
    }
});

$("newUsername").focus();

</script>

  </stripes:layout-component>


</stripes:layout-render>
