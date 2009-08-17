class Book {

	static hasMany = [workBooks: WorkBook]

	String name

    static constraints = {
		name(blank:false)
    }
}
