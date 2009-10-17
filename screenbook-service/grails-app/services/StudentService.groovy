
/**
 * Created by IntelliJ IDEA.
 * User: knifhen
 * Date: Sep 13, 2009
 * Time: 7:04:48 PM
 * To change this template use File | Settings | File Templates.
 */
class StudentService {

  def getStudents(String accountName) {
    def students = Student.findAll("from Student as student where student.account.username = :accountName", [accountName: accountName])
    return students
  }

  def findStudent(String accountName, String studentName) {
    def student = Student.find("from Student s where s.account.username = :accountName and s.username = :studentName", [accountName: accountName, studentName: studentName])
    log.info("Found student ${student}")
    return student
  }

  def changeScreenKeyboard(accountName, studentName, Boolean screenKeyboard) {
    def student = findStudent(accountName, studentName)
    student.screenKeyboard = screenKeyboard
  }
}
