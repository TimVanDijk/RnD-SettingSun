package com.example.tim.settingsun;

import android.graphics.Color;

/**
 * This class contains static methods that can be used to get mode information on a block given its type.
 * @authors Ward Theunisse, Tim van Dijk, Martijn Heitkönig en Luuk van Bitterswijk
 */
public class BlockInfo {

    public static Tuple<Integer> getDimensions (int type) {
        /**
         * Fetches the desired block size by type. The type can be a 1x1, 1x2, 2x1,or 2x2 block.
         */
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

    public static Tuple<Float> getCenter (int type) {
        /**
         * Calculates the center coordinates of the block, depending in block type
         */
        return new Tuple<>(
                new Float(0.5 * (float) getDimensions(type).x),
                new Float(0.5 * (float) getDimensions(type).y)
        );
    }

    public static int getColor (int type) {
        /**
         * Assigns different colors to different sized blocks
         */
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