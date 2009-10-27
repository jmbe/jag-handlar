import se.pictosys.license.LicenseSelection
import se.pictosys.payment.api.ContactInformation

class PurchaseService {

  static transactional = true

  def addInvoicePurchase(String username,
                         LicenseSelection licenseSelection,
                         ContactInformation contactInformation) {

    log.info "Adding invoice purchase to account ${username}"



    def purchase = null

    Purchase.withTransaction {status ->
      log.info "Saving purchase"
      purchase = new Purchase(licenseSelection, contactInformation)
      purchase.save()
      log.info "Saved purchase ${purchase}."
    }



    Account.withTransaction {status ->
      def account = Account.findByUsername(username, [fetch: [purchases: "eager"]])

      log.info "Account ${account}"
      log.info "Account.purchases ${account.purchases}"

      if (account == null) {
        log.warn "Could not find account ${username}"
        return
      }

      log.info "Account is ${account}"

      log.info "There are ${account.purchases.size()} purchases for the account"

      account.addToPurchases(purchase)

      log.info("Added purchase ${purchase} to account ${account}")

    }

    return purchase

  }

}
