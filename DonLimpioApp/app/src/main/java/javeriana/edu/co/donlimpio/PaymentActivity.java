package javeriana.edu.co.donlimpio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaymentActivity extends AppCompatActivity {

    Button pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        pay = (Button) findViewById(R.id.pay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TrackServiceActivity.class);
                startActivity(i);
            }
        });
    }
}
