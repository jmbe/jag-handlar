package se.jaghandlar.localization;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.localization.LocalizationBundleFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
class MessageBundle implements LocalizationBundleFactory {

    /** Logger for this class. */
    private static final Logger log = LoggerFactory
            .getLogger(MessageBundle.class);
    private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;

    public MessageBundle() {
        this.reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource
                .setBasename("classpath:StripesResources");
    }

    public ResourceBundle getFormFieldBundle(final Locale locale)
            throws MissingResourceException {

        log.trace("Getting form field bundle for locale {}", locale);
        return getBundleForLocale(locale);
    }

    public ResourceBundle getErrorMessageBundle(final Locale locale)
            throws MissingResourceException {
        log.trace("Getting error message bundle for locale {}", locale);
        return getBundleForLocale(locale);
    }

    private ResourceBundle getBundleForLocale(final Locale locale) {
        return new AliasAwareResourceBundle(
                reloadableResourceBundleMessageSource, locale);
    }

    public void init(final Configuration configuration) throws Exception {
        /* Nothing to do. */

    }

    public String getMessageText(final Locale locale, final String key) {
        return getFormFieldBundle(locale).getString(key);
    }

}
