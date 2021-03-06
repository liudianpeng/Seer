package com.rc.graduation.seer;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.rc.graduation.seer.common.AppManager;
import com.rc.graduation.seer.db.DBManager;
import com.rc.graduation.seer.di.component.AppComponent;
import com.rc.graduation.seer.di.component.DaggerAppComponent;
import com.rc.graduation.seer.di.module.AppModule;
import com.rc.graduation.seer.utils.SharedPreferencesUtil;
import com.socks.library.KLog;
import com.ys.yoosir.xianz.greendao.gen.DaoMaster;
import com.ys.yoosir.xianz.greendao.gen.DaoSession;

import org.greenrobot.greendao.database.Database;

import javax.inject.Inject;

/**
 * @version 1.0
 * Created by rosen on 2016/10/21 0021.
 */
public class MyApplication extends Application{
    private final String TAG = this.getClass().getSimpleName();
    private static MyApplication mApplication;

    private AppComponent mAppComponent;
    private AppModule mAppModule;
    @Inject
    protected AppManager mAppManager;

    private static DaoSession mDaoSession;

    public static Context getInstance(){
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        this.mAppModule = new AppModule(this,mAppManager);//提供application
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(mAppModule)
                .build();

        initDayNightMode();
        initDaoSession();
        KLog.init(true);
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if(mAppModule != null){
            this.mAppModule = null;
        }
        if(mAppManager != null){
            this.mAppManager.release();
            this.mAppManager = null;
        }
        if(mApplication != null){
            mApplication = null;
        }
        DBManager.closeDB();
    }

    /**
     *  将AppComponent 返回出去，供其他地方使用，AppComponent接口中声明的方法返回的实例，
     *  在getAppComponent()拿到对象后都可以直接使用
     * @return
     */
    public AppComponent getAppComponent(){
        return mAppComponent;
    }

    private void initDayNightMode() {
        if (SharedPreferencesUtil.isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void initDaoSession(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"xianz-db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession(){
        return mDaoSession;
    }
}
