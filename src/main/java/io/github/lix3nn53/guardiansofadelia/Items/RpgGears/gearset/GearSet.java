package io.github.lix3nn53.guardiansofadelia.Items.RpgGears.gearset;

import java.util.Objects;

public class GearSet {
    private final String name;
    private final int pieceCount;

    public GearSet(String name, int pieceCount) {
        this.name = name;
        this.pieceCount = pieceCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GearSet gearSet = (GearSet) o;
        return pieceCount == gearSet.pieceCount &&
                Objects.equals(name, gearSet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pieceCount);
    }

    public String getName() {
        return name;
    }

    public int getPieceCount() {
        return pieceCount;
    }
}
