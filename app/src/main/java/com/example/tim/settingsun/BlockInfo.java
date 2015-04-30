package com.example.tim.settingsun;

import android.graphics.Color;

public class BlockInfo {

    public static Tuple<Integer> getDimensions(int type) {
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

    public static Tuple<Float> getCenter(int type){
        return new Tuple<>(
                new Float(0.5 * (float) getDimensions(type).x),
                new Float(0.5 * (float) getDimensions(type).y)
        );
    }

    public static int getColor(int type){
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