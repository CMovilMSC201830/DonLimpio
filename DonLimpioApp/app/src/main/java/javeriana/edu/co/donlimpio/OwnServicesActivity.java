package javeriana.edu.co.donlimpio;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javeriana.edu.co.classes.Provider;
import javeriana.edu.co.util.CustomAdapter;

public class OwnServicesActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference mRef;
    Button addServicesBttn;
    String[] services;
    ListView listView;
    String userID;

    ArrayList<Provider> arrayList = new ArrayList<>();
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_services);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();

        mRef = FirebaseDatabase.getInstance().getReference().child("Providers/").child(userID);

        listView = findViewById(R.id.serviceslist_dynamic);

        addServicesBttn = findViewById(R.id.add_services_button);

        addServicesBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddServiceActivity.class);
                startActivity(i);
            }
        });

        adapter = new CustomAdapter(arrayList, this);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Provider p = ds.getValue(Provider.class);
                    arrayList.add(p);
                }
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //TODO: inflate list view
        /*
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "http://ec2-100-24-124-229.compute-1.amazonaws.com:909/cm-donlimpio-service-rest-api/professional/services/request/";

         */

    }

}
