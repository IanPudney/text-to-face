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
import android.view.Window;
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
    long period;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        int wpm = 400;//intent.getIntExtra("EXTRA_WPM", 300);
        period = 60000 / wpm;

        displayedFront = (TextView)findViewById(R.id.first_letters);
        displayedMiddle = (TextView)findViewById(R.id.middle_letter);
        displayedBack = (TextView)findViewById(R.id.last_letters);

        displayedFront.setText("");
        displayedMiddle.setText("O");
        displayedBack.setText("");

        timer = new Timer();
        timer.schedule(callDisplayNextWord, 1000, period);
    }

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

    public void printWord(String word) {
        int wlength = word.length();
        if (wlength > 11) {
            String next_word = word.substring(wlength/2,wlength);
            wordArray[currentIndex] = next_word;
            --currentIndex;
            word = word.substring(0,wlength/2) + "-";
            wlength = word.length();
        }
        double multiplier = 1.0;
        if (wlength < 4) {
            multiplier = 0.7;
        } else if (wlength > 9) {
            multiplier = 1.5;
        } else if (wlength > 6) {
            multiplier = 1.2;
        }
        timer.schedule(callDisplayNextWord,1000, (long)(period * multiplier));

        int frontLength = (wlength + 2) / 4;
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
