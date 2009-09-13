/**
 * User domain class.
 */
class Account {
	static hasMany = [authorities: Role,
                      students: Student]
    static belongsTo = Role

	/** Username */
	String username
	/** MD5 Password */
	String passwd
    String apikey
	/** enabled */
	boolean enabled

	static constraints = {
		username(blank: false, unique: true)
		passwd(blank: false)
		enabled()
	}

	def hasFreeLicenses() {
		return true
	}
}
