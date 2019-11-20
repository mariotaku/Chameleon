package org.mariotaku.chameleon.view;

import android.content.Context;
import android.content.res.ColorStateList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ImageViewCompat;
import android.util.AttributeSet;

import org.mariotaku.chameleon.Chameleon;
import org.mariotaku.chameleon.ChameleonUtils;
import org.mariotaku.chameleon.ChameleonView;
import org.mariotaku.chameleon.R;
import org.mariotaku.chameleon.internal.ChameleonTypedArray;

/**
 * Created by mariotaku on 2016/12/18.
 */

public class ChameleonFloatingActionButton extends FloatingActionButton implements ChameleonView {
    public ChameleonFloatingActionButton(Context context) {
        super(context);
    }

    public ChameleonFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChameleonFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isPostApplyTheme() {
        return false;
    }

    @Nullable
    @Override
    public Appearance createAppearance(@NonNull Context context, @NonNull AttributeSet attributeSet, @NonNull Chameleon.Theme theme) {
        Appearance appearance = new Appearance();
        ChameleonTypedArray a = ChameleonTypedArray.obtain(context, attributeSet,
                R.styleable.ChameleonFloatingActionButton, theme);
        final int backgroundTint = a.getColor(R.styleable.ChameleonFloatingActionButton_backgroundTint, theme.getColorAccent(), false);
        appearance.setBackgroundTint(backgroundTint);
        appearance.setIconTint(ChameleonUtils.getColorDependent(backgroundTint));
        a.recycle();
        return appearance;
    }

    @Override
    public void applyAppearance(@NonNull ChameleonView.Appearance appearance) {
        Appearance a = (Appearance) appearance;
        ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(a.getBackgroundTint()));
        final int iconTint = a.getIconTint();
        if (iconTint == 0) {
            ImageViewCompat.setImageTintList(this, null);
        } else {
            ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(iconTint));
        }
    }

    public static class Appearance implements ChameleonView.Appearance {
        private int backgroundTint;
        private int iconTint;

        public int getBackgroundTint() {
            return backgroundTint;
        }

        public void setBackgroundTint(int backgroundTint) {
            this.backgroundTint = backgroundTint;
        }

        public int getIconTint() {
            return iconTint;
        }

        public void setIconTint(int iconTint) {
            this.iconTint = iconTint;
        }
    }

}
