package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private  EditText username; //用户名输入框
    private  EditText password; //密码输入框
    private Button LoginButton; //登录按钮
    private Button  RegisterButton; //注册按钮
    private CheckBox passwordCheck; //显示密码复选框
//    private EditText new_username;//注册页面的用户名输入框
//    private EditText new_password;//注册页面的密码输入框
//    private EditText new_again_password;//注册页面的再次输入密码框

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        initialize(); //初始化各种控件
        ischeck(passwordCheck); //设置密码显示或不显示
        LoginButton.setOnClickListener(this);
        RegisterButton.setOnClickListener(this);




    }
    /**
     * 初始化控件
     */
    private void initialize(){
        username=(EditText)findViewById(R.id.username_text); //创建用户名输入框对象
        password=(EditText)findViewById(R.id.password_text); //创建密码输入框对象
        RegisterButton=(Button)findViewById(R.id.register_button); //创建注册按钮对象
        LoginButton =(Button)findViewById(R.id.login_button);  //创建登录按钮对象
        passwordCheck=(CheckBox)findViewById(R.id.password_checkbook); //创建显示密码复选框对象
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
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    //不显示明文，即设置为不可见的密码
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    /**
     * 点击登录或注册按钮时触发的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button://登录的点击事件
                login();
                break;
            case R.id.register_button://注册的点击事件
                //点击注册按钮时转去第二个活动页面
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    /**
     * 登录
     */
    private  void login()  {
        //获取输入的用户名与密码
        String usernametext=username.getText().toString().trim();
        String passwordtext=password.getText().toString().trim();

        System.out.println(usernametext+" "+passwordtext);
        //定义用户名和密码规则
        String usernameregex = "[a-zA-Z]{5}";
        String passwordregex = "[0-9]{5}";
        //校验用户名
        if (!(usernametext.matches(usernameregex))) {
            // JOptionPane.showMessageDialog(this, "用户名格式不对，请输入5位单词字母)");
//            new AlertDialog.Builder(MainActivity.this)
//                    .setTitle("error").setMessage("用户名格式不对，请输入5位单词字母")
//                    .setPositiveButton("好的",null).show();
            Toast.makeText(MainActivity.this,"用户名格式不对，请输入5位单词字母",Toast.LENGTH_LONG).show();
            //清空用户名框文本，并把输入光标放回到输入框
            this.username.setText("");
            this.username.requestFocus();
            return;
        }
        //校验密码
        if (!(passwordtext.matches(passwordregex))) {
            //JOptionPane.showMessageDialog(this, "密码格式不对，请输入6-12位任意字符)");
            Toast.makeText(MainActivity.this,"密码格式不正确，请输入6-12位任意字符",Toast.LENGTH_LONG).show();
//              new AlertDialog.Builder(MainActivity.this)
//                      .setTitle("error").setMessage("密码格式不正确，请输入6-12位任意字符")
//                      .setPositiveButton("好的",null).show();
//
            //清空用户名框文本,并把输入光标放回到输入框
            this.password.setText("");
            this.username.requestFocus();
            return;
        }

        //调用登录功能
        UserDaoImpl loginImpl=new UserDaoImpl();
        boolean flag=loginImpl.isloging(this,usernametext,passwordtext);
        if(!flag){
            //如果登录不成功
            Toast.makeText(MainActivity.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
            //将用户名框与密码框清空，并把光标放回框中
            this.username.setText("");
            this.password.setText("");
            this.username.requestFocus();
            this.password.requestFocus();

        }else{
            //登录成功
            Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(MainActivity.this,Library_function.class);
            startActivity(intent);

        }
    }


    private void openPerssion() {
        verifyStoragePermissions(this);
    }

    /**
     * @des 权限组的获取
     * @author DELL
     * @time  10:44
     */
    public void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}