package vlasovspringbanksystem.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Locale;

@Component
public class LocaleGetter {
    public Locale getCurrentLocale(HttpSession session) {
        String localeData = (String) session.getAttribute("currentLang");
        if (localeData == null) {
            localeData = "en_US";
        }
        String[] languageAndCountry = localeData.split("_");
        return new Locale(languageAndCountry[0], languageAndCountry[1]);
    }
}
