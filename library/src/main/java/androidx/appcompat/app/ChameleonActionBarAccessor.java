package androidx.appcompat.app;

import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.DecorToolbar;

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
