package weather.khdanapp.com.weatherapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;


public class ResultFragment extends Fragment implements View.OnClickListener {

    public static final String EXTRA_MESSAGE = "id";
    public static final String EXTRA_CHECKBOX = "checkbox";
    public static final String EXTRA_WEATHER_ID = "w_id";
    TextView textRes;
    Button btnOK;
    String  messageText;

    HashMap<Integer, TextView> checkBoxMap = new HashMap<>();
    HashMap<Integer, Integer> idArray = new HashMap<>();
    //private int weath_id;

    private int weatherId = -1;
    boolean[] markWhatNeed;

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public void setMarkWhatNeed(boolean[] markWhatNeed) {
        this.markWhatNeed = markWhatNeed;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_result);
        View view = inflater.inflate(R.layout.activity_result,  container, false);
        btnOK = (Button) view.findViewById(R.id.btn_share);
        btnOK.setOnClickListener(this);

        TextView pres_res = (TextView) view.findViewById(R.id.pressure_res);
        checkBoxMap.put(0,pres_res);
        TextView wind_res = (TextView) view.findViewById(R.id.wind_speed_res);
        checkBoxMap.put(1,wind_res);
        TextView moisture_res = (TextView) view.findViewById(R.id.moisture_res);
        checkBoxMap.put(2,moisture_res);

        idArray.put(0,R.array.pressure);
        idArray.put(1,R.array.wind_speed);
        idArray.put(2,R.array.moisture);

        TextView textRes = (TextView) view.findViewById(R.id.result);
        //textRes.setText(messageText); //????

        String city = DataClass.getValueFromArray(getContext(),weatherId,R.array.cities);
        String weather = DataClass.getValueFromArray(getContext(),weatherId,R.array.weather_for_city);
        messageText = city + " " + weather;
        textRes.setText(messageText);

        setCheckBoxResults(markWhatNeed);

        ImageView image = (ImageView) view.findViewById(R.id.image_res);
        image.setImageResource(R.drawable.ic_line_style_black_24dp);


//        Intent intent = new Intent();
//        if  (intent != null )   {
//            //intent.setResult(RESULT_OK, intent);
//
//            messageText = intent.getStringExtra(EXTRA_MESSAGE);
//            TextView textRes = (TextView) view.findViewById(R.id.result);
//            textRes.setText(messageText);
//
//            weath_id = intent.getIntExtra(EXTRA_WEATHER_ID,-1);
//            if (weath_id == -1) throw new RuntimeException("что то пошло не так");
//
//            boolean[] b = intent.getBooleanArrayExtra(EXTRA_CHECKBOX);
//            setCheckBoxResults(b);
//        }
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_TEXT, messageText);
      //  if (intentShare.resolveActivity(getPackageManager()) != null)
        startActivity(intentShare);
    }

    private void setCheckBoxResults(boolean[] b){
        for(int i = 0; i <= 2; i++) {
            String text = DataClass.getValueFromArray(getContext(), weatherId,idArray.get(i));
            if (b[i]) checkBoxMap.get(i).setText(text);
        }
    }


}
