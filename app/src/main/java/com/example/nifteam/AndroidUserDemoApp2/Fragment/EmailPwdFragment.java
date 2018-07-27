package com.example.nifteam.AndroidUserDemoApp2.Fragment;


import android.os.Bundle;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_pwd, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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
            Utils.showDialog(getContext(), getContext().getResources().getText(R.string.message_error_email_do_not_input).toString());
        } else {
            Mbaas.signupByEmail(edtSignupMail.getText().toString(), getContext(), new Callback() {
                @Override
                public void onClickOK() {
                    Utils.clearField(mainContainer);
                }
            });
        }
    }

    private void signin() {
        if (Utils.isBlankOrEmpty(edtSigninMail) || Utils.isBlankOrEmpty(edtSigninPwd)) {
            Utils.showDialog(getContext(), getContext().getResources().getText(R.string.message_error_not_input).toString());
        } else {
            Mbaas.signinByEmail(edtSigninMail.getText().toString(), edtSigninPwd.getText().toString(), getContext(), new Callback() {
                @Override
                public void onClickOK() {
                    Utils.clearField(mainContainer);
                }
            });
        }
    }
}
