package golife.com.gojektest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import golife.com.gojektest.R;
import golife.com.gojektest.adapter.WeatherAdapter;


/**
 * Created by Swetarani Panda on 7/7/2018.
 */

public class ForecastFrag extends Fragment {
    RecyclerView rcv;
    WeatherAdapter adapter;
    ArrayList<String> dayList = new ArrayList<>();
    ArrayList<String> tempList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_forecast, container, false);
        getExtras();
        initUI(view);
        return view;
    }

    private void getExtras() {
        Bundle bundle = getArguments();
        dayList = bundle.getStringArrayList("daylist");
        tempList = bundle.getStringArrayList("templist");
    }

    private void initUI(View view) {
        rcv = view.findViewById(R.id.rcv);
        setAdapter();
    }


    private void setAdapter() {
        rcv.setHasFixedSize(true);
        StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, 1);
        rcv.setLayoutManager(gaggeredGridLayoutManager);
        adapter = new WeatherAdapter(getContext(),dayList, tempList);
        rcv.setAdapter(adapter);
    }


}
