package texttofaceinc.texttoface;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An {@link Activity} showing a tuggable "Hello World!" card.
 * <p/>
 * The main content view is composed of a one-card {@link CardScrollView} that provides tugging
 * feedback to the user when swipe gestures are detected.
 * If your Glassware intends to intercept swipe gestures, you should set the content view directly
 * and use a {@link com.google.android.glass.touchpad.GestureDetector}.
 *
 * @see <a href="https://developers.google.com/glass/develop/gdk/touch">GDK Developer Guide</a>
 */
public class MainActivity extends Activity {
    private CardScrollView mCardScroller;
    private Camera camera;
    private View mView;
    private Camera.PictureCallback mPicture;

    //Speedreader:
    public String[] wordArray;
    public int currentIndex;
    public int maxIndex;
    TextView displayedFront;
    TextView displayedMiddle;
    TextView displayedBack;
    Timer timer;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        //Intent intent = getIntent();
        String message = "This is a callibration message.  Please read all the words and ensure it works correctly.  Thank you for your cooperation.";//intent.getStringExtra("EXTRA_MESSAGE");
        wordArray = message.split(" ");
        currentIndex = -1;
        maxIndex = wordArray.length - 1;
        int wpm = 300;//intent.getIntExtra("EXTRA_WPM", 300);
        long period = 60000 / wpm;

        displayedFront = (TextView)findViewById(R.id.first_letters);
        displayedFront.setTextSize(40);
        displayedMiddle = (TextView)findViewById(R.id.middle_letter);
        displayedMiddle.setTextSize(40);
        displayedBack = (TextView)findViewById(R.id.last_letters);
        displayedBack.setTextSize(40);

        displayedFront.setText("");
        displayedMiddle.setText("O");
        displayedBack.setText("");

        mView = buildView();
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardScrollAdapter() {
            @Override
            public int getCount() {
                return 1;
            }
            @Override
            public Object getItem(int position) {
                return mView;
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return mView;
            }
            @Override
            public int getPosition(Object item) {
                if (mView.equals(item)) {
                    return 0;
                }
                return AdapterView.INVALID_POSITION;
            }
        });
        // Handle the TAP event.
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Plays disallowed sound to indicate that TAP actions are not supported.
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(Sounds.DISALLOWED);
            }
        });
        setContentView(mCardScroller);

        final Runnable displayNextWord = new Runnable() {
            public void run() {
                if (currentIndex < maxIndex) {
                    ++currentIndex;
                } else {
                    timer.cancel();
                    finish();
                    return;
                }
                printWord(wordArray[currentIndex]);
            }
        };

        TimerTask callDisplayNextWord = new TimerTask() {
            int i = 1;
            public void run() {
                runOnUiThread(displayNextWord);
            }
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(callDisplayNextWord,1000,period);
    }

    public void printWord(String word) {
        int frontLength = (word.length() + 2) / 4;
        displayedFront.setText(word.substring(0,frontLength));
        displayedMiddle.setText(word.substring(frontLength,frontLength+1));
        displayedBack.setText(word.substring(frontLength+1,word.length()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }

    /**
     * Builds a Glass styled "Hello World!" view using the {@link CardBuilder} class.
     */
    private View buildView() {
        View view = new CardBuilder(this, CardBuilder.Layout.EMBED_INSIDE)
                .setEmbeddedLayout(R.layout.main_activity_layout)
                .setFootnote("Foods you tracked")
                .setTimestamp("today")
                .getView();
        return view;
    }

}
