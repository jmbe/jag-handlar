package se.jaghandlar.web.subscribe

import javax.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import se.pictosys.license.LicenseSelection
import se.pictosys.payment.api.ContactInformation
import se.pictosys.payment.api.data.NewPurchaseInfo
import se.pictosys.payment.web.api.SubscriptionHandler
import se.pictosys.web.stripes.subscribe.invoice.CreateInvoiceForm
import se.pictosys.web.stripes.subscribe.invoice.CreatePaynovaPurchaseForm

@Component("subscriptionHandler")
class JagHandlarSubscriptionHandler implements SubscriptionHandler {

  java.lang.Object addPendingPurchase(String username, LicenseSelection licenseSelection, String type) {
    throw new UnsupportedOperationException("Not implemented");
  }

  void addPurchase(String username, LicenseSelection licenseSelection, ContactInformation contactInformation, String type) {
    throw new UnsupportedOperationException("Not implemented");
  }

  String getCurrentLicense(String username) {
    throw new UnsupportedOperationException("Not implemented");
  }

  void savePayPalPurchaseId(HttpServletRequest request, Object purchaseId) {
    throw new UnsupportedOperationException("Not implemented");
  }

  void savePurchaseInfo(HttpServletRequest request, NewPurchaseInfo purchaseInfo) {
    throw new UnsupportedOperationException("Not implemented");
  }

  NewPurchaseInfo loadPurchaseInfo(HttpServletRequest request) {
    throw new UnsupportedOperationException("Not implemented");
  }

  void saveLicenseSelection(HttpServletRequest request, LicenseSelection licenseSelection) {
    throw new UnsupportedOperationException("Not implemented");
  }

  LicenseSelection loadLicenseSelection(HttpServletRequest request) {
    throw new UnsupportedOperationException("Not implemented");
  }

  boolean doesUserExist(String username) {
    throw new UnsupportedOperationException("Not implemented");
  }

  String getLoggedInUsername(HttpServletRequest request) {
    throw new UnsupportedOperationException("Not implemented");
  }

  boolean isValidLogin(String username, String password) {
    throw new UnsupportedOperationException("Not implemented");
  }

  boolean isLoggedInAsCustomer(HttpServletRequest request) {
    throw new UnsupportedOperationException("Not implemented");
  }

  void addUser(HttpServletRequest request, NewPurchaseInfo newPurchaseInfo) {
    throw new UnsupportedOperationException("Not implemented");
  }

  boolean isRenewal(HttpServletRequest request) {
    throw new UnsupportedOperationException("Not implemented");
  }

  void loadAccountFormValues(HttpServletRequest request, CreateInvoiceForm createInvoiceForm) {
    throw new UnsupportedOperationException("Not implemented");
  }

  void loadAddressFormValues(HttpServletRequest request, CreatePaynovaPurchaseForm createPaynovaPurchaseForm) {
    throw new UnsupportedOperationException("Not implemented");
  }

  void onSubscriptionRenewedEvent(HttpServletRequest request, String username) {
    throw new UnsupportedOperationException("Not implemented");
  }
}