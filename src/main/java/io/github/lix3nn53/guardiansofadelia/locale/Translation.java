package io.github.lix3nn53.guardiansofadelia.locale;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;

import java.util.HashMap;

public class Translation {

    public static final String DEFAULT_LANG = "en_us";
    private static final HashMap<String, TranslationStorage> languageTranslation = new HashMap<>();

    public static void add(String language, String filename, String path, String t) {
        TranslationStorage translationStorage;
        if (languageTranslation.containsKey(language)) {
            translationStorage = languageTranslation.get(language);
        } else {
            translationStorage = new TranslationStorage(language);
            languageTranslation.put(language, translationStorage);
        }

        translationStorage.add(filename, path, t);
    }

    public static String t(GuardianData guardianData, String path) {
        String lang = guardianData.getLanguage();
        TranslationStorage translationStorage = languageTranslation.get(lang);
        return translationStorage.t(path);
    }

    public static boolean exists(String language) {
        return languageTranslation.containsKey(language);
    }
}
