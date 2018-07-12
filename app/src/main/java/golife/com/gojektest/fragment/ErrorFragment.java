package golife.com.gojektest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import golife.com.gojektest.R;
import golife.com.gojektest.activity.WeatherActivity;
import golife.com.gojektest.view.RobotoThinTextView;

/**
 * Created by Swetarani Panda on 7/9/2018.
 */

public class ErrorFragment extends Fragment {
    private String errorMsg;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.error_screen,container,false);
        getExtra();
        initUI(view);
        return view;
    }
    private void getExtra(){
        errorMsg=getArguments().getString("errormsg");
    }
    private void initUI(View view){
        Button retryBtn=view.findViewById(R.id.retry_btn);
        RobotoThinTextView errorText=view.findViewById(R.id.msg_tv);

        errorText.setText(errorMsg);

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),WeatherActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }
}
