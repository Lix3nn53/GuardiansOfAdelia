package io.github.lix3nn53.guardiansofadelia.minigames.guildwar;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class Castle {
    private int conqueror = 0;
    private int flagHeight = 10;
    private final Block flagGround;

    public Castle(Block flagGround) {
        this.flagGround = flagGround;
    }

    public int getConqueror() {
        return conqueror;
    }

    public Block getFlagGround() {
        return flagGround;
    }

    public Block getFlag() {
        return flagGround.getRelative(0,flagHeight,0);
    }

    public void reset() {
        for (int i = 1; i <= 9; i++) {
            flagGround.getRelative(0, i, 0).setType(Material.AIR);
        }
        flagGround.getRelative(0, 10, 0).setType(Material.WHITE_WOOL);
        flagHeight = 10;
        conqueror = 0;
    }

    public int conquer(int teamNo, Material teamMaterial) {
        if (conqueror == teamNo) {
            if (flagHeight < 10) {
                upFlagHeight();
            }
        } else {
            if (flagHeight == 1) {
                changeFlagMaterial(teamMaterial);
                conqueror = teamNo;
            } else {
                downFlagHeight();
            }
        }
        return conqueror;
    }

    private void changeFlagMaterial(Material woolMaterial) {
        getFlag().setType(woolMaterial);
    }

    private void upFlagHeight() {
        Block flag = getFlag();
        Material type = flag.getType();
        Block relative = flag.getRelative(BlockFace.UP);
        flag.setType(Material.AIR);
        relative.setType(type);
        flagHeight++;
    }

    private void downFlagHeight() {
        Block flag = getFlag();
        Material type = flag.getType();
        Block relative = flag.getRelative(BlockFace.DOWN);
        flag.setType(Material.AIR);
        relative.setType(type);
        flagHeight--;
    }
}
