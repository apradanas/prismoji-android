package com.apradanas.prismoji;

import android.content.Context;
import android.text.Spannable;

import com.apradanas.prismoji.emoji.Emoji;

import java.util.ArrayList;
import java.util.List;

import static com.apradanas.prismoji.PrismojiHandler.SpanRangeList.SPAN_NOT_FOUND;

public final class PrismojiHandler {
    public static void addEmojis(final Context context, final Spannable text, final int emojiSize) {
        final SpanRangeList existingSpanRanges = new SpanRangeList(text);
        final PrismojiManager prismojiManager = PrismojiManager.getInstance();
        int index = 0;

        while (index < text.length()) {
            final int existingSpanEnd = existingSpanRanges.spanEnd(index);

            if (existingSpanEnd == SPAN_NOT_FOUND) {
                final int nextSpanStart = existingSpanRanges.nextSpanStart(index);
                final int searchRange = nextSpanStart == SPAN_NOT_FOUND ? text.length() : nextSpanStart;
                final Emoji found = prismojiManager.findEmoji(text.subSequence(index, searchRange));

                if (found != null) {
                    text.setSpan(new PrismojiSpan(context, found.getResource(), emojiSize), index, index + found.getLength(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    index += found.getLength();
                } else {
                    index++;
                }
            } else {
                index += existingSpanEnd - index;
            }
        }
    }

    private PrismojiHandler() {
        throw new AssertionError("No instances.");
    }

    static final class SpanRangeList {
        static final int SPAN_NOT_FOUND = -1;

        private final List<Range> spanRanges = new ArrayList<>();

        SpanRangeList(final Spannable text) {
            for (final PrismojiSpan span : text.getSpans(0, text.length(), PrismojiSpan.class)) {
                spanRanges.add(new Range(text.getSpanStart(span), text.getSpanEnd(span)));
            }
        }

        int spanEnd(final int index) {
            for (final Range spanRange : spanRanges) {
                if (spanRange.start == index) {
                    return spanRange.end;
                }
            }

            return SPAN_NOT_FOUND;
        }

        int nextSpanStart(final int index) {
            for (final Range spanRange : spanRanges) {
                if (spanRange.start > index) {
                    return spanRange.start;
                }
            }

            return SPAN_NOT_FOUND;
        }
    }

    static final class Range {
        final int start;
        final int end;

        Range(final int start, final int end) {
            this.start = start;
            this.end = end;
        }
    }
}
