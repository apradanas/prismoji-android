package com.apradanas.prismoji;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;

public class PrismojiTextView extends AppCompatTextView {
    private int emojiSize;

    public PrismojiTextView(final Context context) {
        this(context, null);
    }

    public PrismojiTextView(final Context context, final AttributeSet attrs) {
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
}
