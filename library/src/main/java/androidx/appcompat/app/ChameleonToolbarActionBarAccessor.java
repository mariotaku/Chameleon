package androidx.appcompat.app;

import androidx.appcompat.widget.DecorToolbar;

public class ChameleonToolbarActionBarAccessor {
    public static DecorToolbar getWindowDecorActionBar(ToolbarActionBar actionBar) {
        return actionBar.mDecorToolbar;
    }
}
