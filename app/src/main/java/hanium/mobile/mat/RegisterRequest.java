package hanium.mobile.mat;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //서버 URL 설정(PHP 파일 연동)
    final static private String URL = "http://mat.dothome.co.kr/register.php";
    private Map<String, String> parameter;

    public RegisterRequest(String userID, String userPassword, String userName, String userEmail,Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);//위 url에 post방식으로 값을 전송

        parameter=new HashMap<>();
        parameter.put("userID",userID);
        parameter.put("userPassword",userPassword);
        parameter.put("userName",userName);
        parameter.put("userEmail",userEmail);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameter;
    }
}
