package org.mariotaku.chameleon.view;


import android.content.Context;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.AttributeSet;

import org.mariotaku.chameleon.ChameleonView;

public class ChameleonCoordinatorLayout extends CoordinatorLayout implements ChameleonView.StatusBarThemeable {
    public ChameleonCoordinatorLayout(Context context) {
        super(context);
    }

    public ChameleonCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChameleonCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isStatusBarColorHandled() {
        return getStatusBarBackground() != null;
    }
}
