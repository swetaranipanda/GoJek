package golife.com.gojektest.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


abstract class CustomTextView extends AppCompatTextView {

    public static final boolean IS_CUSTOM_FONT_ALLOWED = true;

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public CustomTextView(Context context) {
        super(context);
        init(null);
    }

    protected void init(AttributeSet attrs) {
        if (IS_CUSTOM_FONT_ALLOWED) {
            Typeface myTypeface = FontCache.get(getFontName(), getContext());
            setTypeface(myTypeface);


        }
    }

    protected abstract String getFontName();

}