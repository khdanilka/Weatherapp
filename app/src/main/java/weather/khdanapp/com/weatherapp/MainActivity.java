package weather.khdanapp.com.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static int VERTICAL = 1;

    private Spinner city_selection;
    TextView seeNext;
    CheckBox pressure;
    CheckBox windSpeed;
    CheckBox moisture;
    boolean[] checkBoxValue = new boolean[3];

    static final int CODE_FOR_RESULT = 1;
    static int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city_selection = (Spinner) findViewById(R.id.spin);
        int city_n = DataClass.readData(this);
        checkBoxValue = DataClass.readCheckBox(this);
        if (savedInstanceState!= null) {
            int k = savedInstanceState.getInt(CITY_INSTANCE);
            city_selection.setSelection(k);
        }
        else if (city_n > 0) city_selection.setSelection(city_n);

//        Button find_b = (Button) findViewById(R.id.find);
//        find_b.setOnClickListener(MainActivity.this);

        seeNext = (TextView) findViewById(R.id.seeAll);

        pressure = (CheckBox) findViewById(R.id.pressure);
        if (checkBoxValue[0]) pressure.setChecked(true);
        windSpeed = (CheckBox) findViewById(R.id.wind_speed);
        if (checkBoxValue[1]) windSpeed.setChecked(true);
        moisture = (CheckBox) findViewById(R.id.moisture);
        if (checkBoxValue[2]) moisture.setChecked(true);

        pressure.setOnClickListener(this);
        windSpeed.setOnClickListener(this);
        moisture.setOnClickListener(this);


        RecyclerView itemCityRecyclerView = (RecyclerView) findViewById(R.id.recycler_view); //Найдем наш RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this); //Создадим LinearLayoutManager
        layoutManager.setOrientation(VERTICAL);//Обозначим ориентацию
        itemCityRecyclerView.setLayoutManager(layoutManager);//Назначим нашему RecyclerView созданный ранее layoutManager
        itemCityRecyclerView.setAdapter(new MyAdapter());//Назначим нашему RecyclerView адаптер


    }

    @Override
    public void onClick(View view) {

//        if   (view.getId()  ==  R.id.find)   {
////                int  city_pos  = city_selection.getSelectedItemPosition();
////                String cityWeather = DataClass.getWeatherByCityId(this,city_pos);
////                String mes = city_selection.getSelectedItem() + ": " + cityWeather;
////                Intent n = new Intent(this,ResultActivity.class);
////                n.putExtra(ResultActivity.EXTRA_MESSAGE, mes);
////                n.putExtra(ResultActivity.EXTRA_CHECKBOX,checkBoxValue);
////                n.putExtra(ResultActivity.EXTRA_WEATHER_ID,city_pos);
////                startActivityForResult(n, CODE_FOR_RESULT);
//        } else
        if (view.getId() == R.id.pressure) {
            setCheckBox(pressure,0);
        } else if (view.getId() == R.id.wind_speed) {
            setCheckBox(windSpeed,1);
        } else if (view.getId() == R.id.moisture) {
            setCheckBox(moisture,2);
        }
    }

    private void setCheckBox(CheckBox checkBox, int i) {
        if (checkBox.isChecked()) checkBoxValue[i] = true;
        else if (!checkBox.isChecked()) checkBoxValue[i] = false;
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


    final String LOG_TAG = "myLogs";

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");

        super.onDestroy();
    }

    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(LOG_TAG, "onRestoreInstanceState");
    }

    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume ");
    }

    private static final String CITY_INSTANCE = "city_instance";

    protected void onSaveInstanceState(Bundle outState) {
        int  city_pos  = city_selection.getSelectedItemPosition();
        outState.putInt(CITY_INSTANCE, city_pos);
        super.onSaveInstanceState(outState);
        Log.d(LOG_TAG, "onSaveInstanceState");
    }

    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    protected void onStop() {

        int  city_pos  = city_selection.getSelectedItemPosition();
        String cityWeather = DataClass.getWeatherByCityId(this,city_pos);
        DataClass.writeToPref(this,city_pos);
        DataClass.writeCheckBoxToPref(this,checkBoxValue);

        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView categoryNameTextView;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.view_item,parent,false));
            itemView.setOnClickListener(this);
            categoryNameTextView = (TextView) itemView.findViewById(R.id.category_item_text);
        }

        void bind(int position) {
            //String category = Nail.nails[position].getName();
            String category = DataClass.getValueFromArray(MainActivity.this,position,R.array.cities);
            categoryNameTextView.setText(category);
        }

        @Override
        public void onClick(View view) {
            showActivity(this.getLayoutPosition());
        }
    }

    //Адаптер для RecyclerView
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            return new MyViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return DataClass.getItemCount(MainActivity.this,R.array.cities);
        }
    }

    //Запускаем активити для конкретной услуги
    private void showActivity(int categoryId) {
//        Intent intent = new Intent(this, NailActivity.class);
//        intent.putExtra(NailActivity.EXTRA_NAILNOM, categoryId);
//        startActivity(intent);

        int  city_pos  = categoryId;
        String cityWeather = DataClass.getWeatherByCityId(this,city_pos);
        String mes = city_selection.getSelectedItem() + ": " + cityWeather;
        Intent n = new Intent(this,ResultActivity.class);
        n.putExtra(ResultActivity.EXTRA_MESSAGE, mes);
        n.putExtra(ResultActivity.EXTRA_CHECKBOX,checkBoxValue);
        n.putExtra(ResultActivity.EXTRA_WEATHER_ID,city_pos);
        startActivityForResult(n, CODE_FOR_RESULT);

    }



}

