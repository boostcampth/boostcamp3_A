package com.aone.menurandomchoice.views.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<L, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>
        implements BaseRecyclerViewModel<L> {

    private List<L> items = new ArrayList<>();


    @Override
    public void setItem(int position, @NonNull L item) {
        items.set(position, item);

        notifyItemChanged(position);
    }

    @Override
    public void setItems(@NonNull List<L> items) {
        this.items.clear();
        this.items = items;

        notifyDataSetChanged();
    }

    @Override
    public void addItem(@NonNull L item) {
        int countOfBefore = getItemsCount();
        items.add(item);

        notifyItemInserted(countOfBefore);
    }

    @Override
    public void addItem(int position, @NonNull L item) {
        items.add(position, item);

        notifyItemInserted(position);
    }

    @Override
    public void addItems(@NonNull List<L> items) {
        int countOfBefore = getItemsCount();
        this.items.addAll(items);

        notifyItemRangeInserted(countOfBefore, items.size());
    }

    @Override
    public void removeItem(int position) {
        items.remove(position);

        notifyItemRemoved(position);
    }

    @Override
    public void clearItems() {
        items.clear();

        notifyDataSetChanged();
    }

    @Override
    public L getItem(int position) {
        return items.get(position);
    }

    @Override
    public List<L> getItems() {
        return items;
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }

    @Override
    public int getItemCount() {
        return getItemsCount();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        return createViewHolder(layoutInflater, viewGroup, i);
    }

    @Override
    public void onBindViewHolder(@NonNull VH viewHolder, int i) {
        bindViewHolder(viewHolder, getItem(i));
    }


    @NonNull
    abstract VH createViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup, int i);

    abstract void bindViewHolder(@NonNull VH viewHolder, @NonNull L item);

}