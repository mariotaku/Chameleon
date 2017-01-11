package org.mariotaku.chameleon.internal;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.StyleableRes;

/**
 * Created by mariotaku on 2017/1/11.
 */

public class InternalUtils {

    @ColorInt
    public static int getColor(TypedArray a, @StyleableRes int index, @ColorInt int defValue) {
        try {
            return a.getColor(index, defValue);
        } catch (Resources.NotFoundException e) {
            // Fuck Shitsung
            return defValue;
        }
    }

}
