package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

public enum RPGSlotType {
    PARROT,
    EARRING,
    NECKLACE,
    GLOVE,
    RING,
    CHARACTER_INFO,
    PET,
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS,
    OFFHAND,
    MAINHAND;

    public int getSlotNo() {
        if (this == RPGSlotType.PARROT) {
            return 11;
        } else if (this == RPGSlotType.EARRING) {
            return 15;
        } else if (this == RPGSlotType.NECKLACE) {
            return 19;
        } else if (this == RPGSlotType.GLOVE) {
            return 23;
        } else if (this == RPGSlotType.RING) {
            return 28;
        } else if (this == RPGSlotType.CHARACTER_INFO) {
            return 14;
        } else if (this == RPGSlotType.PET) {
            return 34;
        } else if (this == RPGSlotType.HELMET) {
            return 13;
        } else if (this == RPGSlotType.CHESTPLATE) {
            return 22;
        } else if (this == RPGSlotType.LEGGINGS) {
            return 31;
        } else if (this == RPGSlotType.BOOTS) {
            return 40;
        } else if (this == RPGSlotType.OFFHAND) {
            return 21;
        }
        return 30;
    }
}
