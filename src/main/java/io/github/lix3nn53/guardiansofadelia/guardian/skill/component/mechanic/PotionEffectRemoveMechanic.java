package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PotionEffectRemoveMechanic extends MechanicComponent {

    private final List<PotionEffectType> potionEffectTypes = new ArrayList<>();

    public PotionEffectRemoveMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("potionEffectType")) {
            configLoadError("potionEffectType");
        }

        List<String> potionEffectTypeStr = configurationSection.getStringList("potionEffectType");
        for (String potionStr : potionEffectTypeStr) {
            PotionEffectType byName = PotionEffectType.getByName(potionStr);
            potionEffectTypes.add(byName);
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        for (PotionEffectType potionEffectType : potionEffectTypes) {
            for (LivingEntity target : targets) {
                target.removePotionEffect(potionEffectType);

                if (target instanceof Player) {
                    Player player = (Player) target;
                    if (GuardianDataManager.hasGuardianData(player)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                            PlayerInventory inventory = player.getInventory();

                            ItemStack inventoryHelmet = inventory.getHelmet();
                            ItemStack inventoryChestplate = inventory.getChestplate();
                            ItemStack inventoryLeggings = inventory.getLeggings();
                            ItemStack inventoryBoots = inventory.getBoots();
                            ItemStack itemInMainHand = inventory.getItemInMainHand();
                            ItemStack itemInOffHand = inventory.getItemInOffHand();

                            ArmorGearType helmetType = ArmorGearType.fromMaterial(inventoryHelmet.getType());
                            ArmorGearType chestplateType = ArmorGearType.fromMaterial(inventoryChestplate.getType());
                            ArmorGearType leggingsType = ArmorGearType.fromMaterial(inventoryLeggings.getType());
                            ArmorGearType bootsType = ArmorGearType.fromMaterial(inventoryBoots.getType());

                            rpgCharacterStats.recalculateGearSetEffects(inventoryHelmet, inventoryChestplate, inventoryLeggings, inventoryBoots, itemInMainHand, itemInOffHand,
                                    helmetType, chestplateType, leggingsType, bootsType);
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
