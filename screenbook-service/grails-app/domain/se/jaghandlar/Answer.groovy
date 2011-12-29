package se.jaghandlar
class Answer {
	static belongsTo = [book: WorkBook]

	String question_key
	String answer

	static constraints = {
		question_key(blank: false)
	}
}
