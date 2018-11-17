package javeriana.edu.co.donlimpio;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import javeriana.edu.co.classes.DocumentTypes;
import javeriana.edu.co.classes.Payload;
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
    private EditText mId;
    private Button mSignupBtn;
    View signupLayout;
    CircleImageView circlePhoto;
    Uri imageUri;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        mName = (EditText) findViewById(R.id.input_firstname);
        mLastName = (EditText) findViewById(R.id.input_lastname);
        mPsswd = (EditText) findViewById(R.id.input_password);
        mEmail = (EditText) findViewById(R.id.input_email);
        mRepeatPsswd = (EditText) findViewById(R.id.input_reenter_password);
        mPhone = (EditText) findViewById(R.id.input_phoneNumber);
        mSignupBtn = (Button) findViewById(R.id.signup_btn);
        circlePhoto = (CircleImageView) findViewById(R.id.signup_image);
        signupLayout = findViewById(R.id.signup_layout);
        mId = findViewById(R.id.input_id);

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
                    Toast.makeText(getBaseContext(), "Successfully signed out.",
                            Toast.LENGTH_LONG).show();
                }
            }
        };

        boolean hasPermissionGallery = (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionGallery)
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, IMAGE_REQUEST);
        else {
            circlePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choosePhoto();
                }
            });
        }

        boolean hasPermissionCamera = (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionCamera) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_REQUEST);
        } else {
            circlePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choosePhoto();
                }
            });
        }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case IMAGE_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(signupLayout, "¡Permiso concedido!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(signupLayout, "¡La aplicación no puede usar la galeria!",
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
            case CAMERA_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(signupLayout, "¡Permiso concedido!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(signupLayout, "¡La aplicación no puede usar la cámara!",
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        }
    }

    private void choosePhoto() {
        final CharSequence[] options = {"Camara", "Galeria", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
        builder.setTitle("Cambiar Foto");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (options[i].equals("Camara")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "fotoPerfil.jpg");
                    imageUri = Uri.fromFile(f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (options[i].equals("Galeria")) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, IMAGE_REQUEST);
                } else if (options[i].equals("Cancelar")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (resultCode == RESULT_OK) {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    BitmapFactory.decodeFile(imageUri.getPath()).compress(Bitmap.CompressFormat.JPEG, 80, bytes);
                    circlePhoto.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray())));
                }
            }
            case IMAGE_REQUEST: {
                if (resultCode == RESULT_OK) {
                    try {
                        imageUri = data.getData();
                        circlePhoto.setImageBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri)));
                    } catch (Exception e) {
                        Log.d(TAG, e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void createAccount() {
        boolean okName = isAlpha(mName.getText().toString());
        boolean okLastName = isAlpha(mLastName.getText().toString());
        boolean okEmail = validEmail(mEmail.getText().toString());
        boolean okPsswd = verifyPasswds(mPsswd.getText().toString(), mRepeatPsswd.getText().toString());
        boolean okPhone = verifyPhone(mPhone.getText().toString());
        boolean okId = verifyId(mId.getText().toString());

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
        if(!okId) {
            Toast.makeText(getApplicationContext(), R.string.id_matches, Toast.LENGTH_SHORT).show();
        }

        if (okName && okLastName && okEmail && okPsswd && okPhone && okId) {
            addUserFirebase();
        }
    }

    private boolean verifyId(String s) {
        if (8 == s.length()) {
            return true;
        }
        return false;    }

    public void addUserDONLIMPIO(String uid, String mEmail, String mName, String mLastName, String mPhone, String mId) throws JSONException {
        User u = new User();
        u.setFirstName(mName);
        u.setLastName(mLastName);
        u.setUserPhoneNumber(Long.parseLong(mPhone));
        u.setEmail(mEmail);
        u.setUuid(uid);
        DocumentTypes doc = new DocumentTypes();
        doc.setId(Long.parseLong(mId));
        doc.setShortName("CC");
        doc.setLongName("CEDULA DE CIUDADANIA");
        u.setDoc(doc);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.23:9090/cm-donlimpio-service-rest-api/personas/save";

        Gson gson = new Gson();
        String jsonString = gson.toJson(u);
        JSONObject obj = new JSONObject(jsonString);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url,obj,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("TAG", "Error handling rest invocation" + error.getCause());
                    }
                }
        );
        queue.add(req);
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
                                try {
                                    addUserDONLIMPIO(user.getUid(), mEmail.getText().toString(), mName.getText().toString(), mLastName.getText().toString(), mPhone.getText().toString(), mId.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
