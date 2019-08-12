package io.github.lix3nn53.guardiansofadelia.sounds;

public enum GoaSound {
    LEVEL_UP,
    WHEEL_OF_FORTUNE,
    ANVIL,
    FAIL,
    SUCCESS,
    PET_ANGRY,
    PET_ATTACK,
    PET_HAPPY1,
    PET_HAPPY2,
    PET_JOY,
    SKILL_ABSORBING,
    SKILL_ARROW_RAIN,
    SKILL_BARD_MUSIC,
    SKILL_BUFF,
    SKILL_FIRE_AURA,
    SKILL_FIRE_SLASH,
    SKILL_HEAL,
    SKILL_JUMP,
    SKILL_LIGHTNING_FLUX,
    SKILL_LIGHTNING_NORMAL,
    SKILL_MULTI_ARROW,
    SKILL_PIG,
    SKILL_POISON_ARROW,
    SKILL_POISON_AURA,
    SKILL_POISON_SLASH,
    SKILL_POWERFUL_LANDING,
    SKILL_PROJECTILE_FIRE,
    SKILL_PROJECTILE_ICE,
    SKILL_PROJECTILE_VOID,
    SKILL_SCREAM,
    SKILL_SMASH,
    SKILL_SOFT_WIND,
    SKILL_SONIC_BOOM,
    SKILL_SPLASH,
    SKILL_STORM_AMBIANCE,
    SKILL_STUN_HIT,
    SKILL_SWORD_MULTI_SLASH,
    SKILL_THROW_SMALL_OBJECT,
    SKILL_VOID_SIGNAL,
    SKILL_WIND_PUSH;

    public CustomSound getCustomSound() {
        String sound = getSoundString();
        float volume = 0.5f;
        float pitch = 1f;

        return new CustomSound(sound, volume, pitch);
    }

    private String getSoundString() {
        String sound = this.name().toLowerCase().replace("skill_", "skill.");
        return sound;
    }
}
