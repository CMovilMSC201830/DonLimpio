package javeriana.edu.co.donlimpio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.BundleCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.google.gson.Gson;

import java.util.Calendar;

import javeriana.edu.co.classes.Category;
import javeriana.edu.co.classes.ProviderXCategory;
import javeriana.edu.co.classes.RequestUser;
import javeriana.edu.co.classes.ServiceRequest;

public class ServicesActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    TextView mLimpieza, mPlomeria, mCarros;
    ImageButton btnProfile;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String userID;
    Category result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        mLimpieza = findViewById(R.id.limpieza_textview);
        mPlomeria = findViewById(R.id.plomeria_textview);
        mCarros = findViewById(R.id.carros_textview);

        mAuth = FirebaseAuth.getInstance();

        mLimpieza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DomesticConfigurationActivity.class);
                startActivity(i);
            }
        });

        mPlomeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ServicesActivity.this, OwnServicesActivity.class);
                startActivity(i);
            }
        });

        mCarros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ServicesActivity.this, CarWashingActivity.class);
                startActivity(i);
            }
        });

        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        database = FirebaseDatabase.getInstance();

        myRef = database.getReference("/ProvidersRequests");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    RequestUser pxc = singleSnapshot.getValue(RequestUser.class);

                    if (pxc.getUuidUser().equals(userID)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ServicesActivity.this);
                        builder.setCancelable(true);
                        myRef = FirebaseDatabase.getInstance().getReference();
                        /*int catId = Integer.parseInt(myRef.child("Services_Request").child(pxc.getUuidRequest()).child("categoryId").getKey());
                        Category category = getDBCategory(catId);*/
                        myRef = FirebaseDatabase.getInstance().getReference();
                        String name = myRef.child("Users/").child(pxc.getUuidUser()).child("firstName").getKey();
                        builder.setTitle("Servicio de " /*+ category.getName()*/);
                        builder.setMessage("Valor: " + pxc.getPrice() + ", Persona: " + name);
                        final String rqstuuid = pxc.getUuidRequest();
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        myRef.child("Services_Request").child(rqstuuid).child("serviceStatus").setValue(1);
                                    }
                                });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("SEARCHING SERVICE", "error en la consulta", databaseError.toException());
            }
        });
    }

    private Category getDBCategory(int categoryId) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://ec2-100-24-124-229.compute-1.amazonaws.com:9090/cm-donlimpio-service-rest-api/categories/search/";
        String path = categoryId + "";

        StringRequest req = new StringRequest(Request.Method.GET, url + path,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("CATEGORY SEARCH", "response :" + response);
                        Gson gson = new Gson();
                        Category cate = gson.fromJson(response, Category.class);
                        result = cate;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("SEND_REQUESTS", "Error handling rest invocation" + error.getCause());
                        result = null;
                    }
                }
        );
        queue.add(req);
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if (itemClicked == R.id.menuLogOut) {
            mAuth.signOut();
            Intent intent = new Intent(ServicesActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (itemClicked == R.id.menuSettings) {
            Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(i);
        } else if (itemClicked == R.id.menuMyServices) {
            Intent i = new Intent(getApplicationContext(), OwnServicesActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
