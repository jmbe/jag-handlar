package se.jaghandlar.localization;


import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceResourceBundle;

class AliasAwareResourceBundle extends MessageSourceResourceBundle {

    AliasAwareResourceBundle(final MessageSource source,
            final Locale locale) {
        super(source, locale);
    }

    protected Object handleGetObject(final String code) {

        println "Trying to find code ${code}"

        Map<String, String> map = getTranslationAliases();

        if (map.containsKey(code)) {
            String alias = map.get(code);
            return super.handleGetObject(alias);
        }

        return super.handleGetObject(code);
    }

    boolean containsKey(final String code) {
        return getTranslationAliases().containsKey(code) || super.containsKey(code);
    }

    private Map<String, String> getTranslationAliases() {
        Map<String, String> aliases = new TreeMap<String, String>();
        aliases.put("subscribeCheckLoginForm.subscribeUsername",
                "common.username");
        aliases.put("subscribeCheckLoginForm.subscribePassword",
                "common.password");
        aliases.put("createAccountForm.newUsername",
                "contact.requested.username");
        aliases.put("createAccountForm.email", "common.email");
        aliases.put("createAccountForm.verifyEmail", "common.email.verify");
        aliases.put("createAccountForm.newUsername.valueDoesNotMatch",
                "error.invalid.characters");

        aliases.put("createAccountForm.email.invalidEmail", "errors.email");
        aliases.put("createAccountForm.verifyEmail.invalidEmail",
                "errors.email");

        aliases
                .put("createInvoiceForm.contactPerson",
                        "contact.contact.person");
        aliases
                .put("createInvoiceForm.firstName",
                        "purchase.address.firstname");
        aliases.put("createInvoiceForm.lastName", "purchase.address.lastname");
        aliases.put("createInvoiceForm.phone", "contact.number.phone");
        aliases.put("createInvoiceForm.streetAddressLine1",
                "purchase.address.street");
        aliases.put("createInvoiceForm.zip", "purchase.address.zip");
        aliases.put("createInvoiceForm.city", "purchase.address.city");

        aliases
                .put("createInvoiceForm.countryCode",
                        "purchase.address.country");

        aliases.put("invoiceFirstName", "purchase.address.firstname");
        aliases.put("invoiceCity", "purchase.address.city");
        aliases.put("invoiceFirstName", "purchase.address.firstname");
        aliases.put("invoiceLastName", "purchase.address.lastname");
        aliases.put("invoiceStreetAddressLine1", "purchase.address.street");
        aliases.put("invoiceZip", "purchase.address.zip");
        aliases.put("invoiceCountryCode", "purchase.address.country");

        aliases.put("createInvoiceForm.invoiceFirstName",
                "purchase.address.firstname");
        aliases.put("createInvoiceForm.invoiceCity", "purchase.address.city");
        aliases.put("createInvoiceForm.invoiceFirstName",
                "purchase.address.firstname");
        aliases.put("createInvoiceForm.invoiceLastName",
                "purchase.address.lastname");
        aliases.put("createInvoiceForm.invoiceStreetAddressLine1",
                "purchase.address.street");
        aliases.put("createInvoiceForm.invoiceZip", "purchase.address.zip");
        aliases.put("createInvoiceForm.invoiceCountryCode",
                "purchase.address.country");

        /* Validation errors */
        aliases.put("errors.required", "validation.required.valueNotPresent");

        return aliases;
    }

}
