import grails.converters.XML
import se.jaghandlar.exceptions.UserNotFoundException
import se.jaghandlar.exceptions.IncorrectPasswordException
import se.jaghandlar.util.XmlResults

class AuthenticationController {
    def accountService
    def answerService
    
    def createMainAccount = {
      def account = accountService.createMainAccount(params.username)

      render account as XML
    }

	def verifyLogin = {
		def username = params.username;
		def password = params.password;
        def result = false
        try {
          result = accountService.verifyLogin(username, password) != null
        } catch (UserNotFoundException e) {
        } catch (IncorrectPasswordException) {}

        render result as XML
	}

    def loginAsTeacher = {
      def username = params.username
      def password = params.password

      def apikey
      def result
      def error
      try {
        apikey = accountService.verifyLogin(username, password)
        result ="success"
      } catch (UserNotFoundException e) {
        result = "failure"
        error = e.message
      } catch (IncorrectPasswordException e) {
        result = "failure"
        error = e.message
      }

      render text: XmlResults.getLoginAsTeacherResult(username, apikey, result, error), contentType:"text/xml"
    }

    def verifyApiLogin = {
      def username = params.username
      def apikey = params.apikey

      render accountService.verifyApiLogin(username, apikey) as XML
    }

	

}
