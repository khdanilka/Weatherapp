package weather.khdanapp.com.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    private final static int VERTICAL = 1;

    RecyclerView itemCityRecyclerView;
    MyHistoryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_history);
        toolbar.setTitle("History");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        itemCityRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_history); //Найдем наш RecyclerView
        registerForContextMenu(itemCityRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HistoryActivity.this); //Создадим LinearLayoutManager
        layoutManager.setOrientation(VERTICAL);//Обозначим ориентацию
        itemCityRecyclerView.setLayoutManager(layoutManager);//Назначим нашему RecyclerView созданный ранее layoutManager
        adapter = new MyHistoryAdapter();
        itemCityRecyclerView.setAdapter(adapter);//Назначим нашему RecyclerView адаптер

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public class MyHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

        private TextView categoryNameTextView;

        public MyHistoryViewHolder(LayoutInflater inflater, ViewGroup parent){
            this(inflater.inflate(R.layout.view_item,parent,false));
            itemView.setOnClickListener(this);
            categoryNameTextView = (TextView) itemView.findViewById(R.id.category_item_text);
        }

        public MyHistoryViewHolder(View v){
            super(v);
            v.setOnCreateContextMenuListener(this);
        }

        void bind(int position) {
            String category = DataClass.getValueFromArray(HistoryActivity.this,position,R.array.cities);
            categoryNameTextView.setText(category);
        }

        @Override
        public void onClick(View view) {
            //showActivity(this.getLayoutPosition());
            //currentContextPosition = this.getLayoutPosition();
            itemCityRecyclerView.showContextMenuForChild(view);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.contex_menu, contextMenu);

        }
    }

    //Адаптер для RecyclerView
    private class MyHistoryAdapter extends RecyclerView.Adapter<MyHistoryViewHolder> {

        @Override
        public MyHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(HistoryActivity.this);
            return new  MyHistoryViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(MyHistoryViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return DataClass.getItemCount(HistoryActivity.this,R.array.cities);
        }
    }


}
