package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;

import java.util.ArrayList;
import java.util.List;

public class RPGCharacterStats {

    private int totalExp;
    private int maxHealth;
    private int currentHealth;
    private int maxMana;
    private int currentMana;
    private int defense;
    private int magicDamage;
    private int magicDefense;
    private double criticalChance;

    private Attribute fire = new Attribute(AttributeType.FIRE);
    private Attribute lightning = new Attribute(AttributeType.LIGHTNING);
    private Attribute earth = new Attribute(AttributeType.EARTH);
    private Attribute water = new Attribute(AttributeType.WATER);
    private Attribute wind = new Attribute(AttributeType.WIND);

    private List<Integer> investedSkillPoints = new ArrayList<>();

    public RPGCharacterStats() {
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
        investedSkillPoints.add(0);
    }

    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
    }

    public void giveExp(int give) {
        this.totalExp += give;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setMagicDamage(int magicDamage) {
        this.magicDamage = magicDamage;
    }

    public void setMagicDefense(int magicDefense) {
        this.magicDefense = magicDefense;
    }

    public void setCriticalChance(double criticalChance) {
        this.criticalChance = criticalChance;
    }

    public Attribute getFire() {
        return fire;
    }

    public Attribute getLightning() {
        return lightning;
    }

    public Attribute getEarth() {
        return earth;
    }

    public Attribute getWater() {
        return water;
    }

    public Attribute getWind() {
        return wind;
    }

    public int getTotalMaxHealth() {
        return (int) (maxHealth + earth.getIncrement() + 0.5);
    }

    public int getTotalMaxMana() {
        return (int) (maxMana + water.getIncrement() + 0.5);
    }

    public int getPhysicalDamageBonus(boolean addBonus) {
        return (int) (fire.getIncrement() + 0.5);
    }

    public int getTotalDefense() {
        return defense;
    }

    public int getTotalMagicDamage() {
        return (int) (magicDamage + lightning.getIncrement() + 0.5);
    }

    public int getTotalMagicDefense() {
        return magicDefense;
    }

    public double getTotalCriticalChance() {
        return criticalChance + wind.getIncrement();
    }

    public void resetAttributes() {
        fire.setInvested(0);
        lightning.setInvested(0);
        earth.setInvested(0);
        water.setInvested(0);
        wind.setInvested(0);
    }

    public void clearBonuses() {
        fire.setBonus(0);
        lightning.setBonus(0);
        earth.setBonus(0);
        water.setBonus(0);
        wind.setBonus(0);
    }

    /**
     * @param skillNo 1,2,3 normal skills, 4 passive, 5 ultimate
     * @param points  to invest in skill
     */
    public void investSkillPoints(int skillNo, int points) {
        int index = skillNo - 1;
        int invested = investedSkillPoints.get(index);
        invested += points;
        investedSkillPoints.set(index, invested);
    }

    public int getInvestedSkillPoints(int skillNo) {
        int index = skillNo - 1;
        return investedSkillPoints.get(index);
    }
}
