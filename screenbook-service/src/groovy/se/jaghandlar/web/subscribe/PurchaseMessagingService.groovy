package se.jaghandlar.web.subscribe

import java.text.SimpleDateFormat
import javax.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PurchaseMessagingService {

  def log = LoggerFactory.getLogger(PurchaseMessagingService.class)

  @Resource(name = "mailService")
  def mailService

  @Resource(name = "jagHandlarSettings")
  def settings;


  def sendCustomerExpirationWarning(def account, Date expiryDate) {
    if (expiryDate == null) {
      log.warn "Expiry date was null for ${account.username}. Skipping sending mail."
      return
    }

    if (new Date().after(expiryDate)) {
      log.warn "Account has already expired for account ${account.username}. Skipping sending mail."
      return
    }

    log.info("Sending customer expiration warning mail to ${account.email} for account ${account.username}.")

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")

    def mailBody = """
Ditt abonnemang på Jag handlar med användarnamnet ${account.username} upphör ${sdf.format(expiryDate)}.

När du förlänger ditt abonnemang löper det 15 månader framåt i tiden från och med det datum ditt nuvarande abonnemang upphör.

Välkommen in på http://www.jaghandlar.se/subscribe/ för att förnya ditt abonnemang!
"""

    mailService.sendMail {
      to account.email
      from settings.emailFromAddress
      subject "Ditt abonnemang på Jag handlar löper snart ut"
      body mailBody
    }
    
  }


  def sendPasswordMailToNewCustomer(def purchase, String plainTextPassword) {
    log.info "Sending password mail to new customer ${purchase.account.username}"


    mailService.sendMail {
      to purchase.account.email
      from settings.emailFromAddress
      subject "Kontouppgifter till Jag handlar"
      body """\
Det här är dina inloggningsuppgifter till Jag handlar:

Användarnamn: ${purchase.account.username}
Lösenord: ${plainTextPassword}

Abonnemanget gäller i 15 månader.

Välkommen till http://www.jaghandlar.se/!
      """


    }
  }

  def sendRenewalConfirmationToCustomer(def purchase) {

    log.info "Sending renewal confirmation to customer ${purchase.account.username}"

    mailService.sendMail {
      to purchase.account.email
      from settings.emailFromAddress
      subject "Bekräftelse förlängt abonnemang"
      body """\
Tack för att du valt att förlänga ditt abonnemang på Jag handlar!

Du kan nu använda Jag handlar i ytterligare 15 månader.

Välkommen till http://www.jaghandlar.se/!
      """
    }


  }

  def sendCustomerNewPurchaseMail(def purchase) {
    log.info "Sending customer new purchase mail"

    mailService.sendMail {
      to purchase.account.email
      from settings.emailFromAddress
      subject "Orderbekräftelse Jag handlar"
      body """\
Tack för din beställning av Jag handlar!

Vi kommer nu att behandla din beställning och du får inom kort ett nytt mail med inloggningsuppgifter."""
    }
  }

  def sendCustomerRenewalPurchaseMail(def purchase) {
    log.info "Sending customer renewal confirmation mail"

    mailService.sendMail {
      to purchase.account.email
      from settings.emailFromAddress
      subject "Orderbekräftelse Jag handlar - förnyat abonnemang"
      body """\
Tack för din beställning av Jag handlar!

Ditt abonnemang har förlängts med 15 månader.

Välkommen till http://www.jaghandlar.se/
"""
    }
    
    
  }

  def sendAdminRenewalPurchaseNotification(def purchase) {
    sendAdminPurchaseNotification(purchase, "Förnyat abonnemang", "Abonnemanget har redan aktiverats, eftersom detta gäller en tidigare kund.")
  }

  def sendAdminNewPurchaseNotification(def purchase) {
    sendAdminPurchaseNotification(purchase, "Abonnemangsbeställning")
  }

  def sendAdminPurchaseNotification(def purchase, String purchaseType, def notice = "") {
  
    log.info "Sending admin new purchase notification"

    def account = purchase.account
    def url = settings.formatBackofficeUrl("/account/show/%s", account.id)


    def adminMails = getOrderEmailRecipients()


    mailService.sendMail {
      to adminMails.toArray()
      from settings.emailFromAddress
      subject "${purchaseType} på Jag handlar från ${purchase.account.username}"
      body """\
${purchaseType} på Jag handlar:

Användarnamn: ${account.username}
Epostadress: ${account.email}
Kontaktperson: ${purchase.contactPerson}

Faktura skickas till:

${purchase.invoiceAddress}



Leveransadress:

${purchase.deliveryAddress}


Licens: ${purchase.license}, ${purchase.amount} ${purchase.currency}



${notice}
${url}

      """
    }

  }

  def getOrderEmailRecipients() {
    ["jm.bergqvist@gmail.com", "roland.lundgren@spsm.se", "jaghandlar-order@spsm.se"]
  }
}
