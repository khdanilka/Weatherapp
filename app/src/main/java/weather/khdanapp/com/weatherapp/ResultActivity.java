package weather.khdanapp.com.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;


public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MESSAGE = "id";
    public static final String EXTRA_CHECKBOX = "checkbox";
    public static final String EXTRA_WEATHER_ID = "w_id";
    TextView textRes;
    Button btnOK;
    String  messageText;

    HashMap<Integer, TextView> checkBoxMap = new HashMap<>();
    HashMap<Integer, Integer> idArray = new HashMap<>();
    private int weath_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        btnOK = (Button) findViewById(R.id.btn_share);
        btnOK.setOnClickListener(this);

        TextView pres_res = (TextView) findViewById(R.id.pressure_res);
        checkBoxMap.put(0,pres_res);
        TextView wind_res = (TextView) findViewById(R.id.wind_speed_res);
        checkBoxMap.put(1,wind_res);
        TextView moisture_res = (TextView) findViewById(R.id.moisture_res);
        checkBoxMap.put(2,moisture_res);

        idArray.put(0,R.array.pressure);
        idArray.put(1,R.array.wind_speed);
        idArray.put(2,R.array.moisture);

        Intent intent = getIntent();
        if  (intent != null )   {
            setResult(RESULT_OK, intent);
            messageText = intent.getStringExtra(EXTRA_MESSAGE);
            TextView textRes = (TextView) findViewById(R.id.result);
            textRes.setText(messageText);

            weath_id = intent.getIntExtra(EXTRA_WEATHER_ID,-1);
            if (weath_id == -1) throw new RuntimeException("что то пошло не так");

            boolean[] b = intent.getBooleanArrayExtra(EXTRA_CHECKBOX);
            setCheckBoxResults(b);
        }
    }
    @Override
    public void onClick(View view) {
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_TEXT, messageText);
        if (intentShare.resolveActivity(getPackageManager()) != null) startActivity(intentShare);
    }

    private void setCheckBoxResults(boolean[] b){
        for(int i = 0; i <= 2; i++) {
            String text = DataClass.getValueFromArray(this, weath_id,idArray.get(i));
            if (b[i]) checkBoxMap.get(i).setText(text);
        }
    }


}
