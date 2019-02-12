package com.aone.menurandomchoice.views.menuregister.adapter.viewholder;

import android.view.View;

import com.aone.menurandomchoice.databinding.ItemMenuCategoryBinding;
import com.aone.menurandomchoice.views.menuregister.adapter.item.MenuCategoryItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MenuCategoryViewHolder extends RecyclerView.ViewHolder {

    private ItemMenuCategoryBinding itemMenuCategoryBinding;
    private OnItemClickListener onItemClickListener;

    public MenuCategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        setUpDataBinding();
    }

    private void setUpDataBinding() {
        itemMenuCategoryBinding = DataBindingUtil.bind(itemView);
        if(itemMenuCategoryBinding != null) {
            itemMenuCategoryBinding.setViewHolder(this);
        }
    }

    public void setOnItemClickListener(@NonNull OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setMenuCategoryItem(MenuCategoryItem categoryItem) {
        itemMenuCategoryBinding.itemMenuCategoryTv.setText(categoryItem.getCategoryName());
        itemMenuCategoryBinding.itemMenuCategoryTv.setSelected(categoryItem.isSelect());
    }

    public void onClickItemView(View view) {
        if(onItemClickListener != null) {
            onItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

}
