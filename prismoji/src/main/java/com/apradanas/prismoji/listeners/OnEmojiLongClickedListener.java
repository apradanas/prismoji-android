package com.apradanas.prismoji.listeners;

import android.view.View;

import com.apradanas.prismoji.emoji.Emoji;

public interface OnEmojiLongClickedListener {
    void onEmojiLongClicked(final View view, final Emoji emoji);
}
