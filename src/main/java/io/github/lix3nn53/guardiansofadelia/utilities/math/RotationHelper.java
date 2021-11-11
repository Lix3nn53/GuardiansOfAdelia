package io.github.lix3nn53.guardiansofadelia.utilities.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class RotationHelper {

    public static Location rotation(Location point, float[][] rotationMatrix) {
        float[][] locationMatrix = MatrixHelper.locationToMatrix(point);

        float[][] result = MatrixHelper.multiplyMatrices(rotationMatrix, locationMatrix);

        return MatrixHelper.matrixToLocation(point.getWorld(), result);
    }

    public static void rotateAroundAxisX(Vector v, float cos, float sin) {
        float y = (float) (v.getY() * cos - v.getZ() * sin);
        float z = (float) (v.getY() * sin + v.getZ() * cos);
        v.setY(y).setZ(z);
    }

    public static void rotateAroundAxisY(Vector v, float cos, float sin) {
        float x = (float) (v.getX() * cos + v.getZ() * sin);
        float z = (float) (v.getX() * -sin + v.getZ() * cos);
        v.setX(x).setZ(z);
    }

    public static void rotateAroundAxisZ(Vector v, float cos, float sin) {
        float x = (float) (v.getX() * cos - v.getY() * sin);
        float y = (float) (v.getX() * sin + v.getY() * cos);
        v.setX(x).setY(y);
    }

    /**
     * @param vectors
     * @param yaw
     * @param pitch
     */
    public static void rotateYawPitch(Vector[] vectors, float yaw, float pitch) {
        // the numbers are the angles on which you want to rotate your animation.
        float xangle = (float) Math.toRadians(pitch); // note that here we do have to convert to radians.
        float xAxisCos = (float) Math.cos(xangle); // getting the cos value for the pitch.
        float xAxisSin = (float) Math.sin(xangle); // getting the sin value for the pitch.

        // DON'T FORGET THE ' - ' IN FRONT OF 'yangle' HERE.
        //float yangle = Math.toRadians(60); // note that here we do have to convert to radians.
        float yangle = (float) Math.toRadians(yaw);
        float yAxisCos = (float) Math.cos(-yangle); // getting the cos value for the yaw.
        float yAxisSin = (float) Math.sin(-yangle); // getting the sin value for the yaw.

        /*float zangle = Math.toRadians(30); // note that here we do have to convert to radians.
        float zAxisCos = Math.cos(zangle); // getting the cos value for the roll.
        float zAxisSin = Math.sin(zangle); // getting the sin value for the roll.*/

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
        float xangle = (float) Math.toRadians(pitch); // note that here we do have to convert to radians.
        float xAxisCos = (float) Math.cos(xangle); // getting the cos value for the pitch.
        float xAxisSin = (float) Math.sin(xangle); // getting the sin value for the pitch.

        // DON'T FORGET THE ' - ' IN FRONT OF 'yangle' HERE.
        //float yangle = Math.toRadians(60); // note that here we do have to convert to radians.
        float yangle = (float) Math.toRadians(yaw);
        float yAxisCos = (float) Math.cos(-yangle); // getting the cos value for the yaw.
        float yAxisSin = (float) Math.sin(-yangle); // getting the sin value for the yaw.

        /*float zangle = Math.toRadians(30); // note that here we do have to convert to radians.
        float zAxisCos = Math.cos(zangle); // getting the cos value for the roll.
        float zAxisSin = Math.sin(zangle); // getting the sin value for the roll.*/

        RotationHelper.rotateAroundAxisX(vector, xAxisCos, xAxisSin);
        RotationHelper.rotateAroundAxisY(vector, yAxisCos, yAxisSin);
    }
}
