package javeriana.edu.co.donlimpio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class OwnServicesActivity extends AppCompatActivity {

    Button addServicesBttn;
    String[] services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_services);

        addServicesBttn = findViewById(R.id.add_services_button);

        addServicesBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),AddServiceActivity.class);
                startActivity(i);
                finish();
            }
        });

        initServices();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, services);
        ListView listView = (ListView) findViewById(R.id.serviceslist_dynamic);
        listView.setAdapter(adapter);
    }

    private void initServices() {
        services =  new String[20];
        for(int i = 0; i < 20; ++i) {
            services[i] = "Servicio " + i;
            if (0 == i%2) {
                services[i] += " Plomeria";
            } else if (0 == i%3) {
                services[i] += " Lava Autos";
            } else {
                services[i] +=  " Electricidad";
            }
        }
    }
}
