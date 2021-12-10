package io.github.lix3nn53.guardiansofadelia.text.locale;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.ChatColor;

import java.util.HashMap;

public class TranslationStorage {

    private final String language;
    private final HashMap<String, String> pathToTranslation = new HashMap<>();

    public TranslationStorage(String language) {
        this.language = language;
    }

    public String t(String path) {
        if (pathToTranslation.containsKey(path)) {
            return pathToTranslation.get(path);
        }

        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "ERR TranslationStorage: " + language + " - " + path);

        return "";
    }

    public void add(String ymlName, String path, String t) {
        pathToTranslation.put(ymlName + "." + path, t);
    }
}
