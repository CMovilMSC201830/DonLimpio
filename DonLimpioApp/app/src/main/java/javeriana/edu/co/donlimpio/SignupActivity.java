package javeriana.edu.co.donlimpio;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import javeriana.edu.co.classes.User;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG_SIGNIN = "EMAIL_SIGNIN";
    private static final String TAG = "AddToDatabase";
    private static final String TAG_PROFILE = "CreateProfile";
    public static final int IMAGE_REQUEST = 100;
    public static final int CAMERA_REQUEST = 20;
    private EditText mName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPsswd;
    private EditText mRepeatPsswd;
    private EditText mPhone;
    private Button mSignupBtn;
    View signupLayout;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference myRef;
    StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        mName = findViewById(R.id.input_firstname);
        mLastName = findViewById(R.id.input_lastname);
        mPsswd = findViewById(R.id.input_password);
        mEmail = findViewById(R.id.input_email);
        mRepeatPsswd = findViewById(R.id.input_reenter_password);
        mPhone = findViewById(R.id.input_phoneNumber);
        mSignupBtn = findViewById(R.id.signup_btn);
        signupLayout = findViewById(R.id.signup_layout);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(getBaseContext(), "Successfully signed in with: " + user.getEmail(),
                            Toast.LENGTH_LONG).show();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(getBaseContext(), "Ready to sign up.",
                            Toast.LENGTH_LONG).show();
                }
            }
        };

        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createAccount() {
        boolean okName = isAlpha(mName.getText().toString());
        boolean okLastName = isAlpha(mLastName.getText().toString());
        boolean okEmail = validEmail(mEmail.getText().toString());
        boolean okPsswd = verifyPasswds(mPsswd.getText().toString(), mRepeatPsswd.getText().toString());
        boolean okPhone = verifyPhone(mPhone.getText().toString());

        if (!okName) {
            Toast.makeText(getApplicationContext(), R.string.name_only_letters, Toast.LENGTH_SHORT)
                    .show();
        }
        if (!okLastName) {
            Toast.makeText(getApplicationContext(), R.string.lastname_only_letters, Toast.LENGTH_SHORT)
                    .show();
        }
        if (!okPsswd) {
            Toast.makeText(getApplicationContext(), R.string.no_repeat_psswd, Toast.LENGTH_SHORT).show();
        }
        if (!okPhone) {
            Toast.makeText(getApplicationContext(), R.string.phone_matches, Toast.LENGTH_SHORT).show();
        }

        if (okName && okLastName && okEmail && okPsswd && okPhone) {
            addUserFirebase();
        }
    }

    private void addUserFirebase() {
        mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPsswd.getText().toString())
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG_SIGNIN, "createUserWithEmail:onComplete:" + task.isSuccessful());
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) { //Update user Info
                                UserProfileChangeRequest.Builder upcrb = new UserProfileChangeRequest.Builder();
                                upcrb.setDisplayName(
                                        mName.getText().toString() + " " + mLastName.getText().toString());
                                //upcrb.setPhotoUri(Uri.parse("res/to/pic"));//fake uri, use Firebase Storage
                                user.updateProfile(upcrb.build());
                                myRef = FirebaseDatabase.getInstance().getReference();
                                myRef.child("Users").child(user.getUid()).setValue(new User(mEmail.getText().toString(), mName.getText().toString(),
                                        mLastName.getText().toString(), Long.parseLong(mPhone.getText().toString())));
                                startActivity(new Intent(SignupActivity.this, ServicesActivity.class)); //o en el listener
                                finish();
                            }
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this,
                                    R.string.auth_failed + task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                            Log.e(TAG_SIGNIN, task.getException().getMessage());
                        }
                    }
                });
    }

    private boolean validEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
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
