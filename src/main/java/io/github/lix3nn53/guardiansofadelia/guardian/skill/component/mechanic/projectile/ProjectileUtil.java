package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class ProjectileUtil {

    private static final Vector X_VEC = new Vector(1, 0, 0);


    /**
     * Calculates the directions for projectiles spread from
     * the centered direction using the given angle and
     * number of projectiles to be fired.
     *
     * @param dir    center direction of the spread
     * @param angle  angle which to spread at
     * @param amount amount of directions to calculate
     * @return the list of calculated directions
     */
    public static ArrayList<Vector> calcSpread(Vector dir, float angle, int amount) {
        // Special cases
        if (amount <= 0) {
            return new ArrayList<>();
        }

        ArrayList<Vector> list = new ArrayList<>();

        // One goes straight if odd amount
        if (amount == 1) {
            list.add(dir);
            amount--;
        }

        if (amount <= 0) {
            return list;
        }

        // Get the base velocity
        Vector base = dir.clone();
        base.setY(0);
        base.normalize();

        // Get the vertical angle
        float vBaseAngle = (float) Math.acos(Math.max(-1, Math.min(base.dot(dir), 1)));
        if (dir.getY() < 0) {
            vBaseAngle = -vBaseAngle;
        }
        float hAngle = (float) Math.toDegrees(Math.acos(Math.max(-1f, Math.min(1f, (float) base.dot(X_VEC)))));
        System.out.println("hAngle: " + hAngle);
        if (dir.getZ() < 0) {
            hAngle = -hAngle;
        }

        // Calculate directions
        float angleIncrement = angle / amount;
        System.out.println("angleIncrement: " + angleIncrement);
        if (amount % 2 == 1) {
            angle += angleIncrement;
        }
        for (int i = 0; i < amount; i++) {
            // Initial calculations
            float bonusAngle = angle / 2 - angleIncrement * i;
            System.out.println("bonusAngle: " + bonusAngle);
            float totalAngle = hAngle + bonusAngle;
            System.out.println("totalAngle" + totalAngle);
            float vAngle = (float) (vBaseAngle * Math.cos(Math.toRadians(bonusAngle)));
            float x = (float) Math.cos(vAngle);

            // Get the velocity
            float vx = (float) (x * Math.cos(Math.toRadians(totalAngle)));
            float vy = (float) Math.sin(vAngle);
            float vz = (float) (x * Math.sin(Math.toRadians(totalAngle)));

            // Launch the projectile
            list.add(new Vector(vx, vy, vz));
        }

        return list;
    }

    /**
     * Calculates the locations to spawn projectiles to rain them down
     * over a given location.
     *
     * @param loc    the center location to rain on
     * @param radius radius of the circle
     * @param height height above the target to use
     * @param amount amount of locations to calculate
     * @return list of locations to spawn projectiles
     */
    public static ArrayList<Location> calcRain(Location loc, float radius, float height, int amount) {
        ArrayList<Location> list = new ArrayList<>();
        if (amount <= 0) {
            return list;
        }
        loc.add(0, height, 0);

        // One would be in the center
        list.add(loc);
        amount--;

        // Calculate locations
        int tiers = (amount + 7) / 8;
        for (int i = 0; i < tiers; i++) {
            float rad = radius * (tiers - i) / tiers;
            int tierNum = Math.min(amount, 8);
            float increment = 360 / tierNum;
            float angle = (float) ((i % 2) * 22.5);
            for (int j = 0; j < tierNum; j++) {
                float dx = (float) (Math.cos(angle) * rad);
                float dz = (float) (Math.sin(angle) * rad);
                Location l = loc.clone();
                l.add(dx, 0, dz);
                list.add(l);
                angle += increment;
            }
            amount -= tierNum;
        }

        return list;
    }
}
