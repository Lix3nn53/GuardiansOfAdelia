package io.github.lix3nn53.guardiansofadelia.Items;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.HealMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.ManaMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.RepeatMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.List;

public enum Consumable {
    BUFF_ELEMENT_DAMAGE,
    BUFF_ELEMENT_DEFENSE,
    POTION_INSTANT_HEALTH,
    POTION_INSTANT_MANA,
    POTION_INSTANT_HYBRID,
    POTION_REGENERATION_HEALTH;

    private final double HYBRID_NERF = 0.6;
    private final double REGEN_NERF = 0.05;

    public void consume(Player player, int skillLevel, ItemStack itemStack) {
        if (PersistentDataContainerUtil.hasInteger(itemStack, "reqLevel")) {
            int reqLevel = PersistentDataContainerUtil.getInteger(itemStack, "reqLevel");
            if (player.getLevel() < reqLevel) {
                player.sendMessage(ChatColor.RED + "Required level to use this item is " + reqLevel);
                return;
            }
        }

        List<LivingEntity> targets = new ArrayList<>();
        targets.add(player);
        SkillComponent trigger = getTrigger();
        trigger.execute(player, skillLevel, targets, 0, -1);
        int amount = itemStack.getAmount();
        if (PersistentDataContainerUtil.hasInteger(itemStack, "consumableUsesLeft")) {
            int usesLeft = PersistentDataContainerUtil.getInteger(itemStack, "consumableUsesLeft");
            if (usesLeft > 1) {
                PersistentDataContainerUtil.putInteger("consumableUsesLeft", usesLeft - 1, itemStack);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta.hasDisplayName()) {
                    String displayName = itemMeta.getDisplayName();
                    String replace = displayName.replace("(" + usesLeft + " Uses left)", "(" + (usesLeft - 1) + " Uses left)");
                    itemMeta.setDisplayName(replace);
                    itemStack.setItemMeta(itemMeta);
                }
            } else {
                itemStack.setAmount(amount - 1);
            }
        } else {
            itemStack.setAmount(amount - 1);
        }
    }

    public SkillComponent getTrigger() {
        List<Double> multipliers = new ArrayList<>(); //buff multipliers
        multipliers.add(getBuffMultiplier(1));
        multipliers.add(getBuffMultiplier(2));
        multipliers.add(getBuffMultiplier(3));
        multipliers.add(getBuffMultiplier(4));
        multipliers.add(getBuffMultiplier(5));
        multipliers.add(getBuffMultiplier(6));
        multipliers.add(getBuffMultiplier(7));
        multipliers.add(getBuffMultiplier(8));
        multipliers.add(getBuffMultiplier(9));
        multipliers.add(getBuffMultiplier(10));

        List<Integer> ticks = new ArrayList<>(); //buff durations
        int duration = 20 * 60 * 10; //10 minutes
        ticks.add(duration);
        ticks.add(duration);
        ticks.add(duration);
        ticks.add(duration);
        ticks.add(duration);
        ticks.add(duration);
        ticks.add(duration);
        ticks.add(duration);
        ticks.add(duration);
        ticks.add(duration);

        SkillComponent trigger = new SelfTarget();
        switch (this) {
            case BUFF_ELEMENT_DAMAGE:
                trigger.addChildren(new BuffMechanic(BuffType.ELEMENT_DAMAGE, multipliers, ticks));
                break;
            case BUFF_ELEMENT_DEFENSE:
                trigger.addChildren(new BuffMechanic(BuffType.ELEMENT_DEFENSE, multipliers, ticks));
                break;
            case POTION_INSTANT_HEALTH:
                List<Integer> healAmounts = new ArrayList<>();
                healAmounts.add(getInstantHealAmount(1));
                healAmounts.add(getInstantHealAmount(2));
                healAmounts.add(getInstantHealAmount(3));
                healAmounts.add(getInstantHealAmount(4));
                healAmounts.add(getInstantHealAmount(5));
                healAmounts.add(getInstantHealAmount(6));
                healAmounts.add(getInstantHealAmount(7));
                healAmounts.add(getInstantHealAmount(8));
                healAmounts.add(getInstantHealAmount(9));
                healAmounts.add(getInstantHealAmount(10));
                trigger.addChildren(new HealMechanic(healAmounts, new ArrayList<>(), null));
                break;
            case POTION_INSTANT_MANA:
                List<Integer> manaAmounts = new ArrayList<>();
                manaAmounts.add(getInstantManaAmount(1));
                manaAmounts.add(getInstantManaAmount(2));
                manaAmounts.add(getInstantManaAmount(3));
                manaAmounts.add(getInstantManaAmount(4));
                manaAmounts.add(getInstantManaAmount(5));
                manaAmounts.add(getInstantManaAmount(6));
                manaAmounts.add(getInstantManaAmount(7));
                manaAmounts.add(getInstantManaAmount(8));
                manaAmounts.add(getInstantManaAmount(9));
                manaAmounts.add(getInstantManaAmount(10));
                trigger.addChildren(new ManaMechanic(manaAmounts, new ArrayList<>(), null));
                break;
            case POTION_INSTANT_HYBRID:
                healAmounts = new ArrayList<>();
                healAmounts.add((int) (getInstantHealAmount(1) * HYBRID_NERF));
                healAmounts.add((int) (getInstantHealAmount(2) * HYBRID_NERF));
                healAmounts.add((int) (getInstantHealAmount(3) * HYBRID_NERF));
                healAmounts.add((int) (getInstantHealAmount(4) * HYBRID_NERF));
                healAmounts.add((int) (getInstantHealAmount(5) * HYBRID_NERF));
                healAmounts.add((int) (getInstantHealAmount(6) * HYBRID_NERF));
                healAmounts.add((int) (getInstantHealAmount(7) * HYBRID_NERF));
                healAmounts.add((int) (getInstantHealAmount(8) * HYBRID_NERF));
                healAmounts.add((int) (getInstantHealAmount(9) * HYBRID_NERF));
                healAmounts.add((int) (getInstantHealAmount(10) * HYBRID_NERF));
                trigger.addChildren(new HealMechanic(healAmounts, new ArrayList<>(), null));
                manaAmounts = new ArrayList<>();
                manaAmounts.add((int) (getInstantManaAmount(1) * HYBRID_NERF));
                manaAmounts.add((int) (getInstantManaAmount(2) * HYBRID_NERF));
                manaAmounts.add((int) (getInstantManaAmount(3) * HYBRID_NERF));
                manaAmounts.add((int) (getInstantManaAmount(4) * HYBRID_NERF));
                manaAmounts.add((int) (getInstantManaAmount(5) * HYBRID_NERF));
                manaAmounts.add((int) (getInstantManaAmount(6) * HYBRID_NERF));
                manaAmounts.add((int) (getInstantManaAmount(7) * HYBRID_NERF));
                manaAmounts.add((int) (getInstantManaAmount(8) * HYBRID_NERF));
                manaAmounts.add((int) (getInstantManaAmount(9) * HYBRID_NERF));
                manaAmounts.add((int) (getInstantManaAmount(10) * HYBRID_NERF));
                trigger.addChildren(new ManaMechanic(manaAmounts, new ArrayList<>(), null));
                break;
            case POTION_REGENERATION_HEALTH:
                List<Integer> repetitions = new ArrayList<>();
                repetitions.add(18);
                repetitions.add(18);
                repetitions.add(18);
                repetitions.add(18);
                repetitions.add(18);
                repetitions.add(18);
                repetitions.add(18);
                repetitions.add(18);
                repetitions.add(18);
                repetitions.add(18);
                RepeatMechanic repeatMechanic = new RepeatMechanic(20L, repetitions);
                healAmounts = new ArrayList<>();
                healAmounts.add((int) (getInstantHealAmount(1) * REGEN_NERF));
                healAmounts.add((int) (getInstantHealAmount(2) * REGEN_NERF));
                healAmounts.add((int) (getInstantHealAmount(3) * REGEN_NERF));
                healAmounts.add((int) (getInstantHealAmount(4) * REGEN_NERF));
                healAmounts.add((int) (getInstantHealAmount(5) * REGEN_NERF));
                healAmounts.add((int) (getInstantHealAmount(6) * REGEN_NERF));
                healAmounts.add((int) (getInstantHealAmount(7) * REGEN_NERF));
                healAmounts.add((int) (getInstantHealAmount(8) * REGEN_NERF));
                healAmounts.add((int) (getInstantHealAmount(9) * REGEN_NERF));
                healAmounts.add((int) (getInstantHealAmount(10) * REGEN_NERF));
                repeatMechanic.addChildren(new HealMechanic(healAmounts, new ArrayList<>(), null));
                trigger.addChildren(repeatMechanic);
                break;
        }
        return trigger;
    }

    public Material getMaterial() {
        switch (this) {
            case BUFF_ELEMENT_DAMAGE:
                return Material.COOKED_BEEF;
            case BUFF_ELEMENT_DEFENSE:
                return Material.RABBIT_STEW;
            /*case BUFF_MAGICAL_DAMAGE:
                return Material.COOKED_SALMON;
            case BUFF_MAGICAL_DEFENSE:
                return Material.SUSPICIOUS_STEW;*/
        }
        return Material.POTION;
    }

    private int getReqLevel(int skillLevel) {
        if (skillLevel == 1) {
            return 1;
        }
        return (skillLevel * 10) - 10;
    }

    public ItemStack getItemStack(int skillLevel, int uses) {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemStack.getType().equals(Material.POTION)) {
            PotionMeta potionMeta = (PotionMeta) itemMeta;
            potionMeta.setColor(getPotionColor());
        }
        itemMeta.setDisplayName(getName() + " Tier-" + skillLevel + " (" + uses + " Uses left)");
        int reqLevel = getReqLevel(skillLevel);

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.YELLOW + "Required Level: " + reqLevel);
        lore.add("");
        lore.addAll(getLoreTechnicalInfo(skillLevel));
        lore.add("");
        lore.add(ChatColor.GRAY + "Hold right click while this item");
        lore.add(ChatColor.GRAY + "is in your hand to use");
        lore.add("");
        lore.addAll(getDescription());
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);
        PersistentDataContainerUtil.putString("customConsumable", toString(), itemStack);
        PersistentDataContainerUtil.putInteger("consumableLevel", skillLevel, itemStack);
        PersistentDataContainerUtil.putInteger("consumableUsesLeft", uses, itemStack);
        PersistentDataContainerUtil.putInteger("reqLevel", reqLevel, itemStack);
        return itemStack;
    }

    private Color getPotionColor() {
        switch (this) {
            case POTION_INSTANT_HEALTH:
                return Color.RED;
            case POTION_INSTANT_MANA:
                return Color.AQUA;
            case POTION_INSTANT_HYBRID:
                return Color.YELLOW;
            case POTION_REGENERATION_HEALTH:
                return Color.FUCHSIA;
        }
        return Color.RED;
    }

    public String getName() {
        switch (this) {
            case BUFF_ELEMENT_DAMAGE:
                return ChatColor.RED + "Steak (Damage Buff)";
            case BUFF_ELEMENT_DEFENSE:
                return ChatColor.AQUA + "Beef Stew (Defense Buff)";
            /*case BUFF_MAGICAL_DAMAGE:
                return ChatColor.LIGHT_PURPLE + "Cooked Fish (Magical-Damage Buff)";
            case BUFF_MAGICAL_DEFENSE:
                return ChatColor.GREEN + "Highland Soup (Magical-Defense Buff)";*/
            case POTION_INSTANT_HEALTH:
                return ChatColor.RED + "Health Potion";
            case POTION_INSTANT_MANA:
                return ChatColor.AQUA + "Mana Potion";
            case POTION_INSTANT_HYBRID:
                return ChatColor.LIGHT_PURPLE + "Hybrid Potion";
            case POTION_REGENERATION_HEALTH:
                return ChatColor.GOLD + "Health Regeneration Potion";
        }
        return "";
    }

    public List<String> getDescription() {
        List<String> lore = new ArrayList<>();
        switch (this) {
            case BUFF_ELEMENT_DAMAGE:
                lore.add(ChatColor.GRAY + "Increases element damage for 10 minutes");
                break;
            case BUFF_ELEMENT_DEFENSE:
                lore.add(ChatColor.GRAY + "Increases element defense for 10 minutes");
                break;
            /*case BUFF_MAGICAL_DAMAGE:
                lore.add(ChatColor.GRAY + "Increases magical damage for 10 minutes");
                break;
            case BUFF_MAGICAL_DEFENSE:
                lore.add(ChatColor.GRAY + "Increases magical defense for 10 minutes");
                break;*/
            case POTION_INSTANT_HEALTH:
                lore.add(ChatColor.GRAY + "Restores health");
                break;
            case POTION_INSTANT_MANA:
                lore.add(ChatColor.GRAY + "Restores mana");
                break;
            case POTION_INSTANT_HYBRID:
                lore.add(ChatColor.GRAY + "Restores health & mana");
                break;
            case POTION_REGENERATION_HEALTH:
                lore.add(ChatColor.GRAY + "Restores health every seconds for 18 seconds");
                break;
        }
        return lore;
    }

    public List<String> getLoreTechnicalInfo(int level) {
        List<String> lore = new ArrayList<>();
        switch (this) {
            case BUFF_ELEMENT_DAMAGE:
                lore.add(ChatColor.RED + "Multiplier: " + getBuffMultiplier(level));
                break;
            case BUFF_ELEMENT_DEFENSE:
                lore.add(ChatColor.AQUA + "Multiplier: " + getBuffMultiplier(level));
                break;
            /*case BUFF_MAGICAL_DAMAGE:
                lore.add(ChatColor.LIGHT_PURPLE + "Multiplier: " + getBuffMultiplier(level));
                break;
            case BUFF_MAGICAL_DEFENSE:
                lore.add(ChatColor.GREEN + "Multiplier: " + getBuffMultiplier(level));
                break;*/
            case POTION_INSTANT_HEALTH:
                lore.add(ChatColor.RED + "Restores: " + getInstantHealAmount(level));
                break;
            case POTION_INSTANT_MANA:
                lore.add(ChatColor.AQUA + "Restores: " + getInstantManaAmount(level));
                break;
            case POTION_INSTANT_HYBRID:
                lore.add(ChatColor.RED + "Restores: " + (int) (getInstantHealAmount(level) * HYBRID_NERF));
                lore.add(ChatColor.AQUA + "Restores: " + (int) (getInstantManaAmount(level) * HYBRID_NERF));
                break;
            case POTION_REGENERATION_HEALTH:
                lore.add(ChatColor.RED + "Restores: " + (int) (getInstantHealAmount(level) * REGEN_NERF));
                break;
        }
        return lore;
    }

    public int getInstantHealAmount(int level) {
        switch (level) {
            case 1:
                return 80;
            case 2:
                return 200;
            case 3:
                return 500;
            case 4:
                return 800;
            case 5:
                return 1200;
            case 6:
                return 1700;
            case 7:
                return 2400;
            case 8:
                return 3000;
            case 9:
                return 3400;
            case 10:
                return 4000;
        }
        return 80;
    }

    public int getInstantManaAmount(int level) {
        switch (level) {
            case 1:
                return 80;
            case 2:
                return 200;
            case 3:
                return 500;
            case 4:
                return 800;
            case 5:
                return 1200;
            case 6:
                return 1700;
            case 7:
                return 2400;
            case 8:
                return 3000;
            case 9:
                return 3400;
            case 10:
                return 4000;
        }
        return 80;
    }

    public double getBuffMultiplier(int level) {
        switch (level) {
            case 1:
                return 0.08;
            case 2:
                return 0.1;
            case 3:
                return 0.12;
            case 4:
                return 0.14;
            case 5:
                return 0.16;
            case 6:
                return 0.18;
            case 7:
                return 0.2;
            case 8:
                return 0.22;
            case 9:
                return 0.22;
            case 10:
                return 0.24;
        }
        return 0.08;
    }
}
