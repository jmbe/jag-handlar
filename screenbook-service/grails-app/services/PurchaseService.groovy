import se.jaghandlar.Account;
import se.jaghandlar.Purchase;
import se.jaghandlar.Role;
import se.pictosys.license.LicenseSelection
import se.pictosys.payment.api.ContactInformation

class PurchaseService {

  static transactional = true

  def licenseRepository

  def purchaseMessagingService
  def accountService

  def findLatestActivePurchase(String username) {
    def account = Account.findByUsername(username)
    return account.latestActivePurchase()
  }

  def addInvoicePurchase(String username,
                         LicenseSelection licenseSelection,
                         ContactInformation contactInformation) {

    log.info "Adding invoice purchase to account ${username}."


    def account = Account.findByUsername(username)

    if (account == null) {
      log.warn "Could not find account for ${username}. Skipping adding purchase."
      return
    }

    log.info "Account is ${account}."
    log.info "There are currently ${account.purchases ? account.purchases.size() : 'no'} purchases for the account."


    def purchase = new Purchase(licenseSelection, contactInformation, licenseRepository)

    /* Must add purchase to account before saving it, to pass validation. */
    log.info "Adding purchase ${purchase} to account ${account}..."
    account.addToPurchases(purchase)
    log.info("Added purchase ${purchase} to account ${account}")



    boolean isRenewal = account.isRenewal()

    if (isRenewal) {
      /** Seems that if this method is called after purchase.save(), then the changes made in the method will
       * not be persisted.
       */
      activateRenewalPurchase purchase
    }


    log.info "There are ${account.purchases?.size()} purchases for the account."


    log.info "Saving purchase."

    /* Must save purchase. It is not enough to just save the account aggregate. */
    if (purchase.save()) {
      log.info "Saved purchase ${purchase}."
    } else {
      log.warn "There was a problem saving purchase for user ${username}."
      purchase.errors.allErrors.each {
        log.warn it
      }
    }


    /* Update account contact information */
    log.info "Updating account with new contact details."
    account.phoneNumber = contactInformation.phoneNumber
    account.contactPerson = contactInformation.contactPerson

    /* Save account to update reminders */
    log.info "Saving account ${account}"
    if (!account.save()) {
      log.warn "There was a problem saving reminders for account ${username}"
      account.errors.allErrors.each {
        log.warn it
      }
    }


    if (isRenewal) {
      fireRenewalPurchaseAdded purchase
    } else {
      fireNewPurchaseAdded purchase
    }


    return purchase

  }

  def activateRenewalPurchase(Purchase purchase) {
    log.info "Activating renewal purchase for purchase ${purchase.id}"

    Account account = purchase.account

    Date previousEndDate = account.latestEndDate()
    purchase.markPaid(previousEndDate)

    purchase.account.resetReminders()
    log.info "Account reminders have been reset."
  }

  def fireRenewalPurchaseAdded(Purchase purchase) {
    log.info "Handling renewal purchase"

    purchaseMessagingService.sendAdminRenewalPurchaseNotification purchase
    purchaseMessagingService.sendCustomerRenewalPurchaseMail purchase
  }

  def fireNewPurchaseAdded(Purchase purchase) {
    log.info "Handling new purchase"

    purchaseMessagingService.sendAdminNewPurchaseNotification purchase
    purchaseMessagingService.sendCustomerNewPurchaseMail purchase
  }

  def activatePurchase(long purchaseId) {
    def purchase = Purchase.findById(purchaseId)

    purchase.markPaid()
    purchase.account.resetReminders()


    sendConfirmInvoicePurchaseActivatedMailToCustomer purchase
    
  }

  def sendConfirmInvoicePurchaseActivatedMailToCustomer(Purchase purchase) {

    def account = purchase.account

    if (account.isNew()) {

      def plainTextPassword = accountService.generateNewPassword();
      purchaseMessagingService.sendPasswordMailToNewCustomer purchase, plainTextPassword;
      
      account.accountActivatedWithPassword(plainTextPassword)


      Role role = Role.findByAuthority("ROLE_TEACHER")

      if (!account.authorities.contains(role)) {
        log.warn "Account ${account.username} didn't have teacher role"
      }

      account.save()


    } else {
      /* send subscription renewed confirmation */
    }

  }
}
