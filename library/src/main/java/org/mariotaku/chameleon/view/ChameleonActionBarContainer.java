package org.mariotaku.chameleon.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ActionBarContainer;
import android.util.AttributeSet;

import org.mariotaku.chameleon.Chameleon;
import org.mariotaku.chameleon.ChameleonView;
import org.mariotaku.chameleon.R;
import org.mariotaku.chameleon.internal.ChameleonTypedArray;

@SuppressLint("RestrictedApi")
public class ChameleonActionBarContainer extends ActionBarContainer implements ChameleonView {
    public ChameleonActionBarContainer(Context context) {
        super(context);
    }

    public ChameleonActionBarContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isPostApplyTheme() {
        return false;
    }

    @Nullable
    @Override
    public Appearance createAppearance(@NonNull Context context, @NonNull AttributeSet attributeSet, @NonNull Chameleon.Theme theme) {
        return Appearance.create(context, attributeSet, theme);
    }

    @Override
    public void applyAppearance(@NonNull ChameleonView.Appearance appearance) {
        Appearance a = (Appearance) appearance;
        setPrimaryBackground(a.getBackground());
    }

    public static class Appearance implements ChameleonView.Appearance {

        private Drawable background;

        public void setBackground(Drawable background) {
            this.background = background;
        }

        public Drawable getBackground() {
            return background;
        }

        @NonNull
        public static Appearance create(@NonNull Context context, @NonNull AttributeSet attributeSet, @NonNull Chameleon.Theme theme) {
            Appearance appearance = new Appearance();
            ChameleonTypedArray a = ChameleonTypedArray.obtain(context, attributeSet,
                    R.styleable.ChameleonToolbar, theme);
            final Drawable background = a.getDrawable(R.styleable.ChameleonToolbar_android_background, false);
            if (background != null) {
                appearance.setBackground(background);
            } else {
                appearance.setBackground(new ColorDrawable(theme.getColorToolbar()));
            }
            a.recycle();
            return appearance;
        }

    }
}
