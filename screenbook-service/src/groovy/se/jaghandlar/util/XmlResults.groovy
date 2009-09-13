package se.jaghandlar.util

import groovy.xml.MarkupBuilder

/**
 * Created by IntelliJ IDEA.
 * User: knifhen
 * Date: Sep 13, 2009
 * Time: 2:15:46 PM
 * To change this template use File | Settings | File Templates.
 */
class XmlResults {

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

  def static getAnswersResult(String studentString, String bookString, answerList) {
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

  def static getStudentsResult(String numberOfLicenses, studentList) {
    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)

    xml.result() {
      numberOfLicenses(numberOfLicenses)
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
