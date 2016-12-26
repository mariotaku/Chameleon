package org.mariotaku.chameleon.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.widget.RadioButton;

import org.mariotaku.chameleon.Chameleon;
import org.mariotaku.chameleon.ChameleonUtils;
import org.mariotaku.chameleon.ChameleonView;
import org.mariotaku.chameleon.R;

import static org.mariotaku.chameleon.ChameleonUtils.createTintedDrawable;

/**
 * Created by mariotaku on 2016/12/26.
 */

public class ChameleonRadioButton extends AppCompatRadioButton implements ChameleonView {

    public ChameleonRadioButton(Context context) {
        super(context);
    }

    public ChameleonRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChameleonRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
        Appearance a = (Appearance) appearance;
        setTint(this, a.getAccentColor(), a.isDark());
    }

    public static void setTint(@NonNull RadioButton radioButton, @ColorInt int color, boolean useDarker) {
        ColorStateList sl = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled, -android.R.attr.state_checked},
                new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}
        }, new int[]{
                // Rdio button includes own alpha for disabled state
                ChameleonUtils.stripAlpha(ContextCompat.getColor(radioButton.getContext(), useDarker ? R.color.chameleon_control_disabled_dark : R.color.chameleon_control_disabled_light)),
                ContextCompat.getColor(radioButton.getContext(), useDarker ? R.color.chameleon_control_normal_dark : R.color.chameleon_control_normal_light),
                color
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            radioButton.setButtonTintList(sl);
        } else {
            Drawable d = createTintedDrawable(ContextCompat.getDrawable(radioButton.getContext(), R.drawable.abc_btn_radio_material), sl);
            radioButton.setButtonDrawable(d);
        }
    }

    public static class Appearance implements ChameleonView.Appearance {
        int accentColor;
        boolean dark;

        @NonNull
        public static Appearance create(@NonNull Chameleon.Theme theme) {
            Appearance appearance = new Appearance();
            appearance.setAccentColor(theme.getColorAccent());
            appearance.setDark(!ChameleonUtils.isColorLight(theme.getColorBackground()));
            return appearance;
        }

        public int getAccentColor() {
            return accentColor;
        }

        public void setAccentColor(int accentColor) {
            this.accentColor = accentColor;
        }

        public boolean isDark() {
            return dark;
        }

        public void setDark(boolean dark) {
            this.dark = dark;
        }
    }
}
