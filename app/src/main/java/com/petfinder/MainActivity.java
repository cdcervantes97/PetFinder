package com.petfinder;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.petfinder.ui.find.petAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    List<Pet> pets;
    com.petfinder.ui.find.petAdapter petAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_find, R.id.navigation_post, R.id.navigation_chat)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        PostResponseAsyncTask task = new PostResponseAsyncTask(MainActivity.this, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                //Toast.makeText(MainActivity.this, s,Toast.LENGTH_LONG).show();
                //Log.d("+======================",s);
                pets = new JsonConverter<Pet>().toList(s, Pet.class);
                petAdapter = new petAdapter(MainActivity.this, pets);
            }
        });
        task.execute("http://petfinderapp.x10host.com/pettable.php");

        if(Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},2);
        }
        //checkNetworkStatus();
    }

    public List<Pet> getPetsArr() {
        return this.pets;
    }
    public petAdapter getPetAdapter() { return this.petAdapter;}

    public void checkNetworkStatus(){
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(MainActivity.CONNECTIVITY_SERVICE);

        boolean is3g = Objects.requireNonNull(manager).getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

        boolean isWifi = Objects.requireNonNull(manager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI)).isConnectedOrConnecting();
        if (is3g) {
            //Toast.makeText(this, "Running on Mobile Network.", Toast.LENGTH_SHORT).show();
        } else if (isWifi) {
            //Toast.makeText(this, "Running on Wi-Fi Network.", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder networkDialog = new AlertDialog.Builder(MainActivity.this);

            networkDialog.setMessage("You're not connected to a network.")//R.string.dialog_fire_missiles)
                    .setPositiveButton("Connect to Wi Fi", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(MainActivity.this, "Please connect to a Wi Fi Network.", Toast.LENGTH_LONG).show();
                            // Prompt user to turn on wi-fi
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    })
                    .setNegativeButton("Turn on Mobile Data", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                setMobileDataEnabled(MainActivity.this, true);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog dialog = networkDialog.create();
            dialog.show();
        }
    }

    private void setMobileDataEnabled(Context context, boolean enabled) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        final ConnectivityManager conman = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
        connectivityManagerField.setAccessible(true);
        final Object connectivityManager = connectivityManagerField.get(conman);
        final Class connectivityManagerClass =  Class.forName(connectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);

        setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
    }

}
