package com.aone.menurandomchoice.views.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

public abstract class BaseActivity<B extends ViewDataBinding, V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends AppCompatActivity
        implements BaseContract.View {

    private B dataBinding;
    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUp();
        attachViewToPresenter();
    }

    @Override
    protected void onStart() {
        attachViewToPresenter();

        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        detachViewFromPresenter();
        onSaveInstanceStateToBundle(outState);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        detachViewFromPresenter();

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        detachViewFromPresenter();

        super.onDestroy();
    }

    private void setUp() {
        dataBinding = setUpDataBinding();
        presenter = setUpPresenter();
    }

    @NonNull
    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    protected B getDataBinding() {
        return dataBinding;
    }

    protected P getPresenter() {
        return presenter;
    }

    private void attachViewToPresenter() {
        if(presenter != null && !presenter.isAttachView()) {
            presenter.attachView(getView());
        }
    }

    private void detachViewFromPresenter() {
        if(presenter != null && presenter.isAttachView()) {
            presenter.detachView();
        }
    }

    // FIXME setUpDataBinding() 대신 protected abstract int getLayoutId(); 로 만들고 작업해주는게 더 좋을것 같습니다.
    /*
      setUp()에서 사용
      dataBinding = DataBindingUtil.setContentView(this, getLayoutId());
     */
    @NonNull
    abstract protected B setUpDataBinding();

    @NonNull
    abstract protected P setUpPresenter();

    @NonNull
    abstract protected V getView();

    // FIXME abstract로 상속받는부분에서 함수를 무조건 구현하게 하는것보다는 필요한 경우 onSaveInstanceState()를 override해서 사용하도록 하는게 좋을것 같습니다. 현재 존재하는 코드에서도 실제 사용하는 부분이 없습니다.
    abstract protected void onSaveInstanceStateToBundle(@NonNull Bundle outState);

}
