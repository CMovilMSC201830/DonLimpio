package javeriana.edu.co.donlimpio;

import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mLastName;
    private EditText mPsswd;
    private EditText mRepeatPsswd;
    private EditText mPhone;
    private Button mSignupBtn; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        
        mName = (EditText) findViewById(R.id.text_view_name);
        mLastName = (EditText) findViewById(R.id.text_view_lastName);
        mPsswd = (EditText) findViewById(R.id.password);
        mRepeatPsswd = (EditText) findViewById(R.id.repeat_password);
        mPhone = (EditText) findViewById(R.id.phone);
        mSignupBtn = (Button)findViewById(R.id.signup_btn); 
        
        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean okPsswd = verifyPasswds(mPsswd.getText().toString(), mRepeatPsswd.getText().toString());
                boolean okPhone = verifyPhone(mPhone.getText().toString());
                boolean okName = isAlpha(mName.getText().toString());
                boolean okLastName = isAlpha(mLastName.getText().toString());

                if (!okName) {
                    Toast.makeText(getApplicationContext(), R.string.name_only_letters, Toast.LENGTH_SHORT).show();
                }
                if (!okLastName) {
                    Toast.makeText(getApplicationContext(), R.string.lastname_only_letters, Toast.LENGTH_SHORT).show();
                }
                if (!okPsswd) {
                    Toast.makeText(getApplicationContext(), R.string.no_repeat_psswd, Toast.LENGTH_SHORT).show();
                }
                if (!okPhone) {
                    Toast.makeText(getApplicationContext(), R.string.phone_matches, Toast.LENGTH_SHORT).show();
                }

                if (okName && okLastName && okPsswd && okPhone) {
                    Intent i = new Intent(getApplicationContext(), ServicesActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z\\s]{5,10}");
    }

    private boolean verifyPhone(String phone) {
        if (10 == phone.length()) {
            return true;
        }
        return false;
    }

    private boolean verifyPasswds(String psswd, String psswdR) {
        if (psswd.equals(psswdR)) {
            return true;
        }
        return false;
    }
}
