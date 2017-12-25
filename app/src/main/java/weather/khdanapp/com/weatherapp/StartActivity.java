package weather.khdanapp.com.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.camera.OnImageReadyListener;
import com.esafirm.imagepicker.model.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity implements FragmentOneListener, NavigationView.OnNavigationItemSelectedListener, DialogCityChooseFragment.OnFragmentInteractionListener {

    FragmentOne detailFragment;
    private String AVATAR_IMAGE = "avatar";

    private static final int RC_CODE_PICKER = 2000;
    ImageButton imB;
    String messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        detailFragment = new FragmentOne();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_segment, detailFragment);
        transaction.commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_start);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_start);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View header = navigationView.getHeaderView(0);
        imB = (ImageButton) header.findViewById(R.id.imageViewButtom);
        imB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.create(StartActivity.this) // Activity or Fragment
                        .returnAfterFirst(true) // set whether pick or camera action should return immediate result or not. For pick image only work on single mode
                        .folderMode(true) // folder mode (false by default)
                        .folderTitle("Folder") // folder selection title
                        .imageTitle("Tap to select") // image selection title
                        .single() // single mode
                        .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                        .enableLog(false) // disabling log
                        .start(RC_CODE_PICKER); // start image picker activity with request code
            }
        });

        File file = new File(getApplicationContext().getFilesDir(),AVATAR_IMAGE);
        if(file.exists()) {
            Drawable d = Drawable.createFromPath(file.getPath());
            imB.setImageDrawable(d);
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_TEXT, messageText);
                //  if (intentShare.resolveActivity(getPackageManager()) != null)
                if (messageText!=null && !messageText.isEmpty()) startActivity(intentShare);
                else Toast.makeText(getApplicationContext(),"пусто",Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public void onItemClicked(int id,boolean[] b) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_start);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_weather) {
            // Handle the camera action
        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_feedback) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_start);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String str) {
        detailFragment.setWeatherText(str);
    }

    private ArrayList<Image> images = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            images = (ArrayList<Image>) ImagePicker.getImages(data);
            Drawable d = Drawable.createFromPath(images.get(0).getPath());
            saveOnInternalStorage(images.get(0).getPath());
            imB.setImageDrawable(d);

            return;
        }
    }

    private void saveOnInternalStorage(String path){
        FileOutputStream outputStream;
        try {
            if ( path!=null || !path.isEmpty()) {

                File sourceLocation = new File(path);
                InputStream in = new FileInputStream(sourceLocation);

                outputStream = openFileOutput(AVATAR_IMAGE, Context.MODE_PRIVATE);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    outputStream.write(buf, 0, len);
                }
                outputStream.flush();
                outputStream.close();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setShareText(String text){

        messageText = text;

    }




}
