package com.example.tim.settingsun;

import android.graphics.Color;

/**
 * This class contains static methods that can be used to get mode information on a block given its type.
 * @authors Ward Theunisse, Tim van Dijk, Martijn Heitkönig, Luuk van Bitterswijk
 */
public class BlockInfo {

    /**
     * Fetches the desired block size by type. The type can be a 1x1, 1x2, 2x1,or 2x2 block.
     * @param type the type of the block whose dimensions we want
     */
    public static Tuple<Integer> getDimensions (int type) {
        switch (type) {
            case 1:
                return new Tuple<>(1 ,1);
            case 2:
                return new Tuple<>(1, 2);
            case 3:
                return new Tuple<>(2, 1);
            case 4:
                return new Tuple<>(2, 2);
            default:
                return null;
        }
    }

    /**
     * Calculates the center coordinates of the block, depending in block type
     * @param type the type of the block whose center coordinates we want
     */
    public static Tuple<Float> getCenter (int type) {
        return new Tuple<>(
                new Float(0.5 * (float) getDimensions(type).x),
                new Float(0.5 * (float) getDimensions(type).y)
        );
    }

    /**
     * Assigns different colors to different sized blocks
     * @param type the type of the block whose color we want
     */
    public static int getColor (int type) {

        switch (type) {
            case 1:
                return Color.YELLOW;
            case 2:
                return Color.rgb(28,96,255);
            case 3:
                return Color.rgb(28,96,255);
            case 4:
                return Color.rgb(255,66,88);
            default:
                return 0;
        }
    }
}