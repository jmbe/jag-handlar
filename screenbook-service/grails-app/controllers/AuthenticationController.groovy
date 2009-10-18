import grails.converters.XML
import se.jaghandlar.exceptions.UserNotFoundException
import se.jaghandlar.exceptions.IncorrectPasswordException
import se.jaghandlar.util.XmlResults

class AuthenticationController {
    def accountService
    def answerService

    def createMainAccount = {
      def account = accountService.createMainAccount(params.username, params.email)

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

      render XmlResults.getLoginAsTeacherResult(username, apikey, result, error)
    }

    def changeTeacherPassword = {
      def accountName = params.account
      def oldPassword = params.oldPassword
      def newPassword = params.newPassword

      def result = accountService.changePassword(accountName, oldPassword, newPassword)

      render result as XML

    }

    def verifyApiLogin = {
      def username = params.username
      def apikey = params.apikey

      def valid = accountService.verifyApiLogin(username, apikey);

      render new XmlResults().withVerifyApiLoginResult(valid).toXml();
    }


    def lostPassword = {
      def accountIdentifier = params.accountIdentifier
      log.info("Received new password request for ${accountIdentifier}.")
      render accountService.resetPassword(accountIdentifier) as XML
    }

}
