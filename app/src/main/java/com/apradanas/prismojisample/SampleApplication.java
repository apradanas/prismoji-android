package com.apradanas.prismojisample;

import android.app.Application;

import com.apradanas.prismoji.PrismojiManager;
import com.apradanas.prismoji.one.PrismojiOneProvider;

/**
 * Created by apradanas.
 */

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PrismojiManager.install(new PrismojiOneProvider());
    }
}
