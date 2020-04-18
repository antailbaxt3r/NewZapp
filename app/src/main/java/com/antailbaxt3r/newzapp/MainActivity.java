package com.antailbaxt3r.newzapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.antailbaxt3r.newzapp.models.NewsReponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText keywordEt;
    private Button goButton;
    private RecyclerView newsRv;
    private NewsRVAdapter adapter;
    private TextView me;
    private TextView newsApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        makeCall();
    }

    private void initView() {
        keywordEt = (EditText) findViewById(R.id.keyword_et);
        goButton = (Button) findViewById(R.id.go_button);
        newsRv = (RecyclerView) findViewById(R.id.news_rv);

        goButton.setOnClickListener((v) -> {
            makeCall();
        });
        me = (TextView) findViewById(R.id.me);
        me.setOnClickListener((v) -> {
            Intent meI = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/antailbaxt3r"));
            meI.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(meI);
        });
        newsApi = (TextView) findViewById(R.id.news_api);
        newsApi.setOnClickListener((v) -> {
            Intent newI = new Intent(Intent.ACTION_VIEW, Uri.parse("https://newsapi.org/"));
            newI.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newI);
        });
    }

    private void makeCall() {

        String s = keywordEt.getText().toString().trim();

        Map<String, String> map = new HashMap<>();
        map.put("country", "in");
        map.put("q", s.isEmpty() ? "corona" : s);
        map.put("apiKey", ""); //add your API key here (generate from newsapi.org)
        Call<NewsReponse> call = RetrofitClient.getClient().getNews(map);
        call.enqueue(new Callback<NewsReponse>() {
            @Override
            public void onResponse(Call<NewsReponse> call, Response<NewsReponse> response) {
                if (response.isSuccessful()) {
                    NewsReponse res = response.body();
                    if (res.getStatus().equals("ok")) {
                        adapter = new NewsRVAdapter(res.getArticles(), getApplicationContext());
                        newsRv.setAdapter(adapter);
                        newsRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsReponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });


    }
}
