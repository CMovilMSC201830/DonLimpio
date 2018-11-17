package javeriana.edu.co.donlimpio;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CarWashingActivity extends Activity {

    Button m_next;
    RadioGroup m_radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_washing);

        m_next = findViewById(R.id.next_step);

        m_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextStep();
            }
        });

        m_radioGroup = findViewById(R.id.type);
    }

    private void nextStep() {
        int selectedId = -1;
        selectedId = m_radioGroup.getCheckedRadioButtonId();
        if(-1 != selectedId) {
            Intent i = new Intent(getApplicationContext(), DateTimePickerActivity.class);
            i.putExtra("SERVICE", 4);
            startActivity(i);
        } else {
            Toast.makeText(CarWashingActivity.this, "Seleccione tipo de vehículo", Toast.LENGTH_SHORT).show();
        }
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_small:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_big:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
}
