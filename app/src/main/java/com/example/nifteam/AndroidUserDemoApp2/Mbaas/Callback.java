package com.example.nifteam.AndroidUserDemoApp2.Mbaas;

import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBUser;

public interface Callback{
    void onSuccess(NCMBUser ncmbUser);
    void onSuccess();
    void onFailure(NCMBException e);
}
