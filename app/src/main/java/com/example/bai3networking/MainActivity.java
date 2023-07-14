package com.example.bai3networking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final static String URL = "https://jsonplaceholder.typicode.com/photos";
    ArrayList<AlbumModel> albumList = new ArrayList<>();
    RecyclerView recyclerView;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rcy);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        new JsonTask().execute(URL);
    }


    private class JsonTask extends AsyncTask<String, String, String> {
        AlbumModel albumModel;

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String line = "";
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();


                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }
                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {


                //JSONObject jsonObject= null;
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonResponse = jsonArray.getJSONObject(i);
                        String albumId = jsonResponse.getString("albumId");
                        String id = jsonResponse.getString("id");
                        String title = jsonResponse.getString("title");
                        String url = jsonResponse.getString("url");
                        String thumbnailUrl = jsonResponse.getString("thumbnailUrl");
                        albumModel = new AlbumModel(albumId, id, title, url, thumbnailUrl);
                        albumList.add(albumModel);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                customAdapter = new CustomAdapter(MainActivity.this, albumList);
                recyclerView.setAdapter(customAdapter);
            }
        }

    }
}