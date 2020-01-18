package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityListener;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class InvincibleGiveAction implements Action {

    private final long duration;

    public InvincibleGiveAction(long duration) {
        this.duration = duration;
    }

    @Override
    public void perform(Player player) {
        ImmunityListener.addInvincible(player);

        new BukkitRunnable() {
            @Override
            public void run() {
                ImmunityListener.removeInvincible(player);
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), duration);
    }
}
