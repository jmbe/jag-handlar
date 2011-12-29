package se.jaghandlar
import grails.converters.XML
import se.jaghandlar.Account;
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
      def result = "failure"
      def error
      try {
        /* Authentication */
        apikey = accountService.verifyLogin(username, password)

        /* Authorization */
        def account = Account.findByUsername(username)
        if (account.isActive()) {
          result = "success"
        } else {
          error = "error.account.expired"
        }
      } catch (UserNotFoundException e) {
        error = e.message
      } catch (IncorrectPasswordException e) {
        error = e.message
      }

      render XmlResults.getLoginAsTeacherResult(username, apikey, result, error, false)
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
      String error = "";
      def active = false;

      def valid = accountService.verifyApiLogin(username, apikey);

      if (valid) {
        active = Account.findByUsername(username).isActive();
        if (!active) {
          error = "error.account.expired"
        }
      }

      render new XmlResults().withVerifyApiLoginResult(valid && active, error).toXml();
    }


    def lostPassword = {
      def accountIdentifier = params.accountIdentifier
      log.info("Received new password request for ${accountIdentifier}.")
      render accountService.resetPassword(accountIdentifier) as XML
    }

    def clearBookmarkReminder = {
      def username = params.username
      log.info "Clearing bookmark reminder for account ${username}"



      accountService.clearBookmarkReminder(username)

      render ""
    }

}
