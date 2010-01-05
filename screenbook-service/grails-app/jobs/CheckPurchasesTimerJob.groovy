class CheckPurchasesTimerJob {

  def accountService
  def purchaseMessagingService

  //def timeout = 5000L // execute job once in 5 seconds. Used for debugging.

  /**
   * Cron with quartz syntax is *seconds* minutes hour day month days-of-week.
   */
  static triggers = {
    cron name: 'cronTrigger', startDelay: 5000, cronExpression:  "0 4 21 * * ?" 
  }

  def execute() {
    def purchases = Purchase.findAllByInvoiceSent(true)

    int tomorrow = 1;
    int twoWeeks = 14;
    int sixWeeks = 6 * 7;

    sendReminders(purchases, tomorrow);
    sendReminders(purchases, twoWeeks);
    sendReminders(purchases, sixWeeks);
  }

  def sendReminders(List<Purchase> purchases, int daysBefore) {

    Calendar calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, daysBefore)
    Date expiry = calendar.getTime()


    def expiredPurchases = purchases.findAll {purchase ->
      purchase.endsBefore(expiry)
    }

    log.info "Found ${expiredPurchases?.size()} expired purchases out of ${purchases?.size()} invoiced purchases in total."


    for (it in expiredPurchases) {
      sendReminder it, daysBefore
    }


  }

  def sendReminder(def purchase, int daysBefore) {
    if (!shouldReminderBeSent(purchase, daysBefore)) {
      log.info "Reminder has already been sent to ${purchase.account.username} for ${daysBefore} days before."
      return
    }

    /*
     * Logging on warn level so that a mail will be sent - not an actual
     * warning.
     */
    log.warn "Sending reminder for purchase ${purchase.id} for user ${purchase.account.username}. (Ignore warning.)"


    def account = purchase.account

    purchaseMessagingService.sendCustomerExpirationWarning(account, account.latestEndDate())

    if (isDayBefore(daysBefore)) {
      account.setDayBeforeNoticeSent(true);
    }

    if (isTwoWeeksBefore(daysBefore)) {
      account.setTwoWeeksNoticeSent(true);
    }

    if (isSixWeeksBefore(daysBefore)) {
      account.setSixWeeksNoticeSent(true);
    }

  }

  def shouldReminderBeSent(def purchase, int daysBefore) {
    def user = purchase.account

    if (isDayBefore(daysBefore)) {
      return !user.dayBeforeNoticeSent;
    } else if (isTwoWeeksBefore(daysBefore)) {
      return !user.twoWeeksNoticeSent;
    }

    return isSixWeeksBefore(daysBefore) && !user.sixWeeksNoticeSent;


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

}
