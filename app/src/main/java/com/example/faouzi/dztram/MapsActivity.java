package com.example.faouzi.dztram;

import android.app.Dialog;
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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;

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

//import static com.example.faouzi.dztram.R.id.button;
import static com.example.faouzi.dztram.R.id.map;
import static java.security.AccessController.getContext;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Map<String, Marker> Markers = new HashMap<String, Marker>();
    private GoogleMap mMap;
    private int first=0;
    private float indx=3.0f;
    private String firstS="Les Fusillés";
    private int second=0;
    private String secondS="Cité Universitaire - CUB 1";
    final Context context=this;


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
                firstS = spinner.getSelectedItem().toString();
                secondS = spinner2.getSelectedItem().toString();
                if (btn.getText().equals("Distance") && (firstS.equals(secondS)==false)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                    //if (CalculDistance2(firstS,secondS)>1) Toast.makeText(getApplicationContext(),"Distance  : "+CalculDistance2(firstS,secondS),Toast.LENGTH_LONG).show();
                    if (CalculDistance2(firstS, secondS) > 0) {
                        ColorMap();
                        ColorPath(firstS,secondS);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(readLatLong().get(firstS)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(readLatLong().get(firstS), 14.0f));
                        double db=CalculDistance2(firstS, secondS);
                        builder.setMessage("Distance : " + db + " Km \nDuree de parcours : "+db*4+" min")
                                .setTitle(firstS +"\n"+ secondS);
                    }
                        //else if (CalculDistance2(secondS,firstS)>1) Toast.makeText(getApplicationContext(),"Distance  : "+CalculDistance2(secondS,firstS),Toast.LENGTH_LONG).show();
                    else if (CalculDistance2(secondS, firstS) > 0) {
                        ColorMap();
                        ColorPath(secondS,firstS);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(readLatLong().get(firstS)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(readLatLong().get(firstS), 14.0f));

                        builder.setMessage("La distance entre " + firstS + " et " + secondS + " est : " + CalculDistance2(secondS, firstS) + " Km")
                                .setTitle("Distance");
                    }

                        //Toast.makeText(getApplicationContext(), "Veuiilez choisir deux stations diferentes ", Toast.LENGTH_LONG).show();

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getWindow().setLayout(800, 700);
                } else if ((btn.getText().equals("Recherche"))){
                    if (firstS.equals("Dergana Centre")) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(36.772013,3.260286)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(36.772013,3.260286), 14.0f));
                        Marker mk1;
                        mk1=Markers.get(firstS);
                        mk1.showInfoWindow();
                    }
                    else {
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(readLatLong().get(firstS)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(readLatLong().get(firstS), 14.0f));
                        Marker mk1;
                        mk1=Markers.get(firstS);
                        mk1.showInfoWindow();
                    }
                }else if (firstS.equals(secondS)){
                    Toast.makeText(getApplicationContext(), "Veuiilez choisir deux stations diferentes ", Toast.LENGTH_LONG).show();

                }


            }
        });

        final Switch sw = findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    spinner2.setVisibility(View.INVISIBLE);
                    btn.setText("Recherche");

                }
                else {
                    spinner2.setVisibility(View.VISIBLE);
                    btn.setText("Distance");

                }
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
        Boolean bol=false;
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
//                Log.d("DISTANCE",name);
//                Log.d("DISTANCE",s1);
//                Log.d("DISTANCE",s2);
                if ((name.equals(s2)) && (bol==false)){
                    s2=s1;
                    s1=name;
                    bol=true;
                }
                if (name.equals(s1)) {
                    for (int j=i; j<jsonMainNode.length(); j++){
                        JSONObject jsonChildNode2 = jsonMainNode.getJSONObject(j);
                        JSONObject StationNode2 = jsonChildNode2.getJSONObject("B");
                        String name2 = StationNode2.optString("name");
                        dist = dist+jsonChildNode2.optDouble("dist");
                        dist= dist+2.0;
                        //Log.d("INSIDE",dist.toString());
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
        dist=dist/1000;
        dist = round(dist,2);
        return dist;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
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
//        mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
//
//        mMap.addTileOverlay(new TileOverlayOptions()
//                .tileProvider(new CustomMapTileProvider(getResources().getAssets()))
//                .zIndex(2.0f)
//        );

        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),R.drawable.tramicon);
        icon = bitmapSizeByScall(icon, 0.1f);
        icon = bitmapSizeByScall(icon, 0.6f);
        BitmapDescriptor bit = BitmapDescriptorFactory.fromBitmap(icon);
        Map<String, LatLng> mapy = readLatLong();

        for(Map.Entry<String, LatLng> station : mapy.entrySet()) {
            String key = station.getKey();
            LatLng lat = station.getValue();
            if (lat != null){
                Marker mk ;
                mk=mMap.addMarker(new MarkerOptions().position(lat).title(key).icon(bit).snippet("Latitude : "+lat.latitude+", Longitude : "+lat.longitude));
                Markers.put(key,mk);
            }
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(36.746036, 3.086786)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(36.746036, 3.086786), 14.0f));
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.setMaxZoomPreference(14.0f);
        mMap.setMinZoomPreference(12.0f);

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
                        .zIndex(4.0f)
                        .add(latLng,latLng2)
                        .width(10)
                        .color(Color.GREEN));

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

    public void ColorMap(){
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
                        .zIndex(indx++)
                        .add(latLng,latLng2)
                        .width(10)
                        .color(Color.GREEN));

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

    public void ColorPath(String stat1,String stat2){
        boolean bol=false;
        String jsonResult = null;
        String name2= "";
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
                JSONObject jsonChildNode2 = jsonMainNode.getJSONObject(i);
                JSONObject StationNode2 = jsonChildNode2.getJSONObject("A");
                String name = StationNode2.optString("name");
                if (stat2.equals(name) && bol==false){
                    stat2=stat1;
                    stat1=name;
                    bol=true;
                }
                if (stat1.equals(name)) {
                    int j=i;
                    while (name2.equals(stat2)==false) {
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(j);
                        JSONObject StationNode = jsonChildNode.getJSONObject("A");
                        JSONObject LatNode = StationNode.getJSONObject("LatLong");
                        Double Lat = LatNode.optDouble("latitude");
                        Double Long = LatNode.optDouble("longitude");
                        LatLng latLng = new LatLng(Lat, Long);
                        JSONObject StationNode3 = jsonChildNode.getJSONObject("B");
                        name2 = StationNode3.optString("name");
                        JSONObject LatNode2 = StationNode3.getJSONObject("LatLong");
                        Double Lat2 = LatNode2.optDouble("latitude");
                        Double Long2 = LatNode2.optDouble("longitude");
                        LatLng latLng2 = new LatLng(Lat2, Long2);
                        Polyline line = mMap.addPolyline(new PolylineOptions()
                                .zIndex(indx++)
                                .add(latLng, latLng2)
                                .width(14)
                                .color(Color.RED));
                        if (j<jsonMainNode.length()) j++;

                    }
                }
                if (name2.equals(stat2)) break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(), Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(), Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
