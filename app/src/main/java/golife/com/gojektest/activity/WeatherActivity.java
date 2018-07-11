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
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

import golife.com.gojektest.R;
import golife.com.gojektest.api.services.WeatherAPIService;
import golife.com.gojektest.fragment.ErrorFragment;
import golife.com.gojektest.fragment.ForecastFrag;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            AppUtils.changeStatusBarColor(this, WeatherActivity.this);
        }
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
                switchOnGPS();
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

    private void openFrag() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.translate_anim, R.anim.translate_anim);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("daylist", dayList);
        bundle.putStringArrayList("templist", tempList);
        ForecastFrag forecastFrag = new ForecastFrag();
        forecastFrag.setArguments(bundle);
        ft.replace(R.id.root_view, forecastFrag, "frag");
        ft.commitAllowingStateLoss();
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
            try {
                Log.i("api", "1");
                WeatherAPIService.getInstance().getWeatherData(getString(R.string.api_key), locality, 5, weatherCallback());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loadingImage.startAnimation(startRotateAnimation);
            loadingImage.setVisibility(View.VISIBLE);

            // openErrorScreen();
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

    private Callback weatherCallback() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        openErrorScreen();

                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() == 200) {
                    WeatherRes weatherRes = GsonUtils.gson.fromJson(response.body().string(), new TypeToken<WeatherRes>() {
                    }.getType());
                    final String s = weatherRes.getLocation().getName();
                    List<WeatherRes.Forecastday> forecastdays = weatherRes.getForecast().getForecastday();
                    final double currentTemp = weatherRes.getCurrent().getTemp_c();
                    dayList.clear();
                    tempList.clear();
                    for (int i = 1; i < forecastdays.size(); i++) {
                        dayList.add(forecastdays.get(i).getDate());
                        tempList.add(String.valueOf(Math.round(forecastdays.get(i).getDay().getAvgtemp_c())));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            loadingImage.clearAnimation();
                            loadingImage.setVisibility(View.GONE);
                            locationTxt.setVisibility(View.VISIBLE);
                            tempText.setVisibility(View.VISIBLE);
                            locationTxt.setText(s);
                            tempText.setText(String.valueOf(Math.round(currentTemp)) + (char) 0x00B0);
                            openFrag();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            openErrorScreen();

                        }
                    });
                }
            }
        };
    }

    private void openErrorScreen() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.parent_view, new ErrorFragment());
        ft.commit();
    }

    private void switchOnGPS() {
        stsrtLocationUpdate();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        //  Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        //  Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(WeatherActivity.this, 5);
                        } catch (IntentSender.SendIntentException e) {
                            //   Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 5:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        if (AppUtils.haveLocationPermission(this)) {
                            displayLocation();
                        } else {
                            Log.i("q", "onActivity");
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(this, "Please enable location to proceed further", Toast.LENGTH_SHORT).show();
                finish();
                }
                break;
        }
    }
}
