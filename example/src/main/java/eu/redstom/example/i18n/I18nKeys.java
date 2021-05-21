package eu.redstom.example.i18n;

import eu.redstom.botapi.i18n.ITranslationKey;

public enum I18nKeys implements ITranslationKey {

    HELLO("hello", "Hello, translations world !"),
    DOES_NOT_EXIST("does_not_exist", "This translation won't exist in the translation files !");

    private final String key, def;

    I18nKeys(String key, String def) {
        this.key = key;
        this.def = def;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDefault() {
        return def;
    }
}
