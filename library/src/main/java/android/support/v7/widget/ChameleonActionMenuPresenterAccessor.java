package android.support.v7.widget;

import android.support.annotation.NonNull;

public class ChameleonActionMenuPresenterAccessor {
    public static AppCompatImageView getOverflowButton(@NonNull ActionMenuPresenter presenter) {
        return presenter.mOverflowButton;
    }
}
