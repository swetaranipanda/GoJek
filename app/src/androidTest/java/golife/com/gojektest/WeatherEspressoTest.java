package golife.com.gojektest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.test.espresso.FailureHandler;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import golife.com.gojektest.activity.WeatherActivity;
import golife.com.gojektest.utils.AppUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Swetarani Panda on 7/10/2018.
 */

@RunWith(AndroidJUnit4.class)
public class WeatherEspressoTest {

    @Rule
    public ActivityTestRule<WeatherActivity> testRule = new ActivityTestRule<WeatherActivity>(WeatherActivity.class);
    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE);
    @Before
    public void init() throws Exception {
        testRule.getActivity().getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void launchApp() {
        System.out.println("UItesting started");
        testRule.launchActivity(new Intent()); // breakpoint here
    }


    @Test
    public void apiSuccess() {
        testRule.launchActivity(new Intent());
        onView(withId(R.id.rcv)).check(matches(isDisplayed()));

    }


    @Test
    public void noInternetSecenrio() {
        if (!AppUtils.isOnline(testRule.getActivity())) {
            onView(withId(R.id.loading_iv)).check(matches(isDisplayed()));
            testRule.launchActivity(new Intent());
            onView(withId(R.id.msg_tv)).check(matches(isDisplayed()));

        }
    }

    @Test
    public void WifiConnectedScenerio() {
        if (AppUtils.isOnline(testRule.getActivity())) {
            if (viewIsDisplayed(R.id.loading_iv)) {
                changeWifiState(false);
                onView(withText(testRule.getActivity().getResources().getString(R.string.poor_connection))).check(matches(isDisplayed()));
            } else if (viewIsDisplayed(R.id.msg_tv)) {

                onView(withId(R.id.retry_btn)).perform(click());

                onView(withId(R.id.loading_iv)).check(matches(isDisplayed()));

            } else {
                onView(withId(R.id.rcv)).check(matches(isDisplayed()));
            }
            changeWifiState(true);
        }
    }

    private void changeWifiState(boolean state) {
        WifiManager wifi = (WifiManager) testRule.getActivity().getSystemService(Context.WIFI_SERVICE);
        if (wifi != null) {
            wifi.setWifiEnabled(state);
        }
    }

    @Test
    public void testLocationPermission() {
        if (!AppUtils.haveLocationPermission(testRule.getActivity())){
            onView(withText("DENY")).check(matches(isDisplayed()));
        }
    }
    public static boolean viewIsDisplayed(int viewId) {
        final boolean[] isDisplayed = {true};
        onView(withId(viewId)).withFailureHandler(new FailureHandler() {
            @Override
            public void handle(Throwable error, Matcher<View> viewMatcher) {
                isDisplayed[0] = false;
            }
        }).check(matches(isDisplayed()));
        return isDisplayed[0];
    }


}
