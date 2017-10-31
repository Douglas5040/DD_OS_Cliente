package com.example.douglas.dd_os_cliente.activitys;

/**
 * Created by Douglas on 22/08/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MyLocation {
    Timer timer1;
    LocationManager lm;
    LocationResult locationResult;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    Context contextlocal = null;

    public boolean getLocation(Context context, LocationResult result) {
        contextlocal = context;
        //É usado o callback LocationResult para passar as coordenadas para o codigo do usuario.
        locationResult = result;
        if (lm == null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //se o provedor de localizacao nao estiver habilitado, teremos uma excecao.
        try {
            //gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (ContextCompat.checkSelfPermission(contextlocal, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(contextlocal, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                gps_enabled = true;
            }
        } catch (Exception ex) {
            Log.e("Provedor GPS: ", "DESABILITADO");
        }
        try {
            //network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (ContextCompat.checkSelfPermission(contextlocal, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(contextlocal, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                network_enabled = true;
            }
        } catch (Exception ex) {
            Log.e("Provedor NETWORK: ", "DESABILITADO");
        }

        //Codigo para nao tentar fazer a leitura sem provedor de localizacao disponivel
        if (!gps_enabled && !network_enabled) {
            Log.e("Provedor GPS: ", "DESABILITADO");
            return false;
        }

        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission(contextlocal, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contextlocal, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) contextlocal, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                lm.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListenerGps, null);
            }
        }
        Log.e("Provedor GPS: ", "HABILITADO");
        if (network_enabled) {
            if (ActivityCompat.checkSelfPermission(contextlocal, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(contextlocal, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) contextlocal, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 0);
                lm.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListenerNetwork, null);
            }
        }
        timer1 = new Timer();
        timer1.schedule(new GetLastLocation(), 1000);
        return true;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer1.cancel();
            locationResult.gotLocation(location);
            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerNetwork);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer1.cancel();
            locationResult.gotLocation(location);
            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerGps);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    class GetLastLocation extends TimerTask {
        @Override
        public void run() {
            lm.removeUpdates(locationListenerGps);
            lm.removeUpdates(locationListenerNetwork);

            Location net_loc = null, gps_loc = null;
            if (gps_enabled) {
                if (ActivityCompat.checkSelfPermission(contextlocal, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contextlocal, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }if(network_enabled){
                if (ContextCompat.checkSelfPermission(contextlocal, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED){
                        net_loc=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            }

            //se tiver os dois valores, usar o mais atualizado
            if(gps_loc!=null && net_loc!=null){
                if(gps_loc.getTime()>net_loc.getTime())
                    locationResult.gotLocation(gps_loc);
                else
                    locationResult.gotLocation(net_loc);
                return;
            }

            if(gps_loc!=null){
                locationResult.gotLocation(gps_loc);
                return;
            }
            if(net_loc!=null){
                locationResult.gotLocation(net_loc);
                return;
            }
            locationResult.gotLocation(null);
        }
    }

    public static abstract class LocationResult{
        public abstract void gotLocation(Location location);
    }
}