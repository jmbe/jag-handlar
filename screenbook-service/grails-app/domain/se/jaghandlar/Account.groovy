package se.jaghandlar
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
  String contactPerson = ""
  String phoneNumber = ""

  Date lastUpdated
  Date dateCreated

  boolean dayBeforeNoticeSent = false;

  boolean twoWeeksNoticeSent = false;

  boolean sixWeeksNoticeSent = false;

  boolean newAccount = true

  boolean enabled = true

  boolean showBookmarkReminder = true

  boolean newsletterSubscribe = false

  static constraints = {
    username(blank: false, unique: true)
    passwd(blank: false)
    enabled()

    lastUpdated(nullable: true)
    dateCreated(nullable: true)
    contactPerson(nullable: true)
    phoneNumber(nullable: true)
  }

  /* Prevent Hibernate from persisting these fields. */
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

  def isActive() {
    return hasRole(Role.ADMIN) || hasActivePurchase();
  }

  def hasRole(def roleName) {
    for (Role role: this.authorities) {
      if (role.authority.equals(roleName)) {
        return true;
      }
    }

    return false;
  }

  def hasActivePurchase() {
    return latestActivePurchase() != null;
  }

  def getNumberOfLicenses() {
    def purchase = latestActivePurchase()
    if (purchase == null) {
      return 0
    }

    return purchase.license

  }


  def isRenewal() {
    return !isNew() && latestActivePurchase() != null
  }

  Date latestEndDate() {
    Purchase purchase = latestActivePurchase()

    if (purchase == null || purchase.endDate == null) {
      return null
    }

    return purchase.endDate
  }

  def latestActivePurchase() {
    Date now = new Date();
    Purchase latestPurchase = null;
    for (Purchase purchase: this.purchases) {
      if (purchase.invoiceSent && purchase.endsAfter(now) && (latestPurchase == null || purchase.endsAfter(latestPurchase))) {
        latestPurchase = purchase
      }
    }
    if (latestPurchase == null) {
      log.info "No active purchase found for user ${username}"
    }

    return latestPurchase
  }

  def latestInvoicedPurchase() {
    Purchase latestPurchase = null;
    for (Purchase purchase: this.purchases) {
      if (purchase.invoiceSent && (latestPurchase == null || purchase.purchasedAfter(latestPurchase.purchaseDate))) {
        latestPurchase = purchase;
      }
    }
    if (latestPurchase == null) {
      log.info "No invoiced purchases found for user ${username}."
    }

    return latestPurchase;
  }

  boolean shouldReminderBeSent(int daysBefore) {
    if (!enabled) {
      return false;
    }

    if (isDayBefore(daysBefore)) {
      return !isDayBeforeNoticeSent();
    } else if (isTwoWeeksBefore(daysBefore)) {
      return !isTwoWeeksNoticeSent();
    }

    return isSixWeeksBefore(daysBefore) && !isSixWeeksNoticeSent();

  }

  private boolean isSixWeeksBefore(final int daysBefore) {
    boolean sixWeeksBefore = daysBefore <= 6 * 7;
    return sixWeeksBefore;
  }

  private boolean isTwoWeeksBefore(final int daysBefore) {
    boolean twoWeeksBefore = daysBefore <= 14;
    return twoWeeksBefore;
  }

  private boolean isDayBefore(final int daysBefore) {
    boolean dayBefore = daysBefore <= 1;
    return dayBefore;
  }

  def setReminderNoticeSent(int daysBefore) {
    if (isDayBefore(daysBefore)) {
      setDayBeforeNoticeSent(true);
    }

    if (isTwoWeeksBefore(daysBefore)) {
      setTwoWeeksNoticeSent(true);
    }

    if (isSixWeeksBefore(daysBefore)) {
      setSixWeeksNoticeSent(true);
    }

  }
}
