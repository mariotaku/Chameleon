package org.mariotaku.chameleon.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AbsActionBarViewAccessor;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.ActionMenuPresenterAccessor;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import org.mariotaku.chameleon.Chameleon;
import org.mariotaku.chameleon.ChameleonUtils;
import org.mariotaku.chameleon.ChameleonView;
import org.mariotaku.chameleon.internal.SupportMethods;

/**
 * Created by mariotaku on 2017/1/1.
 */

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
        Appearance a = (Appearance) appearance;
        Appearance.apply(a, this);
    }

    public static void themeOverflow(ActionBarContextView view, Chameleon.Theme theme) {
        int itemColor = ChameleonUtils.getColorDependent(theme.getColorToolbar());
        final AppCompatImageView overflowIconView = ActionMenuPresenterAccessor.getOverflowButton(
                AbsActionBarViewAccessor.getActionMenuPresenter(view));
        if (overflowIconView != null) {
            overflowIconView.setColorFilter(itemColor, PorterDuff.Mode.SRC_ATOP);
        }
        ImageView closeButton = (ImageView) view.findViewById(android.support.v7.appcompat.R.id.action_mode_close_button);

        if (closeButton != null) {
            if (closeButton.getDrawable() == null) {
                closeButton.setImageResource(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
            }
            closeButton.setColorFilter(itemColor, PorterDuff.Mode.SRC_ATOP);
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
        public static Appearance create(@NonNull Chameleon.Theme theme) {
            Appearance appearance = new Appearance();
            ChameleonToolbar.Appearance.TextColor textColor = ChameleonToolbar.Appearance.TextColor.get(theme);
            appearance.setTitleTextColor(textColor.primary);
            appearance.setSubTitleTextColor(textColor.secondary);
            appearance.setBackground(new ColorDrawable(theme.getColorToolbar()));
            return appearance;
        }

        public static void apply(Appearance appearance, ActionBarContextView view) {
            view.setTitle(view.getTitle());
            TextView titleView = (TextView) view.findViewById(android.support.v7.appcompat.R.id.action_bar_title);
            TextView subTitleView = (TextView) view.findViewById(android.support.v7.appcompat.R.id.action_bar_subtitle);
            titleView.setTextColor(appearance.titleTextColor);
            subTitleView.setTextColor(appearance.subTitleTextColor);
            SupportMethods.setBackground(view, appearance.getBackground());
        }
    }
}
