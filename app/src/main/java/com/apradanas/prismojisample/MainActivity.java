package com.apradanas.prismojisample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apradanas.prismoji.PrismojiEditText;
import com.apradanas.prismoji.PrismojiPopup;
import com.apradanas.prismoji.listeners.OnSoftKeyboardCloseListener;

public class MainActivity extends AppCompatActivity {

    private PrismojiPopup prismojiPopup;

    private PrismojiEditText editText;
    private ViewGroup rootView;
    private ImageView emojiButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (PrismojiEditText) findViewById(R.id.main_activity_edit_text);
        rootView = (ViewGroup) findViewById(R.id.main_activity_root_view);
        emojiButton = (ImageView) findViewById(R.id.main_activity_emoji);

        emojiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                prismojiPopup.toggle();
            }
        });

        setUpPrismojiPopup();
    }

    private void setUpPrismojiPopup() {
        prismojiPopup = PrismojiPopup.Builder.fromRootView(rootView)
                .setOnSoftKeyboardCloseListener(new OnSoftKeyboardCloseListener() {
                    @Override public void onKeyboardClose() {
                        prismojiPopup.dismiss();
                    }
                })
                .build(editText);
    }

    @Override
    public void onBackPressed() {
        if (prismojiPopup != null && prismojiPopup.isShowing()) {
            prismojiPopup.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        if (prismojiPopup != null) {
            prismojiPopup.dismiss();
        }

        super.onStop();
    }
}
