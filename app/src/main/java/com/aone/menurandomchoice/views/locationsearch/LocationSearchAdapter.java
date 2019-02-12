package com.aone.menurandomchoice.views.locationsearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.LocationSearchViewholderBinding;
import com.aone.menurandomchoice.repository.network.pojo.KakaoAddressResult;
import com.aone.menurandomchoice.views.base.adapter.BaseRecyclerViewAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class LocationSearchAdapter extends BaseRecyclerViewAdapter<KakaoAddressResult, LocationSearchAdapter.LocationViewHolder> {

    @Override
    protected void bindViewHolder(@NonNull LocationViewHolder viewHolder, @NonNull KakaoAddressResult item) {
        viewHolder.binding.setItem(item);
    }

    @NonNull
    protected LocationViewHolder createViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.location_search_viewholder, viewGroup, false);
        final LocationViewHolder viewHolder = new LocationViewHolder(view);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onViewHolderClickListener != null) {
                    onViewHolderClickListener.onItemClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                }
            }
        });

        return viewHolder;
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        LocationSearchViewholderBinding binding;

        public LocationViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
