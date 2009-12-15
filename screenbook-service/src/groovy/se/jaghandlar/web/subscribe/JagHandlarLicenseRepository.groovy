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
      for(int i = 1; i <= 8; i++) {
        licenseCollection.add(new License(i, new Price(75*i, sek)));
      }
      return licenseCollection;
  }

}
