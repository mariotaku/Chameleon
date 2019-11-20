package androidx.appcompat.widget;

import androidx.annotation.NonNull;

public class ChameleonActionMenuPresenterAccessor {
    public static AppCompatImageView getOverflowButton(@NonNull ActionMenuPresenter presenter) {
        return presenter.mOverflowButton;
    }
}
