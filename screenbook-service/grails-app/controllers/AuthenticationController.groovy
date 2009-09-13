import grails.converters.XML
import se.jaghandlar.exceptions.UserNotFoundException
import se.jaghandlar.exceptions.IncorrectPasswordException
import se.jaghandlar.api.ApiResults
/**
 * Created by IntelliJ IDEA.
 * User: knifhen
 * Date: Sep 13, 2009
 * Time: 5:12:13 PM
 * To change this template use File | Settings | File Templates.
 */
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

      render text: ApiResults.getLoginAsTeacherResult(username, apikey, result, error), contentType:"text/xml"
    }

    def verifyApiLogin = {
      def username = params.username
      def apikey = params.apikey

      render accountService.verifyApiLogin(username, apikey) as XML
    }

	def loginAsStudent = {
		def mainAccountName = params.mainAccountName
		def studentAccountName = params.studentAccountName

        if (mainAccountName == null) {
            log.warn("Main account name is empty");
            return;
        }

        if (studentAccountName == null) {
            log.warn("Student account name is empty");
            return;
        }

        if (!accountService.mainAccountExists(mainAccountName)) {
            log.warn("Could not find main account '${mainAccountName}'");
            render false as XML;
            return;
        }

		def loginVerified = accountService.verifyStudentLogin(mainAccountName, studentAccountName)
		if (!loginVerified && accountService.verifyFreeLicences(mainAccountName)) {
			accountService.createStudentAccount(mainAccountName, studentAccountName)
		} else if (!loginVerified) {
			render false as XML
			return
		}

		render true as XML
	}

}
