package com.example.test;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Alter_activity extends AppCompatActivity implements View.OnClickListener{

    private EditText alter_name;//原书名文本框
    private EditText alter_new_name;//新书名文本框
    private EditText alter_ID;//书的新编号文本框
    private EditText alter_price;//新价格文本框
    private EditText alter_result;//操作结果文本框
    private Button alter_yes_button;//确定文本框
    private Button alter_no_button;//取消文本框
    private BookDaoImpl alter_impl=new BookDaoImpl();//创建实现类


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_activity);
        setTitle("修改书籍");//设置标题
        init();//控件初始化
        alter_yes_button.setOnClickListener(this);
        alter_no_button.setOnClickListener(this);
    }

    /**
     * 初始化各种控件
     */
    private void init(){
        alter_name=(EditText)findViewById(R.id.alter_bookname_text);
        alter_new_name=(EditText)findViewById(R.id.new_alter_bookname_text);
        alter_ID=(EditText)findViewById(R.id.alter_bookID_text);
        alter_price=(EditText)findViewById(R.id.alter_bookPrice_text);
        alter_result=(EditText)findViewById(R.id.alter_result_text) ;
        alter_yes_button=(Button)findViewById(R.id.alter_yes_button);
        alter_no_button=(Button)findViewById(R.id.alter_no_button);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.alter_yes_button:
                getResult();
                break;
            case R.id.alter_no_button:

                break;
            default:
                break;
        }
    }

    /**
     * 操作的类
     * @param
     */
    private void getResult() {

        String bookname=this.alter_name.getText().toString().trim();//获取用户键盘录入的书名
        String newbookname=this.alter_new_name.getText().toString().trim();//获取用户输入的新书名
        String bookid=this.alter_ID.getText().toString().trim();//获取用户输入的新编号
        String bookprice=this.alter_price.getText().toString().trim();//获取用户键盘录入的价格
        String buffer=bookid+'-'+newbookname+'-'+bookprice;
        //调用修改功能
        alter_impl.alterBook(bookname,buffer);
        this.alter_result.setText("修改成功");

    }


}
