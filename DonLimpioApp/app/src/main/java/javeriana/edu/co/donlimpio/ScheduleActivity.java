package javeriana.edu.co.donlimpio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ScheduleActivity extends AppCompatActivity {

    private Button mBtnDo;
    private TextView mCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mBtnDo = (Button) findViewById(R.id.btnDo);
        mCancel = (TextView) findViewById(R.id.tvcancel);

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
                startActivity(i);
            }
        });
    }
}
