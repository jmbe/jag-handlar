class AnswerService {

	static transactional = true

    def getAnswers(accountName, studentName, bookname = "jag-handlar") {
      def answers = findAnswers(accountName, studentName, bookname)
      return answers
    }

	def getAnswer(accountName, studentName, bookname, question_key) {
		def answerInstance = findAnswer(accountName, studentName, bookname, question_key)

        if (answerInstance == null) {
          return new Answer(question_key: question_key, answer: "")
        }

		return answerInstance
	}

	def setAnswer(accountName, studentName, bookname, question_key, answer) {
		def answerInstance = findAnswer(accountName, studentName, bookname, question_key)
		if (answerInstance == null) {
			def student = Student.find("from Student as student where student.account.username = :accountName and student.username = :studentName", [accountName: accountName, studentName: studentName])
			def workBook = findWorkBook(accountName, studentName, bookname)

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

	def removeAnswer(accountName, studentName, bookname, question_key) {
		def answerInstance = findAnswer(accountName, studentName, bookname, question_key)
		if(answerInstance != null) {
			answerInstance.delete(flush:true)
			return true
		}
		return false
	}

    def removeAnswers(accountName, studentName, bookName) {
      def answers = findAnswers(accountName, studentName, bookName)
      for (answer in answers) {
        answer.delete(flush:true)
      }
    }

    def findAnswers(accountName, studentName, bookName) {
        def result = Answer.findAll("from Answer as answer where answer.book.student.account.username = :accountName and answer.book.student.username = :studentName and answer.book.book.name = :bookName",
				[accountName: accountName, studentName: studentName, bookName: bookName])
        log.info("Found answers for ${studentName}: " +  result.toString())
        return result
	}

	def findAnswer(accountName, studentName, bookname, question_key) {
        log.info("Looking up answer for account ${accountName}, student ${studentName}, question ${question_key} in book ${bookname}")
        def result = Answer.find("from Answer as answer where answer.book.student.account.username = :accountName and answer.book.student.username = :studentName and answer.question_key = :question_key and answer.book.book.name = :bookname",
				[accountName: accountName, studentName: studentName, question_key: question_key, bookname: bookname])
		return result
	}

	def findWorkBook(accountName, studentName, bookname) {
        def result = WorkBook.find("from WorkBook as workbook where workbook.student.account.username = :accountName and workbook.book.name = :bookname and workbook.student.username = :studentName",
                [accountName: accountName, studentName: studentName, bookname: bookname])
		return result
	}
}
