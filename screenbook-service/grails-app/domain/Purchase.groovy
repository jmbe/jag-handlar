import se.pictosys.license.LicenseSelection
import se.pictosys.license.api.LicenseRepository
import se.pictosys.payment.api.Address
import se.pictosys.payment.api.ContactInformation

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

  JagHandlarAddress invoiceAddress;
  JagHandlarAddress deliveryAddress;

  static embedded = ['invoiceAddress', 'deliveryAddress']

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

    this.invoiceAddress = new JagHandlarAddress(contactInformation.invoiceAddress)
    this.deliveryAddress = new JagHandlarAddress(contactInformation.deliveryAddress)
  }

}

/**
 * Shadow class for Address, since Grails doesn't seem to be able to use @Embeddable java classes.
 */
class JagHandlarAddress {

  String firstName, lastName;
  String streetLine1, streetLine2;
  String city, state, countryCode, zip;

  static constraints = {
    lastName nullable: true
    streetLine2 nullable: true
    state nullable: true
  }

  JagHandlarAddress() {
    /* Required for Hibernate. */
  }

  JagHandlarAddress(Address address) {
    this.firstName = address.name.first
    this.lastName = address.name.last

    this.streetLine1 = address.street.line1;
    this.streetLine2 = address.street.line2;

    this.city = address.city
    this.state = address.state
    this.countryCode = address.countryCode
    this.zip = address.zip
  }

}
