package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class find extends AppCompatActivity implements View.OnClickListener {
    private EditText findBookName;
    private EditText findResult;
    private Button find_yes;
    private Button find_no;
    private BookDaoImpl find_impl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        setTitle("查询书籍");
        init();
        find_yes.setOnClickListener(this);
        find_no.setOnClickListener(this);

    }

    private void init() {
        findBookName=(EditText)findViewById(R.id.find_bookname_text);
        findResult=(EditText)findViewById(R.id.find_result_text);
        find_yes=(Button)findViewById(R.id.find_yes_button);
        find_no=(Button)findViewById(R.id.find_no_button);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.find_yes_button:
                getresult();
                break;
            case R.id.find_no_button:
                Intent intent=new Intent(find.this,Library_function.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    private void getresult() {
        String find_Bookname=this.findBookName.getText().toString().trim();
        System.out.println(find_Bookname);
        find_impl=new BookDaoImpl();
        String result=find_impl.findBook(find_Bookname);
        System.out.println(result);
        if(result==null){
            this.findResult.setText("无查询结果.");

        }else{
            this.findResult.setText(result);
        }
    }
}