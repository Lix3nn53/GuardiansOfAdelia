package io.github.lix3nn53.guardiansofadelia.bossbar;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;

public class HeaderBar {

    private final Player player;
    private final GuardianData guardianData;
    private final BossBar bar;
    private final HashMap<Player, BukkitTask> playerToCooldown = new HashMap<>();

    public HeaderBar(Player player, GuardianData guardianData) {
        this.player = player;
        this.guardianData = guardianData;

        String title = getTitle();
        this.bar = Bukkit.createBossBar(title, BarColor.BLUE, BarStyle.SOLID);
        this.bar.setProgress(1);
        this.bar.setVisible(true);
    }

    public void showToPlayer(Player player) {
        this.bar.addPlayer(player);
    }

    public void destroy() {
        this.bar.removeAll();
    }

    private String getTitle() {
        StringBuilder message = new StringBuilder(player.getDisplayName());

        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
        if (activeCharacter == null) return message.toString();
        RPGCharacterStats stats = activeCharacter.getRpgCharacterStats();

        ArrayList<String> buffs = new ArrayList<>();
        float buffElementDamage = stats.getBuffValue(BuffType.ELEMENT_DAMAGE);
        if (buffElementDamage != 1) {
            if (buffElementDamage > 1) {
                buffs.add(ChatPalette.RED + "Dmg +" + (int) ((buffElementDamage - 1) * 100 + 0.5) + "%");
            } else {
                buffs.add(ChatPalette.RED + "Dmg -" + (int) ((1 - buffElementDamage) * 100 + 0.5) + "%");
            }
        }
        float buffElementDefense = stats.getBuffValue(BuffType.ELEMENT_DEFENSE);
        if (buffElementDefense != 1) {
            if (buffElementDefense > 1) {
                buffs.add(ChatPalette.BLUE_LIGHT + "Def +" + (int) ((buffElementDefense - 1) * 100 + 0.5) + "%");
            } else {
                buffs.add(ChatPalette.BLUE_LIGHT + "Def -" + (int) ((1 - buffElementDefense) * 100 + 0.5) + "%");
            }
        }
        float buffCriticalChance = stats.getBuffValue(BuffType.CRIT_CHANCE);
        if (buffCriticalChance != 0) {
            buffs.add(ChatPalette.GOLD + "Crit +" + (int) ((buffCriticalChance * 100) + 0.5) + "%");
        }
        float buffCriticalDamage = stats.getBuffValue(BuffType.CRIT_DAMAGE);
        if (buffCriticalDamage != 0) {
            buffs.add(ChatPalette.ORANGE + "CritDmg +" + (int) ((buffCriticalDamage * 100) + 0.5) + "%");
        }
        float buffLifeSteal = stats.getBuffValue(BuffType.LIFE_STEAL);
        if (buffLifeSteal != 0) {
            buffs.add(ChatPalette.RED + "LifeSteal +" + (int) ((buffLifeSteal * 100) + 0.5) + "%");
        }
        float buffAbilityHaste = stats.getBuffValue(BuffType.ABILITY_HASTE);
        if (buffAbilityHaste != 0) {
            buffs.add(ChatPalette.BLUE + "Haste +" + buffAbilityHaste + "");
        }

        for (int i = 0; i < buffs.size(); i++) {
            if (i % 2 == 0) {
                message.insert(0, buffs.get(i) + " ");
            } else {
                message.append(" ").append(buffs.get(i));
            }
        }

        if (buffs.size() % 2 == 1) {
            message.append("          ");
        }

        return message.toString();
    }

    public void update() {
        String title = getTitle();

        this.bar.setTitle(title);
    }

    public void removePlayer(Player player) {
        bar.removePlayer(player);
        BukkitTask remove = playerToCooldown.remove(player);

        if (remove != null) remove.cancel();
    }

}
