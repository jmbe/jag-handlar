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
    def bookName = params.book
    def questionkey = params.question_key
    def answerInstance = answerService.getAnswer(accountName, studentname, bookName, questionkey)

    render answerInstance as XML
  }

  /**
   * @param username
   * @param bookname
   */
  def loadAllAnswers = {
    def accountName = params.account
    def studentName = params.student
    def bookName = params.book
    def answers = answerService.getAnswers(accountName, studentName, bookName)
    def studentInstance = studentService.findStudent(accountName, studentName)

    render XmlResults.getAnswersResult(studentInstance, answers, bookName)
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
    def bookName = params.book
    def questionkey = params.question_key
    def answer = params.answer
    def answerInstance = answerService.setAnswer(accountName, studentName, bookName, questionkey, answer)
    render answerInstance as XML
  }

  def removeAnswer = {
    def accountName = params.account
    def studentName = params.student
    def bookName = params.book
    def questionkey = params.question_key
    render answerService.removeAnswer(accountName, studentName, bookName, questionkey) as XML
  }

  def removeAllAnswers = {
    def accountName = params.account
    def studentName = params.student
    def bookName = params.book
    answerService.removeAnswers(accountName, studentName, bookName)

    def answers = answerService.getAnswers(accountName, studentName, bookName)
    def studentInstance = studentService.findStudent(accountName, studentName)

    render XmlResults.getAnswersResult(studentInstance, answers, bookName)
  }

  def loadStudents = {
    def accountName = params.account
    def students = studentService.getStudents(accountName)
    def numberOfLicenses = accountService.getNumberOfLicenses(accountName)
    render XmlResults.getStudentsResult(numberOfLicenses, students)
  }

  def createStudent = {
    def accountName = params.account
    def studentName = params.student
    def screenKeyboard = "true".equals(params.screenKeyboard)

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
      accountService.createStudentAccount(accountName, studentName, screenKeyboard)
    } else if (!loginVerified) {
      render false as XML
      return
    }

    def answers = answerService.getAnswers(accountName, studentName)
    def studentInstance = studentService.findStudent(accountName, studentName)

    render XmlResults.getAnswersResult(studentInstance, answers)
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
    def studentInstance = studentService.findStudent(accountName, studentName)
    render XmlResults.getAnswersResult(studentInstance, answers)
  }

  def getNumberOfLicenses = {
    def accountName = params.account
    def numberOfLicenses = accountService.getNumberOfLicenses(accountName)
    def result = XmlResults.getNumberOfLicensesResult(numberOfLicenses)
    render result
  }

  def changeScreenKeyboard = {
    def accountName = params.account
    def studentName = params.student
    def screenKeyboard = params.screenKeyboard

    log.info("Changing screen keyboard for ${accountName}:${studentName} to ${screenKeyboard}")

    Boolean useScreenKeyboard = Boolean.valueOf(screenKeyboard)

    studentService.changeScreenKeyboard(accountName, studentName, useScreenKeyboard)

    def answers = answerService.getAnswers(accountName, studentName)
    def studentInstance = studentService.findStudent(accountName, studentName)

    render XmlResults.getAnswersResult(studentInstance, answers)

  }

  def changeStudentName = {
    def accountName = params.account
    def oldStudentName = params.student
    def newStudentName = params.newStudentName

    log.info("Changing name for student ${accountName}:${oldStudentName} to ${newStudentName}")

    def changedName = studentService.changeStudentName(accountName, oldStudentName, newStudentName)

    def answers = answerService.getAnswers(accountName, changedName)
    def studentInstance = studentService.findStudent(accountName, changedName)

    if (studentInstance == null) {
      log.warn "Name change failed. New student could not be found. Returning old name!"
      studentInstance = studentService.findStudent(accountName, oldStudentName)
      answers = answerService.getAnswers(accountName, oldStudentName)
    }

    render XmlResults.getAnswersResult(studentInstance, answers)
  }

  def changeContactDetails = {
    def accountName = params.account
    def newContactPerson = params.contactPerson;
    def newEmail = params.email;
    def newPhone = params.phone;


    log.info "Changing contact details for account ${accountName}"

    def success = accountService.changeContactDetails(accountName, newContactPerson, newEmail, newPhone)

    def accountInstance = Account.findByUsername(accountName)

    render XmlResults.getContactDetailsResult(accountInstance, success)
  }

  def loadContactDetails = {
    def accountName = params.account
    log.info "Loading contact details for account ${accountName}."
    def accountInstance = Account.findByUsername(accountName)
    render XmlResults.getContactDetailsResult(accountInstance, true)
  }
 
}
