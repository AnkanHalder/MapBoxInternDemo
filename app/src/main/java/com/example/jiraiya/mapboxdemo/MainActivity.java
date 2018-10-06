package com.example.jiraiya.mapboxdemo;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.cluster.clustering.Cluster;
import com.mapbox.mapboxsdk.plugins.cluster.clustering.ClusterManagerPlugin;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  {

    private MapView mapView;
    private MapboxMap mMapboxMap;
    private ClusterManagerPlugin<POICluster> clusterManagerPlugin;
    private List<POICluster> poiClusterList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getApplicationContext(), getResources().getString(R.string.access_token));
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;

                mMapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(27.176670,78.008075), 2));
                clusterManagerPlugin = new ClusterManagerPlugin<>(MainActivity.this, mMapboxMap);

                mMapboxMap.addOnCameraIdleListener(clusterManagerPlugin);

                if (poiClusterList.size() > 0) {
                    poiClusterList.clear();
                }

                IconFactory iconFactory = IconFactory.getInstance(MainActivity.this);

                Icon iconRed, iconPurple, iconBlue, iconGreen, iconOrange, iconPink, iconYellow, iconSkyblue,black,brown,deepgreen;
                Icon semigreen;

                iconRed = iconFactory.fromResource(R.drawable.pin_red);
                iconPurple = iconFactory.fromResource(R.drawable.pin_purple);
                iconBlue = iconFactory.fromResource(R.drawable.pin_blue);
                iconGreen = iconFactory.fromResource(R.drawable.green);
                iconOrange = iconFactory.fromResource(R.drawable.orange);
                iconPink = iconFactory.fromResource(R.drawable.pink);
                iconYellow = iconFactory.fromResource(R.drawable.yellow);
                iconSkyblue = iconFactory.fromResource(R.drawable.skyblue);
                brown = iconFactory.fromResource(R.drawable.brown);
                black = iconFactory.fromResource(R.drawable.black);
                deepgreen = iconFactory.fromResource(R.drawable.deep_green);
                semigreen = iconFactory.fromResource(R.drawable.semi_green);



                // static lat/lng
                poiClusterList.add(new POICluster(27.176670, 78.008075, "User1", "India", iconRed));
                poiClusterList.add(new POICluster(28.700987, 77.279359, "User2", "India", iconPurple));
                poiClusterList.add(new POICluster(23.406714, 76.151514, "User3", "India", iconBlue));
                poiClusterList.add(new POICluster(26.316970, 78.107080, "User4", "India", iconGreen));
                poiClusterList.add(new POICluster(32.011725, 76.766748, "User5", "India", iconOrange));
                poiClusterList.add(new POICluster(34.095468, 75.975733, "User6", "India", iconPink));
                poiClusterList.add(new POICluster(22.5726, 88.3639, "User7", "India", iconYellow));
                poiClusterList.add(new POICluster(22.728392, 71.637077, "User8", "India", iconSkyblue));
                poiClusterList.add(new POICluster(9.383452, 76.574059, "User9", "India", brown));
                poiClusterList.add(new POICluster(10.925440, 79.838005, "User10", "India", black));
                poiClusterList.add(new POICluster(14.623801, 75.621788, "User11", "India", deepgreen));
                poiClusterList.add(new POICluster(38.056469, -83.943253, "User12", "India", semigreen));
                poiClusterList.add(new POICluster(15.852792, 74.498703, "User13", "India", iconRed));
                poiClusterList.add(new POICluster(41.437801, 22.642742, "User14", "India", iconPurple));
                poiClusterList.add(new POICluster(22.2587, 71.1924, "User15", "India", iconPink));



                if (poiClusterList != null && poiClusterList.size() > 0) {
                    clusterManagerPlugin.addItems(poiClusterList);
                }

                mMapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {

                        if(marker.getTitle() != null){

                            //Individual
                           // mMapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(marker.getPosition()), 12));
                            CameraPosition position = new CameraPosition.Builder()
                                    .target(new LatLng(marker.getPosition())) // Sets the new camera position
                                    .zoom(12) // Sets the zoom
                                    .bearing(360) // Rotate the camera
                                    .tilt(5) // Set the camera tilt
                                    .build(); // Creates a CameraPosition from the builder

                            mMapboxMap.animateCamera(CameraUpdateFactory
                                    .newCameraPosition(position), 5000);
                        }
                        else
                        {
                            //Cluster
                            CameraPosition position = new CameraPosition.Builder()
                                    .target(new LatLng(marker.getPosition())) // Sets the new camera position
                                    .zoom(5) // Sets the zoom
                                    .build(); // Creates a CameraPosition from the builder

                            mMapboxMap.animateCamera(CameraUpdateFactory
                                    .newCameraPosition(position), 1000);
                           // mMapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(marker.getPosition()), 3));
                        }



                        return true;
                    }
                });
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
