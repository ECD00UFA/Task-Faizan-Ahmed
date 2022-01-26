package com.payoneer.evaluation_task;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.payoneer.evaluation_task.models.ApplicableNetwork;
import com.payoneer.evaluation_task.models.Networks;

public class MainActivity extends AppCompatActivity implements NetworkCallback {

    private ProgressDialog dialog;
    private VMMainActivity vmMainActivity;
    private CardView cardView;
    private Button btnFind;
    private EditText etCode;
    private Networks networks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setEvents();
    }

    private void init() {
        cardView = (CardView) findViewById(R.id.card_view);
        btnFind = (Button) findViewById(R.id.btn_find);
        etCode = (EditText) findViewById(R.id.et_code);
        vmMainActivity = ViewModelProvider.AndroidViewModelFactory.getInstance(App.getInstance()).create(VMMainActivity.class);
        dialog = new ProgressDialog(this);
        dialog.setTitle("please wait...");
        dialog.setMessage("loading applicable networks");
    }

    private void setEvents() {
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etCode.getText().toString().isEmpty()) {
                    etCode.setError("*");
                    etCode.requestFocus();
                } else {
                    ApplicableNetwork applicableNetwork = new ApplicableNetwork();
                    applicableNetwork.setCode(etCode.getText().toString());
                    int index = networks.getApplicable().indexOf(applicableNetwork);
                    if (index >= 0) {
                        ApplicableNetwork validNetwork = networks.getApplicable().get(index);
                        if (validNetwork != null) {
                            switchToNewActivity(validNetwork);
                        } else {
                            Toast.makeText(MainActivity.this, "No applicable networks available!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        etCode.setError("Invalid code");
                        etCode.requestFocus();
                    }
                }
            }
        });
    }

    private void switchToNewActivity(ApplicableNetwork applicableNetwork) {
        Intent i = new Intent(this, SecondActivity.class);
        Bundle dataBundle = new Bundle();
        dataBundle.putSerializable("applicable_networks", applicableNetwork);
        i.putExtra("data_bundle", dataBundle);
        startActivity(i);
    }

    private void getApplicableNetworks() {
        dialog.show();
        vmMainActivity.getApplicableNetworks(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getApplicableNetworks();
    }

    @Override
    public void onSuccess(Networks networks) {
        dialog.dismiss();
        cardView.setVisibility(View.VISIBLE);
        etCode.requestFocus();
        this.networks = networks;
    }

    @Override
    public void onFailure() {
        dialog.dismiss();
        Toast.makeText(this, "something went wrong!", Toast.LENGTH_SHORT).show();
    }
}