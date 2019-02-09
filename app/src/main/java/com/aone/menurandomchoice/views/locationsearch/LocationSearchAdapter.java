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
    public void bindViewHolder(@NonNull LocationViewHolder viewHolder, @NonNull KakaoAddressResult item) {
        viewHolder.binding.setItem(item);
    }

    @NonNull
    public LocationViewHolder createViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.location_search_viewholder, viewGroup, false);
        return new LocationViewHolder(view);
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        LocationSearchViewholderBinding binding;

        public LocationViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
