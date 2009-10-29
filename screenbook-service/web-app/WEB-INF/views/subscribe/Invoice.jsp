<%@ include file="/WEB-INF/partials/common.taglibs.jsp"%>

<c:set var="previousPage" value="/subscribe/invoice/" scope="session" />

<stripes:layout-render name="/WEB-INF/layout/customer.jsp">
  <stripes:layout-component name="titleKey">title.new.subscription</stripes:layout-component>
  <stripes:layout-component name="bodyClasses">subscribe invoice</stripes:layout-component>
  <stripes:layout-component name="content">

<div class="upperblock">
<stripes:form action="/subscribe/invoice/createInvoice" id="invoice">

    <fieldset class="x-fieldset">

    <div class="error-messages"><stripes:errors field="createInvoiceForm.contactPerson" /></div>
    <div class="item">
      <stripes:label for="createInvoiceForm.contactPerson" />
      <stripes:text name="createInvoiceForm.contactPerson" id="contactPerson" />
    </div>

    <div class="error-messages"><stripes:errors field="createInvoiceForm.phone" /></div>
    <div class="item">
      <stripes:label for="createInvoiceForm.phone" />
      <stripes:text name="createInvoiceForm.phone" />
    </div>

    <div class="item optional">
      <stripes:label for="createInvoiceForm.customerNumber" />
      <stripes:text name="createInvoiceForm.customerNumber" id="customerNumber" />
    </div>
    <div class="item">
      <div class="note"><fmt:message key="contact.not.required" /></div>
    </div>

    </fieldset>

    <div class="header">
    <h2><fmt:message key="contact.invoice.address.delivery" /></h2>
    <br />
    </div>

    <fieldset class="x-fieldset" id="deliveryAddressPanel">

    <div class="error-messages"><stripes:errors field="createInvoiceForm.firstName" /></div>
    <div class="item">
      <stripes:label for="createInvoiceForm.firstName" />
      <stripes:text name="createInvoiceForm.firstName" id="firstName" />
    </div>

    <div class="error-messages"><stripes:errors field="createInvoiceForm.lastName" /></div>
    <div class="item">
      <stripes:label for="createInvoiceForm.lastName" />
      <stripes:text name="createInvoiceForm.lastName" />
    </div>

    <div class="error-messages"><stripes:errors field="createInvoiceForm.streetAddressLine1" /></div>
    <div class="item">
      <stripes:label for="createInvoiceForm.streetAddressLine1" />
      <stripes:text name="createInvoiceForm.streetAddressLine1" />
    </div>

    <div class="item"><label>&nbsp;</label>
      <stripes:text name="createInvoiceForm.streetAddressLine2" />
    </div>

    <div class="error-messages"><stripes:errors field="createInvoiceForm.zip" /></div>
    <div class="item">
        <stripes:label for="createInvoiceForm.zip" />
        <stripes:text name="createInvoiceForm.zip" />
    </div>

    <div class="error-messages"><stripes:errors field="createInvoiceForm.city" /></div>
    <div class="item">
      <stripes:label for="createInvoiceForm.city" />
      <stripes:text name="createInvoiceForm.city" />
    </div>


    <div class="item">
      <stripes:label for="createInvoiceForm.countryCode" />
      <stripes:select name="createInvoiceForm.countryCode" id="countryCode">
        <stripes:option value="">&nbsp;</stripes:option>
        <tag:country-options collection="${countries}" selected="${actionBean.createInvoiceForm.countryCode}" />
      </stripes:select>
    </div>

    <div class="item" id="state" style="display: none">
       <stripes:label for="createInvoiceForm.state" />
       <stripes:select name="createInvoiceForm.state">
        <stripes:option value="">&nbsp;</stripes:option>
        <optgroup label="Canada">
          <tag:states-options collection="${canadianStates}" selected="${actionBean.createInvoiceForm.state}" />
        </optgroup>
        <optgroup label="United States">
          <tag:states-options collection="${usStates}" selected="${actionBean.createInvoiceForm.state}" />
        </optgroup>
    </stripes:select></div>


    </fieldset>


    <div class="header">
    <h2><fmt:message key="contact.invoice.address" /></h2>

    <div class="checkbox" style="float: right;">
      <tag:checkbox id="invoiceAddressSame" name="createInvoiceForm.invoiceAddressSame" key="contact.invoice.address.same" checked="${actionBean.createInvoiceForm.invoiceAddressSame}"/></div>

    <br />
    </div>

    <fieldset id="invoiceAddressPanel" class="x-fieldset">



    <div class="error-messages"><stripes:errors field="createInvoiceForm.invoiceFirstName" /></div>
    <div class="item">
        <stripes:label for="createInvoiceForm.invoiceFirstName" />
        <stripes:text id="invoiceFirstName" name="createInvoiceForm.invoiceFirstName" />
    </div>

    <div class="error-messages"><stripes:errors field="createInvoiceForm.invoiceLastName" /></div>
    <div class="item">
        <stripes:label for="createInvoiceForm.invoiceLastName" />
        <stripes:text name="createInvoiceForm.invoiceLastName" />
    </div>

    <div class="error-messages"><stripes:errors field="createInvoiceForm.invoiceStreetAddressLine1" /></div>
    <div class="item">
        <stripes:label for="createInvoiceForm.invoiceStreetAddressLine1" />
        <stripes:text name="createInvoiceForm.invoiceStreetAddressLine1" />
    </div>

    <div class="item">
      <label>&nbsp;</label>
      <stripes:text name="createInvoiceForm.invoiceStreetAddressLine2" />
    </div>

    <div class="error-messages"><stripes:errors field="createInvoiceForm.invoiceZip" /></div>
    <div class="item">
      <stripes:label for="createInvoiceForm.invoiceZip" />
      <stripes:text name="createInvoiceForm.invoiceZip" />
    </div>

    <div class="error-messages"><stripes:errors field="createInvoiceForm.invoiceCity" /></div>
    <div class="item">
      <stripes:label for="createInvoiceForm.invoiceCity" />
      <stripes:text name="createInvoiceForm.invoiceCity" />
    </div>


    <div class="item">
      <stripes:label for="createInvoiceForm.invoiceCountryCode" />
      <stripes:select name="createInvoiceForm.invoiceCountryCode" id="invoiceCountryCode">
        <stripes:option value="">&nbsp;</stripes:option>
        <tag:country-options collection="${countries}" selected="${actionBean.createInvoiceForm.invoiceCountryCode}"></tag:country-options>
    </stripes:select></div>

    <div class="item" id="invoiceState" style="display: none;">
      <stripes:label for="createInvoiceForm.invoiceState" />
      <stripes:select
        name="createInvoiceForm.invoiceState">
        <stripes:option value="">&nbsp;</stripes:option>
        <optgroup label="Canada">
          <tag:states-options collection="${canadianStates}" selected="${actionBean.createInvoiceForm.invoiceState}" />
        </optgroup>

        <optgroup label="United States">
          <tag:states-options collection="${usStates}" selected="${actionBean.createInvoiceForm.invoiceState}" />
        </optgroup>
    </stripes:select></div>


    </fieldset>



    <div class="backnext" style="width: 455px; margin-bottom: 150px;">
      <a href="/subscribe/account/" class="back"><fmt:message key="common.back" /></a>

    <button type="submit" class="next" name="createInvoice"><fmt:message
        key="common.next" /></button>
    </div>
