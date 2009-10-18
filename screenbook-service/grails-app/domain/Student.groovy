class Student {

    static hasMany = [books: WorkBook]
    static belongsTo = [account: Account]

    String username
    boolean screenKeyboard

    static constraints = {
      username(blank: false, unique: false)
    }

	static def findByUsernameAndMainAccount(String username, String mainAccountName) {
		return Student.find("from Student as s join s.account as a where s.username = :username and a.username = :mainAccountName ", [username: username,  mainAccountName: mainAccountName])
	}
}
