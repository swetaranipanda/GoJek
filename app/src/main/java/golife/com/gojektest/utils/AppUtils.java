package golife.com.gojektest.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.Window;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import golife.com.gojektest.R;


/**
 * Created by Swetarani Panda on 6/28/2018.
 */

public class AppUtils {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void changeStatusBarColor(Context context, Activity activity) {
        Window window = activity.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(context, R.color.grey));
    }


    public static boolean haveLocationPermission(Context context) {
        if (Build.VERSION.SDK_INT > 23) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static String convertDateToDay(String day) {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = date_format.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
        return dayOfTheWeek;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void askLocationPermission(Activity context) {
        context.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

    }

    public static boolean isGPSEnabled(Context mContext) {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static String geoCodeData(Context context, double latitude, double longitude) {
        String finalAddress = "";
        Geocoder geoCoder = new Geocoder(context, Locale.getDefault()); //it is Geocoder
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(latitude, longitude, 1);
            // String  maxLine = address.get(0).getAdminArea();
            //String  maxLine1 = address.get(0).getFeatureName();
            //String  maxLine2 = address.get(0).getPremises();
            finalAddress = address.get(0).getLocality();
            //String  maxLine4 = address.get(0).getSubAdminArea();
            //String  maxLine5 = address.get(0).getSubLocality();

        } catch (IOException e) {
        } catch (NullPointerException e) {
        }
        return finalAddress;
    }
}
