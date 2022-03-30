package io.github.lix3nn53.guardiansofadelia.utilities.particle;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.math.MatrixHelper;
import io.github.lix3nn53.guardiansofadelia.utilities.math.RotationHelper;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.ArrangementSingle;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class ParticleShapes {

    /**
     * Plays a particle at the given location based on the string
     *
     * @param loc      location to playSingleParticle the effect
     * @param particle particle to playSingleParticle
     */
    public static void playSingleParticle(Location loc, Particle particle, float offsetX, float offsetY, float offsetZ, float extra, Particle.DustOptions dustOptions, boolean force) {
        loc.getWorld().spawnParticle(particle, loc, 1, offsetX, offsetY, offsetZ, extra, dustOptions, force);
    }

    public static void playSingleParticle(Location loc, Particle particle, Particle.DustOptions dustOptions) {
        loc.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0, 0, dustOptions);
    }

    public static void playSingleParticleWithHeight(Location loc, Particle particle, Particle.DustOptions dustOptions, float height, float gap) {
        float heightTemp = 0;
        World world = loc.getWorld();
        Location clone = loc.clone();
        for (int i = 0; i < 20; i++) {
            world.spawnParticle(particle, clone, 1, 0, 0, 0, 0, dustOptions);
            heightTemp += gap;
            if (heightTemp > height) break;
            clone.setY(clone.getY() + gap);
        }
    }

    public static void playSingleParticle(World world, Vector vector, Particle particle, Particle.DustOptions dustOptions) {
        world.spawnParticle(particle, vector.getX(), vector.getY(), vector.getZ(), 1, 0, 0, 0, 0, dustOptions);
    }

    public static void playSingleParticleWithHeight(World world, Vector vector, Particle particle, Particle.DustOptions dustOptions, float height, float gap) {
        float heightTemp = 0;
        for (int i = 0; i < 20; i++) {
            world.spawnParticle(particle, vector.getX(), vector.getY(), vector.getZ(), 1, 0, 0, 0, 0, dustOptions);
            heightTemp += gap;
            if (heightTemp > height) break;
            vector.setY(vector.getY() + gap);
        }
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
            float radius,
            int amount,
            Particle.DustOptions dustOptions,
            Direction direction) {
        Location temp = loc.clone();
        float rSquared = radius * radius;
        float twoRadius = radius * 2;
        int index = 0;

        // Play the particles
        while (index < amount) {
            if (direction == Direction.XY || direction == Direction.XZ) {
                temp.setX(loc.getX() + GuardiansOfAdelia.RANDOM.nextFloat() * twoRadius - radius);
            }
            if (direction == Direction.XY || direction == Direction.YZ) {
                temp.setY(loc.getY() + GuardiansOfAdelia.RANDOM.nextFloat() * twoRadius - radius);
            }
            if (direction == Direction.XZ || direction == Direction.YZ) {
                temp.setZ(loc.getZ() + GuardiansOfAdelia.RANDOM.nextFloat() * twoRadius - radius);
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
     * @param location location to center the effect around
     * @param particle the string value for the particle
     * @param radius   radius of the sphere
     * @param amount   amount of particles to use
     */
    public static void fillSphere(Location location, Particle particle, float radius, int amount, int amounty, Particle.DustOptions dustOptions,
                                  boolean rotate, float yaw, float pitch, Vector offset) {
        Location center = rotate ? new Location(location.getWorld(), 0, 0, 0) : location;

        Vector centerVector = center.toVector().add(offset);

        float fullRadian = (float) Math.toRadians(360);

        Vector[] points = new Vector[amount * amounty];
        int index = 0;
        for (float i = 0; i < amount; i++) {
            for (float y = 0; y < amounty; y++) {
                float percent = i / amount;
                float percenty = y / amounty;

                float v = 1 * percenty;
                float theta = fullRadian * percent;
                float phi = (float) Math.acos(2 * v - 1);

                float v1 = GuardiansOfAdelia.RANDOM.nextFloat();
                phi = phi * v1;

                float dx = (float) (radius * Math.sin(phi) * Math.cos(theta));
                float dy = (float) (radius * Math.cos(phi));
                float dz = (float) (radius * Math.sin(phi) * Math.sin(theta));
                points[index] = centerVector.clone().add(new Vector(dx, dy, dz));
                index++;
            }
        }

        if (rotate) {
            RotationHelper.rotateYawPitch(points, yaw, pitch);

            Vector translateVector = location.toVector();

            for (Vector point : points) {
                MatrixHelper.translate(point, translateVector);
            }
        }

        World world = center.getWorld();

        for (Vector point : points) {
            ParticleShapes.playSingleParticle(world, point, particle, dustOptions);
        }
    }

    public static void fillCone(Location location, Particle particle, float length, int amount, int amounty, float angle, Particle.DustOptions dustOptions,
                                boolean rotate, float yaw, float pitch, Vector offset) {
        float fullRadian = (float) Math.toRadians(360);
        float phi = (float) Math.toRadians(angle);

        Location center = rotate ? new Location(location.getWorld(), 0, 0, 0) : location;

        Vector centerVector = center.toVector().add(offset);

        Vector[] points = new Vector[amount * amounty];
        int index = 0;
        for (float i = 0; i < amount; i++) {
            for (float y = 0; y < amounty; y++) {
                float percent = i / amount;
                float percenty = y / amounty;

                float v = GuardiansOfAdelia.RANDOM.nextFloat();
                float randPhi = phi * v;

                float lengthCurrent = length * percenty;
                float theta = fullRadian * percent;
                float dx = lengthCurrent * (float) Math.sin(randPhi) * (float) Math.cos(theta);
                float dy = lengthCurrent * (float) Math.cos(randPhi);
                float dz = lengthCurrent * (float) Math.sin(randPhi) * (float) Math.sin(theta);
                points[index] = centerVector.clone().add(new Vector(dx, dy, dz));
                index++;
            }
        }

        if (rotate) {
            RotationHelper.rotateYawPitch(points, yaw, pitch);

            Vector translateVector = location.toVector();

            for (Vector point : points) {
                MatrixHelper.translate(point, translateVector);
            }
        }

        World world = center.getWorld();

        for (Vector point : points) {
            ParticleShapes.playSingleParticle(world, point, particle, dustOptions);
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
    public static void fillHemisphere(Location loc, Particle particle, float radius, int amount, Particle.DustOptions dustOptions) {
        Location temp = loc.clone();
        float twoRadius = radius * 2;

        // Play the particles
        for (int i = 0; i < amount; i++) {
            temp.setX(loc.getX() + GuardiansOfAdelia.RANDOM.nextFloat() * twoRadius - radius);
            temp.setY(loc.getY() + GuardiansOfAdelia.RANDOM.nextFloat() * radius);
            temp.setZ(loc.getZ() + GuardiansOfAdelia.RANDOM.nextFloat() * twoRadius - radius);

            playSingleParticle(temp, particle, dustOptions);
        }
    }

    public static void drawLine(Location start, Particle particle, Particle.DustOptions dustOptions, float length, float gap) {
        Vector dir = start.getDirection().normalize();

        for (float i = 0; i < length; i += gap) {
            Vector multiply = dir.clone().multiply(i);// multiply
            Location add = start.clone().add(multiply);// add
            // display particle at 'start' (display)
            ParticleShapes.playSingleParticle(add, particle, dustOptions);
        }
    }

    public static void drawLineBetween(World world, Vector start, Particle particle, Particle.DustOptions dustOptions, Vector end, float gap) {
        if (gap < 0.1) {
            GuardiansOfAdelia.getInstance().getLogger().info("ERROR: drawLineBetween gap is 0!!!!!");
            return;
        }
        Vector vector = end.clone().subtract(start);

        float length = (float) vector.length();
        Vector dir = vector.normalize();

        for (float y = 0; y < length; y += gap) {
            Vector multiply = dir.clone().multiply(y);// multiply
            Vector add = start.clone().add(multiply);// add
            // display particle at 'start' (display)
            ParticleShapes.playSingleParticle(world, add, particle, dustOptions);
        }
    }

    public static void drawLineBetween(World world, Vector start, ArrangementSingle arrangementSingle, Vector end, float gap) {
        if (gap < 0.1) {
            GuardiansOfAdelia.getInstance().getLogger().info("ERROR: drawLineBetween gap is 0!!!!!");
            return;
        }
        Vector vector = end.clone().subtract(start);

        float length = (float) vector.length();
        Vector dir = vector.normalize();

        for (float y = 0; y < length; y += gap) {
            Vector multiply = dir.clone().multiply(y);// multiply
            Vector add = start.clone().add(multiply);// add
            // display particle at 'start' (display)
            Location loc = new Location(world, add.getX(), add.getY(), add.getZ());
            arrangementSingle.play(loc, new Vector());
        }
    }

    public static void drawCylinder(Location location, ArrangementSingle arrangementSingle, float radius, int amount, float height,
                                    boolean rotate, float yaw, float pitch, Vector offset) {
        float fullRadian = (float) Math.toRadians(360);

        Location center = rotate ? new Location(location.getWorld(), 0, 0, 0) : location;

        Vector centerVector = center.toVector().add(offset);

        Vector[] points = new Vector[amount];
        int index = 0;
        for (float i = 0; i < amount; i++) {
            float percent = i / amount;
            float theta = fullRadian * percent;
            float dx = radius * (float) Math.cos(theta);
            float dy = 0;
            if (height > 0) {
                float v = (float) Math.random();
                dy = height * v;
            }
            float dz = radius * (float) Math.sin(theta);
            //float dy = radius * Math.sin(theta);
            points[index] = centerVector.clone().add(new Vector(dx, dy, dz));
            index++;
        }

        if (rotate) {
            RotationHelper.rotateYawPitch(points, yaw, pitch);

            Vector translateVector = location.toVector();

            for (Vector point : points) {
                MatrixHelper.translate(point, translateVector);
            }
        }

        World world = center.getWorld();

        for (Vector point : points) {
            Location loc = new Location(world, point.getX(), point.getY(), point.getZ());
            arrangementSingle.play(loc, new Vector());
        }
    }

    public static void drawTriangle(Location location, Particle particle, float radius, int amount, Particle.DustOptions dustOptions, float height,
                                    boolean rotate, float yaw, float pitch, Vector offset, float angle) {
        float fullRadian = (float) Math.toRadians(angle);

        Location center = rotate ? new Location(location.getWorld(), 0, 0, 0) : location;

        Vector centerVector = center.toVector().add(offset);

        Vector[] points = new Vector[amount];
        int index = 0;
        for (float i = 0; i < amount; i++) {
            float percent = i / amount;
            float theta = fullRadian * percent;
            float dx = radius * (float) Math.cos(theta);
            float dy = 0;
            if (height > 0) {
                float v = (float) Math.random();
                dy = height * v;
            }
            float dz = radius * (float) Math.sin(theta);
            //float dy = radius * Math.sin(theta);
            points[index] = centerVector.clone().add(new Vector(dx, dy, dz));
            index++;
        }

        if (rotate) {
            RotationHelper.rotateYawPitch(points, yaw, pitch);

            Vector translateVector = location.toVector();

            for (Vector point : points) {
                MatrixHelper.translate(point, translateVector);
            }
        }

        World world = center.getWorld();

        for (Vector point : points) {
            ParticleShapes.playSingleParticle(world, point, particle, dustOptions);
        }
    }

    public static void drawCone(Location location, ArrangementSingle arrangementSingle, float length, int amount, int amounty, float angle,
                                boolean rotate, float yaw, float pitch, Vector offset) {
        float fullRadian = (float) Math.toRadians(360);
        float phi = (float) Math.toRadians(angle);
        float sinPhi = (float) Math.sin(phi);
        float cosPhi = (float) Math.cos(phi);

        Location center = rotate ? new Location(location.getWorld(), 0, 0, 0) : location;

        Vector centerVector = center.toVector().add(offset);

        Vector[] points = new Vector[amount * amounty];
        int index = 0;
        for (float i = 0; i < amount; i++) {
            for (float y = 0; y < amounty; y++) {
                float percent = i / amount;
                float percenty = y / amounty;

                float lengthCurrent = length * percenty;
                float theta = fullRadian * percent;
                float dx = lengthCurrent * sinPhi * (float) Math.cos(theta);
                float dy = lengthCurrent * cosPhi;
                float dz = lengthCurrent * sinPhi * (float) Math.sin(theta);
                points[index] = centerVector.clone().add(new Vector(dx, dy, dz));
                index++;
            }
        }

        if (rotate) {
            RotationHelper.rotateYawPitch(points, yaw, pitch);

            Vector translateVector = location.toVector();

            for (Vector point : points) {
                MatrixHelper.translate(point, translateVector);
            }
        }

        World world = center.getWorld();

        for (Vector point : points) {
            Location loc = new Location(world, point.getX(), point.getY(), point.getZ());
            arrangementSingle.play(loc, new Vector());
        }
    }

    public static void drawSphere(Location location, Particle particle, float radius, int amount, int amounty, Particle.DustOptions dustOptions,
                                  boolean rotate, float yaw, float pitch, Vector offset) {
        Location center = rotate ? new Location(location.getWorld(), 0, 0, 0) : location;

        Vector centerVector = center.toVector().add(offset);

        float fullRadian = (float) Math.toRadians(360);

        Vector[] points = new Vector[amount * amounty];
        int index = 0;
        for (float i = 0; i < amount; i++) {
            for (float y = 0; y < amounty; y++) {
                float percent = i / amount;
                float percenty = y / amounty;

                float v = 1 * percenty;
                float theta = fullRadian * percent;
                float phi = (float) Math.acos(2 * v - 1);
                float dx = radius * (float) Math.sin(phi) * (float) Math.cos(theta);
                float dy = radius * (float) Math.cos(phi);
                float dz = radius * (float) Math.sin(phi) * (float) Math.sin(theta);
                points[index] = centerVector.clone().add(new Vector(dx, dy, dz));
                index++;
            }
        }

        if (rotate) {
            RotationHelper.rotateYawPitch(points, yaw, pitch);

            Vector translateVector = location.toVector();

            for (Vector point : points) {
                MatrixHelper.translate(point, translateVector);
            }
        }

        World world = center.getWorld();

        for (Vector point : points) {
            ParticleShapes.playSingleParticle(world, point, particle, dustOptions);
        }
    }

    public static void drawHemiSphere(Location center, Particle particle, float radius, int amount, int amounty, Particle.DustOptions dustOptions) {
        float fullRadian = (float) Math.toRadians(360);

        for (float i = 0; i < amount; i++) {
            for (float y = 0; y < amounty; y++) {
                float percent = i / amount;
                float percenty = y / amounty;

                float v = 1 * percenty;
                float theta = fullRadian * percent;
                float phi = (float) Math.acos(2 * v - 1);
                float dx = radius * (float) Math.sin(phi) * (float) Math.cos(theta);
                float dy = radius / 2 * (float) Math.cos(phi);
                float dz = radius * (float) Math.sin(phi) * (float) Math.sin(theta);
                Location add = center.clone().add(dx, dy, dz);
                ParticleShapes.playSingleParticle(add, particle, dustOptions);
            }
        }
    }

    public static Vector[] drawCube(Location location, ArrangementSingle arrangementSingle, Vector vector, float gap,
                                    boolean rotate, float yaw, float pitch) {
        Vector[] points = calculateCubeCorners(location, vector, rotate, yaw, pitch);

        World world = location.getWorld();

        // Edges
        drawCubeEdges(world, points, arrangementSingle, gap);

        return points;
    }

    public static Vector[] calculateCubeCorners(Location location, Vector vector, boolean rotate, float yaw, float pitch) {
        Vector[] points = new Vector[8];

        Location center = rotate ? new Location(location.getWorld(), 0, 0, 0) : location;

        Vector centerVector = center.toVector();

        float length_x = (float) vector.getX() / 2f;
        float length_y = (float) vector.getY() / 2f;
        float length_z = (float) vector.getZ() / 2f;
        GuardiansOfAdelia.getInstance().getLogger().info("CUBE DRAW: " + vector.getX() + ", " + vector.getY() + ", " + vector.getZ());
        points[0] = centerVector.clone().add(new Vector(-length_x, -length_y, -length_z));
        points[1] = centerVector.clone().add(new Vector(length_x, -length_y, -length_z));
        points[2] = centerVector.clone().add(new Vector(length_x, length_y, -length_z));
        points[3] = centerVector.clone().add(new Vector(-length_x, length_y, -length_z));
        points[4] = centerVector.clone().add(new Vector(-length_x, -length_y, length_z));
        points[5] = centerVector.clone().add(new Vector(length_x, -length_y, length_z));
        points[6] = centerVector.clone().add(new Vector(length_x, length_y, length_z));
        points[7] = centerVector.clone().add(new Vector(-length_x, length_y, length_z));

        if (rotate) {
            RotationHelper.rotateYawPitch(points, yaw, pitch);

            Vector translateVector = location.toVector();

            for (int i = 0; i < 8; i++) {
                Vector point = points[i];
                MatrixHelper.translate(point, translateVector);
            }
        }

        return points;
    }

    public static void drawCubeEdges(World world, Vector[] points, ArrangementSingle arrangementSingle, float gap) {
        for (int i = 0; i < 4; i++) {
            ParticleShapes.drawLineBetween(world, points[i], arrangementSingle, points[(i + 1) % 4], gap);
            ParticleShapes.drawLineBetween(world, points[i + 4], arrangementSingle, points[((i + 1) % 4) + 4], gap);
            ParticleShapes.drawLineBetween(world, points[i], arrangementSingle, points[i + 4], gap);
        }
    }
}
