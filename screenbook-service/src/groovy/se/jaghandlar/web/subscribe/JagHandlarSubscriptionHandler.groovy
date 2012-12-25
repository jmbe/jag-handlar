package se.jaghandlar.web.subscribe

import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

import se.pictosys.account.api.Email;
import se.pictosys.account.api.Username
import se.pictosys.ip.IpAddress
import se.pictosys.license.LicenseSelection
import se.pictosys.password.api.PlainTextPassword
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

    @Resource(name = "userItemLoader")
    def userItemLoader

    Object addPendingPurchase(Username username, LicenseSelection licenseSelection, String type, IpAddress remoteIp) {
        /* PayPal purchases are not supported in Jag handlar. */
        throw new UnsupportedOperationException("Not implemented");
    }

    void addPurchase(Username username, LicenseSelection licenseSelection, ContactInformation contactInformation, String type, IpAddress remoteIp) {
        purchaseService.addInvoicePurchase(username.username, licenseSelection, contactInformation)
    }

    String getCurrentLicense(Username username) {
        return accountService.getNumberOfLicenses(username.username)
    }

    void savePayPalPurchaseId(HttpServletRequest request, Object purchaseId) {
        /* PayPal purchases are not supported in Jag handlar. */
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

    boolean doesUserExist(Username username) {
        log.info "Checking if main account ${username} exists."
        return accountService.mainAccountExists(username.username)
    }

    Username getLoggedInUsername(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not implemented");
    }

    boolean isValidLogin(Username username, PlainTextPassword password) {
        return accountService.isValidLogin(username.username, password.password)
    }

    boolean isLoggedInAsCustomer(HttpServletRequest request) {
        return false;
    }

    void addUser(HttpServletRequest request, NewPurchaseInfo newPurchaseInfo) {
        accountService.createMainAccount(newPurchaseInfo.username.username, newPurchaseInfo.email.email, newPurchaseInfo.notify)
    }

    boolean isRenewal(HttpServletRequest request) {
        return userItemLoader.hasUserItem(request)
    }

    void loadAccountFormValues(HttpServletRequest request, CreateInvoiceForm f) {

        if (!userItemLoader.hasUserItem(request)) {
            return;
        }

        try {
            Username username = userItemLoader.getUserItemUsername(request)
            def purchase = purchaseService.findLatestActivePurchase(username.username)
            if (purchase) {
                f.contactPerson = purchase.contactPerson
                f.phone = purchase.phoneNumber

                f.invoiceFirstName = purchase.invoiceAddress.firstName
                f.invoiceStreetAddressLine1 = purchase.invoiceAddress.streetLine1
                f.invoiceStreetAddressLine2 = purchase.invoiceAddress.streetLine2
                f.invoiceZip = purchase.invoiceAddress.zip
                f.invoiceCity = purchase.invoiceAddress.city
                f.invoiceCountryCode = purchase.invoiceAddress.countryCode
                f.invoiceState = purchase.invoiceAddress.state
            }
        } catch (Exception e) {
            log.error("There was a problem loading account form values", e)
        }
    }

    void loadAddressFormValues(HttpServletRequest request, CreatePaynovaPurchaseForm f) {
        if (!userItemLoader.hasUserItem(request)) {
            return;
        }

        try {

            Username username = userItemLoader.getUserItemUsername(request)
            def purchase = purchaseService.findLatestActivePurchase(username.username)

            if (purchase) {
                f.firstName = purchase.deliveryAddress.firstName
                f.streetAddressLine1 = purchase.deliveryAddress.streetLine1
                f.streetAddressLine2 = purchase.deliveryAddress.streetLine2
                f.zip = purchase.deliveryAddress.zip
                f.city = purchase.deliveryAddress.city
                f.countryCode = purchase.deliveryAddress.countryCode
                f.state = purchase.deliveryAddress.state
            }
        } catch (Exception e) {
            log.error("There was a problem loading address form values", e)
        }
    }

    void onSubscriptionRenewedEvent(HttpServletRequest request, Username username) {
        /* Nothing to do in Jag handlar */
    }

    boolean isPayPalSupported() {
        /* All customers must pay by invoice. */
        return false
    }

    @Override
    public boolean doesEmailExist(Email email) {
        return accountService.emailExists(email.email)
    }

    @Override
    public boolean isLoggedInAsHabilitation(HttpServletRequest request) {
        return false;
    }

    boolean isSubAccount(Username username) {
        return false;
    }
}
