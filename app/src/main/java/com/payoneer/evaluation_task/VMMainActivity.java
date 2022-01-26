package com.payoneer.evaluation_task;

import androidx.lifecycle.ViewModel;

public class VMMainActivity extends ViewModel {


    public void getApplicableNetworks(NetworkCallback networkCallback) {
        Repository.getInstance().getApplicableNetworksFromUrl(networkCallback);
    }

}
