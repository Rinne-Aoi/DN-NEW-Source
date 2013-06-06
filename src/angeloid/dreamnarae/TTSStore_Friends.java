package angeloid.dreamnarae;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TTSStore_Friends extends Fragment {
	Button buy;
	Button voice;
	MediaPlayer mplayer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.ttsstore_friend,
				null);

		buy = (Button) root.findViewById(R.id.buy);
		voice = (Button) root.findViewById(R.id.button1);
		mplayer = MediaPlayer.create(getActivity(), R.raw.frineds_voice);
		voice.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mplayer.start();

			}
		});
		buy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), TTS_Biling.class);
				intent.putExtra("Type", "Friends");
				startActivity(intent);

			}
		});
		return root;
	}


}