package o.krymov.photon.ru.ui.screens.photo_list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class PhotoListItem extends RelativeLayout {
    public PhotoListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // get sizes of button
        //final int height = getMeasuredHeight();	// height
        final int width = getMeasuredWidth();	// width

        // create new size
        setMeasuredDimension(width, width);

        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = width;
        lp.width = width;
        setLayoutParams(lp);
    }



}
