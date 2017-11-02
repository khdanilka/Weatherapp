package weather.khdanapp.com.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner city_selection;
    TextView seeNext;

    static final int CODE_FOR_RESULT = 1;
    static int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city_selection = (Spinner) findViewById(R.id.spin);
        int city_n = DataClass.readData(this);
        if (city_n > 0) city_selection.setSelection(city_n);

        Button find_b = (Button) findViewById(R.id.find);
        find_b.setOnClickListener(MainActivity.this);

        seeNext = (TextView) findViewById(R.id.seeAll);
    }

    @Override
    public void onClick(View view) {

        if   (view .getId()  ==  R.id.find)   {
                int  city_pos  = city_selection.getSelectedItemPosition();
                String cityWeather = DataClass.getWeatherByCityId(this,city_pos);
                DataClass.writeToPref(this,city_pos,cityWeather);

                String mes = city_selection.getSelectedItem() + ": " + cityWeather;
                Intent n = new Intent(this,ResultActivity.class);
                n.putExtra(ResultActivity.EXTRA_MESSAGE, mes);
                startActivityForResult(n, CODE_FOR_RESULT);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if ((data!= null) && (requestCode == CODE_FOR_RESULT)) {
            count++;
            String str = DataClass.getTextByID(this,R.string.seeAllEmpty) + ". Уже " + count + " раз";
            seeNext.setText(str);
        }

    }
}

