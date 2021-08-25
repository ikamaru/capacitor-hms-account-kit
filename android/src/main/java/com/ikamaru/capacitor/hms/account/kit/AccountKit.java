package com.ikamaru.capacitor.hms.account.kit;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;

public class AccountKit {
    private static final String TAG = "AccountKit";
    Activity activity;
    AccountKit(Activity activity){
        this.activity=activity;
    }
    public void signIn(Intent intent, OnListener onListener) {
        Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(intent);
        if (authAccountTask.isSuccessful()) {
            // The sign-in is successful, and the user's ID information and authorization code are obtained.
            AuthAccount authAccount = authAccountTask.getResult();
            Log.i(TAG, "serverAuthCode1:" + authAccount.getDisplayName());
            Log.i(TAG, "serverAuthCode2:" + authAccount.getOpenId());
            JSObject result = new JSObject();
            result.put("username", authAccount.getDisplayName());
            result.put("huaweiOpenId", authAccount.getOpenId());
            onListener.onSuccess(result);
        } else {
            // The sign-in failed.
            Log.e(TAG, "sign in failed:" + ((ApiException) authAccountTask.getException()).getStatusCode());
            onListener.onFailure(authAccountTask.getException());
        }
    }

    public interface OnListener{
        void onSuccess(JSObject result);
        void onFailure(Exception exception);
    }
}
