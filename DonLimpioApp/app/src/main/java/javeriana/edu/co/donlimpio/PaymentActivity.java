package javeriana.edu.co.donlimpio;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javeriana.edu.co.classes.Category;
import javeriana.edu.co.classes.DocumentTypes;
import javeriana.edu.co.classes.Invoice;
import javeriana.edu.co.classes.PaymentMethod;
import javeriana.edu.co.classes.PersonaAddresses;
import javeriana.edu.co.classes.ProviderXCategory;
import javeriana.edu.co.classes.RequestUser;
import javeriana.edu.co.classes.Service;
import javeriana.edu.co.classes.User;

public class PaymentActivity extends AppCompatActivity {

    Button pay;
    String direction;
    TextView providerText;
    EditText price1;
    int cat;
    FirebaseDatabase database;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    String userID;

    private Geocoder mGeocoder;
    double lat, lng;
    public static final double lowerLeftLatitude = 1.396967;
    public static final double lowerLeftLongitude = -78.903968;
    public static final double upperRightLatitude = 11.983639;
    public static final double upperRigthLongitude = -71.869905;

    String provider;
    int price;
    EditText total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        if (getIntent().hasExtra("DIRECTION")) {
            direction = getIntent().getStringExtra("DIRECTION");
        }
        pay = findViewById(R.id.pay);

        if (getIntent().hasExtra("SERVICE")) {
            cat = getIntent().getIntExtra("SERVICE", 2);
        }

        if (getIntent().hasExtra("PROVIDER")) {
            provider = getIntent().getStringExtra("PROVIDER");
        }

        price = 0;
        if (getIntent().hasExtra(("PRICE"))) {
            price = getIntent().getIntExtra("PRICE", 0);
        }

        price1 = findViewById(R.id.price1);
        providerText = findViewById(R.id.provider);
        providerText.append(provider);
        price1.setText("$ " + price);

        total = findViewById(R.id.price4);

        total.setText("$ " + (price + 2000 + 3000));
        //Inicialización del objeto
        mGeocoder = new Geocoder(getBaseContext());

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();


        if (!direction.isEmpty()) {
            try {
                List<Address> addresses = mGeocoder
                        .getFromLocationName(direction, 2, lowerLeftLatitude,
                                lowerLeftLongitude,
                                upperRightLatitude, upperRigthLongitude);
                if (addresses != null && !addresses.isEmpty()) {
                    Address addressResult = addresses.get(0);
                    Log.i("PAYMENT", addresses.get(0).getAddressLine(0));
                    LatLng location = new LatLng(addressResult.getLatitude(),
                            addressResult.getLongitude());


                    lat = location.latitude;
                    lng = location.longitude;
                } else {
                    Toast.makeText(PaymentActivity.this, "Dirección no encontrada",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(PaymentActivity.this, "La dirección esta vacía", Toast.LENGTH_SHORT)
                    .show();
        }


        pay.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                RequestQueue queue = Volley.newRequestQueue(view.getContext());
                String url = "http://ec2-34-205-134-66.compute-1.amazonaws.com:9090/cm-donlimpio-service-rest-api/services/register";

                Service s = new Service();
                s.setProfessional("profesional id");
                Category category = new Category();
                category.setId(cat);
                s.setCategory(category);
                Invoice invoice = new Invoice();
                invoice.setPaymentTotal(Long.parseLong(total.getText().toString().replace("", "")));
                invoice.setComments("");
                invoice.setPaymentMethod(new PaymentMethod());
                invoice.setInvoiceDate(Calendar.getInstance().getTime());
                s.setInvoice(invoice);
                User user = new User();
                user.setUuid(userID);
                s.setPersona(user);
                PersonaAddresses pa = new PersonaAddresses();
                pa.setAddress(direction);
                pa.setLat(lat);
                pa.setLng(lng);
                s.setPersonaAddress(pa);
                s.setReservationDate(Calendar.getInstance().getTime());
                s.setStatus(2);


                Gson gson = new Gson();
                String jsonString = gson.toJson(s);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, obj,
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
                Intent i = new Intent(getApplicationContext(), TrackingServiceActivity.class);
                i.putExtra("DIRECTION", direction);
                startActivity(i);
            }
        });
    }

    public class LocationTask extends AsyncTask<Void, Void, Void> {

        String uuidClass;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "http://ec2-100-24-124-229.compute-1.amazonaws.com:9090/cm-donlimpio-service-rest-api/services/notify/request/";
            String uuid = Calendar.getInstance().getTimeInMillis() + "";
            uuidClass = uuid;
            String path = uuid + "/" + cat + "/" + 3;
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            myRef = mFirebaseDatabase.getReference("Services_Requests");
            int status = Integer.parseInt(myRef.child(uuidClass).child("serviceStatus").getKey());
            while (status != 3) {
                StringRequest req = new StringRequest(Request.Method.GET, url + path,
                        new Response.Listener() {
                            @Override
                            public void onResponse(Object response) {


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("SEND_REQUESTS", "Error handling rest invocation" + error.getCause());
                            }
                        }
                );
                try {
                    wait(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.add(req);
                status = Integer.parseInt(myRef.child(uuidClass).child("serviceStatus").getKey());
            }
            return null;
        }
    }

}
