//Zertop™
//www.zertop.com
package com.zertop.linetool;

import Modules.Analysis.GenerateReport;
import Modules.Misc.Sleep;
import Modules.NetworkTools.DetermineIP;
import Modules.NetworkTools.PingIP;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

public class RunningTestsActivity extends Activity {
    static CheckBox determiningCorrectIP;
    static CheckBox pingingTelkomEquipment;
    static CheckBox generatingReport;
    static CheckBox finishingUp;
    static TextView runningTests;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.running);

        //Variables
        determiningCorrectIP = (CheckBox) findViewById(R.id.determiningCorrectIP);
        pingingTelkomEquipment = (CheckBox) findViewById(R.id.pingingTelkomEquipment);
        generatingReport = (CheckBox) findViewById(R.id.generatingReport);
        finishingUp = (CheckBox) findViewById(R.id.finishingUp);
        runningTests = (TextView) findViewById(R.id.runningTests);
        AsyncTask test, dots;

        dots = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {

                Runnable dotsRun = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 50; i++)
                        {
                            publishProgress("Running Tests.");
                            Sleep.Time(1);
                            publishProgress("Running Tests..");
                            Sleep.Time(1);
                            publishProgress("Running Tests...");
                            Sleep.Time(1);
                        }
                    }
                };

                Thread dotsThread = new Thread(dotsRun);
                dotsThread.start();

                return null;
            }

            @Override
            protected void onProgressUpdate(Object[] values) {
                runningTests.setText((String) values[0]);
            }
        };

        test = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                dots.execute();
            }

            @Override
            protected Void doInBackground(Object[] params) {
                //Determine correct IP
                String determinedIP = DetermineIP.run();
                publishProgress(1);

                //Ping the IP
                String pingResults = PingIP.run(determinedIP, 30, 30);
                publishProgress(2);
                Sleep.Time(1);

                //Generate Report from Ping Results
                Variables.report = new GenerateReport(pingResults);
                publishProgress(3);
                Sleep.Time(1);

                //Finish
                publishProgress(4);
                Sleep.Time(1);
                return null;
            }

            @Override
            protected void onProgressUpdate(Object[] values) {
                switch ((int) values[0])
                {
                    case 1: determiningCorrectIP.setChecked(true);
                            break;
                    case 2: pingingTelkomEquipment.setChecked(true);
                            break;
                    case 3: generatingReport.setChecked(true);
                            break;
                    case 4: finishingUp.setChecked(true);
                            Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                            startActivity(intent);
                            break;
                }
            }
        };

        test.execute();
    }

}