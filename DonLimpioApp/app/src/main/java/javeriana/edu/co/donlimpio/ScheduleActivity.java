package javeriana.edu.co.donlimpio;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.UUID;

import javeriana.edu.co.classes.User;

public class ScheduleActivity extends AppCompatActivity {

    private Button mBtnDo;
    private TextView mCancel, mSalut;
    ImageButton mBtnPosition;
    EditText mAddress, lblDate, lblTime;
    FrameLayout progressBarHolder;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    int serviceCat;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    String userID;
    int cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mBtnDo = findViewById(R.id.btnDo);
        mCancel = findViewById(R.id.tvcancel);
        mBtnPosition = findViewById(R.id.btnPosition);
        mAddress = findViewById(R.id.entry);
        lblDate = findViewById(R.id.lbldate);
        lblTime = findViewById(R.id.entry3);
        mSalut = findViewById(R.id.greetings);

        progressBarHolder = findViewById(R.id.progressBarHolder);

        Intent i = getIntent();
        if (i.hasExtra("DIRECTION")) {
            mAddress.setText(i.getStringExtra("DIRECTION"));
        }
        if (i.hasExtra("SERVICE")) {
            serviceCat = i.getIntExtra("SERVICE", 2);
        }

        if (i.hasExtra("DATE")) {
            lblDate.setText(i.getStringExtra("DATE"));
        }

        if (i.hasExtra("TIME")) {
            lblTime.setText(i.getStringExtra("TIME"));
        }

        mBtnPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SetPositionActivity.class);
                startActivity(i);
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ServicesActivity.class);
                startActivity(i);
            }
        });

        if (getIntent().hasExtra("SERVICE")) {
            cat = getIntent().getIntExtra("SERVICE", 2);
        }
        mBtnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SearchingTask().execute();
            }
        });


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Users/").child(userID);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Toast.makeText(getBaseContext(), "Successfully signed with " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "User has signed out", Toast.LENGTH_SHORT).show();
                }
            }
        };

       /* myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User u = new User();
                u.setFirstName(dataSnapshot.getValue(User.class).getFirstName());
                u.setLastName(dataSnapshot.getValue(User.class).getLastName());
                u.setEmail(dataSnapshot.getValue(User.class).getEmail());
                u.setUserPhoneNumber(dataSnapshot.getValue(User.class).getUserPhoneNumber());

                mSalut.setText(u.getFirstName() + ", este es el resumen de tu solicitud");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }

    public class SearchingTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mBtnDo.setEnabled(false);
            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(200);
            progressBarHolder.setAnimation(outAnimation);
            progressBarHolder.setVisibility(View.GONE);
            mBtnDo.setEnabled(true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "http://192.168.0.23:9090/cm-donlimpio-service-rest-api/services/notify/request/";
            String uuid = Calendar.getInstance().getTimeInMillis() + "";
            String path = uuid + "/" + cat + "/" + 3;

            StringRequest req = new StringRequest(Request.Method.GET, url+path,
                    new Response.Listener() {
                        @Override
                        public void onResponse(Object response) {
                            Intent i = new Intent(getApplicationContext(), SearchingActivity.class);
                            i.putExtra("DIRECTION", mAddress.getText().toString());
                            i.putExtra("SERVICE", cat);
                            startActivity(i);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("SEND_REQUESTS", "Error handling rest invocation"+error.getCause());
                        }
                    }
            );
            queue.add(req);
            return null;
        }
    }
}

