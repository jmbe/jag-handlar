class WorkBook {
	static belongsTo = [book: Book, student: Student]
	static hasMany = [answers: Answer]

    static constraints = {
    }
}
