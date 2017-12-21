package com.example.faouzi.dztram;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;

import static com.example.faouzi.dztram.R.id.map;
import static java.security.AccessController.getContext;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int first=0;
    private String firstS="Les Fusillés";
    private int second=0;
    private String secondS="Cité Universitaire - CUB 1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        ////// SPINNER /////////
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray=ListeStations();
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Sorting the adapter

        adapter.sort(new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });

        adapter2.sort(new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });

// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter2);
        //final Map<String, LatLng> map = readLatLong();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //final String newValue = (String) parentView.getItemAtPosition(position);
                //final String newValue = spinner.getSelectedItem().toString();
                first=1;
                //firstS=newValue;
                //Toast.makeText(getApplicationContext(),"S1 :"+firstS+" et S2 "+secondS+ "Distance 2 : "+CalculDistance2(firstS,secondS),Toast.LENGTH_LONG).show();

//                if (second==1){
//
//                    if (CalculDistance2(firstS,secondS)>1) Toast.makeText(getApplicationContext(),"Distance : "+CalculDistance2(firstS,secondS),Toast.LENGTH_LONG).show();
//                    else if (CalculDistance2(secondS,firstS)>1) Toast.makeText(getApplicationContext(),"Distance : "+CalculDistance2(secondS,firstS),Toast.LENGTH_LONG).show();
//                    //else Toast.makeText(getApplicationContext(),"Veuiilez choisir deux stations diferentes" ,Toast.LENGTH_LONG).show();
//                    //Toast.makeText(getApplicationContext(),""+CalculDistance(firstS,secondS),Toast.LENGTH_LONG).show();
//                    //double dist = CalculDistance(firstS,secondS);
//                    //Toast.makeText(getApplicationContext(), "La distance entre " +firstS+" et "+secondS+" est :"+dist, Toast.LENGTH_LONG).show();
//                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //final String newValue = (String) parentView.getItemAtPosition(position);
                //final String newValue = spinner2.getSelectedItem().toString();
                second=1;
                //secondS=newValue;
                //Toast.makeText(getApplicationContext(),"Distance 2 : "+CalculDistance2(firstS,secondS),Toast.LENGTH_LONG).show();
