package javeriana.edu.co.donlimpio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ScheduleActivity extends AppCompatActivity {

    private Button mBtnDo;
    private TextView mCancel;
    ImageButton mBtnPosition;
    EditText mAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mBtnDo = findViewById(R.id.btnDo);
        mCancel = findViewById(R.id.tvcancel);
        mBtnPosition = findViewById(R.id.btnPosition);
        mAddress = findViewById(R.id.entry);

        Intent i = getIntent();
        if (i.hasExtra("DIRECTION")) {
            mAddress.setText(i.getStringExtra("DIRECTION"));
        }

        mBtnPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SetPositionActivity.class);
                startActivity(i);
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ServicesActivity.class);
                startActivity(i);
            }
        });

        mBtnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
                i.putExtra("DIRECTION", mAddress.getText().toString());
                startActivity(i);
            }
        });
    }
}
