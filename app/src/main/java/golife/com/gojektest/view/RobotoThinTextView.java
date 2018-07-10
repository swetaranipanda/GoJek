package golife.com.gojektest.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by swetha on 8/22/2017.
 */

public class RobotoThinTextView extends CustomTextView {
    public static final String FONT_NAME = "Roboto-Thin.ttf";

    public RobotoThinTextView(Context context) {
        super(context);
    }

    public RobotoThinTextView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public RobotoThinTextView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }
    @Override
    protected String getFontName() {
        return FONT_NAME;
    }
}