//                if (first==1){
//                    if (CalculDistance2(firstS,secondS)>1) Toast.makeText(getApplicationContext(),"Distance 2 : "+CalculDistance2(firstS,secondS),Toast.LENGTH_LONG).show();
//                    else if (CalculDistance2(secondS,firstS)>1) Toast.makeText(getApplicationContext(),"Distance 2 : "+CalculDistance2(secondS,firstS),Toast.LENGTH_LONG).show();
//                    //else Toast.makeText(getApplicationContext(),"Veuiilez choisir deux stations diferentes 2" ,Toast.LENGTH_LONG).show();
//
//                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        final Button btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                firstS=spinner.getSelectedItem().toString();
                secondS=spinner2.getSelectedItem().toString();
                if (CalculDistance2(firstS,secondS)>1) Toast.makeText(getApplicationContext(),"Distance  : "+CalculDistance2(firstS,secondS),Toast.LENGTH_LONG).show();
                else if (CalculDistance2(secondS,firstS)>1) Toast.makeText(getApplicationContext(),"Distance  : "+CalculDistance2(secondS,firstS),Toast.LENGTH_LONG).show();
                else Toast.makeText(getApplicationContext(),"Veuiilez choisir deux stations diferentes " ,Toast.LENGTH_LONG).show();

            }
        });
    }

    public List<String> ListeStations(){

        List<String> list= new ArrayList<String>();
        String jsonResult = null;
        try {
            InputStream is = getAssets().open("tramway.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonResult = new String(buffer, "UTF-8");
            //jsonResult = new Scanner(new File("Json/Tramway.json")).useDelimiter("\\Z").next();
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Content");
            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                JSONObject StationNode1 = jsonChildNode.getJSONObject("A");
                String name1 = StationNode1.optString("name");
                list.add(name1);
                if (i==jsonMainNode.length()-1) {
                    JSONObject StationNode2 = jsonChildNode.getJSONObject("B");
                    list.add(StationNode2.optString("name"));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        return list;
    }




    public Double CalculDistance2 (String s1, String s2){
        String jsonResult = null;
        Double dist=0.0;
        try {
            InputStream is = getAssets().open("tramway.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonResult = new String(buffer, "UTF-8");
            //jsonResult = new Scanner(new File("Json/Tramway.json")).useDelimiter("\\Z").next();
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Content");
            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                JSONObject StationNode = jsonChildNode.getJSONObject("A");
                String name = StationNode.optString("name");
                Log.d("DISTANCE",name);
                Log.d("DISTANCE",s1);
                Log.d("DISTANCE",s2);
                if (name.equals(s1)) {
                    for (int j=i; j<jsonMainNode.length(); j++){
                        JSONObject jsonChildNode2 = jsonMainNode.getJSONObject(j);
                        JSONObject StationNode2 = jsonChildNode2.getJSONObject("B");
                        String name2 = StationNode2.optString("name");
                        dist = dist+jsonChildNode2.optDouble("dist");
                        dist= dist+2.0;
                        Log.d("INSIDE",dist.toString());
                        //Log.d("INSIDE",name2);
                        //Log.d("INSIDE",s2);
                        if (name2.equals(s2)) break;

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        return dist;
    }

    public double CalculDistance (String s1, String s2){
        double dist=0;
        boolean bool=true;
        boolean sort=false;
        Map<String, Double> map = new HashMap<String, Double>();
        map = TableDistance();
        String str1=s1;
        String str2=s2;
        while (bool){
            if (dist>20000) bool=false;
            for(Map.Entry<String, Double> station : map.entrySet()) {
                String key = station.getKey();
                Double lat = station.getValue();
                if (key.contains(str1+"#")) {
                    if (key.contains("#"+str2)) {
                        dist=dist+lat;
                        bool=false;
                    }
                    else {
                        str1=key.replace(str1+"#","");
                        dist=dist+lat;
                    }
                    sort=true;
                }
                if (sort) break;
            }
        }
        return dist;
    }

    public Map<String, Double>  TableDistance (){
        String jsonResult = null;
        Map<String, Double> map = new HashMap<String, Double>();
        try {
            InputStream is = getAssets().open("tramway.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonResult = new String(buffer, "UTF-8");
            //jsonResult = new Scanner(new File("Json/Tramway.json")).useDelimiter("\\Z").next();
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Content");
            for (int i = 0; i < jsonMainNode.length(); i++) {
                Double dis=0.0;
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                JSONObject StationNode1 = jsonChildNode.getJSONObject("A");
                String name1 = StationNode1.optString("name");
                for (int j = i; j < jsonMainNode.length(); j++) {
                    JSONObject jsonChildNode2 = jsonMainNode.getJSONObject(j);
                    JSONObject StationNode2 = jsonChildNode2.getJSONObject("B");
                    String name2 = StationNode2.optString("name");
                    dis = dis+ jsonChildNode.optDouble("dist");
                    map.put(name1 + "#" + name2, dis);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        return map;
    }
    public Map<String, LatLng> readLatLong() {
        String jsonResult = null;
        Map<String, LatLng> map = new HashMap<String, LatLng>();
        try {
            InputStream is = getAssets().open("tramway.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonResult = new String(buffer, "UTF-8");
            //jsonResult = new Scanner(new File("Json/Tramway.json")).useDelimiter("\\Z").next();
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Content");
            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                JSONObject StationNode = jsonChildNode.getJSONObject("A");
                String name = StationNode.optString("name");
                //StationNode = jsonChildNode.getJSONObject("A");
                JSONObject LatNode = StationNode.getJSONObject("LatLong");
                Double Lat = LatNode.optDouble("latitude");
                Double Long = LatNode.optDouble("longitude");
                map.put(name,new LatLng(Lat,Long));
                if (i==jsonMainNode.length()-1) {
                    JSONObject StationNode2 = jsonChildNode.getJSONObject("B");
                    JSONObject LatNode2 = StationNode2.getJSONObject("LatLong");
                    Double Lat2 = LatNode2.optDouble("latitude");
                    Double Long2 = LatNode2.optDouble("longitude");
                    map.put(name,new LatLng(Lat2,Long2));
                }
//                JSONArray NameNode = jsonChildNode.optJSONArray("A"); // Parsing the "name" node
//                JSONObject NameObject = NameNode.getJSONObject(0);       // to extract the "value"
//                String name = NameObject.optString("value");            // from the node's element
//                Integer id_product = jsonChildNode.optInt("id");
//                Integer id_image = jsonChildNode.optInt("id_default_image");
//                JSONArray SDescriptionNode = jsonChildNode.optJSONArray("description_short"); // Parsing the "description_short" node
//                JSONObject SDescriptionObject = SDescriptionNode.getJSONObject(0);       // to extract the "value"
//                description_short  = SDescriptionObject.optString("value");            // from the node's element
//                Double price = jsonChildNode.optDouble("price");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        return map;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),R.drawable.tramicon);
        icon = bitmapSizeByScall(icon, 0.1f);
        icon = bitmapSizeByScall(icon, 0.6f);
        BitmapDescriptor bit = BitmapDescriptorFactory.fromBitmap(icon);
        // Add a marker in Sydney and move the camera
        Map<String, LatLng> mapy = readLatLong();

        for(Map.Entry<String, LatLng> station : mapy.entrySet()) {
            String key = station.getKey();
            LatLng lat = station.getValue();
            if (lat != null)
                mMap.addMarker(new MarkerOptions().position(lat).title("\""+key+"\""+" "+lat.latitude+" "+lat.longitude).icon(bit));

        }
        //};
//        LatLng sydney = new LatLng(36.742971, 3.083471);
//        MarkerOptions opt = new MarkerOptions();
//
//        opt.position(sydney).title("Ruisseau");
//        opt.icon(bit);
//        mMap.addMarker(opt);
//        LatLng sydney1 = new LatLng(36.746036,3.086786);
//        mMap.addMarker(new MarkerOptions().position(sydney1).title("Les Fusillés").icon(bit));
//        LatLng sydney2 = new LatLng(36.745159, 3.092308);
//        mMap.addMarker(new MarkerOptions().position(sydney2).title("Tripoli -Thaalibia").icon(bit));
//        LatLng sydney3 = new LatLng(36.743194, 3.097510);
//        mMap.addMarker(new MarkerOptions().position(sydney3).title("Tripoli - Mosqué").icon(bit));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(36.746036, 3.086786)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(36.746036, 3.086786), 13.0f));
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);

        String jsonResult = null;
        try {
            InputStream is = getAssets().open("tramway.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonResult = new String(buffer, "UTF-8");
            //jsonResult = new Scanner(new File("Json/Tramway.json")).useDelimiter("\\Z").next();
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Content");
            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                JSONObject StationNode = jsonChildNode.getJSONObject("A");
                JSONObject LatNode = StationNode.getJSONObject("LatLong");
                Double Lat = LatNode.optDouble("latitude");
                Double Long = LatNode.optDouble("longitude");
                LatLng latLng = new LatLng(Lat,Long);
                JSONObject StationNode2 = jsonChildNode.getJSONObject("B");
                JSONObject LatNode2 = StationNode2.getJSONObject("LatLong");
                Double Lat2 = LatNode2.optDouble("latitude");
                Double Long2 = LatNode2.optDouble("longitude");
                LatLng latLng2 = new LatLng(Lat2,Long2);
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(latLng,latLng2)
                        .width(15)
                        .color(Color.BLUE));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }




    }

    public Bitmap bitmapSizeByScall( Bitmap bitmapIn, float scall_zero_to_one_f) {

        Bitmap bitmapOut = Bitmap.createScaledBitmap(bitmapIn,
                Math.round(bitmapIn.getWidth() * scall_zero_to_one_f),
                Math.round(bitmapIn.getHeight() * scall_zero_to_one_f), false);

        return bitmapOut;
    }
}
