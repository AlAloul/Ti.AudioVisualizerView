/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.audiovisualizerview;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.util.TiConvert;

import com.pheelicks.visualizer.renderer.BarGraphRenderer;
import com.pheelicks.visualizer.renderer.CircleBarRenderer;
import com.pheelicks.visualizer.renderer.CircleRenderer;
import com.pheelicks.visualizer.renderer.LineRenderer;
import com.pheelicks.visualizer.VisualizerView;

import android.app.Activity;
import android.view.View;
import android.view.LayoutInflater;
import android.content.Context;
import android.content.res.*;

import java.util.HashMap;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;

@Kroll.proxy(creatableInModule = AudiovisualizerviewModule.class)
public class VisualizerViewProxy extends TiViewProxy {
	// instance of pheelicks view
	VisualizerView visualizerView;
	private static final String LCAT = "Pheelicks";
	final public int DEFAULT_AUDIOSESSIONID = 0;
	public int audioSessionId = DEFAULT_AUDIOSESSIONID;
	TiApplication appContext;
	Activity activity;
	/* this 4 events never will called, I need the events for rerendering and rebinding */
	@Override
	public void onStart(Activity activity) {
		
		Log.d(LCAT,
				"onStart called ≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠");
	}

	@Override
	public void onResume(Activity activity) {
		super.onResume(activity);
		Log.d(LCAT,
				"onResume called ≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠");
	}

	@Override
	public void onPause(Activity activity) {
		super.onPause(activity);
		Log.d(LCAT,
				"onPause called ≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠≠");
	}

	// Constructor
	public VisualizerViewProxy() {
		super();
		appContext = TiApplication.getInstance();
		activity = appContext.getCurrentActivity();
		Log.d(LCAT, "inside VisualizerViewProxy");

	}

	@Override
	public TiUIView createView(Activity activity) {
		TiUIView view = new TiVisualizerImageView(this);
		view.getLayoutParams().autoFillsHeight = true;
		view.getLayoutParams().autoFillsWidth = true;
		/* this 3 event will never fired: */
		return view;
	}

	// Handle creation options
	@Override
	public void handleCreationDict(KrollDict options) {
		super.handleCreationDict(options);
	}

	private class TiVisualizerImageView extends TiUIView {
		
		public TiVisualizerImageView(final TiViewProxy proxy) {
			
			super(proxy);

			/*
			 * you can bind the visualizer to id=0, this is the mixer out and
			 * depends on volume , the ids >0 are result of getAudioSessionId()
			 */
			if (proxy.hasProperty("audioSessionId")) {
				audioSessionId = TiConvert.toInt(proxy
						.getProperty("audioSessionId"));
				Log.d(LCAT, "audioSessionId " + audioSessionId);
			}
			Log.d(LCAT, "starting createSilentMediaPlayer ");
			// creating view from xml res
			String packageName = proxy.getActivity().getPackageName();
			Resources res = proxy.getActivity().getResources();
			View visualizerContainer;
			LayoutInflater inflater = LayoutInflater.from(getActivity());

			visualizerContainer = inflater.inflate(
					res.getIdentifier("main", "layout", packageName), null);
			visualizerView = (VisualizerView) visualizerContainer
					.findViewById(res.getIdentifier("visualizerView", "id",
							packageName));
			setNativeView(visualizerContainer);

			visualizerView.link(audioSessionId); // binding to mixer
													// out
			if (proxy.hasListeners("ready")) {
				Log.d(LCAT, "fireEvent 'ready' ");
				proxy.fireEvent("ready", new KrollDict());
			}
			
		}

		@Override
		public void processProperties(KrollDict props) {
			super.processProperties(props);
		}

	

	}

	@Kroll.method
	public void release() {
		if (visualizerView != null)
			visualizerView.release();
		else
			Log.d(LCAT, "Error: visualizerView is null");
	}

	@Kroll.method
	public void clearRenderers() {
		if (visualizerView != null)
			visualizerView.clearRenderers();
		else
			Log.d(LCAT, "Error: visualizerView is null");
	}

	@Kroll.method
	public void addLineRenderer(@Kroll.argument(optional = true) KrollDict args) {
		if (args != null && args.containsKey("basicColor")) {

		}
		Paint linePaint = new Paint();
		linePaint.setStrokeWidth(1f);
		linePaint.setAntiAlias(true);
		linePaint.setColor(Color.argb(88, 0, 128, 255));

		Paint lineFlashPaint = new Paint();
		lineFlashPaint.setStrokeWidth(5f);
		lineFlashPaint.setAntiAlias(true);
		lineFlashPaint.setColor(Color.argb(188, 255, 255, 255));
		LineRenderer lineRenderer = new LineRenderer(linePaint, lineFlashPaint,
				true);
		if (visualizerView != null)
			visualizerView.addRenderer(lineRenderer);
		else
			Log.d(LCAT, "Error: visualizerView is null");
	}

	@Kroll.method
	public void addCircleRenderer() {
		Paint paint = new Paint();
		paint.setStrokeWidth(3f);
		paint.setAntiAlias(true);
		paint.setColor(Color.argb(255, 222, 92, 143));
		CircleRenderer circleRenderer = new CircleRenderer(paint, true);
		if (visualizerView != null)
			visualizerView.addRenderer(circleRenderer);
		else
			Log.d(LCAT, "Error: visualizerView is null");

	}

	@Kroll.method
	public void addCircleBarRenderer() {
		Paint paint = new Paint();
		paint.setStrokeWidth(8f);
		paint.setAntiAlias(true);
		paint.setXfermode(new PorterDuffXfermode(Mode.LIGHTEN));
		paint.setColor(Color.argb(255, 222, 92, 143));
		CircleBarRenderer circleBarRenderer = new CircleBarRenderer(paint, 32,
				true);
		if (visualizerView != null)
			visualizerView.addRenderer(circleBarRenderer);
		else
			Log.d(LCAT, "Error: visualizerView is null");
	}

	@Kroll.method
	public void addBarGraphRenderers() {
		Log.d(LCAT, "starting addBarGraphRenderers ");
		/*
		 * Paint paint = new Paint(); paint.setStrokeWidth(50f);
		 * paint.setAntiAlias(true); paint.setColor(Color.argb(200, 56, 138,
		 * 252)); BarGraphRenderer barGraphRendererBottom = new
		 * BarGraphRenderer(16, paint, false);
		 * visualizerView.addRenderer(barGraphRendererBottom);
		 */
		Paint paint2 = new Paint();
		paint2.setStrokeWidth(12f);
		paint2.setAntiAlias(true);
		paint2.setColor(Color.argb(200, 56, 138, 252));
		BarGraphRenderer barGraphRendererTop = new BarGraphRenderer(4, paint2,
				false);
		visualizerView.addRenderer(barGraphRendererTop);

	}
}