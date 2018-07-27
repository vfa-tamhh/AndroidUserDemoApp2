package com.example.nifteam.AndroidUserDemoApp2.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nifteam.AndroidUserDemoApp2.Mbaas.Callback;
import com.example.nifteam.AndroidUserDemoApp2.Mbaas.Mbaas;
import com.example.nifteam.AndroidUserDemoApp2.R;
import com.example.nifteam.AndroidUserDemoApp2.Utils;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anonymous, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        btnSignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                Mbaas.signinByAnonymousID(getContext(), new Callback() {
                    @Override
                    public void onClickOK() {

                    }
                });
                break;

        }
    }
}
