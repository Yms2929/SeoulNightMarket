package seoulnightmarket.seoulnightmarket.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import seoulnightmarket.seoulnightmarket.R;

public class MembershipJoinActivity extends AppCompatActivity {
    private EditText phonenumberText;
    private EditText passwordText;
    private EditText repasswordText;
    private EditText nicknameText;
    private String phonenumber;
    private String password;
    private String repassword;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_join);

        phonenumberText = (EditText) findViewById(R.id.phoneNumber);
        passwordText = (EditText) findViewById(R.id.password);
        repasswordText = (EditText) findViewById(R.id.repassword);
        nicknameText = (EditText) findViewById(R.id.nickname);

        phonenumberText.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.ADD.SRC_ATOP);
        passwordText.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.ADD.SRC_ATOP);
        repasswordText.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.ADD.SRC_ATOP);
        nicknameText.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.ADD.SRC_ATOP);
    }

    public void btnConfirm(View v) { // 회원 가입 버튼 서버로 회원 정보 전송
        phonenumber = phonenumberText.getText().toString();
        password = passwordText.getText().toString();
        repassword = repasswordText.getText().toString();
        nickname = nicknameText.getText().toString();

        if (phonenumber.length() == 0) {
            Toast.makeText(getApplicationContext(), "핸드폰 번호를 입력하세요", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() == 0) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
        }
        else if (repassword.length() == 0) {
            Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인하세요", Toast.LENGTH_SHORT).show();
        }
        else if (nickname.length() == 0) {
            Toast.makeText(getApplicationContext(), "닉네임을 입력하세요", Toast.LENGTH_SHORT).show();
        }
        else {
            startActivity(new Intent(MembershipJoinActivity.this, LoginActivity.class));

            Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();
        }
    }
}