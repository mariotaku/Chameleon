package android.support.v7.app;

import android.support.v7.widget.DecorToolbar;

public class ChameleonToolbarActionBarAccessor {
    public static DecorToolbar getWindowDecorActionBar(ToolbarActionBar actionBar) {
        return actionBar.mDecorToolbar;
    }
}
