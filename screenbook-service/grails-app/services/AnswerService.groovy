class AnswerService {

	boolean transactional = true

    def getAnswers(username, bookname) {
      def answers = Answer.findByUsernameAndBookname(username, bookname)
      return answers
    }

	def getAnswer(username, bookname, question_key) {
		def answerInstance = findAnswer(username, bookname, question_key)
		return answerInstance
	}

	def setAnswer(username, bookname, question_key, answer) {
		def answerInstance = findAnswer(username, bookname, question_key)
		if (answerInstance == null) {
			def student = Student.findByUsername(username)
			def workBook = findWorkBook(username, bookname)

			if(workBook == null) {
				def book = Book.findByName(bookname)
				workBook = new WorkBook(book: book, student: student)
				workBook.save()
			}

			answerInstance = new Answer(student: student, question_key: question_key, answer: answer, book: workBook)
		} else {
			answerInstance.answer = answer
		}
		answerInstance.save()
		return answerInstance
	}

	def removeAnswer(username, bookname, question_key) {
		def answerInstance = findAnswer(username, bookname, question_key)
		if(answerInstance != null) {
			answerInstance.delete(flush:true)
			return true
		}
		return false
	}

	def findAnswer(username, bookname, question_key) {
		def result = Answer.find("from Answer as a join a.book as w join w.student as s join w.book as b where s.username = :username and a.question_key = :question_key and b.name = :bookname",
				[username: username, question_key: question_key, bookname: bookname])
		if(result != null) {
			return result[0]
		}
	}

	def findWorkBook(username, bookname) {
		def result = WorkBook.find("from WorkBook as w join w.book as b join w.student as s where b.name = :bookname and s.username = :username", [username: username, bookname: bookname])
		if(result != null) {
			return result[0]
		}
	}
}
