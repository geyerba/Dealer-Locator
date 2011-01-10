package com.dealerlocator;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class DealerLocatorItemizedOverlay extends ItemizedOverlay {
	
	private Context CONTEXT;
	Coordinate selectedMapCoordinate;
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

	public DealerLocatorItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	

	public DealerLocatorItemizedOverlay(Drawable defaultMarker,
			Context theContext) {
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
		dialog.setTitle(item.getTitle()).setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// MyActivity.this.finish();
						dialog.dismiss();
					}
				});

		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;

		/** This one uses Toast, which seems limited **/
		// OverlayItem item = mOverlays.get(index);
		// Toast.makeText(THE_ACTIVITY, item.getTitle(),
		// Toast.LENGTH_SHORT).show();
		// return true;
	}
	

}
