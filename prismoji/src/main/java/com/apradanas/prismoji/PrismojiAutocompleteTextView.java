package com.apradanas.prismoji;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.AutoCompleteTextView;

import com.apradanas.prismoji.emoji.Emoji;

/**
 * Created by apradanas.
 */

public class PrismojiAutocompleteTextView extends AutoCompleteTextView {
    private int emojiSize;

    public PrismojiAutocompleteTextView(Context context) {
        this(context, null);
    }

    public PrismojiAutocompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(@Nullable final AttributeSet attrs) {
        if (!isInEditMode()) {
            PrismojiManager.getInstance().verifyInstalled();
        }

        if (attrs == null) {
            emojiSize = (int) getTextSize();
        } else {
            final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.emoji);

            try {
                emojiSize = (int) a.getDimension(R.styleable.emoji_emojiSize, getTextSize());
            } finally {
                a.recycle();
            }
        }

        setText(getText());
    }

    @Override
    public void setText(final CharSequence rawText, final BufferType type) {
        final CharSequence text = rawText == null ? "" : rawText;
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        PrismojiHandler.addEmojis(getContext(), spannableStringBuilder, emojiSize);
        super.setText(spannableStringBuilder, type);
    }

    public void setEmojiSize(final int pixels) {
        emojiSize = pixels;
    }

    public void backspace() {
        final KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
        dispatchKeyEvent(event);
    }

    public void input(final Emoji emoji) {
        if (emoji != null) {
            final int start = getSelectionStart();
            final int end = getSelectionEnd();
            if (start < 0) {
                append(emoji.getUnicode());
            } else {
                getText().replace(Math.min(start, end), Math.max(start, end), emoji.getUnicode(), 0, emoji.getUnicode().length());
            }
        }
    }
}
