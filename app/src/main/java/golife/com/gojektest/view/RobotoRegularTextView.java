package golife.com.gojektest.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by swetha on 8/22/2017.
 */

public class RobotoRegularTextView extends CustomTextView {
    public static final String FONT_NAME = "Roboto-Regular.ttf";

    public RobotoRegularTextView(Context context) {
        super(context);
    }

    public RobotoRegularTextView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public RobotoRegularTextView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }
    @Override
    protected String getFontName() {
        return FONT_NAME;
    }
}
