package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText new_username;//注册页面的用户名输入框
    private EditText new_password;//注册页面的密码输入框
    private EditText new_again_password;//注册页面的再次输入密码框
    private Button new_register;//注册按钮
    private Button back;//返回按钮
    private CheckBox ispassword;//显示密码按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();//初始化控件
        ischeck(ispassword);
        new_register.setOnClickListener(this);
        back.setOnClickListener(this);


    }

    /**
     * 初始化控件
     */
    private void init() {
        new_username=(EditText)findViewById(R.id.newusername_text);//创建注册页面的用户名输入框对象
        new_password=(EditText)findViewById(R.id.newpassword_text);//创建注册页面的密码输入框对象
        new_again_password=(EditText)findViewById(R.id.new_again_password_text);//创建注册页面的再次确认密码输入框对象
        new_register=(Button)findViewById(R.id.yes_register_button);//创建注册按钮对象
        back=(Button)findViewById(R.id.back_button);//创建返回按钮对象
        ispassword=(CheckBox)findViewById(R.id.new_password_checkbook);//创建显示密码按钮对象
    }
    private void register(){
        //获取输入的用户名与密码
        String usernametext=new_username.getText().toString().trim();
        String passwordtext=new_password.getText().toString().trim();
        //定义用户名和密码规则
        String usernameregex = "[a-zA-Z]{5}";
        String passwordregex = "[0-9]{5}";
        //校验用户名
        if (!(usernametext.matches(usernameregex))) {
            // JOptionPane.showMessageDialog(this, "用户名格式不对，请输入5位单词字母)");
            //            new AlertDialog.Builder(MainActivity.this)
            //                    .setTitle("error").setMessage("用户名格式不对，请输入5位单词字母")
            //                    .setPositiveButton("好的",null).show();
            Toast.makeText(RegisterActivity.this,"用户名格式不对，请输入5位单词字母",Toast.LENGTH_LONG).show();
            //清空用户名框文本，并把输入光标放回到输入框
            this.new_username.setText("");
            this.new_username.requestFocus();
            return;
        }
        //校验密码
        if (!(passwordtext.matches(passwordregex))) {
            //JOptionPane.showMessageDialog(this, "密码格式不对，请输入6-12位任意字符)");
            Toast.makeText(RegisterActivity.this,"密码格式不正确，请输入5位数字字符",Toast.LENGTH_LONG).show();
            //              new AlertDialog.Builder(MainActivity.this)
            //                      .setTitle("error").setMessage("密码格式不正确，请输入6-12位任意字符")
            //                      .setPositiveButton("好的",null).show();
            //
            //清空用户名框文本,并把输入光标放回到输入框
            this.new_password.setText("");
            this.new_password.requestFocus();
            return;
        }
        //校验两次输入的密码是否一致
        String again_password=new_again_password.getText().toString().trim();
        if(!again_password.equals(new_password)){
            Toast.makeText(RegisterActivity.this,"密码不一致",Toast.LENGTH_LONG).show();
        }
        //调用注册功能
        User user=new User(usernametext,passwordtext);
        UserDaoImpl loginImpl=new UserDaoImpl();
        boolean result=loginImpl.register(this,user);//判断是否注册成功
        if(result){
            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_LONG).show();
            //清空用户名框文本,并把输入光标放回到输入框
            this.new_username.setText("");
            this.new_username.requestFocus();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.yes_register_button:
                register();//校验输入的用户名与密码
                break;
            case R.id.back_button:
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    /**
     * 设置显示密码的点击操作
     *
     */
    private void ischeck(CheckBox passwordCheck){
        passwordCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //显示明文，即设置为可见的密码
                    new_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    new_again_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    //不显示明文，即设置为不可见的密码
                    new_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    new_again_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }
}
