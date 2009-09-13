import grails.converters.XML
import api.ApiResults

class ApiController {

	def accountService
	def answerService

    def beforeInterceptor = [action:this.&apiAuthentication,except:'index']

    def apiAuthentication =  {
      //def id = params.id
      //def apikey = params.apikey
      //println "ApiAuthentictaion interceptor found id:" + id + ", apikey: " + apikey
    }

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
        def result = accountService.verifyLogin(username, password) != null
		render result as XML
	}

    def loginAsTeacher = {
      def username = params.username
      def password = params.password

      def apikey = accountService.verifyLogin(username, password)
      def result
      if (apikey) {
        result ="success"
      } else {
        result = "failure"
      }
      render text: ApiResults.getLoginAsTeacherResult(username, apikey, result), contentType:"text/xml"
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

	/**
     * @param username
     * @param question_key
     */
    def getAnswer = {
      def answerInstance = answerService.getAnswer(params.username, params.bookname, params.question_key)
      println answerInstance
      render answerInstance as XML
    }


    /**
    * @param username
    * @param bookname
    */
    def getAnswers = {
      def username = params.username
      def bookname = params.bookname
      def answers = answerService.getAnswers(username, bookname)

      render text: ApiResults.getAnswersResult(username, bookname, answers), contentType: "text/xml"
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
