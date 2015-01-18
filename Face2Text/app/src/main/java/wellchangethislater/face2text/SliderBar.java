package wellchangethislater.face2text;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.glass.widget.CardScrollView;
import com.google.android.glass.widget.Slider;
import com.google.android.glass.widget.CardScrollAdapter;

/**
 * Created by jessi on 1/18/15.
 */
/*public class SliderBar extends Activity{


    private static final int MAX_SLIDER_VALUE = 5;
    private static final long ANIMATION_DURATION_MILLIS = 5000;

    private CardScrollView mCardScroller;
    private Slider mSlider;
    private Slider.Indeterminate mIndeterminate;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        // Create the cards for the view
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardAdapter(createCards()));

        // Set the view for the Slider
        mSlider = Slider.from(mCardScroller);

        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Toggle between showing/hiding the indeterminate slider.
                if (mIndeterminate != null) {
                    mIndeterminate.hide();
                    mIndeterminate = null;
                } else {
                    mIndeterminate = mSlider.startIndeterminate();
                }
            });

            setContentView(mCardScroller);
        }


}
*/