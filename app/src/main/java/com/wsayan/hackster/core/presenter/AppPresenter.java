package com.wsayan.hackster.core.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.wsayan.hackster.core.config.ApiClient;
import com.wsayan.hackster.core.config.db.DbConfig;
import com.wsayan.hackster.core.config.db.DbCrud;
import com.wsayan.hackster.core.utility.DbUtils;
import com.wsayan.hackster.core.utility.SharedPrefUtils;
import com.wsayan.hackster.core.utility.WebUtils;

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
