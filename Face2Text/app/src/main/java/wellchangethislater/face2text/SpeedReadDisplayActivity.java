package wellchangethislater.face2text;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import java.util.Timer;
import java.util.TimerTask;


public class SpeedReadDisplayActivity extends Activity {
    private CardScrollView mCardScroller;
    public String[] wordArray;
    public int currentIndex;
    public int maxIndex;
    TextView displayedFront;
    TextView displayedMiddle;
    TextView displayedBack;
    Timer timer;
    public View mView;
    int wpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        wordArray = message.split(" ");
        currentIndex = -1;
        maxIndex = wordArray.length - 1;
        wpm = intent.getIntExtra("EXTRA_WPM", 300);
        long period = 60000 / wpm;

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

        displayedFront = (TextView)findViewById(R.id.textView5);
        displayedFront.setTextSize(40);
        displayedMiddle = (TextView)findViewById(R.id.textView3);
        displayedMiddle.setTextSize(40);
        displayedBack = (TextView)findViewById(R.id.textView4);
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
            int i = 1;
            public void run() {
                runOnUiThread(displayNextWord);
            }
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(callDisplayNextWord,1000,period);
    }

    private View buildView() {
        CardBuilder card = new CardBuilder(this, CardBuilder.Layout.EMBED_INSIDE)
            .setEmbeddedLayout(R.layout.activity_speedread_display.xml)
            .setFootnote("WPM: " + Integer.toString(wpm));
        //getPreview();
        return card.getView();
    }

    public void printWord(String word) {
        int frontLength = (word.length() + 2) / 4;
        displayedFront.setText(word.substring(0,frontLength));
        displayedMiddle.setText(word.substring(frontLength,frontLength+1));
        displayedBack.setText(word.substring(frontLength+1,word.length()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
