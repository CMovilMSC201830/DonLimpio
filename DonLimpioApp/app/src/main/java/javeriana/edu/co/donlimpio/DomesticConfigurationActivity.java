package javeriana.edu.co.donlimpio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

public class DomesticConfigurationActivity extends AppCompatActivity {

    Spinner m_comboBuild;
    Button m_next;
    CheckBox m_ironing;
    CheckBox m_washing;
    CheckBox m_outside;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domestic_configuration);

        m_comboBuild = findViewById(R.id.spinnerBuild);
        m_next = findViewById(R.id.next_step);
        m_ironing = findViewById(R.id.ironing);
        m_washing = findViewById(R.id.washing);
        m_outside = findViewById(R.id.outside);

        m_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextStep();
            }
        });
    }

    private void nextStep() {
        Intent i = new Intent(getApplicationContext(), DateTimePickerActivity.class);
        startActivity(i);
    }
}
