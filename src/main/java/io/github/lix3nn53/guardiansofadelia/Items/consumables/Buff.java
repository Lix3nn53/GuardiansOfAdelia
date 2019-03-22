package io.github.lix3nn53.guardiansofadelia.Items.consumables;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import org.bukkit.scheduler.BukkitRunnable;

public class Buff {

    private final String skillCode;
    private final int timeLimitInMinutes;
    private BukkitRunnable countDown;

    public Buff(String skillCode, int timeLimitInMinutes) {
        this.skillCode = skillCode;
        this.timeLimitInMinutes = timeLimitInMinutes;
    }

    public boolean addBuff(GuardianData guardianData) {
        if (!guardianData.hasBuff(this.skillCode)) {
            guardianData.addBuff(this.skillCode);
            startBuffCountDown(guardianData);
        }
        return false;
    }

    private void startBuffCountDown(GuardianData guardianData) {
        this.countDown = new BukkitRunnable() {

            int minutesPass = 0;

            @Override
            public void run() {
                if (minutesPass == timeLimitInMinutes) {
                    //end dungeon
                    cancel();
                    removeBuff(guardianData);
                } else {
                    minutesPass++;
                }
            }
        };
        countDown.runTaskTimer(GuardiansOfAdelia.getInstance(), 5L, 20 * 60L);
    }

    private void removeBuff(GuardianData guardianData) {
        guardianData.removeBuff(skillCode);
    }

    public String getSkillCode() {
        return skillCode;
    }
}
