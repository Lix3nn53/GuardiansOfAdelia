package io.github.lix3nn53.guardiansofadelia.guardian.character;

public class RPGClassStats {
    private int totalExp = 0;
    private int one, two, three, passive, ultimate = 0;

    public RPGClassStats(int totalExp, int one, int two, int three, int passive, int ultimate) {
        this.totalExp = totalExp;
        this.one = one;
        this.two = two;
        this.three = three;
        this.passive = passive;
        this.ultimate = ultimate;
    }

    public RPGClassStats() {
    }

    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
    }

    public int getOne() {
        return one;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public int getTwo() {
        return two;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public int getThree() {
        return three;
    }

    public void setThree(int three) {
        this.three = three;
    }

    public int getPassive() {
        return passive;
    }

    public void setPassive(int passive) {
        this.passive = passive;
    }

    public int getUltimate() {
        return ultimate;
    }

    public void setUltimate(int ultimate) {
        this.ultimate = ultimate;
    }
}
