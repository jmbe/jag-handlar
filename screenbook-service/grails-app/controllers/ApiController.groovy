import grails.converters.XML

class ApiController {

	def accountService
	def answerService

	def index = {
		def methods = [loginAsStudent: "mainAccountName, studentAccountName",
					   createMainAccount: "username",
					   verifyLogin: "username, password",
				       getAnswer: "username, bookname, question_key",
					   setAnswer: "username, bookname, question_key, answer",
					   removeAnswer: "username, bookname, question_key"]
		render methods as XML
	}


	def createMainAccount = {
      def account = accountService.createMainAccount(params.username)

      render account as XML
    }

	def verifyLogin = {
		def username = params.username;
		def password = params.password;

		render accountService.verifyLogin(username, password) as XML
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

		def loginVerified = accountService.verifyStudentLogin(mainAccountName, studentAccountName)
		if (!loginVerified && accountService.verifyFreeLicences(mainAccountName)) {
			accountService.createStudentAccount(mainAccountName, studentAccountName)
		} else if (!loginVerified) {
			render false as XML
			return
		}

		render true as XML
	}

	/**
     * @param username
     * @param question_key
     */
    def getAnswer = {
      def answerInstance = answerService.getAnswer(params.username, params.bookname, params.question_key)
      render answerInstance as XML
    }

    /**
     * @param username
     * @param question_key
     * @param answer
     */
    def setAnswer = {
      //username, question_key, answer
      def answerInstance = answerService.setAnswer(params.username, params.bookname, params.question_key, params.answer)
      render answerInstance as XML
    }

	def removeAnswer = {
		render answerService.removeAnswer(params.username, params.bookname, params.question_key) as XML
	}
}
