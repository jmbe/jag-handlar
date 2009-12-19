import org.codehaus.groovy.grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class PurchaseController {

  def scaffold = Purchase

}
