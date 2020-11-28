package io.github.lix3nn53.guardiansofadelia.rewards.daily;

import java.time.LocalDate;

public class DailyRewardInfo {

    private LocalDate lastObtainDate;

    public LocalDate getLastObtainDate() {
        return lastObtainDate;
    }

    public void setLastObtainDate(LocalDate lastObtainDate) {
        this.lastObtainDate = lastObtainDate;
    }
}
