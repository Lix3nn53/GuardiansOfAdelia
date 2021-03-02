package io.github.lix3nn53.guardiansofadelia.utilities.math;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class MatrixHelper {

    public static double[][] rotationZ(double angle) {
        return new double[][]{
                new double[]{Math.cos(angle), -Math.sin(angle), 0d},
                new double[]{Math.sin(angle), Math.cos(angle), 0d},
                new double[]{0d, 0d, 1d},
        };
    }

    public static double[][] rotationX(double angle) {
        return new double[][]{
                new double[]{1d, 0d, 0d},
                new double[]{0d, Math.cos(angle), -Math.sin(angle)},
                new double[]{0d, Math.sin(angle), Math.cos(angle)},
        };
    }

    public static double[][] rotationY(double angle) {
        return new double[][]{
                new double[]{Math.cos(angle), 0d, Math.sin(angle)},
                new double[]{0d, 1d, 0d},
                new double[]{-Math.sin(angle), 0d, Math.cos(angle)},
        };
    }

    public static double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }

        return result;
    }

    public static double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

    public static double[][] locationToMatrix(Location location) {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        return new double[][]{new double[]{x}, new double[]{y}, new double[]{z}};
    }

    public static Location matrixToLocation(World world, double[][] locationMatrix) {
        double x = locationMatrix[0][0];
        double y = locationMatrix[1][0];
        double z = locationMatrix[2][0];

        return new Location(world, x, y, z);
    }

    public static double[][] translationMatrix(double x, double y, double z) {
        return new double[][]{
                new double[]{1d, 0d, 0d, x},
                new double[]{0d, 1d, 0d, y},
                new double[]{0d, 0d, 1d, z},
                new double[]{0d, 0d, 0d, 1},
        };
    }

    /**
     * @param x
     * @param y
     * @param z
     * @param vector translate by vector
     * @return
     */
    public static double[][] translate(double x, double y, double z, Vector vector) {
        double vx = vector.getX();
        double vy = vector.getY();
        double vz = vector.getZ();

        double[][] translationMatrix = translationMatrix(vx, vy, vz);

        double[][] doubles = {new double[]{x}, new double[]{y}, new double[]{z}, new double[]{1}};

        double[][] multiply = multiplyMatrices(translationMatrix, doubles);

        return new double[][]{new double[]{multiply[0][0]}, new double[]{multiply[1][0]}, new double[]{multiply[2][0]}};
    }

    /**
     * @param location location to translate
     * @param vector   translate by vector
     * @return result of translate
     */
    public static Location translate(Location location, Vector vector) {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        double[][] translate = translate(x, y, z, vector);

        return new Location(location.getWorld(), translate[0][0], translate[1][0], translate[2][0]);
    }

    /**
     * @param toTranslate     vector to translate
     * @param translateVector translate toTranslate by this vector
     */
    public static void translate(Vector toTranslate, Vector translateVector) {
        double x = toTranslate.getX();
        double y = toTranslate.getY();
        double z = toTranslate.getZ();

        double[][] translate = translate(x, y, z, translateVector);

        toTranslate.setX(translate[0][0]);
        toTranslate.setY(translate[1][0]);
        toTranslate.setZ(translate[2][0]);
    }
}
