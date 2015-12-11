package com.zertop.linetool;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ResultsActivity extends Activity {
    ImageView copyPlainButton;
    ImageView copyForumButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        TextView results = (TextView) findViewById(R.id.resultsTextbox);
        results.setText(Variables.report.getIntelReport());


        copyPlainButton = (ImageView) findViewById(R.id.copyPlain);
        copyPlainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipboard(fileToString(Variables.report.getPlainTxtPath()));
                popUpNotification("Full plain report copied!");
            }
        });

        copyForumButton = (ImageView) findViewById(R.id.copyForum);
        copyForumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipboard(fileToString(Variables.report.getFormattedTxtPath()));
                popUpNotification("Full formatted report copied!");
            }
        });
    }

    private String fileToString (String path)
    {
        String out = "";
        try {
            Scanner file = new Scanner(new File(path));
                while (file.hasNextLine())
                {
                    out+= "\n"+file.nextLine();
                }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return out;
    }

    private void copyToClipboard (String text)
    {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("LineTool",text);
        clipboard.setPrimaryClip(data);
    }

    private void popUpNotification (String text)
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast popUp = Toast.makeText(context, text, duration);
        popUp.show();
    }
}