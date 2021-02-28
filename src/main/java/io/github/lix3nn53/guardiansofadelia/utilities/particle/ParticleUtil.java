package io.github.lix3nn53.guardiansofadelia.utilities.particle;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.util.Random;

public class ParticleUtil {

    private static final Random random = new Random();

    /**
     * Plays a particle at the given location based on the string
     *
     * @param loc      location to playSingleParticle the effect
     * @param particle particle to playSingleParticle
     */
    public static void playSingleParticle(Location loc, Particle particle, double offsetX, double offsetY, double offsetZ, double extra, Particle.DustOptions dustOptions, boolean force) {
        loc.getWorld().spawnParticle(particle, loc, 1, offsetX, offsetY, offsetZ, extra, dustOptions, force);
    }

    public static void playSingleParticle(Location loc, Particle particle, Particle.DustOptions dustOptions) {
        loc.getWorld().spawnParticle(particle, loc, 1, dustOptions);
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
            Particle.DustOptions dustOptions,
            Direction direction) {
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

            playSingleParticle(temp, particle, dustOptions);
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
    public static void fillSphere(Location loc, Particle particle, double radius, int amount, Particle.DustOptions dustOptions) {
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

            playSingleParticle(temp, particle, dustOptions);
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
    public static void fillHemisphere(Location loc, Particle particle, double radius, int amount, Particle.DustOptions dustOptions) {
        Location temp = loc.clone();
        double twoRadius = radius * 2;

        // Play the particles
        for (int i = 0; i < amount; i++) {
            temp.setX(loc.getX() + random.nextDouble() * twoRadius - radius);
            temp.setY(loc.getY() + random.nextDouble() * radius);
            temp.setZ(loc.getZ() + random.nextDouble() * twoRadius - radius);

            playSingleParticle(temp, particle, dustOptions);
        }
    }

    public static void drawLine(Location start, Particle particle, Particle.DustOptions dustOptions, double length, double gap) {
        Vector dir = start.getDirection().normalize();

        for (double i = 0; i < length; i += gap) {
            Vector multiply = dir.clone().multiply(i);// multiply
            Location add = start.clone().add(multiply);// add
            // display particle at 'start' (display)
            ParticleUtil.playSingleParticle(add, particle, dustOptions);
        }
    }

    public static void drawLineBetween(Location start, Particle particle, Particle.DustOptions dustOptions, Location end, double gap) {
        Vector vector = end.clone().subtract(start.add(0, 0.5, 0)).add(0, 0.5, 0).toVector();

        double length = vector.length();
        Vector dir = vector.normalize();

        for (double y = 0; y < length; y += gap) {
            Vector multiply = dir.clone().multiply(y);// multiply
            Location add = start.clone().add(multiply);// add
            // display particle at 'start' (display)
            ParticleUtil.playSingleParticle(add, particle, dustOptions);
        }
    }

    public static void drawCylinder(Location center, Particle particle, double radius, int amount, Particle.DustOptions dustOptions, double height) {
        for (int i = 0; i < amount; i++) {
            double u = Math.random();
            double theta = 2 * Math.PI * u;
            double dx = radius * Math.cos(theta);
            double dy = 0;
            if (height > 0) {
                double v = Math.random();
                dy = height * v;
            }
            double dz = radius * Math.sin(theta);
            //double dy = radius * Math.sin(theta);
            Location add = center.clone().add(dx, dy, dz);
            ParticleUtil.playSingleParticle(add, particle, dustOptions);
        }
    }

    public static void drawSphere(Location center, Particle particle, double radius, int amount, Particle.DustOptions dustOptions) {
        for (int i = 0; i < amount; i++) {
            double u = Math.random();
            double v = Math.random();
            double theta = 2 * Math.PI * u;
            double phi = Math.acos(2 * v - 1);
            double dx = radius * Math.sin(phi) * Math.cos(theta);
            double dy = radius * Math.sin(phi) * Math.sin(theta);
            double dz = radius * Math.cos(phi);
            Location add = center.clone().add(dx, dy, dz);
            ParticleUtil.playSingleParticle(add, particle, dustOptions);
        }
    }
}
