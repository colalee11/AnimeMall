package com.dlsd.property.activitys.base;

import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.dlsd.property.MyApp;
import com.dlsd.property.R;
import com.dlsd.property.activitys.MainActivity;
import com.dlsd.property.base.AppManager;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityLoginBinding;
import com.dlsd.property.db.User;
import com.dlsd.property.guard.activitys.GuardMainActivity;
import com.dlsd.property.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.dlsd.property.utils.StatusBarUtil.setStatusBarDarkTheme;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;
    private boolean seePwd = false;
    AlertDialog alertDialog;
    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    @Override
    protected void initView() {
        setStatusBarDarkTheme(this, true);
        binding.btnLogin.setOnClickListener(this);
        binding.btnRegister.setOnClickListener(this);
        binding.tvForgetPwd.setOnClickListener(this);
        binding.ivSeePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seePwd = !seePwd;
                if (seePwd) {
                    setPasswordVisibility(binding.etPwd, true);
                    binding.ivSeePwd.setImageResource(R.drawable.icon_eye_open);
                } else {
                    setPasswordVisibility(binding.etPwd, false);
                    binding.ivSeePwd.setImageResource(R.drawable.icon_eye_close);
                }
            }
        });
    }

    @Override
    protected void initData() {
        SPUtil.clear(MyApp.getInstance());
        MyApp.mUserLogin = null;
        Log.e("lsh", "??????????????????");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                alertDialog = new AlertDialog.Builder(this)
                        .setTitle("????????????")
                        .setMessage("???????????????,?????????..")
                        .create();
                alertDialog.show();
                String phone = binding.etPhone.getText().toString().trim();
                String pwd = binding.etPwd.getText().toString().trim();
                if (phone.length() == 11 && phone.substring(0, 1).equals("1")) {
                    if (pwd.length() < 8) {
                        showToast("???????????????8???");
                        return;
                    }
                    login(phone, pwd);
                } else {
                    showToast("??????????????????,???????????????");
                    return;
                }
                break;
            case R.id.btn_register:
                startActivity(new Intent(LoginActivity.this, RegisterNextActivity.class));
                break;
            case R.id.tv_forget_pwd:
                startActivity(new Intent(LoginActivity.this, ForgetPwdActivity.class));
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        alertDialog.dismiss();
    }

    /**
     * ??????
     */
    private void login(String phone, String pwd) {

        // ?????????????????????
        BmobQuery<User> bmobQuery1 = new BmobQuery<User>();
        bmobQuery1.addWhereEqualTo("phone", phone);
        BmobQuery<User> bmobQuery2 = new BmobQuery<User>();
        bmobQuery2.addWhereEqualTo("pwd", pwd);
//?????????????????????and??????
        List<BmobQuery<User>> andQuerys = new ArrayList<BmobQuery<User>>();
        andQuerys.add(bmobQuery1);
        andQuerys.add(bmobQuery2);
//??????????????????and????????????
        BmobQuery<User> query = new BmobQuery<User>();
        query.and(andQuerys);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    if (object.size() > 0) {
                        MyApp.mUserLogin = object.get(0);
                        showToast("????????????");
                        SPUtil.put(LoginActivity.this, SPUtil.MOBILE, MyApp.mUserLogin.getPhone() + "");
                        SPUtil.put(LoginActivity.this, SPUtil.NICK_NAME, MyApp.mUserLogin.getNickName() + "");
                        SPUtil.put(LoginActivity.this, SPUtil.PASSWORD, MyApp.mUserLogin.getPwd() + "");
                        SPUtil.put(LoginActivity.this, SPUtil.USER_NAME, MyApp.mUserLogin.getRealName() + "");
                        SPUtil.put(LoginActivity.this, SPUtil.MOOD, MyApp.mUserLogin.getMood() + "");
                        SPUtil.put(LoginActivity.this, SPUtil.SEX, MyApp.mUserLogin.getSex() + "");
                        SPUtil.put(LoginActivity.this, SPUtil.HEAD_URL, MyApp.mUserLogin.getHeadUrl() + "");
                        SPUtil.put(LoginActivity.this, SPUtil.ROLE, MyApp.mUserLogin.getRole());
                        SPUtil.put(LoginActivity.this, SPUtil.USER_OBJECT_ID, MyApp.mUserLogin.getObjectId());
                        SPUtil.put(LoginActivity.this, SPUtil.IS_LOGIN, true);

                        if (MyApp.mUserLogin.getRole()==0) {
                            startActivity(new Intent(LoginActivity.this, GuardMainActivity.class));
                        }else {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                        finish();
                    } else {
                       alertDialog.dismiss();
                        showToast("????????????????????????");

                    }
                } else {
                    alertDialog.dismiss();
                    showToast(getString(R.string.error_msg));
                }
            }
        });
    }

    /**
     * @category ???EditText?????????????????????????????????mask??????
     */
    public void setPasswordVisibility(EditText et, boolean flag) {
        int type = flag ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                : (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et.setInputType(type);
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //??????????????????????????????????????????
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //????????????????????????????????????
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //??????2000ms??????????????????????????????Toast????????????
                Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();
                //???????????????????????????????????????????????????????????????????????????
                mExitTime = System.currentTimeMillis();
            } else {
                //??????2000ms??????????????????????????????????????????-??????System.exit()??????????????????
                AppManager.getAppManager().finishAllActivity();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
