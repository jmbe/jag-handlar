package se.jaghandlar
class Book {

	String name

    static constraints = {
		name(blank:false)
    }
}
