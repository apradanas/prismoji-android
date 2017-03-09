package com.apradanas.prismoji;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.apradanas.prismoji.emoji.Emoji;
import com.apradanas.prismoji.emoji.EmojiCategory;
import com.apradanas.prismoji.emoji.EmojiTree;

import static com.apradanas.prismoji.Utils.checkNotNull;

/**
 * PrismojiManager where an PrismojiProvider can be installed for further usage.
 */
public final class PrismojiManager {
    private static final PrismojiManager INSTANCE = new PrismojiManager();

    private final EmojiTree emojiTree = new EmojiTree();
    private EmojiCategory[] categories;

    private PrismojiManager() {
        // No instances apart from singleton.
    }

    public static PrismojiManager getInstance() {
        return INSTANCE;
    }

    /**
     * Installs the given PrismojiProvider.
     *
     * NOTE: That only one can be present at any time.
     *
     * @param provider the provider that should be installed.
     */
    public static void install(@NonNull final PrismojiProvider provider) {
        INSTANCE.categories = checkNotNull(provider.getCategories(), "categories == null");
        INSTANCE.emojiTree.clear();

        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < INSTANCE.categories.length; i++) {
            final Emoji[] emojis = checkNotNull(INSTANCE.categories[i].getEmojis(), "emojies == null");

            //noinspection ForLoopReplaceableByForEach
            for (int j = 0; j < emojis.length; j++) {
                INSTANCE.emojiTree.add(emojis[j]);
            }
        }
    }

    EmojiCategory[] getCategories() {
        verifyInstalled();
        return categories; // NOPMD
    }

    @Nullable
    Emoji findEmoji(@NonNull final CharSequence candiate) {
        verifyInstalled();
        return emojiTree.findEmoji(candiate);
    }

    public void verifyInstalled() {
        if (categories == null) {
            throw new IllegalStateException("Please install an PrismojiProvider through the PrismojiManager.install() method first.");
        }
    }
}
