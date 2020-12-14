package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class cancel_activity extends AppCompatActivity implements View.OnClickListener{
    private EditText cancelBookName;
    private EditText cancelID;
    private EditText cancelRecult;
    private Button cancel_yes;
    private Button cancel_no;
    private BookDaoImpl cancelImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_activity);
        setTitle("删除书籍");
        init();
        cancel_yes.setOnClickListener(this);
        cancel_no.setOnClickListener(this);


    }

    private void init() {
        cancelBookName=(EditText)findViewById(R.id.cancel_bookname_text);
        cancelID=(EditText)findViewById(R.id.cancel_bookID_text);
        cancelRecult=(EditText)findViewById(R.id.cancel_result_text);
        cancel_yes=(Button)findViewById(R.id.cancel_yes_button);
        cancel_no=(Button)findViewById(R.id.cancel_no_button);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cancel_yes_button:
                getResult();
                break;
            case R.id.cancel_no_button:
                Intent intent=new Intent(cancel_activity.this,Library_function.class);
                startActivity(intent);
                break;
        }

    }

    private void getResult() {

        String cancel_bookName=this.cancelBookName.getText().toString().trim();
        String cancel_ID=this.cancelID.getText().toString().trim();
        cancelImpl=new BookDaoImpl();
        cancelImpl.cancelBook(cancel_bookName,cancel_ID);
        this.cancelRecult.setText("删除成功");
    }
}