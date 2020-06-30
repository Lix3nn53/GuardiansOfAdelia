package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.config.ItemReferenceLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class ActionLoader {

    public static Action load(ConfigurationSection configurationSection) {
        String actionType = configurationSection.getString("actionType");

        if (actionType == null) {
            GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "NULL ACTION TYPE");
            return null;
        }

        if (actionType.equals(ClearPotionEffectAction.class.getSimpleName())) {
            String potionEffectTypeStr = configurationSection.getString("potionEffectType");
            PotionEffectType potionEffectType = PotionEffectType.getByName(potionEffectTypeStr);

            return new ClearPotionEffectAction(potionEffectType);
        } else if (actionType.equals(FinishQuestAction.class.getSimpleName())) {
            int questId = configurationSection.getInt("questId");

            return new FinishQuestAction(questId);
        } else if (actionType.equals(GiveItemAction.class.getSimpleName())) {
            ItemStack itemStack = ItemReferenceLoader.loadItemReference(configurationSection.getConfigurationSection("item"));

            return new GiveItemAction(itemStack);
        } else if (actionType.equals(GiveWeaponAction.class.getSimpleName())) {
            int gearLevel = configurationSection.getInt("gearLevel");
            ItemTier itemTier = ItemTier.valueOf(configurationSection.getString("itemTier"));
            String itemTag = configurationSection.getString("itemTag");

            return new GiveWeaponAction(gearLevel, itemTier, itemTag);
        } else if (actionType.equals(InvincibleGiveAction.class.getSimpleName())) {
            long duration = configurationSection.getLong("duration");

            return new InvincibleGiveAction(duration);
        } else if (actionType.equals(PotionEffectAction.class.getSimpleName())) {
            String potionEffectTypeStr = configurationSection.getString("potionEffectType");
            PotionEffectType potionEffectType = PotionEffectType.getByName(potionEffectTypeStr);

            int duration = configurationSection.getInt("duration");
            int amplifier = configurationSection.getInt("amplifier");

            return new PotionEffectAction(potionEffectType, duration, amplifier);
        } else if (actionType.equals(SendMessageAction.class.getSimpleName())) {
            String message = configurationSection.getString("message");

            return new SendMessageAction(message);
        } else if (actionType.equals(SendTitleAction.class.getSimpleName())) {
            String top = configurationSection.getString("top");
            String bottom = configurationSection.getString("bottom");

            return new SendTitleAction(top, bottom);
        } else if (actionType.equals(StartQuestAction.class.getSimpleName())) {
            int questId = configurationSection.getInt("questId");

            return new StartQuestAction(questId);
        } else if (actionType.equals(TeleportAction.class.getSimpleName())) {
            World world = Bukkit.getWorld(configurationSection.getString("world"));

            double x = configurationSection.getDouble(".x");
            double y = configurationSection.getDouble(".y");
            double z = configurationSection.getDouble(".z");
            float yaw = (float) configurationSection.getDouble(".yaw");
            float pitch = (float) configurationSection.getDouble(".pitch");

            Location location = new Location(world, x, y, z, yaw, pitch);

            long delay = configurationSection.getLong("delay");

            return new TeleportAction(location, delay);
        } else if (actionType.equals(TutorialEndAction.class.getSimpleName())) {
            return new TutorialEndAction();
        }

        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "NO SUCH ACTION IN LOADER");

        return null;
    }
}
