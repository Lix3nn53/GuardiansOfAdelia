package io.github.lix3nn53.guardiansofadelia.Items;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.List;

public enum Consumable {
    BUFF_PHYSICAL_DAMAGE,
    BUFF_PHYSICAL_DEFENSE,
    BUFF_MAGICAL_DAMAGE,
    BUFF_MAGICAL_DEFENSE,
    POTION_INSTANT_HEALTH,
    POTION_INSTANT_MANA,
    POTION_INSTANT_HYBRID,
    POTION_REGENERATION_HEALTH;

    public void consume(Player player, int skillLevel, ItemStack itemStack) {
        if (PersistentDataContainerUtil.hasInteger(itemStack, "reqLevel")) {
            int reqLevel = PersistentDataContainerUtil.getInteger(itemStack, "reqLevel");
            if (player.getLevel() < reqLevel) {
                player.sendMessage(ChatColor.RED + "Required level to use this item is " + reqLevel);
                return;
            }
        }
        /*TODO Cast skillapi skill when item consumed
        SkillAPIUtils.forceUseSkill(player, getSkillCode(), skillLevel);*/
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
                itemStack.setAmount(0);
            }
        } else {
            itemStack.setAmount(0);
        }
    }

    public String getSkillCode() {
        switch (this) {
            case BUFF_PHYSICAL_DAMAGE:
                return "buffPhysicalDamage";
            case BUFF_PHYSICAL_DEFENSE:
                return "buffPhysicalDefense";
            case BUFF_MAGICAL_DAMAGE:
                return "buffMagicalDamage";
            case BUFF_MAGICAL_DEFENSE:
                return "buffMagicalDefense";
            case POTION_INSTANT_HEALTH:
                return "potionInstantHealth";
            case POTION_INSTANT_MANA:
                return "potionInstantMana";
            case POTION_INSTANT_HYBRID:
                return "potionInstantHybrid";
            case POTION_REGENERATION_HEALTH:
                return "potionRegenerationHealth";
        }
        return "";
    }

    public Material getMaterial() {
        switch (this) {
            case BUFF_PHYSICAL_DAMAGE:
                return Material.COOKED_BEEF;
            case BUFF_PHYSICAL_DEFENSE:
                return Material.RABBIT_STEW;
            case BUFF_MAGICAL_DAMAGE:
                return Material.COOKED_SALMON;
            case BUFF_MAGICAL_DEFENSE:
                return Material.SUSPICIOUS_STEW;
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
            case BUFF_PHYSICAL_DAMAGE:
                return ChatColor.RED + "Steak (Physical-Damage Buff)";
            case BUFF_PHYSICAL_DEFENSE:
                return ChatColor.AQUA + "Beef Stew (Physical-Defense Buff)";
            case BUFF_MAGICAL_DAMAGE:
                return ChatColor.LIGHT_PURPLE + "Cooked Fish (Magical-Damage Buff)";
            case BUFF_MAGICAL_DEFENSE:
                return ChatColor.GREEN + "Highland Soup (Magical-Defense Buff)";
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
            case BUFF_PHYSICAL_DAMAGE:
                lore.add(ChatColor.GRAY + "Increases physical damage for 20 minutes");
                break;
            case BUFF_PHYSICAL_DEFENSE:
                lore.add(ChatColor.GRAY + "Increases physical defense for 20 minutes");
                break;
            case BUFF_MAGICAL_DAMAGE:
                lore.add(ChatColor.GRAY + "Increases magical damage for 20 minutes");
                break;
            case BUFF_MAGICAL_DEFENSE:
                lore.add(ChatColor.GRAY + "Increases magical defense for 20 minutes");
                break;
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
                lore.add(ChatColor.GRAY + "Restores health every 2 seconds for 20 seconds");
                break;
        }
        return lore;
    }

    /*public void startConsuming(Player player) {
        if (GuardianDataManager.hasGuardianData(player.getUniqueId())) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player.getUniqueId());
            if (guardianData.isFreeToAct()) {
                guardianData.setConsuming(true);

                final double startPosX = player.getLocation().getX();
                final double startPosY = player.getLocation().getY();
                final double startPosZ = player.getLocation().getZ();

                new BukkitRunnable() {

                    // We don't want the task to run indefinitely
                    int secsRun;

                    @Override
                    public void run() {
                        secsRun++;

                        double differenceX = Math.abs(startPosX - player.getLocation().getX());
                        double differenceY = Math.abs(startPosY - player.getLocation().getY());
                        double differenceZ = Math.abs(startPosZ - player.getLocation().getZ());

                        if (secsRun == 0) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setConsuming(false);
                                cancel();
                                player.sendMessage("§cConsuming has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Consuming...", ChatColor.YELLOW + "||||||||||||||||", 0, 50, 0);
                            }
                        } else if (secsRun == 1) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setConsuming(false);
                                cancel();
                                player.sendMessage("§cConsuming has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Consuming...", ChatColor.GREEN + "||||" + ChatColor.YELLOW + "||||||||||||", 0, 50, 0);
                            }
                        } else if (secsRun == 2) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setConsuming(false);
                                cancel();
                                player.sendMessage("§cConsuming has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Consuming...", ChatColor.GREEN + "||||||||" + ChatColor.YELLOW + "||||||||", 0, 50, 0);
                            }
                        } else if (secsRun == 3) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setConsuming(false);
                                cancel();
                                player.sendMessage("§cConsuming has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Consuming...", ChatColor.GREEN + "||||||||||||" + ChatColor.YELLOW + "||||", 0, 50, 0);
                            }
                        } else if (secsRun == 4) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setConsuming(false);
                                cancel();
                                player.sendMessage("§cConsuming has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Consuming...", ChatColor.GREEN + "||||||||||||||||", 0, 50, 0);
                            }
                        } else if (secsRun == 5) {
                            cancel();
                            InventoryUtils.removeMaterialFromInventory(player.getInventory(), consumableType.getMaterial(), 1);
                            SkillAPIUtils.forceUseSkill(player, consumableType.getSkillCode(), skillLevel);
                            guardianData.setConsuming(false);
                        }
                    }
                }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 20L);
            }
        }
    }*/
}
