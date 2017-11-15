package com.apradanas.prismojisample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.apradanas.prismoji.PrismojiAutocompleteTextView;
import com.apradanas.prismoji.PrismojiPopup;
import com.apradanas.prismoji.PrismojiTextView;
import com.apradanas.prismoji.listeners.OnSoftKeyboardCloseListener;

public class MainActivity extends AppCompatActivity {

    private PrismojiPopup prismojiPopup;

    private PrismojiAutocompleteTextView editText;
    private PrismojiTextView textView;
    private ViewGroup rootView;
    private ImageButton emojiButton;
    private ImageButton sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.main_activity_edit_text);
        textView = findViewById(R.id.main_activity_text_view);
        rootView = findViewById(R.id.main_activity_root_view);
        emojiButton = findViewById(R.id.main_activity_emoji);
        sendButton = findViewById(R.id.main_activity_send);

        emojiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                prismojiPopup.toggle();
                emojiButton.setImageResource(
                        prismojiPopup.isShowing() ?
                                R.drawable.ic_keyboard_black_48dp : R.drawable.ic_tag_faces_black_48dp
                );
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(editText.getText());
            }
        });

        setUpPrismojiPopup();
    }

    private void setUpPrismojiPopup() {
        prismojiPopup = PrismojiPopup.Builder.fromRootView(rootView)
                .setOnSoftKeyboardCloseListener(new OnSoftKeyboardCloseListener() {
                    @Override
                    public void onKeyboardClose() {
                        prismojiPopup.dismiss();
                    }
                })
                .into(editText)
                .build();
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
