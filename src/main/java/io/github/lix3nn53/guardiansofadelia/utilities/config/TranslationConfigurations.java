package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.text.locale.TranslationStorage;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TranslationConfigurations {

    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "locale";
    private static final HashMap<String, HashMap<String, YamlConfiguration>> langToDirectory = new HashMap<>(); // Lang folder and all yml inside that folder

    public static void createConfigs() {
        List<String> directories = ConfigurationUtils.getAllDirectoriesInFile(filePath);

        for (String directory : directories) {
            HashMap<String, YamlConfiguration> configs = ConfigurationUtils.getAllConfigsInFile(filePath + File.separator + directory);

            langToDirectory.put(directory, configs);
        }
    }

    public static void loadConfigs() {
        for (String language : langToDirectory.keySet()) {
            TranslationStorage translationStorage = new TranslationStorage(language);

            HashMap<String, YamlConfiguration> directory = langToDirectory.get(language);
            for (String yaml : directory.keySet()) {
                YamlConfiguration configuration = directory.get(yaml);

                Set<String> keys = configuration.getKeys(true);

                for (String key : keys) {

                    String value = configuration.getString(key);
                    translationStorage.add(yaml, key, value);
                }
            }

            Translation.put(language, translationStorage);
        }
    }


}