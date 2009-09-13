
/**
 * Created by IntelliJ IDEA.
 * User: knifhen
 * Date: Sep 13, 2009
 * Time: 7:04:48 PM
 * To change this template use File | Settings | File Templates.
 */
class StudentService {

  def getStudents(String username) {
    def students = Student.findAll("from Student as student where student.account.username = :username", [username: username])
    return students
  }
}
