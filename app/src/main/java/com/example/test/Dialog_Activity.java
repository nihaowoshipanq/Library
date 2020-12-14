package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

//增添书籍
public class Dialog_Activity extends AppCompatActivity implements View.OnClickListener{
    private EditText name;//书名文本框
    private EditText ID;//书的编号文本框
    private EditText price;//价格文本框
    private EditText result;//操作结果文本框
    private Button yes_button;//确定文本框
    private Button no_button;//取消文本框
    private BookDaoImpl impl=new BookDaoImpl();//创建实现类
    private Book book;//创建书类对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_);
        setTitle("增添书籍");//设置标题
        init();//控件初始化
        yes_button.setOnClickListener(this);
        no_button.setOnClickListener(this);
    }

    /**
     * 初始化各种控件
     */
    private void init(){
        name=(EditText)findViewById(R.id.add_bookname_text);
        ID=(EditText)findViewById(R.id.add_bookID_text);
        price=(EditText)findViewById(R.id.add_bookPrice_text);
        yes_button=(Button)findViewById(R.id.add_yes_button);
        no_button=(Button)findViewById(R.id.add_no_button);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add_yes_button:
                getResult();
                break;
            case R.id.add_no_button:
                Intent intent=new Intent(Dialog_Activity.this,Library_function.class);
                startActivity(intent);
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

        String bookname=this.name.getText().toString().trim();//获取用户键盘录入的书名
        String bookid=this.ID.getText().toString().trim();//获取用户键盘录入编号
        String bookprice=this.price.getText().toString().trim();//获取用户键盘录入的价格
        book=new Book(bookid,bookname,bookprice);//将书籍信息放到Book类中
        //调用书籍添加功能
        impl.addBook(book);






    }


}