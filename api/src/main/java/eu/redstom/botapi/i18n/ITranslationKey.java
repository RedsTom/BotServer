package eu.redstom.botapi.i18n;

/**
 * Has to be implemented as an enumeration
 * Represents a key for a translation
 */
public interface ITranslationKey {

    /**
     * @return The key of the translation
     */
    String getKey();

    /**
     * @return The default value of the translation
     */
    String getDefault();

}
