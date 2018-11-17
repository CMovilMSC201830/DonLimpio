package javeriana.edu.co.donlimpio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddServiceActivity extends AppCompatActivity {

    Spinner categoria, subcategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        categoria = findViewById(R.id.category_spinner);
        subcategoria = findViewById(R.id.subcategory_spinner);

        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedCat = parent.getItemAtPosition(position).toString();
                switch (selectedCat){
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
