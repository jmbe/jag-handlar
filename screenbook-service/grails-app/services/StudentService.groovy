class StudentService {

  static transactional = true

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
    student.save()
  }

  /**
   * @return name of student
   */
  def changeStudentName(accountName,  oldStudentName,  newStudentName) {
    if (newStudentName == null || newStudentName.trim().length() == 0) {
      log.warn("New student name was empty when renaming ${accountName}:${oldStudentName}.")
      return oldStudentName
    }

    def student = findStudent(accountName, oldStudentName)
    if (student == null) {
      log.warn("Could not find student named ${accountName}:${oldStudentName} when renaming. Aborting...")
      return oldStudentName
    }

    student.username = newStudentName

    student.save()

    return student.username
  }
}
