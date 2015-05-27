package com.ivansolutions.testalsea;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ivansolutions.testalsea.beans.EarthQuakeItemList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by eliasivancarrillogonzalez on 25/05/15.
 */
public class DetailActivity extends AppCompatActivity{

    private GoogleMap mapa;
    private LatLng point;
    private EarthQuakeItemList item;
    private SimpleDateFormat dateFormat;
    private TextView tvMagnitud;
    private TextView tvPlace;
    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_activity);

        tvMagnitud = (TextView)findViewById(R.id.tv_detail_mag);
        tvPlace = (TextView)findViewById(R.id.tv_detail_place);
        tvTime = (TextView)findViewById(R.id.tv_detail_time);

        dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy 'a las' h:mm a z", Locale.getDefault());
        item = getIntent().getExtras().getParcelable("selected");

        tvMagnitud.setText(String.format(getString(R.string.text_magnitud), item.getMagnitud()));
        tvPlace.setText(String.format(getString(R.string.text_place), item.getPlace()));
        tvTime.setText(String.format(getString(R.string.text_time), dateFormat.format(new Date(item.getTime()))));

        point = new LatLng(item.getLatitude(), item.getLongitude());

        mapa = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 5));
        mapa.addMarker(new MarkerOptions().position(point).title("EarthQuake")
                .snippet(item.getPlace()).
                icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
