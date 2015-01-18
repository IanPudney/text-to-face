package texttoface.displaytextwithspritzer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    Button spritzButton;
    Button wpmButton;
    EditText mEdit;
    int wpm = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spritzButton = (Button)findViewById(R.id.spritzButton);
        wpmButton = (Button)findViewById(R.id.wpmButton);
        mEdit = (EditText)findViewById(R.id.editText);
        Toast.makeText(getApplicationContext(),"Welcome!",Toast.LENGTH_SHORT).show();

        wpmButton.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    String enteredString = mEdit.getText().toString();
                    if (!enteredString.matches("-?\\d+(\\/\\d+)?")) {   //check if numeric
                        Toast.makeText(getApplicationContext(),"Please enter an integer number for WPM!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    TextView variableDisplayView = (TextView)findViewById(R.id.textView2);
                    variableDisplayView.setText("WPM: " + enteredString);
                    wpm = Integer.parseInt(enteredString);
                }
            });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void spritzDisplay(View view) {
        Intent intent = new Intent(this,SpeedReadDisplayActivity.class);
        Toast.makeText(getApplicationContext(),"Spritzur",Toast.LENGTH_SHORT).show();

        String message = "This is the sample string that will be used to determine whether the program is successfully displaying the rapid reading system.";

        intent.putExtra("EXTRA_MESSAGE", message);
        intent.putExtra("EXTRA_WPM", wpm);

        startActivity(intent);
    }
}
