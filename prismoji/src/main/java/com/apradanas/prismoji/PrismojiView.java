package com.apradanas.prismoji;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.content.res.AppCompatResources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.apradanas.prismoji.emoji.EmojiCategory;
import com.apradanas.prismoji.listeners.OnEmojiBackspaceClickListener;
import com.apradanas.prismoji.listeners.OnEmojiClickedListener;
import com.apradanas.prismoji.listeners.RepeatListener;

import java.util.concurrent.TimeUnit;

@SuppressLint("ViewConstructor")
final class PrismojiView extends LinearLayout implements ViewPager.OnPageChangeListener {
    private static final long INITIAL_INTERVAL = TimeUnit.SECONDS.toMillis(1) / 2;
    private static final int NORMAL_INTERVAL = 50;

    @ColorInt
    private final int themeAccentColor;
    @ColorInt
    private final int themeIconColor;

    private final View[] emojiTabs;
    private final PrismojiPagerAdapter prismojiPagerAdapter;

    @Nullable
    OnEmojiBackspaceClickListener onEmojiBackspaceClickListener;

    private int emojiTabLastSelectedIndex = -1;

    PrismojiView(final Context context,
                 final OnEmojiClickedListener onEmojiClickedListener,
                 final OnEmojiLongClickedListener onEmojiLongClickedListener,
                 @NonNull final RecentEmoji recentEmoji) {
        super(context);

        View.inflate(context, R.layout.emoji_view, this);

        setOrientation(VERTICAL);

        themeIconColor = ContextCompat.getColor(context, R.color.emoji_icons);
        themeAccentColor = ContextCompat.getColor(context, R.color.emoji_icons_accent);

        final ViewPager emojisPager = (ViewPager) findViewById(R.id.emojis_pager);
        final LinearLayout emojisTab = (LinearLayout) findViewById(R.id.emojis_tab);
        emojisPager.addOnPageChangeListener(this);

        final EmojiCategory[] categories = PrismojiManager.getInstance().getCategories();

        emojiTabs = new View[categories.length + 2];
        emojiTabs[0] = inflateButton(context, R.drawable.emoji_recent, emojisTab);
        for (int i = 0; i < categories.length; i++) {
            emojiTabs[i + 1] = inflateButton(context, categories[i].getIcon(), emojisTab);
        }
        emojiTabs[emojiTabs.length - 1] = inflateButton(context, R.drawable.emoji_backspace, emojisTab);

        handleOnClicks(emojisPager);

        prismojiPagerAdapter = new PrismojiPagerAdapter(onEmojiClickedListener, onEmojiLongClickedListener, recentEmoji);
        emojisPager.setAdapter(prismojiPagerAdapter);

        final int startIndex = prismojiPagerAdapter.numberOfRecentEmojis() > 0 ? 0 : 1;
        emojisPager.setCurrentItem(startIndex);
        onPageSelected(startIndex);
    }

    private void handleOnClicks(final ViewPager emojisPager) {
        for (int i = 0; i < emojiTabs.length - 1; i++) {
            final RelativeLayout categoryLayout = (RelativeLayout) emojiTabs[i].findViewById(R.id.category_layout);
            categoryLayout.setOnClickListener(new EmojiTabsClickListener(emojisPager, i));
        }

        final RelativeLayout backspaceLayout = (RelativeLayout) emojiTabs[emojiTabs.length - 1].findViewById(R.id.category_layout);
        backspaceLayout.setOnTouchListener(
                new RepeatListener(INITIAL_INTERVAL, NORMAL_INTERVAL, new OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        if (onEmojiBackspaceClickListener != null) {
                            onEmojiBackspaceClickListener.onEmojiBackspaceClicked(view);
                        }
                    }
                }));
    }

    public void setOnEmojiBackspaceClickListener(@Nullable final OnEmojiBackspaceClickListener onEmojiBackspaceClickListener) {
        this.onEmojiBackspaceClickListener = onEmojiBackspaceClickListener;
    }

    private View inflateButton(final Context context, @DrawableRes final int icon, final ViewGroup parent) {
        final View categoryView = LayoutInflater.from(context).inflate(R.layout.emoji_category, parent, false);
        final RelativeLayout categoryLayout = (RelativeLayout) categoryView.findViewById(R.id.category_layout);
        final ImageView categoryIcon = (ImageView) categoryView.findViewById(R.id.category_button);

        categoryIcon.setImageDrawable(AppCompatResources.getDrawable(context, icon));
        categoryIcon.setColorFilter(themeIconColor, PorterDuff.Mode.SRC_IN);

        if (icon == R.drawable.emoji_backspace) {
            categoryLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.emoji_backspace_background));
        }

        parent.addView(categoryView);

        return categoryView;
    }

    @Override
    public void onPageSelected(final int i) {
        if (emojiTabLastSelectedIndex != i) {
            if (i == 0) {
                prismojiPagerAdapter.invalidateRecentEmojis();
            }

            if (emojiTabLastSelectedIndex >= 0 && emojiTabLastSelectedIndex < emojiTabs.length) {
                final ImageView categoryIcon = (ImageView) emojiTabs[emojiTabLastSelectedIndex].findViewById(R.id.category_button);
                final View indicator = emojiTabs[emojiTabLastSelectedIndex].findViewById(R.id.category_indicator);

                categoryIcon.setSelected(false);
                categoryIcon.setColorFilter(themeIconColor, PorterDuff.Mode.SRC_IN);
                indicator.setVisibility(INVISIBLE);
            }

            final ImageView categoryIcon = (ImageView) emojiTabs[i].findViewById(R.id.category_button);
            final View indicator = emojiTabs[i].findViewById(R.id.category_indicator);

            categoryIcon.setSelected(true);
            categoryIcon.setColorFilter(themeAccentColor, PorterDuff.Mode.SRC_IN);
            indicator.setVisibility(VISIBLE);

            emojiTabLastSelectedIndex = i;
        }
    }

    @Override
    public void onPageScrolled(final int i, final float v, final int i2) {
        // Don't care.
    }

    @Override
    public void onPageScrollStateChanged(final int i) {
        // Don't care.
    }

    static class EmojiTabsClickListener implements OnClickListener {
        private final ViewPager emojisPager;
        private final int position;

        EmojiTabsClickListener(final ViewPager emojisPager, final int position) {
            this.emojisPager = emojisPager;
            this.position = position;
        }

        @Override
        public void onClick(final View v) {
            emojisPager.setCurrentItem(position);
        }
    }
}
