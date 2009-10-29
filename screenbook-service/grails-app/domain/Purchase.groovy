import se.pictosys.license.LicenseSelection
import se.pictosys.payment.api.ContactInformation
import se.pictosys.payment.api.Address
import se.pictosys.license.api.LicenseRepository

class Purchase {

  static belongsTo = Account;

  Account account;

  Date purchaseDate;
  Date endDate;
  Date invoiceDate;

  String contactPerson;
  String phoneNumber;

  String purchaseType = "invoice"

  String license;
  Integer amount;
  String currency;
  String customerNumber


  boolean invoiceSent;

  static constraints = {
    endDate nullable: true
    invoiceDate nullable: true
    customerNumber(blank: true, nullable: true)
  }



  Purchase() {
    /* Required for Hibernate */
  }

  Purchase(LicenseSelection licenseSelection, ContactInformation contactInformation, LicenseRepository licenseRepository) {
    this.license = licenseSelection.license

    this.amount = licenseSelection.getPrice(licenseRepository).amount
    this.currency = licenseSelection.getPrice(licenseRepository).currency

    this.contactPerson = contactInformation.contactPerson
    this.phoneNumber = contactInformation.phoneNumber
    this.purchaseDate = new Date()
  }

}
