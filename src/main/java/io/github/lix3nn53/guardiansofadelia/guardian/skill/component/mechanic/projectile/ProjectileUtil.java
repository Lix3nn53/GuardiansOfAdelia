package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class ProjectileUtil {

    private static final Vector X_VEC = new Vector(1, 0, 0);
    private static final double DEGREE_TO_RAD = Math.PI / 180;
    private static final Vector vel = new Vector();


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
    public static ArrayList<Vector> calcSpread(Vector dir, double angle, int amount) {
        // Special cases
        if (amount <= 0) {
            return new ArrayList<>();
        }

        ArrayList<Vector> list = new ArrayList<>();

        // One goes straight if odd amount
        if (amount % 2 == 1) {
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
        final Vector baseVelocity = vel.clone();
        baseVelocity.setX(1);
        baseVelocity.setY(0);
        baseVelocity.setZ(0);

        // Get the vertical angle
        double vBaseAngle = Math.acos(Math.max(-1, Math.min(base.dot(dir), 1)));
        if (dir.getY() < 0) {
            vBaseAngle = -vBaseAngle;
        }
        double hAngle = Math.acos(Math.max(-1, Math.min(1, base.dot(X_VEC)))) / DEGREE_TO_RAD;
        if (dir.getZ() < 0) {
            hAngle = -hAngle;
        }

        // Calculate directions
        double angleIncrement = angle / (amount - 1);
        for (int i = 0; i < amount / 2; i++) {
            for (int direction = -1; direction <= 1; direction += 2) {
                // Initial calculations
                double bonusAngle = angle / 2 * direction - angleIncrement * i * direction;
                double totalAngle = hAngle + bonusAngle;
                double vAngle = vBaseAngle * Math.cos(bonusAngle * DEGREE_TO_RAD);
                double x = Math.cos(vAngle);

                // Get the velocity
                baseVelocity.setX(x * Math.cos(totalAngle * DEGREE_TO_RAD));
                baseVelocity.setY(Math.sin(vAngle));
                baseVelocity.setZ(x * Math.sin(totalAngle * DEGREE_TO_RAD));

                // Launch the projectile
                list.add(baseVelocity.clone());
            }
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
    public static ArrayList<Location> calcRain(Location loc, double radius, double height, int amount) {
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
            double rad = radius * (tiers - i) / tiers;
            int tierNum = Math.min(amount, 8);
            double increment = 360 / tierNum;
            double angle = (i % 2) * 22.5;
            for (int j = 0; j < tierNum; j++) {
                double dx = Math.cos(angle) * rad;
                double dz = Math.sin(angle) * rad;
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
