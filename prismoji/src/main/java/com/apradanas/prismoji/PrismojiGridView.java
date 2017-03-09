package com.apradanas.prismoji;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.GridView;

import com.apradanas.prismoji.emoji.EmojiCategory;
import com.apradanas.prismoji.listeners.OnEmojiClickedListener;

class PrismojiGridView extends GridView {
    protected PrismojiArrayAdapter prismojiArrayAdapter;

    PrismojiGridView(final Context context) {
        super(context);

        final int width = getResources().getDimensionPixelSize(R.dimen.emoji_grid_view_column_width);
        final int spacing = getResources().getDimensionPixelSize(R.dimen.emoji_grid_view_spacing);

        setColumnWidth(width);
        setHorizontalSpacing(spacing);
        setVerticalSpacing(spacing);
        setPadding(spacing, spacing, spacing, spacing);
        setNumColumns(AUTO_FIT);
        setClipToPadding(false);
    }

    public PrismojiGridView init(@Nullable final OnEmojiClickedListener onEmojiClickedListener,
                                 @Nullable final OnEmojiLongClickedListener onEmojiLongClickedListener,
                                 @NonNull final EmojiCategory category) {
        prismojiArrayAdapter = new PrismojiArrayAdapter(getContext(), category.getEmojis(), onEmojiClickedListener, onEmojiLongClickedListener);
        setAdapter(prismojiArrayAdapter);

        return this;
    }
}
