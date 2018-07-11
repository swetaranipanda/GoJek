package golife.com.gojektest.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;

import golife.com.gojektest.R;
import golife.com.gojektest.fragment.ErrorFragment;
import golife.com.gojektest.utils.AppUtils;
import golife.com.gojektest.view.RobotoBlackTextView;
import golife.com.gojektest.view.RobotoThinTextView;


public class WeatherActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    String locality;
    private GoogleApiClient mGoogleApiClient;
    LocationRequest locationRequest;
    private Location mLastLocation;
    double latitude, longitude;
    ArrayList<String> dayList = new ArrayList<>();
    ArrayList<String> tempList = new ArrayList<>();

    RobotoBlackTextView tempText;
    RobotoThinTextView locationTxt;
    ImageView loadingImage;
    AlertDialog alertDialog = null;
    boolean isShown = true;
    Animation startRotateAnimation;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        AppUtils.changeStatusBarColor(this, WeatherActivity.this);
        buildGoogleApiClient();
        initUI();
        if (AppUtils.isOnline(this)) {
            if (AppUtils.isGPSEnabled(this)) {

                if (!AppUtils.haveLocationPermission(this)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        AppUtils.askLocationPermission(WeatherActivity.this);
                    }
                }
            } else {
                loadingImage.clearAnimation();
                loadingImage.setVisibility(View.GONE);
            }
        } else {
            loadingImage.clearAnimation();
            loadingImage.setVisibility(View.GONE);
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void initUI() {
        tempText = findViewById(R.id.currenttemp_txt);
        locationTxt = findViewById(R.id.currentloc_txt);
        loadingImage = findViewById(R.id.loading_iv);
        startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        loadingImage.startAnimation(startRotateAnimation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null && !mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
    }



    private void stsrtLocationUpdate() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);
        if (mGoogleApiClient.isConnected()) {
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);

            } catch (SecurityException e) {
                Toast.makeText(this, "exception occ", Toast.LENGTH_SHORT).show();
            }

        } else {
            mGoogleApiClient.connect();

        }
    }

    private void displayLocation() {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException e) {
            Toast.makeText(this, "exception occ", Toast.LENGTH_SHORT).show();
        }
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            locality = AppUtils.geoCodeData(this, latitude, longitude);

        } else {
            loadingImage.startAnimation(startRotateAnimation);
            loadingImage.setVisibility(View.VISIBLE);

            stsrtLocationUpdate();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient != null && !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(WeatherActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (!mGoogleApiClient.isConnected()) {
                        mGoogleApiClient.connect();
                    } else {
                        displayLocation();
                    }
                } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    Log.d("time", String.valueOf(System.currentTimeMillis()));
                    builder.setMessage("Please give permission for location services to see the weather forecast around you")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    alertDialog.cancel();
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                                }
                            });
                    alertDialog = builder.create();
                    alertDialog.show();
                }

                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            locality = AppUtils.geoCodeData(this, lat, lng);
            while (isShown) {
                displayLocation();
                isShown = false;
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
        } catch (SecurityException e) {
            AppUtils.askLocationPermission(WeatherActivity.this);

        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            locality = AppUtils.geoCodeData(this, latitude, longitude);

        }
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mGoogleApiClient.connect();
    }


    private void openErrorScreen() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.parent_view, new ErrorFragment());
        ft.commit();
    }



}
