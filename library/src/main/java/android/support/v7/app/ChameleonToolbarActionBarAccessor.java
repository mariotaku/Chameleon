package android.support.v7.app;

import android.support.v7.widget.DecorToolbar;

/**
 * Created by mariotaku on 2016/12/21.
 */

public class ChameleonToolbarActionBarAccessor {
    public static DecorToolbar getWindowDecorActionBar(ToolbarActionBar actionBar) {
        return actionBar.mDecorToolbar;
    }
}
