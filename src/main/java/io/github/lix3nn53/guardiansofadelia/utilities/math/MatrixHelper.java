package io.github.lix3nn53.guardiansofadelia.utilities.math;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class MatrixHelper {

    public static float[][] rotationZ(float angle) {
        return new float[][]{
                new float[]{(float) Math.cos(angle), (float) -Math.sin(angle), 0f},
                new float[]{(float) Math.sin(angle), (float) Math.cos(angle), 0f},
                new float[]{0f, 0f, 1f},
        };
    }

    public static float[][] rotationX(float angle) {
        return new float[][]{
                new float[]{1f, 0f, 0f},
                new float[]{0f, (float) Math.cos(angle), (float) -Math.sin(angle)},
                new float[]{0f, (float) Math.sin(angle), (float) Math.cos(angle)},
        };
    }

    public static float[][] rotationY(float angle) {
        return new float[][]{
                new float[]{(float) Math.cos(angle), 0f, (float) Math.sin(angle)},
                new float[]{0f, 1f, 0f},
                new float[]{(float) -Math.sin(angle), 0f, (float) Math.cos(angle)},
        };
    }

    public static float[][] multiplyMatrices(float[][] firstMatrix, float[][] secondMatrix) {
        float[][] result = new float[firstMatrix.length][secondMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }

        return result;
    }

    public static float multiplyMatricesCell(float[][] firstMatrix, float[][] secondMatrix, int row, int col) {
        float cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

    public static float[][] locationToMatrix(Location location) {
        float x = (float) location.getX();
        float y = (float) location.getY();
        float z = (float) location.getZ();

        return new float[][]{new float[]{x}, new float[]{y}, new float[]{z}};
    }

    public static Location matrixToLocation(World worlf, float[][] locationMatrix) {
        float x = locationMatrix[0][0];
        float y = locationMatrix[1][0];
        float z = locationMatrix[2][0];

        return new Location(worlf, x, y, z);
    }

    public static float[][] translationMatrix(float x, float y, float z) {
        return new float[][]{
                new float[]{1f, 0f, 0f, x},
                new float[]{0f, 1f, 0f, y},
                new float[]{0f, 0f, 1f, z},
                new float[]{0f, 0f, 0f, 1},
        };
    }

    /**
     * @param x
     * @param y
     * @param z
     * @param vector translate by vector
     * @return
     */
    public static float[][] translate(float x, float y, float z, Vector vector) {
        float vx = (float) vector.getX();
        float vy = (float) vector.getY();
        float vz = (float) vector.getZ();

        float[][] translationMatrix = translationMatrix(vx, vy, vz);

        float[][] floats = {new float[]{x}, new float[]{y}, new float[]{z}, new float[]{1}};

        float[][] multiply = multiplyMatrices(translationMatrix, floats);

        return new float[][]{new float[]{multiply[0][0]}, new float[]{multiply[1][0]}, new float[]{multiply[2][0]}};
    }

    /**
     * @param location location to translate
     * @param vector   translate by vector
     * @return result of translate
     */
    public static Location translate(Location location, Vector vector) {
        float x = (float) location.getX();
        float y = (float) location.getY();
        float z = (float) location.getZ();

        float[][] translate = translate(x, y, z, vector);

        return new Location(location.getWorld(), translate[0][0], translate[1][0], translate[2][0]);
    }

    /**
     * @param toTranslate     vector to translate
     * @param translateVector translate toTranslate by this vector
     */
    public static void translate(Vector toTranslate, Vector translateVector) {
        float x = (float) toTranslate.getX();
        float y = (float) toTranslate.getY();
        float z = (float) toTranslate.getZ();

        float[][] translate = translate(x, y, z, translateVector);

        toTranslate.setX(translate[0][0]);
        toTranslate.setY(translate[1][0]);
        toTranslate.setZ(translate[2][0]);
    }
}
