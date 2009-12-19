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

  def sendAdminNewPurchaseNotification(def purchase) {
    log.info "Sending admin new purchase notification"

    def account = purchase.account
    def url = settings.formatBackofficeUrl("/account/show/%s", account.id)

    mailService.sendMail {
      to settings.orderEmailAddress
      from settings.emailFromAddress
      subject "Abonnemangsbeställning på Jag handlar från ${purchase.account.username}"
      body """\
Abonnemangsbeställning av Jag handlar:

Användarnamn: ${account.username}
Epostadress: ${account.email}
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
