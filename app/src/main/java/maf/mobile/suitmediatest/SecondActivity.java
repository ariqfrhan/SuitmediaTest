package maf.mobile.suitmediatest;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import org.w3c.dom.Text;

public class SecondActivity extends AppCompatActivity {

    private TextView tvUser, tvSelected;
    private Button btChoose;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        tvUser = (TextView) findViewById(R.id.tvUser);
        tvSelected = (TextView) findViewById(R.id.tvSelected);
        btChoose = (Button) findViewById(R.id.btChoose);

       ActivityResultLauncher<Intent> getData = registerForActivityResult(
               new ActivityResultContracts.StartActivityForResult(),
               new ActivityResultCallback<ActivityResult>() {
                   @Override
                   public void onActivityResult(ActivityResult o) {
                       if (o.getResultCode() == Activity.RESULT_OK && o.getData()!=null) {
                            String firstname = o.getData().getStringExtra("firstname");
                            String lastname = o.getData().getStringExtra("lastname");
                            tvSelected.setText(firstname + " " + lastname);
                       }
                   }
               }
       );

        btChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                getData.launch(intent);
//                startActivityForResult(intent, LAUNCH_THIRD_ACTIVITY);
            }
        });

        this.name = getIntent().getStringExtra("name");
        tvUser.setText(name);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}