package weather.khdanapp.com.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MESSAGE = "id";
    TextView textRes;
    Button btnOK;
    String  messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        btnOK = (Button) findViewById(R.id.btn_share);
        btnOK.setOnClickListener(this);

        Intent intent = getIntent();
        if  (intent != null )   {
            setResult(RESULT_OK, intent);
            messageText = intent.getStringExtra( EXTRA_MESSAGE );
            TextView textRes = (TextView) findViewById(R.id.result);
            textRes.setText(messageText);
        }
    }
    @Override
    public void onClick(View view) {
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_TEXT, messageText);
        if (intentShare.resolveActivity(getPackageManager()) != null) startActivity(intentShare);
    }
}
