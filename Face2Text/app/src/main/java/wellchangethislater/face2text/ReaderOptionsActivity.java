package wellchangethislater.face2text;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.glass.widget.CardBuilder;


public class ReaderOptionsActivity extends Activity {
    int wpm = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view1 = new CardBuilder (this, CardBuilder.Layout.TEXT)
           .setText("Text parsed; Tap again to use reader.")
           .setFootnote("WPM: " + Integer.toString(wpm))
           .getView();
    }

    public void speedReadDisplay(View view) {
        Intent intent = new Intent(this,SpeedReadDisplayActivity.class);

        String message = "This is the sample string that will be used to determine whether the program is successfully displaying the rapid reading system.";

        intent.putExtra("EXTRA_MESSAGE", message);
        intent.putExtra("EXTRA_WPM", wpm);

        startActivity(intent);
    }
}
