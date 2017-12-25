package weather.khdanapp.com.weatherapp;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Locale;

import weather.khdanapp.com.weatherapp.data.DataClass;
import weather.khdanapp.com.weatherapp.data.WeatherFromService;


public class MyIntentService extends IntentService {

    private static final String ACTION_FOO = "weather.khdanapp.com.weatherapp.action.FOO";

    private static final String EXTRA_PARAM1 = "weather.khdanapp.com.weatherapp.extra.PARAM1";

    public MyIntentService() {
        super("MyIntentService");
    }

    static MyIntentServiceListener f1;

    public static void startActionFoo(Context context, String city) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, city);
        if (f1==null) f1 = (MyIntentServiceListener) context;
        //intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1);
            }
        }
    }

    private static final Double KELVIN = 273.15;

    private void handleActionFoo(String city) {
        String jsonText = null;
        try {
            jsonText = DataClass.getWeatherForCity(city);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("JSON",jsonText);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        WeatherFromService murzik = gson.fromJson(jsonText, WeatherFromService.class);
        f1.handleWeather(murzik);
    }

    public interface MyIntentServiceListener{
        void handleWeather(WeatherFromService w);
    }


}
