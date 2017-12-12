package org.mariotaku.chameleon;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;

import org.mariotaku.chameleon.Chameleon.Theme.LightStatusBarMode;


public class ChameleonUtils {
    public static int findAttributeIndex(@NonNull AttributeSet attributeSet, int attributeNameResource) {
        for (int i = 0, j = attributeSet.getAttributeCount(); i < j; i++) {
            if (attributeSet.getAttributeNameResource(i) == attributeNameResource) return i;
        }
        return -1;
    }

    public static boolean isColorLight(@ColorInt int color) {
        if (color == Color.BLACK) return false;
        else if (color == Color.WHITE || color == Color.TRANSPARENT) return true;
        final double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness < 0.4;
    }

    @ColorInt
    public static int shiftColor(@ColorInt int color, @FloatRange(from = 0.0f, to = 2.0f) float by) {
        if (by == 1f) return color;
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= by; // value component
        return Color.HSVToColor(hsv);
    }

    public static int adjustAlpha(@ColorInt int color, @FloatRange(from = 0.0, to = 1.0) float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public static int stripAlpha(@ColorInt int color) {
        return Color.rgb(Color.red(color), Color.green(color), Color.blue(color));
    }

    @ColorInt
    public static int darkenColor(@ColorInt int color) {
        return shiftColor(color, 0.9f);
    }

    // This returns a NEW Drawable because of the mutate() call. The mutate() call is necessary because Drawables with the same resource have shared states otherwise.
    @CheckResult
    @Nullable
    public static Drawable createTintedDrawable(@Nullable Drawable drawable, @ColorInt int color) {
        if (drawable == null) return null;
        drawable = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        DrawableCompat.setTint(drawable, color);
        return drawable;
    }

    // This returns a NEW Drawable because of the mutate() call. The mutate() call is necessary because Drawables with the same resource have shared states otherwise.
    @CheckResult
    @Nullable
    public static Drawable createTintedDrawable(@Nullable Drawable drawable, @NonNull ColorStateList sl) {
        if (drawable == null) return null;
        drawable = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTintList(drawable, sl);
        return drawable;
    }

    public static void tintOrColor(@Nullable Drawable drawable, @ColorInt int color) {
        if (drawable == null) return;
        DrawableCompat.setTint(drawable, color);
    }

    @CheckResult
    @Nullable
    public static Activity getActivity(Context context) {
        if (context instanceof Activity) return (Activity) context;
        if (context instanceof ContextWrapper) {
            return getActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    public static int getColorDependent(int color) {
        final boolean isLight = isColorLight(color);
        return isLight ? Color.BLACK : Color.WHITE;
    }

    public static void setOverflowIconColor(@NonNull Toolbar toolbar, int itemColor) {
        final Drawable overflowIcon = toolbar.getOverflowIcon();
        if (overflowIcon != null) {
            DrawableCompat.setTint(overflowIcon, itemColor);
            toolbar.setOverflowIcon(overflowIcon);
        }
        final Drawable navigationIcon = toolbar.getNavigationIcon();
        if (navigationIcon != null) {
            DrawableCompat.setTint(navigationIcon, itemColor);
            toolbar.setNavigationIcon(navigationIcon);
        }
    }

    public static void applyLightStatusBar(@NonNull Window window, int statusBarColor,
                                           @LightStatusBarMode int lightStatusBarMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final View decorView = window.getDecorView();
            final int systemUiVisibility = decorView.getSystemUiVisibility();
            boolean isLightStatusBar;
            if (lightStatusBarMode == LightStatusBarMode.ON) {
                isLightStatusBar = true;
            } else if (lightStatusBarMode == LightStatusBarMode.AUTO) {
                isLightStatusBar = isColorLight(statusBarColor);
            } else {
                isLightStatusBar = false;
            }
            if (isLightStatusBar) {
                decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decorView.setSystemUiVisibility(systemUiVisibility & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    public static void setTaskColor(@NonNull Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ChameleonUtilsL.setTaskColor(activity, color);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static class ChameleonUtilsL {
        static void setTaskColor(@NonNull Activity activity, int colorPrimary) {
            final ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, null, colorPrimary);
            activity.setTaskDescription(description);
        }
    }
}
