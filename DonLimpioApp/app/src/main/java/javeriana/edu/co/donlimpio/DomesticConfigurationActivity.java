package javeriana.edu.co.donlimpio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DomesticConfigurationActivity extends AppCompatActivity {

    Button m_next;
    CheckBox m_ironing;
    CheckBox m_washing;
    CheckBox m_outside;
    EditText m_rooms, m_bath, m_meters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domestic_configuration);

        m_next = findViewById(R.id.next_step);
        m_ironing = findViewById(R.id.ironing);
        m_washing = findViewById(R.id.washing);
        m_outside = findViewById(R.id.outside);
        m_rooms = findViewById(R.id.noRooms);
        m_bath = findViewById(R.id.noBath);
        m_meters = findViewById(R.id.meters);

        m_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextStep();
            }
        });
    }

    private void nextStep() {
        if (validFields()) {
            Intent i = new Intent(getApplicationContext(), DateTimePickerActivity.class);
            i.putExtra("SERVICE", 2);
            startActivity(i);
        } else {
            Toast.makeText(DomesticConfigurationActivity.this, "Asegurese que los campos esten llenos", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validFields() {
        if (!TextUtils.isEmpty(m_bath.getText()) && !TextUtils.isEmpty(m_rooms.getText()) && !TextUtils.isEmpty(m_meters.getText())) {
            return true;
        }
        return false;
    }
}
