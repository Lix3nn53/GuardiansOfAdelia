package io.github.lix3nn53.guardiansofadelia.utilities.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class RotationHelper {

    public static Location rotation(Location point, double[][] rotationMatrix) {
        double[][] locationMatrix = MatrixHelper.locationToMatrix(point);

        double[][] result = MatrixHelper.multiplyMatrices(rotationMatrix, locationMatrix);

        return MatrixHelper.matrixToLocation(point.getWorld(), result);
    }

    public static void rotateAroundAxisX(Vector v, double cos, double sin) {
        double y = v.getY() * cos - v.getZ() * sin;
        double z = v.getY() * sin + v.getZ() * cos;
        v.setY(y).setZ(z);
    }

    public static void rotateAroundAxisY(Vector v, double cos, double sin) {
        double x = v.getX() * cos + v.getZ() * sin;
        double z = v.getX() * -sin + v.getZ() * cos;
        v.setX(x).setZ(z);
    }

    public static void rotateAroundAxisZ(Vector v, double cos, double sin) {
        double x = v.getX() * cos - v.getY() * sin;
        double y = v.getX() * sin + v.getY() * cos;
        v.setX(x).setY(y);
    }

    /**
     * @param vectors
     * @param yaw
     * @param pitch
     */
    public static void rotateYawPitch(Vector[] vectors, float yaw, float pitch) {
        // the numbers are the angles on which you want to rotate your animation.
        double xangle = Math.toRadians(pitch); // note that here we do have to convert to radians.
        double xAxisCos = Math.cos(xangle); // getting the cos value for the pitch.
        double xAxisSin = Math.sin(xangle); // getting the sin value for the pitch.

        // DON'T FORGET THE ' - ' IN FRONT OF 'yangle' HERE.
        //double yangle = Math.toRadians(60); // note that here we do have to convert to radians.
        double yangle = Math.toRadians(yaw);
        double yAxisCos = Math.cos(-yangle); // getting the cos value for the yaw.
        double yAxisSin = Math.sin(-yangle); // getting the sin value for the yaw.

        /*double zangle = Math.toRadians(30); // note that here we do have to convert to radians.
        double zAxisCos = Math.cos(zangle); // getting the cos value for the roll.
        double zAxisSin = Math.sin(zangle); // getting the sin value for the roll.*/

        for (Vector vector : vectors) {
            RotationHelper.rotateAroundAxisX(vector, xAxisCos, xAxisSin);
            RotationHelper.rotateAroundAxisY(vector, yAxisCos, yAxisSin);
        }
    }

    /**
     * @param vector
     * @param yaw
     * @param pitch
     */
    public static void rotateYawPitch(Vector vector, float yaw, float pitch) {
        if (yaw <= 0 && pitch <= 0) return;
        // the numbers are the angles on which you want to rotate your animation.
        double xangle = Math.toRadians(pitch); // note that here we do have to convert to radians.
        double xAxisCos = Math.cos(xangle); // getting the cos value for the pitch.
        double xAxisSin = Math.sin(xangle); // getting the sin value for the pitch.

        // DON'T FORGET THE ' - ' IN FRONT OF 'yangle' HERE.
        //double yangle = Math.toRadians(60); // note that here we do have to convert to radians.
        double yangle = Math.toRadians(yaw);
        double yAxisCos = Math.cos(-yangle); // getting the cos value for the yaw.
        double yAxisSin = Math.sin(-yangle); // getting the sin value for the yaw.

        /*double zangle = Math.toRadians(30); // note that here we do have to convert to radians.
        double zAxisCos = Math.cos(zangle); // getting the cos value for the roll.
        double zAxisSin = Math.sin(zangle); // getting the sin value for the roll.*/

        RotationHelper.rotateAroundAxisX(vector, xAxisCos, xAxisSin);
        RotationHelper.rotateAroundAxisY(vector, yAxisCos, yAxisSin);
    }
}
