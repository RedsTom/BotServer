package eu.redstom.botapi.i18n;

import org.simpleyaml.configuration.file.YamlConfiguration;

/**
 * Represents the translation manager of a plugin
 */
public interface I18n {

    /**
     * Returns all the registered keys for a specific language
     *
     * @param language The language to register
     * @return All the keys for the asked language
     */
    YamlConfiguration getTranslationEntriesFor(ILanguage language);

    /**
     * Returns the translation from the files or the cache from a language and a key
     *
     * @param language The language to get
     * @param key      The key to get
     * @return The translated string
     */
    String get(ILanguage language, ITranslationKey key);

}
