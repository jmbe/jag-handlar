package se.jaghandlar.util

import groovy.xml.MarkupBuilder

class XmlResults {

  def writer
  def xml

  XmlResults() {
    writer = new StringWriter()
    xml = new MarkupBuilder(writer)
  }


  def toXml() {
    return [text: writer.toString(), contentType: "text/xml"]
  }

  def withVerifyApiLoginResult(boolean isValid) {
    xml.result() {
      valid(isValid)
    }
    
    return this;
  }


  def static getLoginAsTeacherResult(String usernameString, String apikeyString, String statusString, String errorString, boolean showBookmarkReminderFlag) {
    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)
    xml.result() {
      status(statusString)
      username(usernameString)
      apikey(apikeyString)
      error(errorString)
      showBookmarkReminder(showBookmarkReminderFlag)
    }
    return [text: writer.toString(), contentType: "text/xml"]
  }

  def static getAnswersResult(studentInstance, answerList, String bookString = "jag-handlar") {
    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)

    xml.result() {
      student(studentInstance.username)
      book(bookString)
      screenKeyboard(studentInstance.screenKeyboard)
      answers() {
        for (answerInstance in answerList) {
          answer() {
            question_key(answerInstance.question_key)
            answer(answerInstance.answer)
          }
        }
      }
    }


    return [text: writer.toString(), contentType: "text/xml"]
  }

  def static getStudentsResult(numberOfLicensesString, studentList) {
    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)

    xml.result() {
      numberOfLicenses(numberOfLicensesString)
      students() {
        for (studentInstance in studentList) {
          student() {
            name(studentInstance.username)
            screenKeyboard(studentInstance.screenKeyboard)
          }
        }
      }
    }

    return [text: writer.toString(), contentType: "text/xml"]
  }

  def static getNumberOfLicensesResult(String numberOfLicensesString) {
    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)

    xml.result() {
      numberOfLicenses(numberOfLicensesString)
    }

    return [text: writer.toString(), contentType: "text/xml"]

  }

  static def getContactDetailsResult(def account, boolean success) {
    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)

    xml.result() {
      contactPerson account.contactPerson
      email account.email
      phone account.phoneNumber
      status success ? "OK" : "NOK"
    }

    return [text: writer.toString(), contentType: "text/xml"]
  }
}
