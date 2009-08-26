import grails.converters.XML

class ApiController {

	def accountService
	def answerService
	
	def createMainAccount = {
      def account = accountService.createMainAccount(params.username)

      render account as XML
    }
	
	def loginAsStudent = {
		def mainAccountName = params.mainAccountName
		def studentAccountName = params.studentAccountName

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
}
