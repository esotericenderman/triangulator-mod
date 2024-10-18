package dev.enderman.minecraft.mods.triangulator.utility;

import org.joml.Vector2d;

public class VectorUtil {
    public static boolean isZeroVector(Vector2d vector) {
        return vector.x == 0 && vector.y == 0;
    }

    public static boolean areLinearlyDependent(Vector2d vectorA, Vector2d vectorB) {
        double x1 = vectorA.x;
        double y1 = vectorA.y;

        double x2 = vectorB.x;
        double y2 = vectorB.y;

        if (x1 == 0 && y1 == 0) {
            return x2 == 0 && y2 == 0;
        }

        double quotient1 = x2/x1;
        double quotient2 = y2/y1;

        return quotient1 == quotient2 || (x1 == 0 && x2 == 0) || (y1 == 0 && y2 == 0);
    }

    public static Vector2d cloneVector(Vector2d originalVector) {
        return new Vector2d(originalVector.x, originalVector.y);
    }
}
