package maf.mobile.suitmediatest;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import maf.mobile.suitmediatest.Adapter.UserAdapter;
import maf.mobile.suitmediatest.Model.UserModel;

public class ThirdActivity extends AppCompatActivity implements OnDataClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<UserModel> userData;
    int page = 1;
    int limit = 2;
    private RecyclerView rvUser;
    private ProgressBar progressBar;
    private TextView noDataText;
    private NestedScrollView nestedSv;
    private UserAdapter userAdapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvUser = (RecyclerView) findViewById(R.id.rvUser);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        noDataText = (TextView) findViewById(R.id.noData);
        nestedSv = (NestedScrollView) findViewById(R.id.nestedSV);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        getDataAPI(page,limit);


        userData = new ArrayList<>();
        userAdapter = new UserAdapter(ThirdActivity.this, userData, this);
        rvUser.setLayoutManager(new LinearLayoutManager(ThirdActivity.this));
        rvUser.setAdapter(userAdapter);
        rvUser.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        nestedSv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getDataAPI(page, limit);
                    userAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void getDataAPI(int page, int limit){
        if (page > limit) {
            Toast.makeText(this, "No more data available", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        noDataText.setVisibility(View.GONE);
        String url = "https://reqres.in/api/users?page="+page+"&per_page=10";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               try {
                   JSONArray data = response.getJSONArray("data");

                   for (int i = 0; i <data.length() ; i++) {
                       JSONObject responseObject = data.getJSONObject(i);
                       String email = responseObject.getString("email");
                       String firstName = responseObject.getString("first_name");
                       String lastName = responseObject.getString("last_name");
                       String avatar = responseObject.getString("avatar");

                       userData.add(new UserModel(firstName, lastName, email, avatar));
                   }
                   userAdapter.notifyDataSetChanged();
                   progressBar.setVisibility(View.GONE);
               } catch (Exception e) {
                   throw new RuntimeException(e);
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void OnDataClick(String firstname, String lastname) {
        Intent intent = new Intent();
        intent.putExtra("firstname", firstname);
        intent.putExtra("lastname", lastname);
        setResult(RESULT_OK, intent);
        finish();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refreshing data", Toast.LENGTH_SHORT).show();
        page++;
        getDataAPI(page, limit);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                userAdapter.notifyDataSetChanged();
            }
        },2000);
    }
}