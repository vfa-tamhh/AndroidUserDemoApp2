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

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IDPwdFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.btn_sign_up)
    Button btnSignup;
    @BindView(R.id.btn_sign_in)
    Button btnSignin;

    @BindView(R.id.edt_sign_up_id)
    EditText edtSignupId;
    @BindView(R.id.edt_sign_up_pwd)
    EditText edtSignupPwd;
    @BindView(R.id.edt_sign_up_pwd_confirm)
    EditText edtSignupPwdConfirm;

    @BindView(R.id.edt_sign_in_id)
    EditText edtSigninId;
    @BindView(R.id.edt_sign_in_pwd)
    EditText edtSigninPwd;

    @BindView(R.id.main_container)
    LinearLayout mainContainer;

    public IDPwdFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_idpwd, container, false);
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
        if (Utils.isBlankOrEmpty(edtSignupId) || Utils.isBlankOrEmpty(edtSignupPwd) || Utils.isBlankOrEmpty(edtSignupPwdConfirm)) {
            Utils.showDialog(getContext(), Objects.requireNonNull(getContext()).getResources().getText(R.string.message_error_not_input).toString());
        } else if (!edtSignupPwd.getText().toString().equals(edtSignupPwdConfirm.getText().toString())) {
            Utils.showDialog(getContext(), Objects.requireNonNull(getContext()).getResources().getText(R.string.message_error_pwd_do_not_match).toString());
        } else {
            Mbaas.onSignupByID(edtSignupId.getText().toString(), edtSignupPwd.getText().toString(), getContext(), new Callback() {
                @Override
                public void onClickOK() {
                    Utils.clearField(mainContainer);
                }
            });
        }
    }

    private void signin() {
        if (Utils.isBlankOrEmpty(edtSigninId) || Utils.isBlankOrEmpty(edtSigninPwd)) {
            Utils.showDialog(getContext(), Objects.requireNonNull(getContext()).getResources().getText(R.string.message_error_not_input).toString());
        } else {
            Mbaas.signinByID(edtSigninId.getText().toString(), edtSigninPwd.getText().toString(), getContext(), new Callback() {
                @Override
                public void onClickOK() {
                    Utils.clearField(mainContainer);
                }
            });
        }
    }
}
