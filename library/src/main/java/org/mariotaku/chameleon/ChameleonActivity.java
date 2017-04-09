package org.mariotaku.chameleon;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.view.SupportActionModeWrapper;
import android.view.Menu;

/**
 * Chameleon Base activity, all themed activities should extend this call {@link Chameleon} instance
 * Created by mariotaku on 2016/12/18.
 */
@SuppressWarnings("RestrictedApi")
@SuppressLint("Registered")
public class ChameleonActivity extends AppCompatActivity implements Chameleon.Themeable {
    private Chameleon mChameleon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mChameleon = Chameleon.getInstance(this, onCreateAppearanceCreator());
        mChameleon.preApply();
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mChameleon.postApply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mChameleon.invalidateActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            mChameleon.cleanUp();
        }
    }

    @Override
    @CallSuper
    public boolean onCreateOptionsMenu(Menu menu) {
        mChameleon.themeOverflow();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    @CallSuper
    public boolean onPrepareOptionsMenu(Menu menu) {
        final boolean result = super.onPrepareOptionsMenu(menu);
        mChameleon.themeActionMenu(menu);
        return result;
    }


    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public android.view.ActionMode startActionMode(android.view.ActionMode.Callback callback, int type) {
        if (type == android.view.ActionMode.TYPE_PRIMARY) {
            ActionMode mode = startSupportActionMode(new SupportActionModeWrapper.CallbackWrapper(this, callback));
            return new SupportActionModeWrapper(this, mode);
        }
        return super.startActionMode(callback, type);
    }

    @Override
    public android.view.ActionMode startActionMode(android.view.ActionMode.Callback callback) {
        ActionMode mode = startSupportActionMode(new SupportActionModeWrapper.CallbackWrapper(this, callback));
        return new SupportActionModeWrapper(this, mode);
    }

    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        super.onSupportActionModeStarted(mode);
        mChameleon.themeActionMode(mode);
    }


    @Override
    public Chameleon.Theme getOverrideTheme() {
        return null;
    }

    @Nullable
    protected Chameleon.AppearanceCreator onCreateAppearanceCreator() {
        return null;
    }
}
