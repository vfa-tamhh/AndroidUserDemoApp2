package com.example.nifteam.AndroidUserDemoApp2.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.nifteam.AndroidUserDemoApp2.Mbaas.Callback;
import com.example.nifteam.AndroidUserDemoApp2.Mbaas.Mbaas;
import com.example.nifteam.AndroidUserDemoApp2.R;
import com.example.nifteam.AndroidUserDemoApp2.Utils;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBUser;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EmailPwdFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.btn_sign_up)
    Button btnSignup;
    @BindView(R.id.btn_sign_in)
    Button btnSignin;

    @BindView(R.id.edt_sign_up_mail)
    EditText edtSignupMail;
    @BindView(R.id.edt_sign_in_mail)
    EditText edtSigninMail;
    @BindView(R.id.edt_sign_in_pwd)
    EditText edtSigninPwd;

    @BindView(R.id.main_container)
    LinearLayout mainContainer;

    public EmailPwdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_pwd, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        btnSignup.setOnClickListener(this);
        btnSignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_up:
                signup();
                break;
            case R.id.btn_sign_in:
                signin();
                break;
        }
    }

    private void signup() {
        if (Utils.isBlankOrEmpty(edtSignupMail)) {
            Utils.showDialog(getContext(), Objects.requireNonNull(getContext()).getResources().getText(R.string.message_error_email_do_not_input).toString());
        } else {
            Utils.showLoading(getContext());
            Mbaas.signupByEmail(edtSignupMail.getText().toString(), new Callback() {
                @Override
                public void onSuccess(NCMBUser ncmbUser) {
                    Utils.hideLoading();
                }

                @Override
                public void onSuccess() {
                    Utils.hideLoading();
                    Utils.showDialog(getContext(), getContext().getResources().getText(R.string.email_pw_registration_complete).toString()
                            , new Utils.ClickListener() {
                                @Override
                                public void onOK() {
                                    Utils.showDialog(getContext(), getContext().getResources().getText(R.string.message_response_registration_complete).toString()
                                            , new Utils.ClickListener() {
                                                @Override
                                                public void onOK() {
                                                    Utils.clearField(mainContainer);
                                                }
                                            });
                                }
                            });
                }

                @Override
                public void onFailure(NCMBException e) {
                    Utils.hideLoading();
                    Mbaas.userError(getContext().getResources().getText(R.string.email_pw_registration_failure).toString()
                            , e, getContext());
                }
            });

        }
    }

    private void signin() {
        if (Utils.isBlankOrEmpty(edtSigninMail) || Utils.isBlankOrEmpty(edtSigninPwd)) {
            Utils.showDialog(getContext(), Objects.requireNonNull(getContext()).getResources().getText(R.string.message_error_not_input).toString());
        } else {
            Utils.showLoading(getContext());
            Mbaas.signinByEmail(edtSigninMail.getText().toString(), edtSigninPwd.getText().toString(), new Callback() {
                @Override
                public void onSuccess(NCMBUser ncmbUser) {
                    Utils.hideLoading();
                    Mbaas.userSuccess(getContext().getResources().getText(R.string.email_pw_login_success).toString(), ncmbUser, getContext(), new Mbaas.CallbackButtonOK() {
                        @Override
                        public void onClickOK() {
                            Utils.clearField(mainContainer);
                        }
                    });
                }

                @Override
                public void onSuccess() {
                    Utils.hideLoading();
                }

                @Override
                public void onFailure(NCMBException e) {
                    Utils.hideLoading();
                    Mbaas.userError(getContext().getResources().getText(R.string.email_pw_login_failure).toString(), e, getContext());
                }
            });
        }
    }
}
