package com.apradanas.prismoji;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.apradanas.prismoji.listeners.OnEmojiClickedListener;

final class PrismojiPagerAdapter extends PagerAdapter {
    private final OnEmojiClickedListener listener;
    private final OnEmojiLongClickedListener longListener;
    private final RecentEmoji recentEmoji;

    private RecentPrismojiGridView recentEmojiGridView;

    PrismojiPagerAdapter(final OnEmojiClickedListener listener,
                         final OnEmojiLongClickedListener longListener,
                         final RecentEmoji recentEmoji) {
        this.listener = listener;
        this.longListener = longListener;
        this.recentEmoji = recentEmoji;
        this.recentEmojiGridView = null;
    }

    @Override
    public int getCount() {
        return PrismojiManager.getInstance().getCategories().length + 1;
    }

    @Override
    public Object instantiateItem(final ViewGroup pager, final int position) {
        final View newView;

        if (position == 0) {
            newView = new RecentPrismojiGridView(pager.getContext()).init(listener, longListener, recentEmoji);

            recentEmojiGridView = (RecentPrismojiGridView) newView;
        } else {
            newView = new PrismojiGridView(pager.getContext()).init(listener, longListener, PrismojiManager.getInstance().getCategories()[position - 1]);
        }

        pager.addView(newView);

        return newView;
    }

    @Override
    public void destroyItem(final ViewGroup pager, final int position, final Object view) {
        pager.removeView((View) view);

        if (position == 0) {
            recentEmojiGridView = null;
        }
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    int numberOfRecentEmojis() {
        return recentEmoji.getRecentEmojis().size();
    }

    void invalidateRecentEmojis() {
        if (recentEmojiGridView != null) {
            recentEmojiGridView.invalidateEmojis();
        }
    }
}
