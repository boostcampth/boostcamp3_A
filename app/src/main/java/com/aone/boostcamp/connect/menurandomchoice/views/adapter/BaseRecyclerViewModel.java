package com.aone.boostcamp.connect.menurandomchoice.views.adapter;

import android.support.annotation.NonNull;

import java.util.List;

public interface BaseRecyclerViewModel<L> {

    void setItem(int position, @NonNull L item);

    void setItems(@NonNull List<L> items);

    void addItem(@NonNull L item);

    void addItem(int position, @NonNull L item);

    void addItems(@NonNull List<L> items);

    void removeItem(int position);

    void clearItems();

    L getItem(int position);

    List<L> getItems();

    int getItemsCount();

}
