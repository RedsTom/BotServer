package eu.redstom.botapi.i18n;

/**
 * Has to be implemented as an enumeration
 * Represents a language
 */
public interface ILanguage {

    /**
     * @return The language key to use in the file name
     */
    String getLanguageKey();

    /**
     * @return The language name in this language (ex : Français, Espaǹol, etc.)
     */
    String getLanguageName();

}
