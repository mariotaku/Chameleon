package org.mariotaku.chameleon.internal;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.collection.ArrayMap;
import androidx.core.graphics.drawable.DrawableCompat;

import org.mariotaku.chameleon.Chameleon;
import org.mariotaku.chameleon.ChameleonView;
import org.mariotaku.chameleon.R;
import org.mariotaku.chameleon.view.ChameleonActionBarContainer;
import org.mariotaku.chameleon.view.ChameleonActionBarContextView;
import org.mariotaku.chameleon.view.ChameleonAutoCompleteTextView;
import org.mariotaku.chameleon.view.ChameleonButton;
import org.mariotaku.chameleon.view.ChameleonCheckBox;
import org.mariotaku.chameleon.view.ChameleonCheckedTextView;
import org.mariotaku.chameleon.view.ChameleonCoordinatorLayout;
import org.mariotaku.chameleon.view.ChameleonEditText;
import org.mariotaku.chameleon.view.ChameleonFloatingActionButton;
import org.mariotaku.chameleon.view.ChameleonMultiAutoCompleteTextView;
import org.mariotaku.chameleon.view.ChameleonProgressBar;
import org.mariotaku.chameleon.view.ChameleonRadioButton;
import org.mariotaku.chameleon.view.ChameleonSeekBar;
import org.mariotaku.chameleon.view.ChameleonSwipeRefreshLayout;
import org.mariotaku.chameleon.view.ChameleonSwitchCompat;
import org.mariotaku.chameleon.view.ChameleonTextView;
import org.mariotaku.chameleon.view.ChameleonToolbar;

public class ChameleonInflationFactory implements LayoutInflater.Factory2 {

    @NonNull
    private final LayoutInflater mInflater;
    @Nullable
    private final Activity mActivity;
    private final Chameleon.AppearanceCreator mCreator;
    @Nullable
    private final AppCompatDelegate mDelegate;
    @NonNull
    private final Chameleon.Theme mTheme;
    private final ArrayMap<ChameleonView, ChameleonView.Appearance> mPostApplyViews;


    public ChameleonInflationFactory(@NonNull LayoutInflater inflater,
                                     @Nullable Activity activity,
                                     Chameleon.AppearanceCreator creator,
                                     @Nullable AppCompatDelegate delegate,
                                     @NonNull Chameleon.Theme theme,
                                     @NonNull ArrayMap<ChameleonView, ChameleonView.Appearance> postApplyViews) {
        this.mInflater = inflater;
        this.mActivity = activity;
        this.mCreator = creator;
        this.mDelegate = delegate;
        this.mTheme = theme;
        this.mPostApplyViews = postApplyViews;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }

