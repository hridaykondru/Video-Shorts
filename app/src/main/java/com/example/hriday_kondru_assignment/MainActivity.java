package com.example.hriday_kondru_assignment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private VideoList videoListFragment;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.setVideoList(new ArrayList<>());
        sharedViewModel.setCurrentPage(0);
        sharedViewModel.setIsFetchingData(false);
        videoListFragment = VideoList.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, videoListFragment);
        transaction.addToBackStack(null); // Add to back stack to allow navigating back
        transaction.commit();
    }

    void onVideoItemClick(int position) {
        VideoFragment videoFragment = VideoFragment.newInstance(position);

        // Replace the current fragment with the VideoFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, videoFragment);
        transaction.addToBackStack(null); // Add to back stack to allow navigating back
        transaction.commit();
//        Video video = videoList.get(position);
//        String videoUrl = video.getVideoUrl();
//
//        // Create a new instance of MediaPlayer
//        MediaPlayer mediaPlayer = new MediaPlayer();
//
//        try {
//            // Set the data source to the video URL
//            mediaPlayer.setDataSource(videoUrl);
//
//            // Set the audio stream type
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//            // Set the surface holder for the video playback
//            mediaPlayer.setDisplay(surfaceHolder);
//
//            // Set a listener to handle when the media is prepared
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    // Start the video playback
//                    mediaPlayer.start();
//                }
//            });
//
//            // Prepare the media asynchronously
//            mediaPlayer.prepareAsync();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}

