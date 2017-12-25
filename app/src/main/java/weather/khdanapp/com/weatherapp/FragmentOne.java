package weather.khdanapp.com.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Locale;

import weather.khdanapp.com.weatherapp.data.DataClass;
import weather.khdanapp.com.weatherapp.data.WeatherDataSourse;
import weather.khdanapp.com.weatherapp.data.WeatherFromService;

public class FragmentOne extends Fragment implements View.OnClickListener {

    private final static int VERTICAL = 1;

    TextView seeNext;
    CheckBox pressure;
    CheckBox windSpeed;
    CheckBox moisture;
    boolean[] checkBoxValue = new boolean[3];
    Button additionalSet;
    Button choosingCity;
    TextView t;
    WeatherDataSourse weatherDataSourse;
    String messageText;

    static final int CODE_FOR_RESULT = 1;
    static int count;

    private FragmentOneListener starActivity;
    //RecyclerView itemCityRecyclerView;
    // MyAdapter adapter;

    @Override
    public void onAttach(Context context) {
        starActivity = (FragmentOneListener) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        weatherDataSourse = new WeatherDataSourse(getContext());

        View view = inflater.inflate(R.layout.activity_main, container, false);
        String city_n = DataClass.readData(getActivity());
        if (!city_n.equals("")) setWeatherText(city_n);

        checkBoxValue = DataClass.readCheckBox(getActivity());

        setHasOptionsMenu(true);

        additionalSet = (Button) view.findViewById(R.id.additional);
        additionalSet.setOnClickListener(this);

        choosingCity = (Button) view.findViewById(R.id.choosing_city);
        choosingCity.setOnClickListener(this);

        t = (TextView) view.findViewById(R.id.seeAll);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                Log.d("тест: " , "edit");
                Intent intent = new Intent(getActivity(),HistoryActivity.class);
                startActivity(intent);

                return true;
            case R.id.menu_clear:
                Log.d("тест: " , "clear");
                return true;
            case R.id.menu_test:
                Log.d("тест: " , "test");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = getActivity().getMenuInflater();
//        inflater.inflate(R.menu.contex_menu, menu);
//    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.context_1:
//                //Log.d("тест: " , "open: " + currentContextPosition);
//                showActivity(currentContextPosition);
//                return true;
//            case R.id.context_2:
//                //Log.d("тест: " , "close: " + currentContextPosition);
//                Toast toast = Toast.makeText(getContext(),
//                        "Context menu was closed", Toast.LENGTH_SHORT);
//                toast.show();
//                return true;
//            default:
//                return super.onContextItemSelected(item);
//        }
//    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.additional) {
            showPopup(view);
        } else if (view.getId() == R.id.choosing_city){
            showDialog();
        }

    }

    private void showDialog() {
        android.support.v4.app.DialogFragment newFragment = DialogCityChooseFragment.newInstance();
        newFragment.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    PopupMenu popup;
    public void showPopup(View v) {
        if (popup==null) {
            popup = new PopupMenu(getContext(), v);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.pres_menu:
                            setCheckBox(item, 0);
                            return true;
                        case R.id.wind_menu:
                            setCheckBox(item, 1);
                            return true;
                        case R.id.mois_menu:
                            setCheckBox(item, 2);
                            return true;
                        default:
                            return false;
                    }
                }
            });
            popup.inflate(R.menu.more_settings_menu);
            MenuItem m1 = popup.getMenu().getItem(0);
            if (checkBoxValue[0]) m1.setChecked(true);
            MenuItem m2 = popup.getMenu().getItem(1);
            if (checkBoxValue[1]) m2.setChecked(true);
            MenuItem m3 = popup.getMenu().getItem(2);
            if (checkBoxValue[2]) m3.setChecked(true);
        }
        popup.show();
    }

    private void setCheckBox(MenuItem item, int i) {
        if (item.isChecked()) {
            checkBoxValue[i] = false;
            item.setChecked(false);
        }
        else if (!item.isChecked()) {
            checkBoxValue[i] = true;
            item.setChecked(true);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //super.onActivityResult(requestCode, resultCode, data);
//
//        if ((data!= null) && (requestCode == CODE_FOR_RESULT)) {
//            count++;
//            String str = DataClass.getTextByID(this,R.string.seeAllEmpty) + ". Уже " + count + " раз";
//            seeNext.setText(str);
//        }
//
//    }

    final String LOG_TAG = "myLogs";
//
//    @Override
//    protected void onDestroy() {
//        Log.d(LOG_TAG, "onDestroy");
//
//        super.onDestroy();
//    }
//
//    protected void onPause() {
//        super.onPause();
//        Log.d(LOG_TAG, "onPause");
//    }

//    protected void onRestart() {
//        //super.onRestart();
//        Log.d(LOG_TAG, "onRestart");
//    }
//
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        Log.d(LOG_TAG, "onRestoreInstanceState");
//    }
//
//    protected void onResume() {
//        super.onResume();
//        Log.d(LOG_TAG, "onResume ");
//    }

    private static final String CITY_INSTANCE = "city_instance";

//    protected void onSaveInstanceState(Bundle outState) {
//        int  city_pos  = city_selection.getSelectedItemPosition();
//        outState.putInt(CITY_INSTANCE, city_pos);
//        super.onSaveInstanceState(outState);
//        Log.d(LOG_TAG, "onSaveInstanceState");
//    }
//
//    protected void onStart() {
//        super.onStart();
//        Log.d(LOG_TAG, "onStart");
//    }

    public void onStop() {

        if (currentCity != null && !currentCity.isEmpty()) {
            DataClass.writeToPref(getActivity(), currentCity);
        } else DataClass.writeToPref(getActivity(), "");
        DataClass.writeCheckBoxToPref(getActivity(),checkBoxValue);
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    private int currentContextPosition;
    private String currentCity;
    private static final Double KELVIN = 273.15;



    public void handleWeatherInFragment(WeatherFromService w) {

        if (!w.getCod().equals(404)) {
            String result = "Город: " + w.getName() + " Погода: " + (w.getMain().getTemp() - KELVIN) + " C";
            Log.i("GSON", "Город: " + w.getName() + "\nПогода: " + w.getMain().getTemp());
            postOnMain(result);
            currentCity = w.getName();
            double d = w.getMain().getTemp() - KELVIN;
            weatherDataSourse.addWeatherToDB(w.getName(), String.format(Locale.US,"%.2f", d),"13-12-2017");
            starActivity.setShareText(result);
        } else postOnMain("City not found");

    }

    public void setWeatherText(final String str) {

        final String city = str;
        Log.d("Choosed ", str);
        MyIntentService.startActionFoo(getContext(), city);
    }
//
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////                try {
////                    String jsonText = DataClass.getWeatherForCity(city);
////                    Log.i("JSON",jsonText);
////                    GsonBuilder builder = new GsonBuilder();
////                    Gson gson = builder.create();
////                    WeatherFromService murzik = gson.fromJson(jsonText, WeatherFromService.class);
////                    if (!murzik.getCod().equals(404)) {
////                        String result = "Город: " + murzik.getName() + " Погода: " + (murzik.getMain().getTemp() - KELVIN) + " C";
////                        Log.i("GSON", "Город: " + murzik.getName() + "\nПогода: " + murzik.getMain().getTemp());
////                        postOnMain(result);
////                        currentCity = murzik.getName();
////                        double d = murzik.getMain().getTemp() - KELVIN;
////                        weatherDataSourse.addWeatherToDB(murzik.getName(), String.format(Locale.US,"%.2f", d),"13-12-2017");
////                        starActivity.setShareText(result);
////                    } else postOnMain("City not found");
////
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////
////            }
////        }).start();
//
//
//    }

    public void postOnMain(String str){

        final String strT = str;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                t.setText(strT);
            }
        });

    }


