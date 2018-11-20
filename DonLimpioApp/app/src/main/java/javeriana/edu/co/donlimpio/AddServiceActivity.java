package javeriana.edu.co.donlimpio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import javeriana.edu.co.classes.Provider;

public class AddServiceActivity extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference mRef;
    Spinner categoria, subcategoria;
    EditText servPrice;
    Provider providerServ;
    String categServ, subcategServ;
    Button agregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        providerServ = new Provider();

        categoria = findViewById(R.id.category_spinner);
        subcategoria = findViewById(R.id.subcategory_spinner);
        servPrice = findViewById(R.id.price_edit_text);
        agregar = findViewById(R.id.ok_button);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference();

        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                categServ = parent.getItemAtPosition(position).toString();
                switch (categServ){
                    case "Construccion":
                        subcategoria.setAdapter(new ArrayAdapter<String>(AddServiceActivity.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.subcat_construccion)));
                        providerServ.setCategoryId(1);
                        break;

                    case "Plomeria":
                        subcategoria.setAdapter(new ArrayAdapter<String>(AddServiceActivity.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.subcat_plomeria)));
                        providerServ.setCategoryId(2);
                        break;

                    case "Carpinteria":
                        subcategoria.setAdapter(new ArrayAdapter<String>(AddServiceActivity.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.subcat_carpinteria)));
                        providerServ.setCategoryId(3);
                        break;

                    case "Electricidad":
                        subcategoria.setAdapter(new ArrayAdapter<String>(AddServiceActivity.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.subcat_electricidad)));
                        providerServ.setCategoryId(4);
                        break;

                    case "Limpieza":
                        subcategoria.setAdapter(new ArrayAdapter<String>(AddServiceActivity.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.subcat_limpieza)));
                        providerServ.setCategoryId(5);
                        break;

                    case "Automoviles":
                        subcategoria.setAdapter(new ArrayAdapter<String>(AddServiceActivity.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.subcat_autos)));
                        providerServ.setCategoryId(6);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        subcategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subcategServ = parent.getItemAtPosition(position).toString();

                providerServ.setCategory(categServ);
                providerServ.setDescription(subcategServ);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!servPrice.getText().toString().isEmpty()){
                    providerServ.setPrice(Integer.parseInt(servPrice.getText().toString()));
                    mRef.child("Providers").child(user.getUid()).push().setValue(providerServ);
                    try {
                        addUserDONLIMPIO(providerServ);
                        Toast.makeText(getBaseContext(),"Servicio creado exitosamente",Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                finish();
            }
        });

    }

    public void addUserDONLIMPIO(Provider p) throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(this);
        // local url -> String url = "http://192.168.0.23:9090/cm-donlimpio-service-rest-api/professional/service/register";
        String url = "http://ec2-34-205-134-66.compute-1.amazonaws.com:9090/cm-donlimpio-service-rest-api/professional/service/register";

        Gson gson = new Gson();
        String jsonString = gson.toJson(p);
        JSONObject obj = new JSONObject(jsonString);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url,obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

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

}
