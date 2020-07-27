package hanium.mobile.mat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText etID, etPw, etName, etEmail,etPwCheck;
    Button btn_register, btn_check;
    boolean validate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etID = findViewById(R.id.regiID);
        etPw = findViewById(R.id.regiPw);
        etPwCheck = findViewById(R.id.regiPwCheck);
        etName = findViewById(R.id.regiName);
        etEmail = findViewById(R.id.regiEmail);


        btn_register = findViewById(R.id.regibtn);
        btn_check = findViewById(R.id.idDoubleCheck);
    }

    public void onClick(View v) {
        final String userID = etID.getText().toString();

        switch (v.getId()) {

            case R.id.idDoubleCheck:
                if(validate) {
                    return;
                }
                if(userID.equals("")) {
                    new AlertDialog.Builder( this )
                            .setMessage("아이디를 먼저 입력해주세요.")
                            .setPositiveButton("확인",null)
                            .show();
                    return;
                }
                //중복체크
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success){
                                new AlertDialog.Builder( RegisterActivity.this )
                                        .setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인",null)
                                        .show();

                                etID.setEnabled(false);
                                validate=true;
                                etID.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                //btn_check.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                btn_check.setText("확인");
                            }
                            else{
                                new AlertDialog.Builder( RegisterActivity.this )
                                        .setMessage("사용할 수 없는 아이디입니다.")
                                        .setPositiveButton("확인",null)
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                ValidateRequest validateRequest=new ValidateRequest(userID,responseListener);
                RequestQueue queue= Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);

                break;
            case R.id.regibtn:
                final String userPw = etPw.getText().toString();
                final String userPwCheck = etPwCheck.getText().toString();
                final String userName = etName.getText().toString();
                final String userEmail = etEmail.getText().toString();

                if(!validate) {
                    new AlertDialog.Builder( this )
                            .setMessage("먼저 중복체크를 해주세요.")
                            .setPositiveButton("확인",null)
                            .show();
                    return;
                }

                if(userID.length() == 0) {
                    Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    etID.requestFocus();
                    return;
                }
                if(userPw.length() == 0) {
                    Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    etPw.requestFocus();
                    return;
                }
                if(userPwCheck.length() == 0) {
                    Toast.makeText(this, "비밀번호를 재입력해주세요.", Toast.LENGTH_SHORT).show();
                    etPwCheck.requestFocus();
                    return;
                }

                if(userName.length() == 0) {
                    Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    etName.requestFocus();
                    return;
                }
                if(userEmail.length() == 0) {
                    Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }


                Response.Listener<String> responseListener2=new Response.Listener<String>() {//volley
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jasonObject=new JSONObject(response);//register php에 response
                            boolean success=jasonObject.getBoolean("success");//register php에 sucess
                            if(userPw.equals(userPwCheck)) {
                                if (success) {//회원등록 성공한 경우
                                    Toast.makeText(getApplicationContext(), "회원가입 되었습니다.", Toast.LENGTH_SHORT).show();
                                    //Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    //startActivity(intent);
                                    finish();
                                }
                                else {//회원등록 실패한 경우
                                    Toast.makeText(getApplicationContext(),"회원가입에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                                etPwCheck.requestFocus();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //서버로 volley를 이용해서 요청을 함
                RegisterRequest registerRequest=new RegisterRequest(userID, userPw, userName, userEmail,responseListener2);
                RequestQueue queue2= Volley.newRequestQueue(RegisterActivity.this);
                queue2.add(registerRequest);
        }
    }
}
