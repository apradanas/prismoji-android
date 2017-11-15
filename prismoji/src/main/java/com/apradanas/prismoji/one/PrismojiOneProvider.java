package com.apradanas.prismoji.one;

import android.support.annotation.NonNull;

import com.apradanas.prismoji.PrismojiProvider;
import com.apradanas.prismoji.emoji.EmojiCategory;
import com.apradanas.prismoji.one.category.ActivityCategory;
import com.apradanas.prismoji.one.category.FlagsCategory;
import com.apradanas.prismoji.one.category.FoodsCategory;
import com.apradanas.prismoji.one.category.NatureCategory;
import com.apradanas.prismoji.one.category.ObjectsCategory;
import com.apradanas.prismoji.one.category.PeopleCategory;
import com.apradanas.prismoji.one.category.SymbolsCategory;
import com.apradanas.prismoji.one.category.TravelCategory;

public final class PrismojiOneProvider implements PrismojiProvider {
    @Override
    @NonNull
    public EmojiCategory[] getCategories() {
        return new EmojiCategory[]{
                new PeopleCategory(),
                new NatureCategory(),
                new FoodsCategory(),
                new ActivityCategory(),
                new TravelCategory(),
                new ObjectsCategory(),
                new SymbolsCategory(),
                new FlagsCategory()
        };
    }
}
