package weather.khdanapp.com.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView weather_res;
    private Spinner city_selection;
    SharedPreferences sharedPref;
    final String SAVED_TEXT = "saved_text";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int city_n = readData();
        String city_arr[] = getResources().getStringArray(R.array.cities);

        weather_res = (TextView) findViewById(R.id.result);
        city_selection = (Spinner) findViewById(R.id.spin);

        if (city_n > 0) city_selection.setSelection(city_n);

        Button find_b = (Button) findViewById(R.id.find);

        find_b.setOnClickListener(MainActivity.this);



    }

    @Override
    public void onClick(View view) {

        if   (view .getId()  ==  R.id.find)   {
                int  city_pos  = city_selection.getSelectedItemPosition();
                String res_weather_arr[] = this.getResources().getStringArray(R.array.weather_for_city);
                weather_res.setText(res_weather_arr[city_pos]);
                writeToPref(city_pos,res_weather_arr[city_pos]);
        }

    }

    private void writeToPref(int city_pos, String weather){
        sharedPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        ed.putString(SAVED_TEXT, String.valueOf(city_pos));
        ed.apply();
    }

    private int readData(){
        sharedPref = getPreferences(MODE_PRIVATE);

        String savedText = sharedPref.getString(SAVED_TEXT, "");
        if (sharedPref.contains(SAVED_TEXT)) return Integer.valueOf(savedText);

        return -1;
    }

}

