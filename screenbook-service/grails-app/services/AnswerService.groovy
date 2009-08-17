class AnswerService {

  boolean transactional = true

  def getAnswer(username, question_key) {
    def answerInstance = findAnswer(username: username, question_key: question_key)
    return answerInstance
  }

  def setAnswer(username, question_key, answer) {
    def answerInstance = findAnswer(username: username, question_key: question_key)
    if (answerInstance == null) {
      def student = Student.findByUsername(username)
      answerInstance = new Answer(student: student, question_key: question_key, answer: answer)
    } else {
      answerInstance.answer = answer
    }
    answerInstance.save()
    return answerInstance
  }

  def findAnswer(username, question_key) {
    return Answer.find("from Answer as a join a.student as s where s.username = ? and a.question_key = ?", [username, question_key])[0]
  }
  
}
