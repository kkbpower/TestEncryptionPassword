package com.kamsung.testencryptionpassword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    EditText pw;
    Button goSHA256, goBase64;
    TextView SHA256, SHA256Base64;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pw = (EditText) findViewById(R.id.pw);
        goSHA256 = (Button) findViewById(R.id.go_SHA256);
        goBase64 = (Button) findViewById(R.id.go_base64);
        SHA256 = (TextView) findViewById(R.id.sha256);
        SHA256Base64 = (TextView) findViewById(R.id.sha256_base64);

        goSHA256.setOnClickListener(this);
        goBase64.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.go_SHA256:
                if(pw.getText().toString().equals("")){
                    Toast.makeText(this, "패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    String originPassword = pw.getText().toString();
                    try {
                        MessageDigest md = MessageDigest.getInstance("SHA-256");
                        md.update(originPassword.getBytes());
                        byte data[] = md.digest();
                        StringBuilder sb = new StringBuilder();
                        for(int i = 0; i < data.length; i++){
                            sb.append(Integer.toString((data[i]&0xff) + 0x100, 16).substring(1));
                        }
                        SHA256.setText(sb.toString());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case R.id.go_base64:
                if(SHA256.getText().toString().equals("")){
                    Toast.makeText(this, "먼저 SHA256으로 해싱해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    String source = SHA256.getText().toString();
                    String hasedString = Base64.encodeToString(source.getBytes(), 0);
                    SHA256Base64.setText(hasedString);
                }
                break;
            default:
                break;
        }
    }
}
