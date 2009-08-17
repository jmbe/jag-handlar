class Student {

    static hasMany = [books: WorkBook]
    static belongsTo = [account: Account]

    String username

    static constraints = {
      username(blank: false, unique: true)
    }
}
