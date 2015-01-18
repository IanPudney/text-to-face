package texttoface.displaytextwithspritzer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class SpeedReadDisplayActivity extends ActionBarActivity {
    public String[] wordArray;
    public int currentIndex;
    public int maxIndex;
    TextView displayedFront;
    TextView displayedMiddle;
    TextView displayedBack;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedread_display);
        Intent intent = getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        wordArray = message.split(" ");
        currentIndex = -1;
        maxIndex = wordArray.length - 1;
        int wpm = intent.getIntExtra("EXTRA_WPM", 300);
        long period = 60000 / wpm;

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
