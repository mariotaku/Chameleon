package androidx.appcompat.app;

import androidx.appcompat.widget.DecorToolbar;

public class ChameleonWindowDecorActionBarAccessor {
    public static DecorToolbar getWindowDecorActionBar(WindowDecorActionBar actionBar) {
        return actionBar.mDecorToolbar;
    }
}
