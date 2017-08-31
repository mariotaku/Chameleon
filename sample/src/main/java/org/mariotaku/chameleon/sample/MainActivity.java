package org.mariotaku.chameleon.sample;

import android.graphics.Color;
import android.os.Bundle;

import org.mariotaku.chameleon.Chameleon;
import org.mariotaku.chameleon.ChameleonActivity;

public class MainActivity extends ChameleonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public Chameleon.Theme getOverrideTheme() {
        Chameleon.Theme theme = Chameleon.Theme.from(this);
        theme.setColorToolbar(Color.RED);
        return theme;
    }
}
