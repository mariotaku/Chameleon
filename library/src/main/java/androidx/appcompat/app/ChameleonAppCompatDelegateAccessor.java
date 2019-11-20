package androidx.appcompat.app;

import androidx.appcompat.widget.ActionBarContextView;

public class ChameleonAppCompatDelegateAccessor {
    public static ActionBarContextView getActionModeView(AppCompatDelegate delegate) {
        if (delegate instanceof AppCompatDelegateImpl) {
            return ((AppCompatDelegateImpl) delegate).mActionModeView;
        }
        return null;
    }
}
