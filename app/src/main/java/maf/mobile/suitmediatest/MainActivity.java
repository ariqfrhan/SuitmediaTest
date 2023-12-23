package maf.mobile.suitmediatest;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etPalindrome;
    private Button btPalindrome, btNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        etName = (EditText) findViewById(R.id.etName);
        etPalindrome = (EditText) findViewById(R.id.etPalindrome);
        btPalindrome = (Button) findViewById(R.id.btPalindrome);
        btNext = (Button) findViewById(R.id.btNext);

        btPalindrome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etPalindrome.getText().toString();
                boolean res = isPalindrome(input);

                if (res == true) {
                    Toast.makeText(MainActivity.this, "isPalindrome", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "not palindrome", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }

    public boolean isPalindrome(String str){
        if (str.isEmpty()) {
            return false;
        }
        String text = str.replaceAll("\\s+", "").toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
        StringBuilder rev = new StringBuilder(text).reverse();
        String reversed = rev.toString();
        Log.d(TAG, "isPalindrome: " + reversed +" Before: " + text);

        if (text.equals(reversed)) {
            return true;
        }
        return false;
    }
}