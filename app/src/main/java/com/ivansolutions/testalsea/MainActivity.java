package com.ivansolutions.testalsea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ivansolutions.testalsea.adapters.ListaPrincipalAdapter;
import com.ivansolutions.testalsea.beans.EarthQuakeItemList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    private static final String URL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
    private JsonObjectRequest jsonRequest;
    private ArrayList<EarthQuakeItemList> listaObjetos;
    private ListView listaPrincipal;
    private ListaPrincipalAdapter adapter;
    private LinearLayout llLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        listaPrincipal = (ListView)findViewById(R.id.lv_principal);
        llLoading = (LinearLayout)findViewById(R.id.ll_loading);

        queue = Volley.newRequestQueue(this);
        jsonRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    parseJsonResponse(response);

                    adapter = new ListaPrincipalAdapter(listaObjetos, MainActivity.this);
                    listaPrincipal.setAdapter(adapter);
                    llLoading.setVisibility(View.INVISIBLE);
                }catch (JSONException e){
                    System.out.println(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonRequest);

        listaPrincipal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("selected", listaObjetos.get(i));
                startActivity(intent);
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

    private void parseJsonResponse(JSONObject response) throws JSONException{

        listaObjetos = new ArrayList<>();

        JSONArray results = response.getJSONArray("features");

        EarthQuakeItemList itemList ;

        //El objeto JSONArray no es un iterable (como desarrollador lo infiero) ya que puede contener simples
        //elementos como el geometry array del response o estructuras mas complejas como otros json
        //objects. un iterador necesita conocer su tipo de dato. Se itera con un FOR normalito
        for(int i = 0; i < results.length(); i++){

            itemList= new EarthQuakeItemList();

            JSONObject item = results.getJSONObject(i);
            JSONObject properties = item.getJSONObject("properties");
            JSONObject geometry = item.getJSONObject("geometry");
            JSONArray coordinates = geometry.getJSONArray("coordinates");

            itemList.setMagnitud((float)properties.getDouble("mag"));
            itemList.setPlace(properties.getString("place"));
            itemList.setTime(properties.getLong("time"));
            itemList.setLatitude(coordinates.getDouble(1));
            itemList.setLongitude(coordinates.getDouble(0));

            listaObjetos.add(itemList);
        }

    }
}
