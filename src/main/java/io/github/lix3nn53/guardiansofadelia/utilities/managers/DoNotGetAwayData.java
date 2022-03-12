package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import org.bukkit.Location;

public class DoNotGetAwayData {
    public final Location center;
    public final float distance;
    public final String onLeave;

    public DoNotGetAwayData(Location center, float distance, String onLeave) {
        this.center = center;
        this.distance = distance;
        this.onLeave = onLeave;
    }
}
