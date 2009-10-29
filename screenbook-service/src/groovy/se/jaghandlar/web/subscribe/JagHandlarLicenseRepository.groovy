package se.jaghandlar.web.subscribe

import org.springframework.stereotype.Component
import se.pictosys.license.api.LicenseRepository
import se.pictosys.license.License
import se.pictosys.license.LicenseSelection
import se.pictosys.license.LicenseCollection
import se.pictosys.price.Price
import se.pictosys.currency.Currency as PictosysCurrency

@Component("licenseRepository")
class JagHandlarLicenseRepository implements LicenseRepository{



  List<License> findAllByCountryCode(String countryCode) {
      return getLicenseCollection(countryCode).toList();
  }

  License findBySelection(LicenseSelection selection) {
    return getLicenseCollection(selection.getCountryCode())
            .findByUserCount(selection.getUserCount());

  }


  private LicenseCollection getLicenseCollection(final String countryCode) {
      PictosysCurrency sek = new PictosysCurrency("SEK", "kr");
      LicenseCollection licenseCollection = new LicenseCollection(sek);
      licenseCollection.add(new License(1, new Price(200, sek)));
      licenseCollection.add(new License(2, new Price(300, sek)));
      licenseCollection.add(new License(4, new Price(400, sek)));
      licenseCollection.add(new License(8, new Price(600, sek)));
      return licenseCollection;
  }

}
