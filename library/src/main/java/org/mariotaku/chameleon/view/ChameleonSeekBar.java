package org.mariotaku.chameleon.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatSeekBar;
import android.util.AttributeSet;

import org.mariotaku.chameleon.Chameleon;
import org.mariotaku.chameleon.ChameleonUtils;
import org.mariotaku.chameleon.ChameleonView;
import org.mariotaku.chameleon.R;

/**
 * Themed SeekBar
 * Created by mariotaku on 2016/12/18.
 */
public class ChameleonSeekBar extends AppCompatSeekBar implements ChameleonView {
    public ChameleonSeekBar(Context context) {
        super(context);
    }

    public ChameleonSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChameleonSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isPostApplyTheme() {
        return false;
    }

    @Nullable
    @Override
    public Appearance createAppearance(@NonNull Context context, @NonNull AttributeSet attributeSet, @NonNull Chameleon.Theme theme) {
        return Appearance.create(theme);
    }

    @Override
    public void applyAppearance(@NonNull ChameleonView.Appearance appearance) {
        final Appearance a = (Appearance) appearance;
        Appearance.apply(this, a);
    }

    public static class Appearance implements ChameleonView.Appearance {
        private int color;
        private boolean dark;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public void setDark(final boolean dark) {
            this.dark = dark;
        }

        public boolean isDark() {
            return dark;
        }

        public static void apply(AppCompatSeekBar view, Appearance a) {
            final int color = a.getColor();
            final ColorStateList s1 = getDisabledColorStateList(color,
                    ContextCompat.getColor(view.getContext(), a.isDark() ? R.color.chameleon_control_disabled_dark : R.color.chameleon_control_disabled_light));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.setThumbTintList(s1);
                view.setProgressTintList(s1);
            } else {
                Drawable progressDrawable = ChameleonUtils.createTintedDrawable(view.getProgressDrawable(), s1);
                view.setProgressDrawable(progressDrawable);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Drawable thumbDrawable = ChameleonUtils.createTintedDrawable(view.getThumb(), s1);
                    view.setThumb(thumbDrawable);
                }
            }
        }

        @NonNull
        public static Appearance create(@NonNull Chameleon.Theme theme) {
            Appearance appearance = new Appearance();
            appearance.setColor(theme.getColorAccent());
            appearance.setDark(!ChameleonUtils.isColorLight(theme.getColorBackground()));
            return appearance;
        }


        @NonNull
        private static ColorStateList getDisabledColorStateList(@ColorInt int normal, @ColorInt int disabled) {
            return new ColorStateList(new int[][]{
                    new int[]{-android.R.attr.state_enabled},
                    new int[]{android.R.attr.state_enabled}
            }, new int[]{
                    disabled,
                    normal
            });
        }

    }
}
