package info.androidhive.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
/*import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;*/
import android.widget.EditText;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        /*final EditText name = (EditText) findViewById(R.id.name);

        Intent intent = getIntent();
        //String Name1 = intent.getStringExtra("name");

        TextView welcomeMessage = (TextView) findViewById(R.id.textView4);
        String message = name + "welcome to your user account";
        welcomeMessage.setText(message);*/
    }

    public void onClickPersonalInfo(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickGraphs(View view){
        Intent intent = new Intent(this, Graphs.class);
        startActivity(intent);
    }

    public void onClickLastTraining(View view){
        Intent intent = new Intent(this, LastTraining.class);
        startActivity(intent);
    }

    public void onClickBestTraining(View view){
        Intent intent = new Intent(this, BestTraining.class);
        startActivity(intent);
    }

    public void onClickStartTraining(View view){
        Intent intent = new Intent(this, StartTraining.class);
        startActivity(intent);
    }
}