</stripes:form></div>
  </stripes:layout-component>

  <stripes:layout-component name="bottomJavascript">
<script type="text/javascript">
Event.observe(window, "load", function() {
    $("contactPerson").focus();
});



(function() {
    var checkbox = "invoiceAddressSame";
    var panel = "invoiceAddressPanel";
    function toggleInvoiceAddress() {
        var show = !($F(checkbox));
        if (show) {
            // Effect.Appear(panel, {duration:0.5});
            $(panel).setOpacity(1.0);
            $("invoiceFirstName").focus();
        } else {
            //Effect.Fade(panel, {duration:0.5});
            $(panel).setOpacity(0.5);
        }
    }

    function setupInvoiceAddress() {
        var show = !($F(checkbox));
        if (show) {
            //$(panel).show();
            $(panel).setOpacity(1.0);
        } else {
            //$(panel).hide();
            $(panel).setOpacity(0.5);
        }
    }

    $(checkbox).observe("change", toggleInvoiceAddress);
    $(checkbox).observe("click", toggleInvoiceAddress); // reqd for IE
    Event.observe(window, "load", setupInvoiceAddress);

})();


(new CountryCodeListener("countryCode", "state"));
(new CountryCodeListener("invoiceCountryCode", "invoiceState"));

</script>

  </stripes:layout-component>

</stripes:layout-render>
