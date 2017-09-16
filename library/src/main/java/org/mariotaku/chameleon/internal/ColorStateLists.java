package org.mariotaku.chameleon.internal;

import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v4.graphics.ColorUtils;

import org.mariotaku.chameleon.Chameleon;

/**
 * Created by mariotaku on 2017/9/16.
 */

public class ColorStateLists {

    static final int[][] TINT_DEFAULT_STATES = {
            {-android.R.attr.state_enabled},
            {android.R.attr.state_focused},
            {android.R.attr.state_pressed},
            {android.R.attr.state_activated},
            {android.R.attr.state_selected},
            {android.R.attr.state_checked},
            {0}
    };

    @NonNull
    public static ColorStateList tintDefault(@NonNull Chameleon.Theme theme) {
        final int[] colors = {
                ColorUtils.setAlphaComponent(theme.getColorControlNormal(), (int) (0.3f * 255)),
                theme.getColorControlActivated(),
                theme.getColorControlActivated(),
                theme.getColorControlActivated(),
                theme.getColorControlActivated(),
                theme.getColorControlActivated(),
                theme.getColorControlNormal()
        };
        return new ColorStateList(TINT_DEFAULT_STATES, colors);
    }

}
