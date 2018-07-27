package com.example.nifteam.AndroidUserDemoApp2.Mbaas;

import android.content.Context;

import com.example.nifteam.AndroidUserDemoApp2.R;
import com.example.nifteam.AndroidUserDemoApp2.Utils;
import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.LoginCallback;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBUser;

public class Mbaas {

    public static void onSignupByID(final String userId, final String pwd, final Context context, final Callback callback) {
        Utils.showLoading(context);
        NCMBUser user = new NCMBUser();
        user.setUserName(userId);
        user.setPassword(pwd);
        user.signUpInBackground(new DoneCallback() {
            @Override
            public void done(NCMBException e) {
                Utils.hideLoading();
                if (e != null) {
                    /* 処理失敗 */
                    userError(context.getResources().getText(R.string.id_pw_registration_failure).toString()
                            , e, context);
                } else {
                    /* 処理成功 */
                    signinByID(userId, pwd, context, callback);
                }
            }
        });
    }

    public static void signinByID(String userId, String pwd, final Context context, final Callback callback) {
        Utils.showLoading(context);
        try {
            NCMBUser.loginInBackground(userId, pwd, new LoginCallback() {
                @Override
                public void done(NCMBUser ncmbUser, NCMBException e) {
                    Utils.hideLoading();
                    if (e != null) {
                        userError(context.getResources().getText(R.string.id_pw_login_failure).toString()
                                , e, context);
                    } else {
                        userSuccess(context.getResources().getText(R.string.login_success).toString(),
                                ncmbUser, context, callback);
                    }
                }
            });
        } catch (NCMBException e) {
            Utils.hideLoading();
            e.printStackTrace();
        }
    }

    public static void signupByEmail(String mailAddress, final Context context, final Callback callback) {
        Utils.showLoading(context);
        NCMBUser.requestAuthenticationMailInBackground(mailAddress, new DoneCallback() {
            @Override
            public void done(NCMBException e) {
                Utils.hideLoading();
                if (e != null) {
                    userError(context.getResources().getText(R.string.email_pw_registration_failure).toString()
                            , e, context);
                } else {
                    Utils.showDialog(context, context.getResources().getText(R.string.email_pw_registration_complete).toString()
                            , new Utils.ClickListener() {
                                @Override
                                public void onOK() {
                                    Utils.showDialog(context, context.getResources().getText(R.string.message_response_registration_complete).toString()
                                            , new Utils.ClickListener() {
                                                @Override
                                                public void onOK() {
                                                    callback.onClickOK();
                                                }
                                            });
                                }
                            });
                }
            }
        });
    }

    public static void signinByEmail(String mailAddress, String pwd, final Context context, final Callback callback) {
        Utils.showLoading(context);
        NCMBUser.loginWithMailAddressInBackground(mailAddress, pwd, new LoginCallback() {
            @Override
            public void done(NCMBUser ncmbUser, NCMBException e) {
                Utils.hideLoading();
                if (e != null) {
                    /* 処理失敗 */
                    userError(context.getResources().getText(R.string.email_pw_login_failure).toString(), e, context);
                } else {
                    /* 処理成功 */
                    userSuccess(context.getResources().getText(R.string.email_pw_login_success).toString(), ncmbUser, context, callback);
                }
            }
        });
    }

    public static void signinByAnonymousID(final Context context, final Callback callback) {
        Utils.showLoading(context);
        NCMBUser.loginWithAnonymousInBackground(new LoginCallback() {
            @Override
            public void done(NCMBUser ncmbUser, NCMBException e) {
                Utils.hideLoading();
                if (e != null) {
                    /* 処理失敗 */
                    userError(context.getResources().getText(R.string.anonymous_login_failure).toString()
                            , e, context);
                } else {
                    /* 処理成功 */
                    userSuccess(context.getResources().getText(R.string.anonymous_login_success).toString()
                            , ncmbUser, context, callback);
                }
            }
        });
    }

    private static void userSuccess(String message, NCMBUser user, final Context context, final Callback callback) {
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

    private static void userError(String message, NCMBException error, Context context) {
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
