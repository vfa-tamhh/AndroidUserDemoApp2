package com.example.nifteam.AndroidUserDemoApp2.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nifteam.AndroidUserDemoApp2.Mbaas.Callback;
import com.example.nifteam.AndroidUserDemoApp2.Mbaas.Mbaas;
import com.example.nifteam.AndroidUserDemoApp2.R;
import com.example.nifteam.AndroidUserDemoApp2.Utils;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBUser;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnonymousFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.btn_sign_in)
    Button btnSignin;

    public AnonymousFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anonymous, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        btnSignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                signinByAnonymousID();
                break;

        }
    }

    private void signinByAnonymousID() {
        Utils.showLoading(getContext());
        Mbaas.signinByAnonymousID(new Callback() {
            @Override
            public void onSuccess(NCMBUser ncmbUser) {
                Utils.hideLoading();
                Mbaas.userSuccess(getContext().getResources().getText(R.string.anonymous_login_success).toString()
                        , ncmbUser, getContext(), new Mbaas.CallbackButtonOK() {
                            @Override
                            public void onClickOK() {

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
                Mbaas.userError(getContext().getResources().getText(R.string.anonymous_login_failure).toString()
                        , e, getContext());
            }
        });

    }
}
