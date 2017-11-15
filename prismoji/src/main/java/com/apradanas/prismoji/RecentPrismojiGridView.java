package com.apradanas.prismoji;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.apradanas.prismoji.emoji.Emoji;
import com.apradanas.prismoji.listeners.OnEmojiClickedListener;
import com.apradanas.prismoji.listeners.OnEmojiLongClickedListener;

import java.util.Collection;

final class RecentPrismojiGridView extends PrismojiGridView {
    private RecentEmoji recentEmojis;

    RecentPrismojiGridView(@NonNull final Context context) {
        super(context);
    }

    public RecentPrismojiGridView init(@Nullable final OnEmojiClickedListener onEmojiClickedListener,
                                       @Nullable final OnEmojiLongClickedListener onEmojiLongClickedListener,
                                       @NonNull final RecentEmoji recentEmoji) {
        recentEmojis = recentEmoji;

        final Collection<Emoji> emojis = recentEmojis.getRecentEmojis();
        prismojiArrayAdapter = new PrismojiArrayAdapter(getContext(), emojis.toArray(new Emoji[emojis.size()]), onEmojiClickedListener, onEmojiLongClickedListener);
        setAdapter(prismojiArrayAdapter);

        return this;
    }

    public void invalidateEmojis() {
        prismojiArrayAdapter.updateEmojis(recentEmojis.getRecentEmojis());
    }
}
