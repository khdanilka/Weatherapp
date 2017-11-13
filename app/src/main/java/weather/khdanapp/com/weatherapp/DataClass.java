package weather.khdanapp.com.weatherapp;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

final class DataClass {

    private static SharedPreferences sharedPref;
    private static final String SAVED_TEXT = "saved_text";
    private static final String SAVED_CHECKBOX_P = "saved_checkbox_p";
    private static final String SAVED_CHECKBOX_W = "saved_checkbox_w";
    private static final String SAVED_CHECKBOX_M = "saved_checkbox_m";

    private DataClass() {

    }

    static String getWeatherByCityId(Context context, int id){
        String res_weather_arr[] = context.getResources().getStringArray(R.array.weather_for_city);
        return res_weather_arr[id];
    }

    static String getTextByID(Context context, int id){
        return context.getResources().getString(id);
    }

    static void writeToPref(Activity activity, int city_pos){
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        ed.putString(SAVED_TEXT, String.valueOf(city_pos));
        ed.apply();
    }

    static int readData(Activity activity){
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String savedText = sharedPref.getString(SAVED_TEXT, "");
        if (sharedPref.contains(SAVED_TEXT)) return Integer.valueOf(savedText);
        return -1;
    }

    static String getValueFromArray(Context context, int id, int id_arr){
        String res_value[] = context.getResources().getStringArray(id_arr);
        return res_value[id];
    }

    static int getItemCount(Context context, int id_arr){
        String res_value[] = context.getResources().getStringArray(id_arr);
        return res_value.length;
    }

    static void writeCheckBoxToPref(Activity activity, boolean[] b){
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        ed.putBoolean(SAVED_CHECKBOX_P, b[0]);
        ed.putBoolean(SAVED_CHECKBOX_W, b[1]);
        ed.putBoolean(SAVED_CHECKBOX_M, b[2]);
        ed.apply();
    }

    static boolean[] readCheckBox(Activity activity){
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


}