//    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
//
//        private TextView categoryNameTextView;
//
//        public MyViewHolder(LayoutInflater inflater, ViewGroup parent){
//            this(inflater.inflate(R.layout.view_item,parent,false));
//            itemView.setOnClickListener(this);
//            categoryNameTextView = (TextView) itemView.findViewById(R.id.category_item_text);
//        }
//
//        public MyViewHolder(View v){
//            super(v);
//            v.setOnCreateContextMenuListener(this);
//        }
//
//        void bind(int position) {
//            String category = DataClass.getValueFromArray(getContext(),position,R.array.cities);
//            categoryNameTextView.setText(category);
//        }
//
//        @Override
//        public void onClick(View view) {
//            //showActivity(this.getLayoutPosition());
//            currentContextPosition = this.getLayoutPosition();
//            itemCityRecyclerView.showContextMenuForChild(view);
//        }
//
//        @Override
//        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//            MenuInflater inflater = getActivity().getMenuInflater();
//            inflater.inflate(R.menu.contex_menu, contextMenu);
//
//        }
//    }
//
//    //Адаптер для RecyclerView
//    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            return new MyViewHolder(inflater, parent);
//        }
//
//        @Override
//        public void onBindViewHolder(MyViewHolder holder, int position) {
//            holder.bind(position);
//        }
//
//        @Override
//        public int getItemCount() {
//            return DataClass.getItemCount(getContext(),R.array.cities);
//        }
//    }
//
//    //Запускаем активити для конкретной услуги
//    private void showActivity(int categoryId) {
//        starActivity.onItemClicked(categoryId,checkBoxValue);
//    }



}

