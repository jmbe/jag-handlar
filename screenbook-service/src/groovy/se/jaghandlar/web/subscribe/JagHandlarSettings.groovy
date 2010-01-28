package se.jaghandlar.web.subscribe

import org.springframework.stereotype.Component
import se.pictosys.payment.api.messaging.PurchaseMessagingSettings

@Component
class JagHandlarSettings implements PurchaseMessagingSettings {

  String getEmailFromAddress() {
    "no-reply@jaghandlar.se"
  }

  String getOrderEmailAddress() {
    "roland.lundgren@spsm.se"
  }

  String getSupportEmailAddress() {
    "jaghandlar@spsm.se"
  }

  String formatBackofficeUrl(String format, Object ... objects) {
    return String.format(getBackofficeBaseUrl() + format, objects);
  }

  def getBackofficeBaseUrl() {
    "http://www.jaghandlar.se"
  }
}
