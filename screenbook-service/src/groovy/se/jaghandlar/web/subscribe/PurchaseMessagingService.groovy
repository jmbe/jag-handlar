package se.jaghandlar.web.subscribe

import javax.annotation.Resource
import org.apache.log4j.LogManager
import org.springframework.stereotype.Component

@Component
class PurchaseMessagingService {

  def log = LogManager.getLogger(PurchaseMessagingService.class)

  @Resource(name = "mailService")
  def mailService

  @Resource(name = "jagHandlarSettings")
  def settings;



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

  def sendAdminNewPurchaseNotification(def purchase) {
    log.info "Sending admin new purchase notification"

    def user = purchase.account
    def url = settings.formatBackofficeUrl("/admin/account/%s", user.username)

    mailService.sendMail {
      to settings.orderEmailAddress
      from settings.emailFromAddress
      subject "Abonnemangsbeställing på Jag handlar från ${purchase.account.username}"
      body """\
Abonnemangsbeställning av Jag handlar:

Användarnamn: ${user.username}
Epostadress: ${user.email}
Kontaktperson: ${purchase.contactPerson}

Faktura skickas till:

${purchase.invoiceAddress}



Leveransadress:

${purchase.deliveryAddress}


Licens: ${purchase.license}, ${purchase.amount} ${purchase.currency}


${url}

      """
    }

  }
}
