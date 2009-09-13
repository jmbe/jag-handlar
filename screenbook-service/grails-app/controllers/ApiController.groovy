import grails.converters.XML
import se.jaghandlar.api.ApiResults
import se.jaghandlar.exceptions.UserNotFoundException
import se.jaghandlar.exceptions.IncorrectPasswordException

class ApiController {

	def accountService
	def answerService

    def beforeInterceptor = {
      def authId = params.id
      def authApikey = params.apikey
      if (!accountService.verifyApiLogin(authId, authApikey)) {
        println "apiAuth not authorized"
        return false
      }
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
