import grails.test.GrailsUnitTestCase
import se.pictosys.license.LicenseSelection
import se.pictosys.payment.api.Address
import se.pictosys.payment.api.ContactInformation
import se.pictosys.payment.api.Name
import se.pictosys.payment.api.Street

class PurchaseTests extends GrailsUnitTestCase {
  protected void setUp() {
    super.setUp()
  }

  protected void tearDown() {
    super.tearDown()
  }


  void testConstructor() {
    def licenseSelection = new LicenseSelection("1", "SE")

    Address address = new Address(new Name("first", "last"), new Street(
            "Street 1", ""), "City", "", "SE", "12057");
    ContactInformation contactInformation = new ContactInformation(
            "Contact", address, address, "", "", "");

    def purchase = new Purchase(licenseSelection, contactInformation)
    assert "1" == purchase.license

  }
}
