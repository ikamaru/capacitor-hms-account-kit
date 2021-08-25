package com.ikamaru.capacitor.hms.account.kit;

import android.content.Intent;
import android.util.Log;

import androidx.activity.result.ActivityResult;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;

@CapacitorPlugin(name = "AccountKit")
public class AccountKitPlugin extends Plugin {

    private static final String TAG = "AccountKitPlugin";
    private AccountKit implementation;

    @Override
    public void load() {
        super.load();
        implementation= new AccountKit(bridge.getActivity());
    }

    @PluginMethod
    public void signIn(PluginCall call) {
        AccountAuthParams authParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM).setAuthorizationCode().createParams();
        AccountAuthService service = AccountAuthManager.getService(bridge.getActivity(), authParams);
        startActivityForResult(call,service.getSignInIntent(), "getHuaweiIdResult");
    }
    @ActivityCallback
    private void getHuaweiIdResult(final PluginCall call, ActivityResult result) {
        Intent intent = result.getData();
        if (intent != null) {
            implementation.signIn(intent,new AccountKit.OnListener() {
                @Override
                public void onSuccess(JSObject result) {
                    call.resolve(result);
                }
                @Override
                public void onFailure(Exception exception) {
                    call.reject(exception.getMessage());
                }
            });
        } else {
            call.reject("Intent Null");
        }
    }
    @PluginMethod
    public void signOut(PluginCall call) {
        AccountAuthParams authParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM).setAuthorizationCode().createParams();
        AccountAuthService service = AccountAuthManager.getService(bridge.getActivity(),authParams);
        Task<Void> signOutTask = service.signOut();
        signOutTask.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                // Processing after the sign-out.
                Log.i(TAG, "signOut complete");
                call.resolve();
            }
        });
        signOutTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                call.reject(e.getMessage());
            }
        });
    }

}
