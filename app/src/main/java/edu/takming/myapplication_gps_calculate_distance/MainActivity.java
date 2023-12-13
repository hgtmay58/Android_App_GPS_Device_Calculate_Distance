package edu.takming.myapplication_gps_calculate_distance;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements LocationListener {
TextView t,tdis;
LocationManager locationManager;
Location location;
double mLatitude = 0;
double mLongitude = 0;
String provider;
MainActivity mactivity;
EditText x,y;
Button btok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mactivity=this;
        t=(TextView)findViewById(R.id.t1);
        tdis=(TextView)findViewById(R.id.textView);
        x=(EditText) findViewById(R.id.editTextText);
        y=(EditText) findViewById(R.id.editTextText2);
        btok=(Button) findViewById(R.id.button);
        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lat = x.getText().toString();
                String lon = y.getText().toString();

                //
                double lat_d=Double.parseDouble(lat);
                double lon_d=Double.parseDouble(lon);
                Location targetLocation = new Location("");//provider name is unnecessary
                targetLocation.setLatitude(lat_d);//your target
                targetLocation.setLongitude(lon_d);
                //
                float distanceInMeters =-1;
                if( location !=null) {
                    distanceInMeters = targetLocation.distanceTo( location);
                    tdis.setText("目前所在位置與目標距離(distance):"+""+distanceInMeters+" " +" m");
                }
                else
                    tdis.setText("尚未定位成功,無法計算與目標距離(Unable to calculate distance)");
            }
        });
        //
        if (ActivityCompat.checkSelfPermission(mactivity, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(mactivity, new String[]{ACCESS_FINE_LOCATION}, 12);

        }
        else
            initlocation();






    }
//
//
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
{
    if(requestCode==12)
    {
        if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED) {
            initlocation();
        }
        else
            return;
    }
}
 //
  void initlocation() {
     try {
         // Getting LocationManager object from System Service LOCATION_SERVICE
         locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
         // Creating a criteria object to retrieve provider
         Criteria criteria = new Criteria();

         // Getting the name of the best provider
         provider = locationManager.getBestProvider(criteria, true);
         // Enabling  get My Location
         // Getting Current Location From GPS
         location = locationManager.getLastKnownLocation(provider);
         locationManager.requestLocationUpdates(provider, 12000, 0, mactivity);
         onLocationChanged(location);
     }
     catch(SecurityException e)
     {
         ActivityCompat.requestPermissions(mactivity, new String[]{ACCESS_FINE_LOCATION}, 12);
     }

 }
    //
    @Override
    public void onLocationChanged(Location location) {

        if(location==null)
        {
          t.setText("尚未定位成功!(GPS positioning failed)");
        }
        else {
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();

            String altitiude = "Altitiude(高度): " + location.getAltitude();
            String accuracy = "Accuracy: " + location.getAccuracy();
            String time = "Time: " + location.getTime();

            t.setText("位置座標(Locastion):"+mLatitude+","+mLongitude+ "\n"
                    + altitiude + "\n" + accuracy + "\n" + time);

        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
