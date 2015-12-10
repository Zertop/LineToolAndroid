package com.zertop.linetool;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultsActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        TextView results = (TextView) findViewById(R.id.resultsTextbox);

        results.setText(Variables.report.getIntelReport());
    }
}