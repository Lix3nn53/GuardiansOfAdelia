package io.github.lix3nn53.guardiansofadelia.creatures.entitySkills;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class EntitySkillComponents {

    public static SkillComponent getComponentPushMechanic(String holoWarnMessage) {
        List<Double> list = new ArrayList<>();
        list.add(2.5D);
        list.add(2.75D);
        list.add(3D);
        list.add(3.25D);
        list.add(3.5D);
        list.add(4D);
        list.add(4D);
        list.add(4D);
        list.add(4D);
        list.add(4D);

        return new PushMechanic(PushMechanic.PushType.FIXED, list, true);
    }

    public static SkillComponent getComponentPotionEffectMechanic(PotionEffectType potionEffectType) {
        List<Integer> duration = new ArrayList<>();
        duration.add(50);
        duration.add(50);
        duration.add(50);
        duration.add(50);
        duration.add(50);
        duration.add(50);
        duration.add(50);
        duration.add(50);
        duration.add(50);
        duration.add(50);

        List<Integer> amplifiers = new ArrayList<>();
        amplifiers.add(1);
        amplifiers.add(1);
        amplifiers.add(1);
        amplifiers.add(1);
        amplifiers.add(2);
        amplifiers.add(2);
        amplifiers.add(2);
        amplifiers.add(2);
        amplifiers.add(3);
        amplifiers.add(3);

        return new PotionEffectMechanic(potionEffectType, duration, amplifiers);
    }

    public static SkillComponent getComponentPullMechanic(String holoWarnMessage) {
        List<Double> speeds = new ArrayList<>();
        speeds.add(-2D);
        speeds.add(-2.5D);
        speeds.add(-3D);
        speeds.add(-3.25D);
        speeds.add(-3.5D);
        speeds.add(-3.75D);
        speeds.add(-4D);
        speeds.add(-4.5D);
        speeds.add(-5D);
        speeds.add(-5.5D);

        return new PushMechanic(PushMechanic.PushType.FIXED, speeds, true);
    }

    public static SkillComponent getComponentLaunchMechanic(String holoWarnMessage) {
        List<Double> upward = new ArrayList<>();
        upward.add(1.2D);
        upward.add(1.4D);
        upward.add(1.6D);
        upward.add(1.8D);
        upward.add(2D);
        upward.add(2.2D);
        upward.add(2.4D);
        upward.add(2.6D);
        upward.add(2.8D);
        upward.add(3D);
        List<Double> forward = new ArrayList<>();
        forward.add(0D);
        forward.add(0D);
        forward.add(0D);
        forward.add(0D);
        forward.add(0D);
        forward.add(0D);
        List<Double> right = new ArrayList<>();
        right.add(0D);
        right.add(0D);
        right.add(0D);
        right.add(0D);
        right.add(0D);
        right.add(0D);

        return new LaunchMechanic(LaunchMechanic.Relative.TARGET, forward, upward, right);
    }

    public static SkillComponent getComponentHealByAmount(String holoWarnMessage) {
        List<Integer> amounts = new ArrayList<>();
        amounts.add(50);
        amounts.add(100);
        amounts.add(200);
        amounts.add(500);
        amounts.add(750);
        amounts.add(1000);
        amounts.add(1250);
        amounts.add(1600);
        amounts.add(2200);
        amounts.add(3600);

        return new HealMechanic(amounts, new ArrayList<>());
    }

    public static SkillComponent getComponentDamageMechanic(DamageMechanic.DamageType type) {
        List<Double> damages = new ArrayList<>();
        damages.add(180D);
        damages.add(292D);
        damages.add(405D);
        damages.add(540D);
        damages.add(675D);
        damages.add(900D);
        damages.add(900D);
        damages.add(900D);
        damages.add(900D);
        damages.add(900D);

        return new DamageMechanic(damages, type);
    }
}
