package com.payoneer.evaluation_task;

import com.payoneer.evaluation_task.models.Networks;

public interface NetworkCallback {
    void onSuccess(Networks networks);

    void onFailure();
}
