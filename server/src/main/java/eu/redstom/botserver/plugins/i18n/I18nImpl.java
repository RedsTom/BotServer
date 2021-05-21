package eu.redstom.botserver.plugins.i18n;

import eu.redstom.botapi.i18n.I18n;
import eu.redstom.botapi.i18n.ILanguage;
import eu.redstom.botapi.i18n.ITranslationKey;
import eu.redstom.botserver.config.FileConfiguration;
import eu.redstom.botserver.plugins.Plugin;
import org.simpleyaml.configuration.file.YamlConfiguration;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class I18nImpl implements I18n {

    private final HashMap<String, FileConfiguration> configs;
    private final HashMap<String, String> mapCache;

    public I18nImpl(Plugin source) {
        File folder = new File(source.getPluginFolder(), "languages");
        this.configs = new HashMap<>();
        this.mapCache = new HashMap<>();
        if (!folder.exists()) folder.mkdirs();

        if (folder.listFiles() == null) return;

        for (File file : folder.listFiles()) {
            if (!file.getName().endsWith(".yml")) continue;

            String language = file.getName().subSequence(0, file.getName().length() - 4).toString();
            try {
                this.configs.put(language, new FileConfiguration(folder, language));
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public YamlConfiguration getTranslationEntriesFor(ILanguage language) {
        return this.configs.containsKey(language.getLanguageKey())
            ? this.configs.get(language.getLanguageKey()).getValues()
            : new YamlConfiguration();
    }

    @Override
    public String get(ILanguage language, ITranslationKey key) {
        if (!this.mapCache.containsKey(formatKeyForCache(language, key))) {
            System.out.println("Pushing " + language.getLanguageKey() + ":" + key.getKey() + " to cache");
            this.mapCache.put(
                formatKeyForCache(language, key),
                this.configs.containsKey(language.getLanguageKey())
                    ? this.configs.get(language.getLanguageKey()).getValues().getString(key.getKey(), key.getDefault())
                    : key.getDefault()
            );
        }
        System.out.println("Loading from cache...");
        return this.mapCache.get(formatKeyForCache(language, key));
    }

    private String formatKeyForCache(ILanguage language, ITranslationKey key) {
        return language.getLanguageKey() + ":" + key.getKey();
    }
}
