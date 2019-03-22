package io.github.lix3nn53.guardiansofadelia.Items.stats;

import java.util.Random;

public class StatPassive implements Stat {

    private int fire = 0;
    private int water = 0;
    private int earth = 0;
    private int lightning = 0;
    private int air = 0;

    public StatPassive(int fire, int water, int earth, int lightning, int air) {
        this.fire = fire;
        this.water = water;
        this.earth = earth;
        this.lightning = lightning;
        this.air = air;
    }

    public StatPassive(int minStatValue, int maxStatValue, double chanceToGetEachStat, int minNumberOfStats) {
        if (maxStatValue <= 0) {
            return;
        }
        if (minStatValue <= 0) {
            minStatValue = 1;
        }
        if (maxStatValue - minStatValue < 1) {
            maxStatValue = minStatValue;
        }

        this.roll(minStatValue, maxStatValue, chanceToGetEachStat);

        if (!isSatisfied(minNumberOfStats)) {
            satisfy(minStatValue, maxStatValue, minNumberOfStats);
        }
    }

    public int getFire() {
        return fire;
    }

    public int getWater() {
        return water;
    }

    public int getEarth() {
        return earth;
    }

    public int getLightning() {
        return lightning;
    }

    public int getAir() {
        return air;
    }

    private int getNumberOfStats() {
        int numberOfStats = 0;
        if (this.fire != 0) {
            numberOfStats++;
        }
        if (this.water != 0) {
            numberOfStats++;
        }
        if (this.earth != 0) {
            numberOfStats++;
        }
        if (this.lightning != 0) {
            numberOfStats++;
        }
        if (this.air != 0) {
            numberOfStats++;
        }
        return numberOfStats;
    }

    private boolean isSatisfied(int minNumberOfStats) {
        if (minNumberOfStats > 5) {
            minNumberOfStats = 5;
        }
        int numberOfStats = getNumberOfStats();
        return numberOfStats >= minNumberOfStats;
    }

    private void roll(int minStatValue, int maxStatValue, double chanceToGetEachStat) {
        if (this.fire == 0) {
            double chance = Math.random();
            if (chance <= chanceToGetEachStat) {
                if (minStatValue >= maxStatValue) {
                    this.fire = maxStatValue;
                } else {
                    int value = new Random().nextInt((maxStatValue - minStatValue) + 1) + minStatValue;
                    this.fire = value;
                }
            }
        }
        if (this.water == 0) {
            double chance = Math.random();
            if (chance <= chanceToGetEachStat) {
                if (minStatValue >= maxStatValue) {
                    this.fire = maxStatValue;
                } else {
                    int value = new Random().nextInt((maxStatValue - minStatValue) + 1) + minStatValue;
                    this.fire = value;
                }
            }
        }
        if (this.earth == 0) {
            double chance = Math.random();
            if (chance <= chanceToGetEachStat) {
                if (minStatValue >= maxStatValue) {
                    this.fire = maxStatValue;
                } else {
                    int value = new Random().nextInt((maxStatValue - minStatValue) + 1) + minStatValue;
                    this.fire = value;
                }
            }
        }
        if (this.lightning == 0) {
            double chance = Math.random();
            if (chance <= chanceToGetEachStat) {
                if (minStatValue >= maxStatValue) {
                    this.fire = maxStatValue;
                } else {
                    int value = new Random().nextInt((maxStatValue - minStatValue) + 1) + minStatValue;
                    this.fire = value;
                }
            }
        }
        if (this.air == 0) {
            double chance = Math.random();
            if (chance <= chanceToGetEachStat) {
                if (minStatValue >= maxStatValue) {
                    this.fire = maxStatValue;
                } else {
                    int value = new Random().nextInt((maxStatValue - minStatValue) + 1) + minStatValue;
                    this.fire = value;
                }
            }
        }
    }

    private void satisfy(int minStatValue, int maxStatValue, int minNumberOfStats) {
        int numberOfStats = getNumberOfStats();
        if (this.fire == 0) {
            if (minStatValue >= maxStatValue) {
                this.fire = maxStatValue;
            } else {
                int value = new Random().nextInt((maxStatValue - minStatValue) + 1) + minStatValue;
                this.fire = value;
            }
            numberOfStats++;
        }
        if (numberOfStats >= minNumberOfStats) {
            return;
        }
        if (this.water == 0) {
            if (minStatValue >= maxStatValue) {
                this.fire = maxStatValue;
            } else {
                int value = new Random().nextInt((maxStatValue - minStatValue) + 1) + minStatValue;
                this.fire = value;
            }
            numberOfStats++;
        }
        if (numberOfStats >= minNumberOfStats) {
            return;
        }
        if (this.earth == 0) {
            if (minStatValue >= maxStatValue) {
                this.fire = maxStatValue;
            } else {
                int value = new Random().nextInt((maxStatValue - minStatValue) + 1) + minStatValue;
                this.fire = value;
            }
            numberOfStats++;
        }
        if (numberOfStats >= minNumberOfStats) {
            return;
        }
        if (this.lightning == 0) {
            if (minStatValue >= maxStatValue) {
                this.fire = maxStatValue;
            } else {
                int value = new Random().nextInt((maxStatValue - minStatValue) + 1) + minStatValue;
                this.fire = value;
            }
            numberOfStats++;
        }
        if (numberOfStats >= minNumberOfStats) {
            return;
        }
        if (this.air == 0) {
            if (minStatValue >= maxStatValue) {
                this.fire = maxStatValue;
            } else {
                int value = new Random().nextInt((maxStatValue - minStatValue) + 1) + minStatValue;
                this.fire = value;
            }
        }
    }
}
