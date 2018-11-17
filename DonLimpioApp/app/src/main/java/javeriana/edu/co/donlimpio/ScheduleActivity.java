package javeriana.edu.co.donlimpio;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import javeriana.edu.co.classes.User;

public class ScheduleActivity extends AppCompatActivity {

    private Button mBtnDo;
    private TextView mCancel, mSalut;
    ImageButton mBtnPosition;
    EditText mAddress, lblDate, lblTime;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    String userID;
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

        Intent i = getIntent();
        if (i.hasExtra("DIRECTION")) {
            mAddress.setText(i.getStringExtra("DIRECTION"));
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

        mBtnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
                i.putExtra("DIRECTION", mAddress.getText().toString());
                startActivity(i);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    User u = new User();
                    u.setFirstName(ds.child(userID).getValue(User.class).getFirstName());
                    u.setLastName(ds.child(userID).getValue(User.class).getLastName());
                    u.setEmail(ds.child(userID).getValue(User.class).getEmail());
                    u.setUserPhoneNumber(ds.child(userID).getValue(User.class).getUserPhoneNumber());

                   mSalut.setText(u.getFirstName() + ", este es el resumen de tu solicitud");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
