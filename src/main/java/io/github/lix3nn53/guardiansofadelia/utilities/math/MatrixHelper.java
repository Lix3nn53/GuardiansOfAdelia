package io.github.lix3nn53.guardiansofadelia.utilities.math;

import org.bukkit.Location;
import org.bukkit.World;

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
}