    @Override
    public View onCreateView(@Nullable View parent, String name, Context context, AttributeSet attrs) {
        View view = null;
        if (shouldSkipTheming(parent)) {

        } else switch (name) {
            case "TextView":
            case "android.support.v7.widget.AppCompatTextView": {
                view = new ChameleonTextView(context, attrs);
                break;
            }
            case "EditText":
            case "android.support.v7.widget.AppCompatEditText": {
                view = new ChameleonEditText(context, attrs);
                break;
            }
            case "AutoCompleteTextView":
            case "android.support.v7.widget.AppCompatAutoCompleteTextView": {
                view = new ChameleonAutoCompleteTextView(context, attrs);
                break;
            }
            case "MultiAutoCompleteTextView":
            case "android.support.v7.widget.AppCompatMultiAutoCompleteTextView": {
                view = new ChameleonMultiAutoCompleteTextView(context, attrs);
                break;
            }
            case "Button":
            case "android.support.v7.widget.AppCompatButton": {
                view = new ChameleonButton(context, attrs);
                break;
            }
            case "ProgressBar": {
                view = new ChameleonProgressBar(context, attrs);
                break;
            }
            case "SeekBar":
            case "android.support.v7.widget.AppCompatSeekBar": {
                view = new ChameleonSeekBar(context, attrs);
                break;
            }
            case "RadioButton":
            case "android.support.v7.widget.AppCompatRadioButton": {
                view = new ChameleonRadioButton(context, attrs);
                break;
            }
            case "CheckBox":
            case "android.support.v7.widget.AppCompatCheckBox": {
                view = new ChameleonCheckBox(context, attrs);
                break;
            }
            case "CheckedTextView":
            case "android.support.v7.widget.AppCompatCheckedTextView": {
                view = new ChameleonCheckedTextView(context, attrs);
                break;
            }
            case "android.support.design.widget.CoordinatorLayout": {
                view = new ChameleonCoordinatorLayout(context, attrs);
                break;
            }
            case "android.support.design.widget.FloatingActionButton": {
                view = new ChameleonFloatingActionButton(context, attrs);
                break;
            }
            case "android.support.v7.widget.ActionBarContainer": {
                view = new ChameleonActionBarContainer(context, attrs);
                break;
            }
            case "android.support.v7.widget.ActionBarContextView": {
                view = new ChameleonActionBarContextView(context, attrs);
                break;
            }
            case "android.support.v4.widget.SwipeRefreshLayout": {
                view = new ChameleonSwipeRefreshLayout(context, attrs);
                break;
            }
            case "android.support.v7.widget.SwitchCompat": {
                view = new ChameleonSwitchCompat(context, attrs);
                break;
            }
            case "android.support.v7.widget.Toolbar": {
                view = new ChameleonToolbar(context, attrs);
                break;
            }
        }

        if (view == null) {
            // First, check if the AppCompatDelegate will give us a view, usually (maybe always) null.
            if (mDelegate != null) {
                view = mDelegate.createView(parent, name, context, attrs);
                if (view == null && mActivity != null)
                    view = mActivity.onCreateView(parent, name, context, attrs);
                else view = null;
            } else {
                view = null;
            }

            if (isExcluded(name))
                return view;

            // Mimic code of LayoutInflater using reflection tricks (this would normally be run when this factory returns null).
            // We need to intercept the default behavior rather than allowing the LayoutInflater to handle it after this method returns.
            if (view == null) {
                try {
                    Context viewContext;
                    final boolean inheritContext = false; // TODO will this ever need to be true?
                    //noinspection PointlessBooleanExpression,ConstantConditions
                    if (parent != null && inheritContext) {
                        viewContext = parent.getContext();
                    } else {
                        viewContext = mInflater.getContext();
                    }
                    Context wrappedContext = LayoutInflaterInternal.getThemeWrapper(viewContext, attrs);
                    if (wrappedContext != null) {
                        viewContext = wrappedContext;
                    }

                    Object[] mConstructorArgs = LayoutInflaterInternal.getConstructorArgs(mInflater);

                    Object lastContext = null;
                    if (mConstructorArgs != null) {
                        lastContext = mConstructorArgs[0];
                        mConstructorArgs[0] = viewContext;
                    }
                    try {
                        if (-1 == name.indexOf('.')) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                view = mInflater.onCreateView(viewContext, parent, name, attrs);
                            } else {
                                view = LayoutInflaterInternal.onCreateView(mInflater, parent, name, attrs);
                            }
                        } else {
                            view = LayoutInflaterInternal.createView(mInflater, name, null, attrs);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (mConstructorArgs != null) {
                            mConstructorArgs[0] = lastContext;
                        }
                    }
                } catch (Throwable t) {
                    throw new RuntimeException(String.format("An error occurred while inflating View %s: %s", name, t.getMessage()), t);
                }
            }
        }


        if (view instanceof ChameleonView) {
            final ChameleonView cv = (ChameleonView) view;
            ChameleonView.Appearance appearance = cv.createAppearance(view.getContext(), attrs, mTheme);
            if (appearance != null) {
                if (cv.isPostApplyTheme()) {
                    mPostApplyViews.put(cv, appearance);
                } else {
                    cv.applyAppearance(appearance);
                }
            }
        } else if (view != null) {
            boolean handled = false;
            if (mCreator != null) {
                ChameleonView.Appearance appearance = mCreator.createAppearance(view, context, attrs, mTheme);
                if (appearance != null) {
                    mCreator.applyAppearance(view, appearance);
                    handled = true;
                }
            }
            if (!handled) {
                ChameleonTypedArray a = ChameleonTypedArray.obtain(context, attrs,
                        R.styleable.ChameleonView, mTheme);
                Drawable background = a.getDrawable(R.styleable.ChameleonView_android_background, false);
                if (background != null) {
                    int tint = a.getColor(R.styleable.ChameleonView_backgroundTint, 0, false);
                    if (tint != 0) {
                        DrawableCompat.setTint(background, tint);
                    }
                    SupportMethods.setBackground(view, background);
                }
                a.recycle();
            }
        }
        return view;
    }

    private boolean isExcluded(@NonNull String name) {
        switch (name) {
            case "android.support.design.internal.NavigationMenuItemView":
            case "ViewStub":
            case "fragment":
            case "include":
                return true;
            default:
                return false;
        }
    }

    private boolean shouldSkipTheming(View parent) {
        if (parent == null) return false;
        return "ignore".equals(parent.getTag());
    }
}
