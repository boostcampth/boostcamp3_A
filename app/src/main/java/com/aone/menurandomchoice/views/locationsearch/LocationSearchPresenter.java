package com.aone.menurandomchoice.views.locationsearch;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aone.menurandomchoice.repository.network.NetworkResponseListener;
import com.aone.menurandomchoice.repository.network.model.AddressResponseBody;
import com.aone.menurandomchoice.repository.network.pojo.KakaoAddressResult;
import com.aone.menurandomchoice.views.base.BasePresenter;
import com.aone.menurandomchoice.views.base.adapter.BaseRecyclerViewAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;

public class LocationSearchPresenter extends BasePresenter<LocationSearchContract.View>
        implements LocationSearchContract.Presenter {

    private Call<AddressResponseBody> call;
    private LocationSearchAdapter adapter; // adapterView, adapterModel

    public void setAdapter(final LocationSearchAdapter adapter) {
        adapter.setOnItemClickListener( new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("Clicked ViewHolder", adapter.getItem(position).getX()+"");
            }
        });
        this.adapter = adapter;
    }

    private void updateList(List<KakaoAddressResult> documents) {
        this.adapter.setItems(documents);
    }

    public void requestLocationSearch(@NonNull String Query) {
        this.call = getRepository().executeLocationSearch( new NetworkResponseListener<AddressResponseBody>() {
                @Override
                public void onError() {

                }

                @Override
                public void onReceived(@NonNull AddressResponseBody response) {
                    updateList(response.getDocuments());
                }
            }, Query);
    }
}
