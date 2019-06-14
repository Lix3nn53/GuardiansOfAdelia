package io.github.lix3nn53.guardiansofadelia.guardian.attribute;

public class Attribute {
    private final AttributeType attributeType;
    private int invested;
    private int bonus;

    public Attribute(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public int getInvested() {
        return invested;
    }

    public void setInvested(int invested) {
        this.invested = invested;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public void removeBonus(int remove) {
        this.bonus -= remove;
    }

    public void addBonus(int add) {
        this.bonus += add;
    }

    public int getTotal() {
        return invested + bonus;
    }

    public double getIncrement() {
        return (invested + bonus) * attributeType.getIncrement();
    }
}
