package api

import groovy.xml.MarkupBuilder

/**
 * Created by IntelliJ IDEA.
 * User: knifhen
 * Date: Sep 13, 2009
 * Time: 2:15:46 PM
 * To change this template use File | Settings | File Templates.
 */
class ApiResults {

  def static getLoginAsTeacherResult(String usernameString, String apikeyString, String statusString) {
    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)
    xml.result() {
      status(statusString)
      username(usernameString)
      apikey(apikeyString)
    }
    return writer.toString()
  }
}
