package com.dealerlocator;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class DealerLocatorItemizedOverlay extends ItemizedOverlay {
	private Activity THE_ACTIVITY;
	private Context CONTEXT;
	Coordinate selectedMapCoordinate;
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

	public DealerLocatorItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	/**
	 * This isn't the right way of passing a reference to the Activity, but this would be needed for Toast.
	 * 
	 * @param defaultMarker
	 * @param theActivityØ
	 */
//	public HelloItemizedOverlay(Drawable defaultMarker, Activity theActivity) {
//		this(defaultMarker);
//		THE_ACTIVITY = theActivity;
//	}
	
	public DealerLocatorItemizedOverlay(Drawable defaultMarker, Context theContext){
		this(defaultMarker);
		CONTEXT = theContext;
		
	}

	@Override
	protected OverlayItem createItem(int i) {

		return mOverlays.get(i);

	}

	@Override
	public int size() {

		return mOverlays.size();
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

	@Override
	protected boolean onTap(int index) {
		// ** This impl uses a custom dialog.
		OverlayItem item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(CONTEXT);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;

		/** This one uses Toast, which seems limited **/
		// OverlayItem item = mOverlays.get(index);
		// Toast.makeText(THE_ACTIVITY, item.getTitle(),
		// Toast.LENGTH_SHORT).show();
		// return true;
	}

	// @Override
	// public boolean onTouchEvent(MotionEvent event, MapView mapView) {
	//
	// // Store whether prior popup was displayed so call invalidate() to
	// // remove it if necessary.
	// boolean isRemovePriorPopup = selectedMapCoordinate != null;
	//
	// // Next test whether a new popup should be displayed
	// selectedMapCoordinate = getHitMapLocation(mapView, event);
	// if (isRemovePriorPopup || selectedMapCoordinate != null) {
	//
	// mapView.invalidate();
	//
	// }
	//
	// // Lastly return true if we handled this onTap()
	// return selectedMapCoordinate != null;
	// }
	//
	// private Coordinate getHitMapLocation(MapView mapView, MotionEvent event)
	// {
	//
	// // Track which MapLocation was hit…if any
	// Coordinate hitMapLocation = null;
	//
	// RectF hitTestRecr = new RectF();
	// int[] screenCoords = new int[2];
	// Iterator<Coordinate> iterator = mapView.getMapLocations().iterator();
	// while(iterator.hasNext()) {
	//
	// MapLocation testLocation = iterator.next();
	//
	// // As above, translate MapLocation lat/long to screen coordinates
	// mapView.getProjection().getPointXY(testLocation.getPoint(),
	// screenCoords);
	//
	// // Use this information to create a ‘hit” testing Rectangle to represent
	// the size
	// // of our location’s icon at the correct location on the screen.
	// // As we want the base of our balloon icon to be at the exact location of
	// // our map location, we set our Rectangle’s location so the bottom-middle
	// of
	// // our icon is at the screen coordinates of our map location (shown
	// above).
	// hitTestRecr.set(-bubbleIcon.width()/2,-bubbleIcon.height(),bubbleIcon.width()/2,0);
	//
	// // Next, offset the Rectangle to location of our location’s icon on the
	// screen.
	// hitTestRecr.offset(screenCoords[0],screenCoords[1]);
	//
	// // Finally test for match between ‘hit’ Rectangle and location clicked by
	// the user.
	// // If a hit occurred, then we stop processing and return the result;
	// if (hitTestRecr.contains(event.getX(),event.getY()) {
	//
	// hitMapLocation = testLocation;
	// break;
	//
	// }
	//
	// }
	//
	// return hitMapLocation;
	//
	// }

}
