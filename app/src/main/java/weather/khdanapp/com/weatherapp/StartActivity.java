package weather.khdanapp.com.weatherapp;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

public class StartActivity extends AppCompatActivity implements FragmentOneListener {

    FragmentOne detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        detailFragment = new FragmentOne();

        //detailFragment.setWeatherId(categoryId);
        //detailFragment.setMarkWhatNeed(checkBoxValue);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_segment, detailFragment);

        transaction.commit();


    }



    @Override
    public void onItemClicked(int id,boolean[] b) {
        ResultFragment detailFragment = new ResultFragment();
        detailFragment.setWeatherId(id);
        detailFragment.setMarkWhatNeed(b);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_segment, detailFragment);
        transaction.addToBackStack(transaction.toString());
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
