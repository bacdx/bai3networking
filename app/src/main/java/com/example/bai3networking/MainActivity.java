package com.example.bai3networking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.HttpAuthHandler;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "https://jsonplaceholder.typicode.com/photos";
    ArrayList<Conntact> contactList=new ArrayList<>();
    RecyclerView view;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view=findViewById(R.id.rcy);
        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        view.setLayoutManager(manager);
        new JsonTask().execute(TAG);
    }


    private class JsonTask extends AsyncTask<String, String, String> {
        Conntact conntact;

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

            if (result !=null){


                for (int i =0; i <10; i++){
                    JSONObject jsonObject= null;
                    try {
                        JSONArray json = new JSONArray(result);
                        JSONObject jsonResponse = json.getJSONObject(i);
                        String albumId = jsonResponse.getString("albumId");
                        String id = jsonResponse.getString("id");
                        String title=jsonResponse.getString("title");
                        String url=jsonResponse.getString("url");
                        String thumbnailUrl=jsonResponse.getString("thumbnailUrl");
                        conntact=new Conntact(albumId,id,title,url,thumbnailUrl);
                        contactList.add(conntact);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                contactAdapter=new ContactAdapter(MainActivity.this,contactList);
                view.setAdapter(contactAdapter);
            }
        }

    }
}