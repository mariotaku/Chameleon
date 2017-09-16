package org.mariotaku.chameleon.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import org.mariotaku.chameleon.Chameleon;
import org.mariotaku.chameleon.ChameleonUtils;
import org.mariotaku.chameleon.ChameleonView;
import org.mariotaku.chameleon.R;
import org.mariotaku.chameleon.internal.ChameleonTypedArray;

import java.lang.reflect.Field;

/**
 * Created by mariotaku on 2016/12/18.
 */

public class ChameleonTextView extends AppCompatTextView implements ChameleonView {
    public ChameleonTextView(Context context) {
        super(context);
    }

    public ChameleonTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChameleonTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isPostApplyTheme() {
        return false;
    }

    @Nullable
    @Override
    public Appearance createAppearance(@NonNull Context context, @NonNull AttributeSet attributeSet, @NonNull Chameleon.Theme theme) {
        return Appearance.create(this, context, attributeSet, theme);
    }


    @Override
    public void applyAppearance(@NonNull ChameleonView.Appearance appearance) {
        Appearance.apply(this, (Appearance) appearance);
    }

    public static class Appearance implements ChameleonView.Appearance {
        private ColorStateList textColor;
        private int linkTextColor;
        private int tintColor;
        private ColorStateList backgroundTintList;


        public int getLinkTextColor() {
            return linkTextColor;
        }

        public void setLinkTextColor(int linkTextColor) {
            this.linkTextColor = linkTextColor;
        }

        public int getTintColor() {
            return tintColor;
        }

        public void setTintColor(int tintColor) {
            this.tintColor = tintColor;
        }

        public ColorStateList getBackgroundTintList() {
            return backgroundTintList;
        }

        public void setBackgroundTintList(ColorStateList backgroundTintList) {
            this.backgroundTintList = backgroundTintList;
        }

        public ColorStateList getTextColor() {
            return textColor;
        }

        public void setTextColor(ColorStateList textColor) {
            this.textColor = textColor;
        }

        public static void apply(TextView view, Appearance appearance) {
            view.setLinkTextColor(appearance.getLinkTextColor());
            final ColorStateList textColor = appearance.getTextColor();
            if (textColor != null) {
                view.setTextColor(textColor);
            }
            final ColorStateList backgroundTintList = appearance.getBackgroundTintList();
            if (backgroundTintList != null) {
                ViewCompat.setBackgroundTintList(view, backgroundTintList);
            }
            setCursorTint(view, appearance.getTintColor());
            setHandlerTint(view, appearance.getTintColor());
            view.setHighlightColor(ChameleonUtils.adjustAlpha(appearance.getTintColor(), 0.4f));
        }

        public static Appearance create(TextView view, Context context, AttributeSet attributeSet, Chameleon.Theme theme) {
            Appearance appearance = new Appearance();
            ChameleonTypedArray a = ChameleonTypedArray.obtain(context, attributeSet,
                    R.styleable.ChameleonEditText, theme);
            appearance.setTextColor(a.getColorStateList(R.styleable.ChameleonEditText_android_textColor, false));
            appearance.setLinkTextColor(a.getColor(R.styleable.ChameleonEditText_android_textColorLink, theme.getTextColorLink(), false));
            appearance.setBackgroundTintList(a.getColorStateList(R.styleable.ChameleonEditText_backgroundTint, false));
            appearance.setTintColor(theme.getColorAccent());
            a.recycle();
            return appearance;
        }

        public static void setCursorTint(@NonNull TextView textView, @ColorInt int color) {
            try {
                int mCursorDrawableRes = getIntField(TextView.class, textView, "mCursorDrawableRes");
                Object editor = getField(TextView.class, textView, "mEditor");
                if (editor != null) {
                    Drawable[] drawables = new Drawable[2];
                    drawables[0] = ContextCompat.getDrawable(textView.getContext(), mCursorDrawableRes);
                    drawables[1] = ContextCompat.getDrawable(textView.getContext(), mCursorDrawableRes);
                    ChameleonUtils.tintOrColor(drawables[0], color);
                    ChameleonUtils.tintOrColor(drawables[1], color);
                    setField(Class.forName("android.widget.Editor"), editor, "mCursorDrawable", drawables);
                }
            } catch (Exception e) {
                // Ignore
            }
        }

        public static void setHandlerTint(@NonNull TextView textView, @ColorInt int color) {
            try {
                int mTextSelectHandleLeftRes = getIntField(TextView.class, textView, "mTextSelectHandleLeftRes");
                int mTextSelectHandleRightRes = getIntField(TextView.class, textView, "mTextSelectHandleRightRes");
                int mTextSelectHandleRes = getIntField(TextView.class, textView, "mTextSelectHandleRes");
                Object editor = getField(TextView.class, textView, "mEditor");
                if (editor != null) {
                    final Class<?> editorClass = Class.forName("android.widget.Editor");
                    final Drawable textSelectHandleLeft = ContextCompat.getDrawable(textView.getContext(), mTextSelectHandleLeftRes);
                    final Drawable textSelectHandleRight = ContextCompat.getDrawable(textView.getContext(), mTextSelectHandleRightRes);
                    final Drawable textSelectHandle = ContextCompat.getDrawable(textView.getContext(), mTextSelectHandleRes);
                    ChameleonUtils.tintOrColor(textSelectHandleLeft, color);
                    ChameleonUtils.tintOrColor(textSelectHandleRight, color);
                    ChameleonUtils.tintOrColor(textSelectHandle, color);
                    setField(editorClass, editor, "mSelectHandleLeft", textSelectHandleLeft);
                    setField(editorClass, editor, "mSelectHandleRight", textSelectHandleRight);
                    setField(editorClass, editor, "mSelectHandleCenter", textSelectHandle);
                }
            } catch (Exception e) {
                // Ignore
            }

        }

        private static Object getField(@NonNull Class<?> cls, @NonNull Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        }

        private static int getIntField(@NonNull Class<?> cls, @NonNull Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.getInt(object);
        }

        private static void setField(@NonNull Class<?> cls, @NonNull Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        }
    }
}
