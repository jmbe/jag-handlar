/**
 * User domain class.
 */
class Account {
  static hasMany = [authorities: Role,
          students: Student,
          purchases: Purchase]

  static mappedBy = [purchases: "account"]

  static belongsTo = Role

  /** Username   */
  String username
  /** MD5 Password   */
  String passwd
  String apikey

  String email

  Date lastUpdated
  Date dateCreated

  /** enabled   */
  boolean enabled

  boolean showBookmarkReminder = true

  static constraints = {
    username(blank: false, unique: true)
    passwd(blank: false)
    enabled()

    lastUpdated(nullable: true)
    dateCreated(nullable: true)

  }

  def hasFreeLicenses() {
    return true
  }
}
