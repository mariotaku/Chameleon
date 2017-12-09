package android.support.v7.app;

import android.support.v7.widget.ActionBarContextView;

public class ChameleonAppCompatDelegateAccessor {
    public static ActionBarContextView getActionModeView(AppCompatDelegate delegate) {
        if (delegate instanceof AppCompatDelegateImplV9) {
            return ((AppCompatDelegateImplV9) delegate).mActionModeView;
        }
        return null;
    }
}
