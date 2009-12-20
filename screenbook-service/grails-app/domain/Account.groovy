/**
 * User domain class.
 */
class Account {
  def authenticateService

  static hasMany = [authorities: Role,
          students: Student,
          purchases: Purchase]

  static mappedBy = [purchases: "account"]

  static belongsTo = Role

  String username

  /**
   * Encrypted password.
   */
  String passwd
  String apikey

  String email

  Date lastUpdated
  Date dateCreated

  boolean dayBeforeNoticeSent = false;

  boolean twoWeeksNoticeSent = false;

  boolean sixWeeksNoticeSent = false;

  boolean newAccount = true

  boolean enabled = true

  boolean showBookmarkReminder = true

  boolean newsLetterSubscribe = false

  static constraints = {
    username(blank: false, unique: true)
    passwd(blank: false)
    enabled()

    lastUpdated(nullable: true)
    dateCreated(nullable: true)

  }

  static transients = ['new']

  def hasFreeLicenses() {
    return true
  }

  /**
   *  Reset account reminders.
   */
  def resetReminders() {
    dayBeforeNoticeSent = false
    twoWeeksNoticeSent = false
    sixWeeksNoticeSent = false
  }

  boolean isNew() {
    newAccount
  }

  def accountActivatedWithPassword(def plainTextPassword) {
    this.newAccount = false;
    this.passwd = authenticateService.encodePassword(plainTextPassword)
  }

  def getNumberOfLicenses() {
    def purchase = latestActivePurchase()
    if (purchase == null) {
      log.warn "No active purchase found for user ${username}"
      return 0
    }

    return purchase.license
    
  }

  def latestActivePurchase() {
      Purchase latestPurchase = null;
      for (Purchase purchase : this.purchases) {
          if (latestPurchase == null
                  || (purchase.invoiceSent && purchase
                          .purchasedAfter(latestPurchase.getPurchaseDate()))) {
              latestPurchase = purchase;
          }
      }
      return latestPurchase;
  }

}
