package io.github.lix3nn53.guardiansofadelia.locale;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;

import java.util.HashMap;
import java.util.Set;

public class Translation {

    public static final String DEFAULT_LANG = "en_us";
    private static final HashMap<String, TranslationStorage> languageTranslation = new HashMap<>();

    public static void put(String language, TranslationStorage translationStorage) {
        languageTranslation.put(language, translationStorage);
    }

    public static void add(String language, String section, String path, String value) {
        TranslationStorage translationStorage = languageTranslation.get(language);
        translationStorage.add(section, path, value);
    }

    public static String t(GuardianData guardianData, String path) {
        String lang = guardianData.getLanguage();
        TranslationStorage translationStorage = languageTranslation.get(lang);
        return translationStorage.t(path);
    }

    public static String t(String lang, String fullPath) {
        TranslationStorage translationStorage = languageTranslation.get(lang);
        return translationStorage.t(fullPath);
    }

    public static String t(String lang, String section, String path) {
        TranslationStorage translationStorage = languageTranslation.get(lang);
        return translationStorage.t(section + "." + path);
    }

    public static boolean exists(String language) {
        return languageTranslation.containsKey(language);
    }

    public static Set<String> getLanguages() {
        return languageTranslation.keySet();
    }
}
