package com.apradanas.prismoji;

import android.view.View;

import com.apradanas.prismoji.emoji.Emoji;

interface OnEmojiLongClickedListener {
    void onEmojiLongClicked(final View view, final Emoji emoji);
}
