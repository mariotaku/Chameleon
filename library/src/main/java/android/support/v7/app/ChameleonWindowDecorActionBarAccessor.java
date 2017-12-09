package android.support.v7.app;

import android.support.v7.widget.DecorToolbar;

public class ChameleonWindowDecorActionBarAccessor {
    public static DecorToolbar getWindowDecorActionBar(WindowDecorActionBar actionBar) {
        return actionBar.mDecorToolbar;
    }
}
