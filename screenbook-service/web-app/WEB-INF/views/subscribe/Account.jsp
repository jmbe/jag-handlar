<%@ include file="/WEB-INF/partials/common.taglibs.jsp"%>

<c:set var="previousPage" value="/subscribe/account/" scope="session" />

<stripes:layout-render name="/WEB-INF/layout/customer.jsp">
  <stripes:layout-component name="titleKey">title.new.subscription</stripes:layout-component>
  <stripes:layout-component name="bodyClasses">subscribe</stripes:layout-component>
  <stripes:layout-component name="content">

<div class="upperblock">
<stripes:form action="/subscribe/account/" id="account">

    <fieldset class="x-fieldset">


    <div class="error-messages"><stripes:errors field="createAccountForm.newUsername" /></div>
    <div class="item">
      <stripes:label for="createAccountForm.newUsername" />
      <stripes:text id="newUsername" name="createAccountForm.newUsername" />
      <div class="note"><fmt:message key="createAccountForm.newUsername.hint" /></div>
    </div>

    <div class="error-messages"><stripes:errors field="createAccountForm.email" /></div>
    <div class="item">
      <stripes:label for="createAccountForm.email" />
      <stripes:text name="createAccountForm.email" />
    </div>

    <div class="error-messages"><stripes:errors field="createAccountForm.verifyEmail" /></div>
    <div class="item">
      <stripes:label for="createAccountForm.verifyEmail" />
      <stripes:text id="verifyEmail" name="createAccountForm.verifyEmail" />
    </div>

    <div class="item">
    <div class="notify"><tag:checkbox name="createAccountForm.notify"
        checked="${empty notify ? true : notify}"
        key="contact.mailinglist.subscribe" /></div>
    </div>
    </fieldset>

    <div class="backnext" style="width: 497px">
      <a href="/subscribe/license/" class="back"><fmt:message key="common.back" /></a>
    <button type="submit" class="next" name="createAccount"><fmt:message
        key="common.next" /></button>
    </div>


</stripes:form></div>
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
