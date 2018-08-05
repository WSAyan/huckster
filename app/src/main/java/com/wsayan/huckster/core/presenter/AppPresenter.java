package com.wsayan.huckster.core.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.wsayan.huckster.core.config.ApiClient;
import com.wsayan.huckster.core.config.db.DbConfig;
import com.wsayan.huckster.core.config.db.DbCrud;
import com.wsayan.huckster.core.utility.DbUtils;
import com.wsayan.huckster.core.utility.SharedPrefUtils;
import com.wsayan.huckster.core.utility.WebUtils;

/**
 * Created by wahid.sadique on 9/12/2017.
 */

public class AppPresenter {

    public IApiInteractor getApiInterface() {
        return ApiClient.getClient(WebUtils.BASE_URL, WebUtils.REQUEST_TIMEOUT).create(IApiInteractor.class);
    }

    public IDbInteractor getDbInterface(Context context) {
        return new DbInteractor(new DbCrud(), new DbConfig(context, DbUtils.DB_NAME, DbUtils.DB_VERSION));
    }

    public SharedPreferences getSharedPrefInterface(Context context) {
        return context.getSharedPreferences(SharedPrefUtils.SPF_NAME, SharedPrefUtils.SPF_MODE);
    }
}
