package org.mariotaku.chameleon.internal;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.StyleableRes;

public class InternalUtils {

    @ColorInt
    public static int getColor(TypedArray a, @StyleableRes int index, @ColorInt int defValue) {
        try {
            return a.getColor(index, defValue);
        } catch (Resources.NotFoundException e) {
            // Fuck Shitsung
            return defValue;
        }catch (UnsupportedOperationException e) {
            // Fuck Shitsung again
            return defValue;
        }
    }

}
