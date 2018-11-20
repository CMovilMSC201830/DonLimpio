package javeriana.edu.co.donlimpio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javeriana.edu.co.classes.RequestUser;
import javeriana.edu.co.classes.ServiceRequest;

public class SearchingActivity extends AppCompatActivity {

    int cat;
    String dir;
    String uuid;

    //Atributos
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        Intent past = getIntent();

        if (past.hasExtra("SERVICE")) {
            cat = past.getIntExtra("SERVICE", 2);
        }

        if (past.hasExtra("DIRECTION")) {
            dir = past.getStringExtra("DIRECTION");
        }

        if (past.hasExtra("REQUEST")) {
            uuid = past.getStringExtra("REQUEST");
        }

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("/Services_Request");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    ServiceRequest rqst = singleSnapshot.getValue(ServiceRequest.class);
                    // Log.i("SEARCHING SERVICE", "Encontró SERVICIO: " + (Long.parseLong(singleSnapshot.getKey()) + "-" + Long.parseLong(uuid)) + " equal: " + (Long.parseLong(singleSnapshot.getKey())==Long.parseLong(uuid) ));

                    if ((Long.parseLong(singleSnapshot.getKey()) == Long.parseLong(uuid))
                            && rqst.getServiceStatus() == 1) {
                        Toast.makeText(SearchingActivity.this, rqst.toString(), Toast.LENGTH_SHORT).show();
                        database = FirebaseDatabase.getInstance();
                        myRef = database.getReference("/ProvidersRequests");
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                    RequestUser rqst = singleSnapshot.getValue(RequestUser.class);

                                    if ((Long.parseLong(rqst.getUuidRequest()) == Long.parseLong(uuid))
                                            && rqst.getActive() == 0) {
                                        Toast.makeText(SearchingActivity.this, rqst.toString(), Toast.LENGTH_SHORT).show();
                                        String name = myRef.child("Users/").child(rqst.getUuidUser()).child("firstName").getKey();
                                        Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
                                        i.putExtra("DIRECTION", dir);
                                        i.putExtra("SERVICE", cat);
                                        i.putExtra("REQUEST", uuid);
                                        i.putExtra("PROVIDER", name);
                                        i.putExtra("PRICE", rqst.getPrice());
                                        startActivity(i);
                                    }

                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("SEARCHING SERVICE", "error en la consulta", databaseError.toException());
                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("SEARCHING SERVICE", "error en la consulta", databaseError.toException());
            }
        });



    }
}
