package golife.com.gojektest.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by swetha on 8/22/2017.
 */

public class RobotoBlackTextView extends CustomTextView {
    public static final String FONT_NAME = "Roboto-Black.ttf";

    public RobotoBlackTextView(Context context) {
        super(context);
    }

    public RobotoBlackTextView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public RobotoBlackTextView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }
    @Override
    protected String getFontName() {
        return FONT_NAME;
    }
}
