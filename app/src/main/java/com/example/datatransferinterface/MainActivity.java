package com.example.datatransferinterface;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroupOverlay;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private JNIInteract jniInteract;
    Button b1,b2,b3,b4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=findViewById(R.id.button1);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);
        b4=findViewById(R.id.button4);
        jniInteract=new JNIInteract();
        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = findViewById(R.id.sample_text);

                // Getting String From Native CPP through JNI
                String stringFromNative = jniInteract.stringFromNative();

                // Update Text Box
                textView.setText(stringFromNative);
            }
        });
        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = findViewById(R.id.sample_text);

                // Temp data which passing to JNI
                char[] charArray = {'a', 'b', 'c'};
                int Year = 2019;
                float Rating = 2.3f;

                // Pass data & get error code
                int newYear = jniInteract.passingDataToJni(charArray, Year, Rating);
                if (newYear != Year) {
                    textView.setText("Data Pass Success\nUpdated Year: "+newYear);
                } else {
                    textView.setText("Data Pass Failed\nUpdated Year: "+newYear);
                }
            }
        });
        b3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = findViewById(R.id.sample_text);
                //Create Sample Data Object
                ClassPass Obj = new ClassPass();
                Obj.setSampleInt(0);
                Obj.setSampleBoolean(true);
                Obj.setSampleString("");

                // Pass data object to Native lib & get error code
                int ret = jniInteract.passObjectToJNI(Obj);

                if (ret == 0) {
                    // if succeded in passing object, show updated values from JNI
                    textView.setText(Obj.getSampleString());
                } else {
                    //Failed
                    textView.setText("Failed to pass Object");
                }
            }
        });
        b4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                    TextView textView = findViewById(R.id.sample_text);
                    ClassPass Obj = jniInteract.getObjectFromJNI();
                    textView.setText(Obj.getSampleString());
            }
        });
    }

}
