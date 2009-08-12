class Student {

    static hasMany = [answers: Answer]

    String username

    static constraints = {
      username(blank: false, unique: true)
    }
}
