package android.support.v7.app;

import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.DecorToolbar;

public class ChameleonActionBarAccessor {

    public static ActionBarContextView getContextView(ActionBar actionBar) {
        if (actionBar instanceof WindowDecorActionBar) {
            return ((WindowDecorActionBar) actionBar).mContextView;
        }
        return null;
    }

    public static DecorToolbar getDecorToolbar(ActionBar actionBar) {
        if (actionBar instanceof WindowDecorActionBar) {
            return ((WindowDecorActionBar) actionBar).mDecorToolbar;
        } else if (actionBar instanceof ToolbarActionBar) {
            return ((ToolbarActionBar) actionBar).mDecorToolbar;
        }
        return null;
    }
}
