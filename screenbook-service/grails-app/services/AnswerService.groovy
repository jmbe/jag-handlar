class AnswerService {

	boolean transactional = true

    def getAnswers(username, bookname) {
      def answers = findAnswers(username, bookname)
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

    def findAnswers(username, bookname) {
        def result = Answer.findAll("from Answer as answer where answer.book.student.username = :username and answer.book.book.name = :bookname",
				[username: username, bookname: bookname])
        println result
        return result
	}

	def findAnswer(username, bookname, question_key) {
        def result = Answer.find("from Answer as answer where answer.book.student.username = :username and answer.question_key = :question_key and answer.book.book.name = :bookname",
				[username: username, question_key: question_key, bookname: bookname])
		if(result != null) {
			return result
		}
	}

	def findWorkBook(username, bookname) {
        def result = WorkBook.find("from WorkBook as workbook where workbook.book.name = :bookname and workbook.student.username = :username",
                [username: username, bookname: bookname])
		if(result != null) {
			return result
		}
	}
}
