package com.rc.graduation.seer.mvp.presenter;

import com.rc.graduation.seer.common.LoadDataType;
import com.rc.graduation.seer.di.scope.FragmentScope;
import com.rc.graduation.seer.mvp.model.apis.interfaces.PhotoModuleApi;
import com.rc.graduation.seer.mvp.model.entity.photos.PhotoGirl;
import com.rc.graduation.seer.mvp.presenter.interfaces.PhotoPresenter;
import com.rc.graduation.seer.mvp.view.PhotoGirlView;

import java.util.List;

import javax.inject.Inject;

/**
 * @version 1.0
 * @author  rosen
 * Created by Administrator on 2016/11/29.
 */
@FragmentScope
public class PhotoPresenterImpl extends BasePresenterImpl<PhotoGirlView,PhotoModuleApi,List<PhotoGirl>> implements PhotoPresenter {

    private int mLoadType = LoadDataType.TYPE_FIRST_LOAD;
    private int mSize = 20;
    private int mStartPage;

    @Inject
    public PhotoPresenterImpl(PhotoGirlView rootView,PhotoModuleApi moduleApi){
        super(rootView,moduleApi);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(mView != null){
            loadPhotoData();
        }
    }

    @Override
    public void loadPhotoData() {
        beforeRequest();
        mLoadType = LoadDataType.TYPE_FIRST_LOAD;
        mStartPage = 1;
        loadPhotoDataRequest(mStartPage);
    }

    @Override
    public void refreshData() {
        mStartPage = 1;
        mLoadType = LoadDataType.TYPE_REFRESH;
        loadPhotoDataRequest(mStartPage);
    }

    @Override
    public void loadMore() {
        mStartPage++;
        mLoadType = LoadDataType.TYPE_LOAD_MORE;
        loadPhotoDataRequest(mStartPage);
    }

    private void loadPhotoDataRequest(int starPage){
        mSubscription = mApi.getPhotoList(this,mSize,starPage);
    }

    @Override
    public void success(List<PhotoGirl> data) {
        super.success(data);
        mView.updateListView(data,mLoadType);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        if(mLoadType == LoadDataType.TYPE_LOAD_MORE){
            mLoadType--;
        }
        mView.updateErrorView(mLoadType);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
