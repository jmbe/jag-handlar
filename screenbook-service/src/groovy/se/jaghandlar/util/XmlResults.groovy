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


  def static getLoginAsTeacherResult(String usernameString, String apikeyString, String statusString, String errorString) {
    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)
    xml.result() {
      status(statusString)
      username(usernameString)
      apikey(apikeyString)
      error(errorString)
    }
    return [text: writer.toString(), contentType: "text/xml"]
  }

  def static getAnswersResult(String studentString, answerList, String bookString = "jag-handlar") {
    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)

    xml.result() {
      student(studentString)
      book(bookString)
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
}
