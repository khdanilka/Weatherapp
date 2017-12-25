package weather.khdanapp.com.weatherapp.data;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import weather.khdanapp.com.weatherapp.R;

public final class DataClass {

    private static SharedPreferences sharedPref;
    private static final String SAVED_CITY = "saved_text";
    private static final String SAVED_CHECKBOX_P = "saved_checkbox_p";
    private static final String SAVED_CHECKBOX_W = "saved_checkbox_w";
    private static final String SAVED_CHECKBOX_M = "saved_checkbox_m";

    private DataClass() {

    }

    public static String getWeatherByCityId(Context context, int id){
        String res_weather_arr[] = context.getResources().getStringArray(R.array.weather_for_city);
        return res_weather_arr[id];
    }

    public  static String getTextByID(Context context, int id){
        return context.getResources().getString(id);
    }

    public static void writeToPref(Activity activity, String city){
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        ed.putString(SAVED_CITY, city);
        ed.apply();
    }

    public static void writeCheckBoxToPref(Activity activity, boolean[] b){
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        ed.putBoolean(SAVED_CHECKBOX_P, b[0]);
        ed.putBoolean(SAVED_CHECKBOX_W, b[1]);
        ed.putBoolean(SAVED_CHECKBOX_M, b[2]);
        ed.apply();
    }

    public static String readData(Activity activity){
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(SAVED_CITY, "");
    }

    public static boolean[] readCheckBox(Activity activity){
        boolean[] b = new boolean[3];
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        boolean savedB = sharedPref.getBoolean(SAVED_CHECKBOX_P, false);
        b[0] = savedB;
        savedB = sharedPref.getBoolean(SAVED_CHECKBOX_W, false);
        b[1] = savedB;
        savedB = sharedPref.getBoolean(SAVED_CHECKBOX_M, false);
        b[2] = savedB;

        return b;
    }

    public static String getValueFromArray(Context context, int id, int id_arr){
        String res_value[] = context.getResources().getStringArray(id_arr);
        return res_value[id];
    }

    public static int getItemCount(Context context, int id_arr){
        String res_value[] = context.getResources().getStringArray(id_arr);
        return res_value.length;
    }


    private static OkHttpClient client = new OkHttpClient();

    public static String getWeatherForCity(String city) throws IOException {

        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=1cc4dc8d4cb24e9e1d04eb5e7c57d8e9";

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }





}
