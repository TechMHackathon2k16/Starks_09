package com.example.hemantsaini.myresult;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class StudentDetailActivity extends AppCompatActivity implements View.OnClickListener {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);


    }

    @Override
    public void onClick(View v) {
    }


    public interface notifychange
    {
        public void notifydatachange();
    }
}
