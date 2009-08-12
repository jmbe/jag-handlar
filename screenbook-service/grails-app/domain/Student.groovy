class Student {

    String username

    static constraints = {
      username(blank: false, unique: true)
    }
}
