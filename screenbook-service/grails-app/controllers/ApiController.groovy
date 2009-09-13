import grails.converters.XML
import se.jaghandlar.util.XmlResults

class ApiController {

  def accountService
  def studentService
  def answerService

  def beforeInterceptor = {
    def accountName = params.account
    def authApikey = params.apikey
    if (!accountService.verifyApiLogin(accountName, authApikey)) {
      println "apiAuth not authorized"
      return false
    }
  }

  /**
   * @param username
   * @param question_key
   */
  def getAnswer = {
    def accountName = params.account
    def studentname = params.student
    def bookname = params.bookname
    def questionkey = params.question_key
    def answerInstance = answerService.getAnswer(accountName, studentname, bookname, questionkey)
    
    render answerInstance as XML
  }

  /**
   * @param username
   * @param bookname
   */
  def getAnswers = {
    def accountName = params.account
    def studentName = params.student
    def bookname = params.book
    def answers = answerService.getAnswers(accountName, studentName, bookname)

    render text: XmlResults.getAnswersResult(studentName, bookname, answers), contentType: "text/xml"
  }

  /**
   * @param username
   * @param question_key
   * @param answer
   */
  def setAnswer = {
    //username, question_key, answer
    def accountName = params.account
    def studentName = params.student
    def bookname = params.book
    def questionkey = params.question_key
    def answer = params.answer
    def answerInstance = answerService.setAnswer(accountName, studentName, bookname, questionkey, answer)
    render answerInstance as XML
  }

  def removeAnswer = {
    def accountName = params.account
    def studentName = params.student
    def bookname = params.book
    def questionkey = params.question_key
    render answerService.removeAnswer(accountName, studentName, bookname, questionkey) as XML
  }

  def removeAnswers = {
    def accountName = params.account
    def studentName = params.student
    def bookName = params.book
    answerService.removeAnswers(accountName, studentName, bookName)
    render true as XML
  }

  def getStudents = {
    def accountName = params.account
    def students = studentService.getStudents(accountName)
    render text: XmlResults.getStudentsResult(students), contentType: "text/xml"
  }

  def openBookAsStudent = {
    def accountName = params.account
    def studentName = params.student

    if (accountName == null) {
      log.warn("Main account name is empty");
      return;
    }

    if (studentName == null) {
      log.warn("Student account name is empty");
      return;
    }

    //TODO This is probably unneccesary since the account already has been verified
    if (!accountService.mainAccountExists(accountName)) {
      log.warn("Could not find main account '${accountName}'");
      render false as XML;
      return;
    }

    def loginVerified = accountService.verifyStudentLogin(accountName, studentName)
    if (!loginVerified && accountService.verifyFreeLicences(accountName)) {
      accountService.createStudentAccount(accountName, studentName)
    } else if (!loginVerified) {
      render false as XML
      return
    }

    render true as XML
  }

  def getNumberOfLicenses = {
    def accountName = params.account
    render text: "<result><numberOfLicenses>4</numberOfLicenses></result>", contentType: "text/xml"
  }

}