package weather.khdanapp.com.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

public class FragmentOne extends Fragment implements View.OnClickListener {

    private final static int VERTICAL = 1;

    private Spinner city_selection;
    TextView seeNext;
    CheckBox pressure;
    CheckBox windSpeed;
    CheckBox moisture;
    boolean[] checkBoxValue = new boolean[3];

    static final int CODE_FOR_RESULT = 1;
    static int count;

    private FragmentOneListener starActivity;


    @Override
    public void onAttach(Context context) {
        starActivity = (FragmentOneListener) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_main,  container, false);
         //city_selection = (Spinner) view.findViewById(R.id.spin);
        //int city_n = DataClass.readData(getContext());
        //checkBoxValue = DataClass.readCheckBox(this);
        if (savedInstanceState!= null) {
            int k = savedInstanceState.getInt(CITY_INSTANCE);
            city_selection.setSelection(k);
        }
        //else if (city_n > 0) city_selection.setSelection(city_n);

        seeNext = (TextView) view.findViewById(R.id.seeAll);

        pressure = (CheckBox) view.findViewById(R.id.pressure);
        if (checkBoxValue[0]) pressure.setChecked(true);
        windSpeed = (CheckBox) view.findViewById(R.id.wind_speed);
        if (checkBoxValue[1]) windSpeed.setChecked(true);
        moisture = (CheckBox) view.findViewById(R.id.moisture);
        if (checkBoxValue[2]) moisture.setChecked(true);

        pressure.setOnClickListener(this);
        windSpeed.setOnClickListener(this);
        moisture.setOnClickListener(this);


        RecyclerView itemCityRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view); //Найдем наш RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()); //Создадим LinearLayoutManager
        layoutManager.setOrientation(VERTICAL);//Обозначим ориентацию
        itemCityRecyclerView.setLayoutManager(layoutManager);//Назначим нашему RecyclerView созданный ранее layoutManager
        itemCityRecyclerView.setAdapter(new MyAdapter());//Назначим нашему RecyclerView адаптер


        return view;
    }

    @Override
    public void onClick(View view) {

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

//    public void onStop() {
//
//////        int  city_pos  = city_selection.getSelectedItemPosition();
////        String cityWeather = DataClass.getWeatherByCityId(getActivity(),city_pos);
////        DataClass.writeToPref(getActivity(),city_pos);
////        DataClass.writeCheckBoxToPref(getActivity(),checkBoxValue);
////        super.onStop();
////        Log.d(LOG_TAG, "onStop");
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView categoryNameTextView;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.view_item,parent,false));
            itemView.setOnClickListener(this);
            categoryNameTextView = (TextView) itemView.findViewById(R.id.category_item_text);
        }

        void bind(int position) {
            String category = DataClass.getValueFromArray(getContext(),position,R.array.cities);
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
            LayoutInflater inflater = LayoutInflater.from(getContext());
            return new MyViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return DataClass.getItemCount(getContext(),R.array.cities);
        }
    }

    //Запускаем активити для конкретной услуги
    private void showActivity(int categoryId) {

        starActivity.onItemClicked(categoryId,checkBoxValue);

    }

}

