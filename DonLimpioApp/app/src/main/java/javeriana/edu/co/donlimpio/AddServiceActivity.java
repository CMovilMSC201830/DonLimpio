package javeriana.edu.co.donlimpio;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javeriana.edu.co.classes.Service;
import javeriana.edu.co.classes.User;

public class AddServiceActivity extends AppCompatActivity {

    private FirebaseUser user;
    DatabaseReference mRef;
    Spinner categoria, subcategoria;
    EditText servPrice;
    Service providerServ;
    String categServ, subcategServ;
    Button agregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        providerServ = new Service();

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
                        break;

                    case "Plomeria":
                        subcategoria.setAdapter(new ArrayAdapter<String>(AddServiceActivity.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.subcat_plomeria)));
                        break;

                    case "Carpinteria":
                        subcategoria.setAdapter(new ArrayAdapter<String>(AddServiceActivity.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.subcat_carpinteria)));
                        break;

                    case "Electricidad":
                        subcategoria.setAdapter(new ArrayAdapter<String>(AddServiceActivity.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.subcat_electricidad)));
                        break;

                    case "Limpieza":
                        subcategoria.setAdapter(new ArrayAdapter<String>(AddServiceActivity.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.subcat_limpieza)));
                        break;

                    case "Automoviles":
                        subcategoria.setAdapter(new ArrayAdapter<String>(AddServiceActivity.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.subcat_autos)));
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

                providerServ.setNombreServicio(categServ);
                providerServ.setDescripcionServicio(subcategServ);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!servPrice.getText().toString().isEmpty()){
                    providerServ.setPrecioServicio(Double.parseDouble(servPrice.getText().toString()));
                    mRef.child("Providers").child(user.getUid()).setValue(providerServ);
                }

                Toast.makeText(getApplicationContext(), providerServ.getDescripcionServicio() + "\n"
                        + providerServ.getNombreServicio(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
