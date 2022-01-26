package com.payoneer.evaluation_task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.payoneer.evaluation_task.models.ApplicableNetwork;
import com.payoneer.evaluation_task.models.InputElement;

public class SecondActivity extends AppCompatActivity {
    private LinearLayout contentPane;
    private ApplicableNetwork applicableNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Applicable Networks");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();
        getDataFromIntent();
        setupViews();
    }

    private void init() {
        contentPane = (LinearLayout) findViewById(R.id.content_pane);
    }

    private void getDataFromIntent() {
        if (getIntent() != null) {
            if (getIntent().hasExtra("data_bundle")) {
                if (getIntent().getBundleExtra("data_bundle") != null) {
                    Bundle dataBundle = getIntent().getBundleExtra("data_bundle");
                    if (dataBundle.containsKey("applicable_networks")) {
                        this.applicableNetwork = (ApplicableNetwork) dataBundle.getSerializable("applicable_networks");
                    }
                }
            }
        }
    }

    private void setupViews() {
        if (this.applicableNetwork != null) {
            for (InputElement element : this.applicableNetwork.getInputElements()) {
                TextInputLayout textInputLayout = (TextInputLayout) LayoutInflater.from(this).inflate(R.layout.layout_text_input_edittext, null);
                textInputLayout.setHint(element.getName());

                contentPane.addView(textInputLayout);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}