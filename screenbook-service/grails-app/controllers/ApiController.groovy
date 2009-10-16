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
      log.warn "api authentication failed for ${accountName}"
      return false
    }
    return true
  }

  /**
   * @param username
   * @param question_key
   */
  def loadAnswer = {
    def accountName = params.account
    def studentname = params.student
    def bookname = params.book
    def questionkey = params.question_key
    def answerInstance = answerService.getAnswer(accountName, studentname, bookname, questionkey)

    render answerInstance as XML
  }

  /**
   * @param username
   * @param bookname
   */
  def loadAllAnswers = {
    def accountName = params.account
    def studentName = params.student
    def bookname = params.book
    def answers = answerService.getAnswers(accountName, studentName, bookname)

    render XmlResults.getAnswersResult(studentName, answers, bookname)
  }

  /**
   * @param username
   * @param question_key
   * @param answer
   */
  def saveAnswer = {
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

  def loadStudents = {
    def accountName = params.account
    def students = studentService.getStudents(accountName)
    def numberOfLicenses = 4
    render XmlResults.getStudentsResult(numberOfLicenses, students)
  }

  def createStudent = {
    def accountName = params.account
    def studentName = params.student

    log.info("Creating student '${studentName}' on account '${accountName}'.")
    
    if (accountName == null) {
      log.warn("Main account name is empty");
      return;
    }

    if (studentName == null) {
      log.warn("Student account name is empty");
      return;
    }

    def loginVerified = accountService.verifyStudentLogin(accountName, studentName)
    if (!loginVerified && accountService.verifyFreeLicences(accountName)) {
      accountService.createStudentAccount(accountName, studentName)
    } else if (!loginVerified) {
      render false as XML
      return
    }

    def answers = answerService.getAnswers(accountName, studentName)
    render XmlResults.getAnswersResult(studentName, answers)
  }

  /**
   * Requires that the client is logged in using API account, and that the student account already exists (non-case
   * sensitive student account name).
   */
  def openBookAsStudent = {
    def accountName = params.account
    def studentName = params.student

    log.info("Opening book for student '${studentName}' on account '${accountName}'.")

    def studentExists = accountService.verifyStudentLogin(accountName, studentName)

    if (!studentExists) {
      render false as XML
      return
    }

    def answers = answerService.getAnswers(accountName, studentName)
    render XmlResults.getAnswersResult(studentName, answers)
  }

  def getNumberOfLicenses = {
    //def accountName = params.account
    def result = XmlResults.getNumberOfLicensesResult("4")
    render result
  }
 
}