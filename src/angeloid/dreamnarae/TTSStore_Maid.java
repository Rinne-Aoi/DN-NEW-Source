package angeloid.dreamnarae;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TTSStore_Maid extends Fragment {
	Button voice;
	MediaPlayer mplayer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.ttsstore_maid,
				null);
		voice = (Button) root.findViewById(R.id.button1);
		mplayer = MediaPlayer.create(getActivity(), R.raw.start);
		voice.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mplayer.start();
				
			}
		});
		return root;
	}
}