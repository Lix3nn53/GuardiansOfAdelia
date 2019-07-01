package io.github.lix3nn53.guardiansofadelia.utilities.particle;

import org.bukkit.Location;
import org.bukkit.Particle;

import java.util.Random;

public class ParticleUtil {

    private static final Random random = new Random();

    /**
     * Plays particles about the given location using the given settings
     *
     * @param loc location to center the effect around
     */
    public static void play(Location loc, Particle particle, ArrangementParticle arrangement, double radius, int amount, Direction dir, double dx, double dy, double dz, double speed) {
        if (arrangement.equals(ArrangementParticle.CIRCLE)) {
            fillCircle(loc, particle, radius, amount, dir, dx, dy, dz, speed);
        } else if (arrangement.equals(ArrangementParticle.SPHERE)) {
            fillSphere(loc, particle, radius, amount, dx, dy, dz, speed);
        } else if (arrangement.equals(ArrangementParticle.HEMISPHERE)) {
            fillHemisphere(loc, particle, radius, amount, dx, dy, dz, speed);
        }
    }

    /**
     * Plays a particle at the given location based on the string
     *
     * @param loc      location to playSingleParticle the effect
     * @param particle particle to playSingleParticle
     */
    public static void playSingleParticle(Location loc, Particle particle, double dx, double dy, double dz, double speed) {
        loc.getWorld().spawnParticle(particle, loc, 1, dx, dy, dz, speed);
    }

    /**
     * Plays several of a particle type randomly within a circle
     *
     * @param loc    center location of the circle
     * @param radius radius of the circle
     * @param amount amount of particles to playSingleParticle
     */
    public static void fillCircle(
            Location loc,
            Particle particle,
            double radius,
            int amount,
            Direction direction,
            double dx, double dy, double dz, double speed) {
        Location temp = loc.clone();
        double rSquared = radius * radius;
        double twoRadius = radius * 2;
        int index = 0;

        // Play the particles
        while (index < amount) {
            if (direction == Direction.XY || direction == Direction.XZ) {
                temp.setX(loc.getX() + random.nextDouble() * twoRadius - radius);
            }
            if (direction == Direction.XY || direction == Direction.YZ) {
                temp.setY(loc.getY() + random.nextDouble() * twoRadius - radius);
            }
            if (direction == Direction.XZ || direction == Direction.YZ) {
                temp.setZ(loc.getZ() + random.nextDouble() * twoRadius - radius);
            }

            if (temp.distanceSquared(loc) > rSquared) {
                continue;
            }

            playSingleParticle(temp, particle, dx, dy, dz, speed);
            index++;
        }
    }

    /**
     * Randomly plays particle effects within the sphere
     *
     * @param loc      location to center the effect around
     * @param particle the string value for the particle
     * @param radius   radius of the sphere
     * @param amount   amount of particles to use
     */
    public static void fillSphere(Location loc, Particle particle, double radius, int amount,
                                  double dx, double dy, double dz, double speed) {
        Location temp = loc.clone();
        double rSquared = radius * radius;
        double twoRadius = radius * 2;
        int index = 0;

        // Play the particles
        while (index < amount) {
            temp.setX(loc.getX() + random.nextDouble() * twoRadius - radius);
            temp.setY(loc.getY() + random.nextDouble() * twoRadius - radius);
            temp.setZ(loc.getZ() + random.nextDouble() * twoRadius - radius);

            if (temp.distanceSquared(loc) > rSquared) {
                continue;
            }

            playSingleParticle(temp, particle, dx, dy, dz, speed);
            index++;
        }
    }

    /**
     * Randomly plays particle effects within the hemisphere
     *
     * @param loc      location to center the effect around
     * @param particle the string value for the particle
     * @param radius   radius of the sphere
     * @param amount   amount of particles to use
     */
    public static void fillHemisphere(Location loc, Particle particle, double radius, int amount,
                                      double dx, double dy, double dz, double speed) {
        Location temp = loc.clone();
        double twoRadius = radius * 2;

        // Play the particles
        for (int i = 0; i < amount; i++) {
            temp.setX(loc.getX() + random.nextDouble() * twoRadius - radius);
            temp.setY(loc.getY() + random.nextDouble() * radius);
            temp.setZ(loc.getZ() + random.nextDouble() * twoRadius - radius);

            playSingleParticle(temp, particle, dx, dy, dz, speed);
        }
    }
}
