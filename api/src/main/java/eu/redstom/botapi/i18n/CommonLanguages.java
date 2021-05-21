package eu.redstom.botapi.i18n;

/**
 * Commonly used languages
 */
public enum CommonLanguages implements ILanguage {

    /**
     * English from USA
     */
    EN_US("en-us", "American English"),
    /**
     * English from UK
     */
    EN_UK("en-uk", "British English"),
    /**
     * French
     */
    FR_FR("fr", "Français"),
    /**
     * Spanish
     */
    ES_ES("es", "Español");

    private final String languageKey, languageName;

    CommonLanguages(String languageKey, String languageName) {
        this.languageKey = languageKey;
        this.languageName = languageName;
    }

    @Override
    public String getLanguageKey() {
        return languageKey;
    }

    @Override
    public String getLanguageName() {
        return languageName;
    }
}
