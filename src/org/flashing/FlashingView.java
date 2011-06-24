package org.flashing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class FlashingView extends Activity {
	private final OnClickListener trigger = new OnClickListener() {

		@Override
		public void onClick(View v) {
			arrowView.removeCallbacks(flashTask);
			flashTask.reset();
			postFlashTask();
		}
	};

	private final FlashTask flashTask = new FlashTask();

	private View arrowView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rocket);

		registerView(R.id.rocketImage);
		registerView(R.id.uparrowImage);

		arrowView = findViewById(R.id.uparrowImage);
	}

	/**
	 * Sets up the click listener for the view with the provided id so that the
	 * flash is triggered when the view is clicked.
	 * 
	 * @param viewId
	 *            the id of the view to set up the listener on
	 */
	private void registerView(int viewId) {
		View view = findViewById(viewId);
		view.setClickable(true);
		view.setOnClickListener(trigger);
	}

	/**
	 * Reposts the task to toggle the visibility after short interval.
	 */
	private void postFlashTask() {
		arrowView.postDelayed(flashTask, 250);
	}

	/**
	 * This is the {@link Runnable} that will toggle the visibility of the
	 * arrow. This can be configured to flash up a certain number of times.
	 */
	private class FlashTask implements Runnable {

		/**
		 * The number of times to flash before stopping.
		 */
		private int countDown = 0;

		/**
		 * Resets the {@link Runnable}, ready to flash the arrow a certain
		 * number of times again.
		 */
		public void reset() {
			countDown = 3;
		}

		@Override
		public void run() {
			if (arrowView.getVisibility() == View.VISIBLE) {
				arrowView.setVisibility(View.INVISIBLE);
				countDown--;
				if (countDown > 0) {
					// Can continue flashing.
					postFlashTask();
				}
			} else {
				arrowView.setVisibility(View.VISIBLE);

				// The arrow should should always finish in the invisible state.
				postFlashTask();
			}
		}
	}
}