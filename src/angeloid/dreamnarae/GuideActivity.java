package angeloid.dreamnarae;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class GuideActivity extends BaseSlidingActivity implements View.OnTouchListener {

	ViewFlipper flipper;
	float xAtDown;
	float xAtUp;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide);
		flipper = (ViewFlipper) findViewById(R.id.viewFlipper1);
		flipper.setOnTouchListener(this);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v != flipper)
			return false;

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			xAtDown = event.getX();
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			xAtUp = event.getX();

			if (xAtUp < xAtDown) {
				flipper.setInAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_left_in));
				flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_left_out));

				// ´ÙÀ½ view º¸¿©ÁÜ
				flipper.showNext();
			} else if (xAtUp > xAtDown) {
				flipper.setInAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_right_in));
				flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_right_out));
				flipper.showPrevious();
			}
		}
		return true;
	}
}