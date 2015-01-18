package wellchangethislater.face2text;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
public class FlickerView extends Activity {

    //Speedreader:
    public String[] wordArray;
    public int currentIndex;
    public int maxIndex;
    TextView displayedFront;
    TextView displayedMiddle;
    TextView displayedBack;
    Timer timer;
    int wpm;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.my_custom_layout);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (sharedText != null) {
                    wordArray = sharedText.split(" ");
                }
            }
        };

        currentIndex = -1;
        maxIndex = wordArray.length - 1;
        wpm = 300;//intent.getIntExtra("EXTRA_WPM", 300);
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
            public void run() {
                runOnUiThread(displayNextWord);
            };
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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
