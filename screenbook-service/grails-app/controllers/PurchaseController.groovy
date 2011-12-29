import org.codehaus.groovy.grails.plugins.springsecurity.Secured

import se.jaghandlar.Purchase;

@Secured(['ROLE_ADMIN'])
class PurchaseController {

  def scaffold = Purchase

}
