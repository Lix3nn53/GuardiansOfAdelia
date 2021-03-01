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
}
