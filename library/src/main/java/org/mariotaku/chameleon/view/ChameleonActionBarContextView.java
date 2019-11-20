package org.mariotaku.chameleon.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.ChameleonAbsActionBarViewAccessor;
import androidx.appcompat.widget.ChameleonActionMenuPresenterAccessor;
import androidx.core.widget.ImageViewCompat;

import org.mariotaku.chameleon.Chameleon;
import org.mariotaku.chameleon.ChameleonUtils;
import org.mariotaku.chameleon.ChameleonView;
import org.mariotaku.chameleon.internal.SupportMethods;

/**
 * Themed ActionMode view
 * Created by mariotaku on 2017/1/1.
 */
@SuppressLint("PrivateResource")
@SuppressWarnings("RestrictedApi")
public class ChameleonActionBarContextView extends ActionBarContextView implements ChameleonView {
    public ChameleonActionBarContextView(Context context) {
        super(context);
    }

    public ChameleonActionBarContextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChameleonActionBarContextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
        Appearance.apply(this, (Appearance) appearance);
    }

    public static void themeOverflow(ActionBarContextView view, Chameleon.Theme theme) {
        int itemColor = ChameleonUtils.getColorDependent(theme.getColorToolbar());
        final AppCompatImageView overflowIconView = ChameleonActionMenuPresenterAccessor.getOverflowButton(
                ChameleonAbsActionBarViewAccessor.getActionMenuPresenter(view));
        if (overflowIconView != null) {
            ImageViewCompat.setImageTintList(overflowIconView, ColorStateList.valueOf(itemColor));
        }
        ImageView closeButton = view.findViewById(androidx.appcompat.R.id.action_mode_close_button);

        if (closeButton != null) {
            if (closeButton.getDrawable() == null) {
                closeButton.setImageDrawable(AppCompatResources.getDrawable(view.getContext(),
                        androidx.appcompat.R.drawable.abc_ic_ab_back_material));
            }
            ImageViewCompat.setImageTintList(closeButton, ColorStateList.valueOf(itemColor));
        }
    }


    public static class Appearance implements ChameleonView.Appearance {

        private int titleTextColor;
        private int subTitleTextColor;
        private Drawable background;

        public void setTitleTextColor(int titleTextColor) {
            this.titleTextColor = titleTextColor;
        }

        public int getTitleTextColor() {
            return titleTextColor;
        }

        public void setSubTitleTextColor(int subTitleTextColor) {
            this.subTitleTextColor = subTitleTextColor;
        }

        public int getSubTitleTextColor() {
            return subTitleTextColor;
        }

        public void setBackground(Drawable background) {
            this.background = background;
        }

        public Drawable getBackground() {
            return background;
        }

        @NonNull
        public static Appearance create(final @NonNull Chameleon.Theme theme) {
            Appearance appearance = new Appearance();
            ChameleonToolbar.Appearance.TextColor textColor = ChameleonToolbar.Appearance.TextColor.get(theme);
            appearance.setTitleTextColor(textColor.primary);
            appearance.setSubTitleTextColor(textColor.secondary);
            appearance.setBackground(new ColorDrawable(theme.getColorToolbar()));
            return appearance;
        }

        public static void apply(final ActionBarContextView view, final Appearance appearance) {
            view.setTitle(view.getTitle());
            TextView titleView = view.findViewById(androidx.appcompat.R.id.action_bar_title);
            TextView subTitleView = view.findViewById(androidx.appcompat.R.id.action_bar_subtitle);
            titleView.setTextColor(appearance.getTitleTextColor());
            subTitleView.setTextColor(appearance.getSubTitleTextColor());
            SupportMethods.setBackground(view, appearance.getBackground());
        }
    }
}
