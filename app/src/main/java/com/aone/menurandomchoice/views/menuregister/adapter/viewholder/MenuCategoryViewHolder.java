package com.aone.menurandomchoice.views.menuregister.adapter.viewholder;

import android.view.View;

import com.aone.menurandomchoice.databinding.ItemMenuCategoryBinding;
import com.aone.menurandomchoice.views.menuregister.adapter.item.MenuCategoryItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MenuCategoryViewHolder extends RecyclerView.ViewHolder {

    private ItemMenuCategoryBinding itemMenuCategoryBinding;
    private OnMenuCategoryClickListener onMenuCategoryClickListener;

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

    public void setOnMenuCategoryClickListener(@NonNull OnMenuCategoryClickListener onMenuCategoryClickListener) {
        this.onMenuCategoryClickListener = onMenuCategoryClickListener;
    }

    public void setMenuCategoryItem(MenuCategoryItem categoryItem) {
        itemMenuCategoryBinding.itemMenuCategoryTv.setText(categoryItem.getCategoryName());
        itemMenuCategoryBinding.itemMenuCategoryTv.setSelected(categoryItem.isSelect());
    }

    public void onClickItemView(View view) {
        if(onMenuCategoryClickListener != null) {
            onMenuCategoryClickListener.onMenuCategoryItemClick(view, getAdapterPosition());
        }
    }

}
