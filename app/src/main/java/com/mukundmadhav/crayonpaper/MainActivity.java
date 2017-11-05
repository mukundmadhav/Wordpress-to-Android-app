package com.mukundmadhav.crayonpaper;

import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    RecyclerView rview;
    public static String tContent;
    private FirebaseAnalytics firebaseAnalytics;

    public static final String url ="https://crayonpaper.com/wp-json/wp/v2/posts?fields=id,title,content,link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
        navigationView = (NavigationView) findViewById(R.id.navbar);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        rview = (RecyclerView) findViewById(R.id.listofProgs);
        rview.setLayoutManager(new LinearLayoutManager(this));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(MainActivity.this, "Home" , Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.howto:
                        Toast.makeText(MainActivity.this, "How To's" , Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.review:
                        Toast.makeText(MainActivity.this, "Tech Reviews" , Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.saved:
                        Toast.makeText(MainActivity.this, "Saved" , Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.about:
                        Toast.makeText(MainActivity.this, "About Us" , Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.help:
                        Toast.makeText(MainActivity.this, "Help" , Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Ret:",response);
                parsejson(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed ", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(stringRequest);

    }

    private void setup() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    private void parsejson(String response) {

        List<DataMembers> data = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0;i<5;++i) {
                DataMembers dataMembers = new DataMembers();

                JSONObject jsonObject = new JSONObject();
                jsonObject = (JSONObject) jsonArray.get(i);
                Log.i("Imported: ", Integer.toString(jsonObject.getInt("id")));

                JSONObject jsonObject1 = new JSONObject();
                jsonObject1 = (JSONObject) jsonObject.get("title");
                Log.i("Imported: ",jsonObject1.getString("rendered"));
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2 = (JSONObject) jsonObject.get("content");
                dataMembers.postTitle = jsonObject1.getString("rendered");
                dataMembers.postId = jsonObject.getInt("id");
                dataMembers.imgurl = getFetaureImageUrl(jsonObject2.getString("rendered"));
                dataMembers.postCode = jsonObject2.getString("rendered");
                Log.i("Imported: ", jsonObject.getString("link"));
                dataMembers.posturl=jsonObject.getString("link");

                MainActivity.tContent = dataMembers.postCode;

                data.add(dataMembers);
            }
            rview.setAdapter(new RecyclerAdapter(MainActivity.this,data));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getFetaureImageUrl(String rendered) {

        String imgurl = "";
        /*
        Document document = Document.createShell(rendered);
        Elements elements = document.select("img");
        imgurl = elements.attr("src");
        Log.i("0",document.text());

         */
        Pattern r = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
        Matcher m = r.matcher(rendered);
        if (m.find()) {
            Log.i("0",m.group(1));
            imgurl = m.group(1);
        }
        else {
            imgurl = "http://localsplashcdn.wpengine.netdna-cdn.com/wp-content/uploads/2013/04/The-page-wont-load.png";
        }

        return imgurl;
    }
    }




