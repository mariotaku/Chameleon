package org.mariotaku.chameleon.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

import org.mariotaku.chameleon.Chameleon;
import org.mariotaku.chameleon.ChameleonView;
import org.mariotaku.chameleon.R;

import static org.mariotaku.chameleon.ChameleonUtils.createTintedDrawable;

/**
 * Created by mariotaku on 2016/12/26.
 */
public class ChameleonCheckedTextView extends AppCompatCheckedTextView implements ChameleonView {

    public ChameleonCheckedTextView(Context context) {
        super(context);
    }

    public ChameleonCheckedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChameleonCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isPostApplyTheme() {
        return false;
    }

    @Nullable
    @Override
    public Appearance createAppearance(@NonNull Context context, @NonNull AttributeSet attributeSet, @NonNull Chameleon.Theme theme) {
        return Appearance.create(theme, context, attributeSet);
    }

    @Override
    public void applyAppearance(@NonNull ChameleonView.Appearance appearance) {
        Appearance a = (Appearance) appearance;
        setTint(this, a.getAccentColor(), a.checkMarkResource);
    }

    public static void setTint(@NonNull CheckedTextView view, @ColorInt int color, final int checkMarkResource) {
        ColorStateList sl = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled, -android.R.attr.state_checked},
                new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}
        }, new int[]{
                ContextCompat.getColor(view.getContext(), R.color.chameleon_control_disabled_light),
                ContextCompat.getColor(view.getContext(), R.color.chameleon_control_normal_light),
                color
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setCheckMarkTintList(sl);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Drawable drawable = createTintedDrawable(view.getCheckMarkDrawable(), sl);
            view.setCheckMarkDrawable(drawable);
        } else if (checkMarkResource != 0) {
            Drawable drawable = createTintedDrawable(ContextCompat.getDrawable(view.getContext(),
                    checkMarkResource), sl);
            view.setCheckMarkDrawable(drawable);
        }
    }

    public static class Appearance implements ChameleonView.Appearance {
        int accentColor;
        int checkMarkResource;

        @NonNull
        public static Appearance create(@NonNull Chameleon.Theme theme, final Context context, final AttributeSet attributeSet) {
            Appearance appearance = new Appearance();
            appearance.setAccentColor(theme.getColorAccent());
            TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.ChameleonCheckedTextView);
            appearance.checkMarkResource = a.getResourceId(R.styleable.ChameleonCheckedTextView_android_checkMark, 0);
            a.recycle();
            return appearance;
        }

        public int getAccentColor() {
            return accentColor;
        }

        public void setAccentColor(int accentColor) {
            this.accentColor = accentColor;
        }

    }
}
