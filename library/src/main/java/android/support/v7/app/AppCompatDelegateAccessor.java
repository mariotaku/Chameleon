package android.support.v7.app;

import android.support.v7.widget.ActionBarContextView;

/**
 * Created by mariotaku on 2017/1/1.
 */
public class AppCompatDelegateAccessor {
    public static ActionBarContextView getActionModeView(AppCompatDelegate delegate) {
        if (delegate instanceof AppCompatDelegateImplV9) {
            return ((AppCompatDelegateImplV9) delegate).mActionModeView;
        }
        return null;
    }
}
