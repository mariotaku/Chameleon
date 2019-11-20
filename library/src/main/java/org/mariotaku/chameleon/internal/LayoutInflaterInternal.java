package org.mariotaku.chameleon.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LayoutInflaterInternal {
    private static Method mOnCreateViewMethod;
    private static Method mCreateViewMethod;
    @Nullable
    private static Field mConstructorArgsField;
    @Nullable
    private static int[] ATTRS_THEME;

    public static View onCreateView(LayoutInflater inflater, View view, String name, AttributeSet attrs) {
        ensureAvailable();
        try {
            return (View) mOnCreateViewMethod.invoke(inflater, view, name, attrs);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static View createView(LayoutInflater inflater, String name, String prefix, AttributeSet attrs) {
        ensureAvailable();
        try {
            return (View) mCreateViewMethod.invoke(inflater, name, null, attrs);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static Context getThemeWrapper(Context viewContext, AttributeSet attrs) {
        ensureAvailable();
        // Apply a theme wrapper, if requested.
        if (ATTRS_THEME != null) {
            final TypedArray ta = viewContext.obtainStyledAttributes(attrs, ATTRS_THEME);
            try {
                final int themeResId = ta.getResourceId(0, 0);
                if (themeResId != 0) return new ContextThemeWrapper(viewContext, themeResId);
            } finally {
                ta.recycle();
            }
        }
        return null;
    }

    @Nullable
    public static Object[] getConstructorArgs(LayoutInflater inflater) {
        try {
            if (mConstructorArgsField == null) return null;
            return (Object[]) mConstructorArgsField.get(inflater);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void ensureAvailable() {
        if (mOnCreateViewMethod == null) {
            try {
                mOnCreateViewMethod = LayoutInflater.class.getDeclaredMethod("onCreateView",
                        View.class, String.class, AttributeSet.class);
                mOnCreateViewMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Failed to retrieve the onCreateView method.", e);
            }
        }
        if (mCreateViewMethod == null) {
            try {
                mCreateViewMethod = LayoutInflater.class.getDeclaredMethod("createView",
                        String.class, String.class, AttributeSet.class);
                mCreateViewMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Failed to retrieve the createView method.", e);
            }
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && mConstructorArgsField == null) {
            try {
                mConstructorArgsField = LayoutInflater.class.getDeclaredField("mConstructorArgs");
                mConstructorArgsField.setAccessible(true);
            } catch (NoSuchFieldException e) {
            }
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && ATTRS_THEME == null) {
            try {
                final Field attrsThemeField = LayoutInflater.class.getDeclaredField("ATTRS_THEME");
                attrsThemeField.setAccessible(true);
                ATTRS_THEME = (int[]) attrsThemeField.get(null);
            } catch (Throwable t) {
                // Ignore
            }
        }
    }
}
