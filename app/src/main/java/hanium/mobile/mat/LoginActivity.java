package hanium.mobile.mat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText etID, etPW;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etID = findViewById(R.id.loginId);
        etPW = findViewById(R.id.loginPwd);
        btnLogin = findViewById(R.id.loginbtn);

        //회원가입 버튼 클릭시 수행
        TextView registerButton = findViewById(R.id.gotoRegiBtn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

    }
    public void onClick(View v) {
        if(v.getId() == R.id.loginbtn) {
            //Toast.makeText(this, "비밀번호를 재입력해주세요.", Toast.LENGTH_SHORT).show();
            String userID = etID.getText().toString();
            String userPw = etPW.getText().toString();

            if(userID.length() == 0) {
                Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                etID.requestFocus();
                return;
            }
            if(userPw.length() == 0) {
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                etPW.requestFocus();
                return;
            }

            Response.Listener<String> responseListener=new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Toast.makeText(LoginActivity.this, "hihihihihi", Toast.LENGTH_SHORT).show();
                        JSONObject jasonObject=new JSONObject(response);
                        boolean success=jasonObject.getBoolean("success");
                        if (success) {//로그인 성공한 경우
//                            Toast.makeText(LoginActivity.this, "zzzzzzzzz", Toast.LENGTH_SHORT).show();
                            String userId = jasonObject.getString("userID");
                            String userPass = jasonObject.getString("userPassword");

                            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            //intent.putExtra("log", "User");
                            intent.putExtra("userID", userId);

                            startActivity(intent);
                        }
                        else{//로그인 실패한 경우
                            Toast.makeText(LoginActivity.this, "byebye", Toast.LENGTH_SHORT).show();

                            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                            return;

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            LoginRequest loginRequest=new LoginRequest(userID,userPw,responseListener);
            RequestQueue queue= Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        }
            //Intent intent = new Intent(this, RegisterActivity.class);

    }
}
