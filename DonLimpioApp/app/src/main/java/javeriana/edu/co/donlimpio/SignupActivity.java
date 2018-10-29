package javeriana.edu.co.donlimpio;

import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javeriana.edu.co.classes.User;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG_SIGNIN = "EMAIL_SIGNIN";
    private EditText mName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPsswd;
    private EditText mRepeatPsswd;
    private EditText mPhone;
    private Button mSignupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        
        mName = (EditText) findViewById(R.id.input_firstname);
        mLastName = (EditText) findViewById(R.id.input_lastname);
        mPsswd = (EditText) findViewById(R.id.input_password);
        mEmail = (EditText) findViewById(R.id.input_email);
        mRepeatPsswd = (EditText) findViewById(R.id.input_reenter_password);
        mPhone = (EditText) findViewById(R.id.input_phoneNumber);
        mSignupBtn = (Button)findViewById(R.id.signup_btn); 
        
        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();

            }
        });
    }

    private void createAccount() {
        boolean okName = isAlpha(mName.getText().toString());
        boolean okLastName = isAlpha(mLastName.getText().toString());
        boolean okEmail = validEmail(mEmail.getText().toString());
        boolean okPsswd = verifyPasswds(mPsswd.getText().toString(), mRepeatPsswd.getText().toString());
        boolean okPhone = verifyPhone(mPhone.getText().toString());


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

        if (okName && okLastName && okEmail && okPsswd && okPhone) {
            Intent i = new Intent(getApplicationContext(), ServicesActivity.class);
            startActivity(i);
            // TODO: create user in database
        }
    }

    private void addUserFirebase() {
        User newUser = new User(mName.getText().toString(), mLastName.getText().toString(),
                Long.parseLong(mPhone.getText().toString()));
        // TODO: add user to database
    }

    private boolean validEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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

    private boolean verifyPasswds(String password1, String password2) {
        if (password1.equals(password2)) {
            return true;
        }
        return false;
    }
}
