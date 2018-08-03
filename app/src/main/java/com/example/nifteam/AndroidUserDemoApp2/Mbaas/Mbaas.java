package com.example.nifteam.AndroidUserDemoApp2.Mbaas;

import android.content.Context;

import com.example.nifteam.AndroidUserDemoApp2.R;
import com.example.nifteam.AndroidUserDemoApp2.Utils;
import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.LoginCallback;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBUser;

public class Mbaas {

    public interface CallbackButtonOK{
        void onClickOK();
    }

    public static void onSignupByID(final String userId, final String pwd, final Callback callback) {

        NCMBUser user = new NCMBUser();
        user.setUserName(userId);
        user.setPassword(pwd);
        user.signUpInBackground(new DoneCallback() {
            @Override
            public void done(NCMBException e) {
                if (e != null) {
                    /* 処理失敗 */
                    callback.onFailure(e);


                } else {
                    /* 処理成功 */
                    callback.onSuccess();

                }
            }
        });
    }

    public static void signinByID(String userId, String pwd, final Callback callback) {

        try {
            NCMBUser.loginInBackground(userId, pwd, new LoginCallback() {
                @Override
                public void done(NCMBUser ncmbUser, NCMBException e) {
                    Utils.hideLoading();
                    if (e != null) {
                        callback.onFailure(e);
                    } else {
                        callback.onSuccess(ncmbUser);
                    }
                }
            });
        } catch (NCMBException e) {
            Utils.hideLoading();
            e.printStackTrace();
        }
    }

    public static void signupByEmail(String mailAddress, final Callback callback) {

        NCMBUser.requestAuthenticationMailInBackground(mailAddress, new DoneCallback() {
            @Override
            public void done(NCMBException e) {

                if (e != null) {
                    callback.onFailure(e);

                } else {
                    callback.onSuccess();

                }
            }
        });
    }

    public static void signinByEmail(String mailAddress, String pwd, final Callback callback) {

        NCMBUser.loginWithMailAddressInBackground(mailAddress, pwd, new LoginCallback() {
            @Override
            public void done(NCMBUser ncmbUser, NCMBException e) {
                Utils.hideLoading();
                if (e != null) {
                    /* 処理失敗 */
                    callback.onFailure(e);

                } else {
                    /* 処理成功 */
                    callback.onSuccess(ncmbUser);

                }
            }
        });
    }

    public static void signinByAnonymousID(final Callback callback) {

        NCMBUser.loginWithAnonymousInBackground(new LoginCallback() {
            @Override
            public void done(NCMBUser ncmbUser, NCMBException e) {

                if (e != null) {
                    /* 処理失敗 */
                    callback.onFailure(e);

                } else {
                    /* 処理成功 */
                    callback.onSuccess(ncmbUser);

                }
            }
        });
    }

    public static void userSuccess(String message, NCMBUser user, final Context context, final CallbackButtonOK callback) {
        String sDisplay = message + " objectId: " + user.getObjectId();
        Utils.showDialog(context, sDisplay, new Utils.ClickListener() {
            @Override
            public void onOK() {
                logout(context);
                Utils.showDialog(context, context.getResources().getText(R.string.logged_out).toString(), new Utils.ClickListener() {
                    @Override
                    public void onOK() {
                        callback.onClickOK();
                    }
                });
            }
        });
    }

    public static void userError(String message, NCMBException error, Context context) {
        String sDisplay = message + " " + error.getMessage();
        Utils.showDialog(context, sDisplay);
    }

    private static void logout(Context context) {
        try {
            Utils.showLoading(context);
            NCMBUser.logout();
            Utils.hideLoading();
        } catch (NCMBException e) {
            Utils.hideLoading();
            e.printStackTrace();
        }
    }


}
