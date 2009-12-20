package se.jaghandlar.web.subscribe

import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import se.pictosys.license.LicenseSelection
import se.pictosys.payment.api.ContactInformation
import se.pictosys.payment.api.data.NewPurchaseInfo
import se.pictosys.payment.web.api.SubscriptionHandler
import se.pictosys.web.api.Scope
import se.pictosys.web.api.ScopeObjectLoader
import se.pictosys.web.stripes.subscribe.invoice.CreateInvoiceForm
import se.pictosys.web.stripes.subscribe.invoice.CreatePaynovaPurchaseForm

@Component("subscriptionHandler")
class JagHandlarSubscriptionHandler implements SubscriptionHandler {

  def licenseLoader = new ScopeObjectLoader<LicenseSelection>("licenseSelection", Scope.SESSION)
  def purchaseInfoLoader = new ScopeObjectLoader<NewPurchaseInfo>("pendingUser", Scope.SESSION)

  def log = LoggerFactory.getLogger(JagHandlarSubscriptionHandler.class)

  @Resource(name = "purchaseService")
  def purchaseService

  @Resource(name = "accountService")
  def accountService

  Object addPendingPurchase(String username, LicenseSelection licenseSelection, String type) {
    throw new UnsupportedOperationException("Not implemented");
  }

  void addPurchase(String username, LicenseSelection licenseSelection, ContactInformation contactInformation, String type) {
    purchaseService.addInvoicePurchase(username, licenseSelection, contactInformation)
  }

  String getCurrentLicense(String username) {
    throw new UnsupportedOperationException("Not implemented");
  }

  void savePayPalPurchaseId(HttpServletRequest request, Object purchaseId) {
    throw new UnsupportedOperationException("Not implemented");
  }

  void savePurchaseInfo(HttpServletRequest request, NewPurchaseInfo purchaseInfo) {
    purchaseInfoLoader.set(request, purchaseInfo)
  }

  NewPurchaseInfo loadPurchaseInfo(HttpServletRequest request) {
    return purchaseInfoLoader.get(request)
  }

  void saveLicenseSelection(HttpServletRequest request, LicenseSelection license) {
    licenseLoader.set(request, license);
  }

  LicenseSelection loadLicenseSelection(HttpServletRequest request) {
    return licenseLoader.get(request)
  }

  boolean doesUserExist(String username) {
    log.info "Checking if main account ${username} exists."
    return accountService.mainAccountExists(username)
  }

  String getLoggedInUsername(HttpServletRequest request) {
    throw new UnsupportedOperationException("Not implemented");
  }

  boolean isValidLogin(String username, String password) {
    throw new UnsupportedOperationException("Not implemented");
  }

  boolean isLoggedInAsCustomer(HttpServletRequest request) {
    return false;
  }

  void addUser(HttpServletRequest request, NewPurchaseInfo newPurchaseInfo) {
    accountService.createMainAccount(newPurchaseInfo.getUsername(), newPurchaseInfo.getEmail(), newPurchaseInfo.isNotify())
  }

  boolean isRenewal(HttpServletRequest request) {
    // TODO hantera renewal
    return false
  }

  void loadAccountFormValues(HttpServletRequest request, CreateInvoiceForm createInvoiceForm) {
    // TODO load account form values
  }

  void loadAddressFormValues(HttpServletRequest request, CreatePaynovaPurchaseForm createPaynovaPurchaseForm) {
    // TODO load address form values
  }

  void onSubscriptionRenewedEvent(HttpServletRequest request, String username) {
    /* Nothing to do in Jag handlar */
  }

  boolean isPayPalSupported() {
    /* All customers must pay by invoice. */
    return false
  }
}
