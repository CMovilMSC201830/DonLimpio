package javeriana.edu.co.donlimpio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class OwnServicesActivity extends AppCompatActivity {
    String[] services;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_services);
        initServices();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, services);
        ListView listView = (ListView) findViewById(R.id.services);
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
