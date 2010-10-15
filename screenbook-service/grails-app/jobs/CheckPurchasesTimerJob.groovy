class CheckPurchasesTimerJob {

  def accountService
  def purchaseMessagingService

  //def timeout = 5000L // execute job once in 5 seconds. Used for debugging.

  /**
   * Cron with quartz syntax is *seconds* minutes hour day month days-of-week.
   */
  static triggers = {
    cron name: 'cronTrigger', startDelay: 5000, cronExpression: "0 0 7 * * ?"
  }

  def execute() {
    sendReminders();
  }

  private def sendReminders() {
    log.info "Checking for expired accounts."

    int tomorrow = 1;
    int twoWeeks = 14;
    int sixWeeks = 6 * 7;

    for (int days: [tomorrow, twoWeeks, sixWeeks]) {
      def accounts = findCustomersWhichExpireWithinDays(days);
      for (account in accounts) {
        sendReminder(account, days);
      }
    }
  }

  private def findCustomersWhichExpireWithinDays(final int days) {

    def accounts = Account.findAll("from Account as account where exists " +
            "(from account.purchases as invoicedPurchase where invoicedPurchase.invoiceSent = true) " +
            "and exists (from account.purchases as activePurchase where activePurchase.invoiceSent = true and activePurchase.endDate > :now) " +
            "and not exists (from account.purchases as activePurchase  where activePurchase.invoiceSent = true and activePurchase.endDate > :date)", [now: new Date(), date: inDays(days)]);

    log.info "Found ${accounts.size()} accounts which potentially expire within ${days} days."

    return accounts;
  }

  /**
   * Days in the future.
   */
  private Date inDays(final int days) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_YEAR, days);
    Date expiry = calendar.getTime();
    return expiry;
  }




  def sendReminder(Account account, int daysBefore) {
    if (!account.shouldReminderBeSent(daysBefore)) {
      log.info "Reminder has already been sent to ${account.username} for ${daysBefore} days before."
      return
    }

    /*
     * Logging on error level so that a mail will be sent - not an actual
     * warning.
     */
    log.error "Sending reminder to user ${account.username}. (Ignore error.)"

    purchaseMessagingService.sendCustomerExpirationWarning(account, account.latestEndDate())
    account.setReminderNoticeSent(daysBefore)
  }


}
