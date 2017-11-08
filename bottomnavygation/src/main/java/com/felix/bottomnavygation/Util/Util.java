package com.felix.bottomnavygation.Util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by user on 08/11/2017.
 */

public class Util {

    public static int VALUE_SIZE = 28;
    public static int VALUE_SIZE_ACTIVE = 33;

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     */
    public static int convertDpToPixel(int dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return Math.round(px);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     */
    public static int convertPixelsToDp(int px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return Math.round(dp);
    }
}
