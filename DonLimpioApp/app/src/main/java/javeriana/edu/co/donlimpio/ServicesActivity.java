package javeriana.edu.co.donlimpio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ServicesActivity extends AppCompatActivity {

    TextView mLimpieza, mPlomeria;
    ImageButton btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        mLimpieza = (TextView) findViewById(R.id.limpieza);
        mPlomeria = (TextView) findViewById(R.id.plomeria);

        mLimpieza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LocationMapActivity.class);
                startActivity(i);
            }
        });

        mPlomeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ServicesActivity.this, LocationMapActivity.class);
                startActivity(i);
            }
        });

        btnProfile = (ImageButton) findViewById(R.id.profile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(i);
            }
        });
    }
}
