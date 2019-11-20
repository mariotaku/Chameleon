package org.mariotaku.chameleon;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

public interface ChameleonView {

    boolean isPostApplyTheme();

    @Nullable
    Appearance createAppearance(@NonNull Context context, @NonNull AttributeSet attributeSet, @NonNull Chameleon.Theme theme);

    void applyAppearance(@NonNull Appearance appearance);

    interface Appearance {

    }

    interface StatusBarThemeable {
        boolean isStatusBarColorHandled();
    }
}
