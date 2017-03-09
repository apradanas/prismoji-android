package com.apradanas.prismoji;

import android.support.annotation.NonNull;

import com.apradanas.prismoji.emoji.EmojiCategory;

/**
 * Interface for a custom emoji implementation that can be used with {@link PrismojiManager}
 *
 * @since 0.4.0
 */
public interface PrismojiProvider {
    /**
     * Returns an array of categories.
     *
     * @return The Array of categories.
     * @since 0.4.0
     */
    @NonNull
    EmojiCategory[] getCategories();
}
