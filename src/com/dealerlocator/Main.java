package com.dealerlocator;

import java.util.List;

import org.apache.http.HttpHost;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Main extends MapActivity {
	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	DealerLocatorItemizedOverlay itemizedOverlay;
	private LocationManager locationManager;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		mapOverlays = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.map_pin);
		// itemizedOverlay = new HelloItemizedOverlay(drawable, this);
		itemizedOverlay = new DealerLocatorItemizedOverlay(drawable, this);

		// this would be for getting location from the embedded gps
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000L, 500.0f, new LocationListener() {

					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						// TODO Auto-generated method stub

					}

					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub

					}

					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub

					}

					public void onLocationChanged(Location location) {
						if (location != null) {
							double lat = location.getLatitude();
							double lng = location.getLongitude();
							GeoPoint p = new GeoPoint((int) lat * 1000000,
									(int) lng * 1000000);
							mapView.getController().animateTo(p);
							Toast.makeText(mapView.getContext(),
									"GPS location updated.",
									Toast.LENGTH_SHORT).show();

						}

					}
				});
		mapView.getController().setZoom(10);

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void myClickHandler(View view) {

		String theZip = "61554";
		EditText editTextZip = (EditText) findViewById(R.id.EditTextZip);
		if (editTextZip.getText().length() > 0) {
			theZip = editTextZip.getText().toString();
		}
		List<Coordinate> theClosestCoordinates = new DealerLocatorService()
				.findClosestDealerForZip(new HttpHost("dealerlocator.cat.com"),
						theZip);
		// Log.v("zipButtonClickHandler", "Coordinate is "
		// + theClosestCoordinate.getxValue() + ", "
		// + theClosestCoordinate.getyValue());

		// new AlertDialog.Builder(this).setMessage(
		// "Value of xValue is " + xValue + " and value of yValue is "
		// + yValue + ".").setPositiveButton("ok",
		// new OnClickListener() {
		//
		// public void onClick(DialogInterface dialog, int which) {
		//
		// }
		// }).show();

		// Log.v("main", "Value of xValue is "
		// + theClosestCoordinate.getxValueMicroDeg()
		// + " and value of yValue is "
		// + theClosestCoordinate.getyValueMicroDeg() + ".");
		for (Coordinate coordinate : theClosestCoordinates) {
			GeoPoint point = new GeoPoint(coordinate.getxValueMicroDeg(),
					coordinate.getyValueMicroDeg());
			OverlayItem overlayitem = new OverlayItem(point,
					coordinate.getDealerName(), coordinate.getAddress()+"\n"+coordinate.getPhoneNumber());
			itemizedOverlay.addOverlay(overlayitem);
		}

		mapOverlays.clear();
		mapOverlays.add(itemizedOverlay);
		// just grab one at random to serve as the point to zoom in to.
		mapView.getController()
				.animateTo(itemizedOverlay.getItem(1).getPoint());
		mapView.getController().setZoom(7);
		mapView.invalidate();

	}

}