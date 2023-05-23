package com.example.hriday_kondru_assignment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {

    private int position;
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private Video video;
    private SharedViewModel sharedViewModel;

    public VideoFragment() {
        // Required empty public constructor
    }

    public static VideoFragment newInstance(int position) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        playerView = view.findViewById(R.id.player_view);
        OnSwipeTouchListener swipeTouchListener = new OnSwipeTouchListener(this.getContext()) {
            @Override
            public void onSwipeUp() {
                // Handle upward swipe event
                // Play the next video or perform any other action
                if (position < sharedViewModel.getVideoList().size() - 1) {
                    position++;
                    initializePlayer();
                    if (position == sharedViewModel.getVideoList().size() - 1){
                        int currentPage = sharedViewModel.getCurrentPage();
                        currentPage++;
                        sharedViewModel.setCurrentPage(currentPage);
                        String apiUrl = "https://internship-service.onrender.com/videos?page=" + sharedViewModel.getCurrentPage();
                        FetchVideosTask fetchVideosTask = new FetchVideosTask(apiUrl, null, sharedViewModel);
                        fetchVideosTask.execute();
                    }
                }
            }

            @Override
            public void onSwipeDown() {
                // Handle downward swipe event
                // Play the previous video or perform any other action
                if (position > 0) {
                    position--;

                    initializePlayer();
                }
            }
            private void animateSwipeUp() {
                // Apply swipe up animation to your video player view
                Animation swipeUpAnimation = AnimationUtils.loadAnimation(view.getContext(), R.anim.swipe_up_animation);
                playerView.startAnimation(swipeUpAnimation);
            }
            private void animateSwipeDown() {
                // Apply swipe down animation to your video player view
                Animation swipeDownAnimation = AnimationUtils.loadAnimation(view.getContext(), R.anim.swipe_down_animation);
                playerView.startAnimation(swipeDownAnimation);
            }
        };
        playerView.setOnTouchListener(swipeTouchListener);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        initializePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    private void initializePlayer() {
        video = sharedViewModel.getVideoList().get(position);
        int appNameStringRes = R.string.app_name;
        TrackSelector trackSelectorDef = new DefaultTrackSelector();
        player = ExoPlayerFactory.newSimpleInstance(this.getContext(), trackSelectorDef); //creating a player instance

        String userAgent = Util.getUserAgent(this.getContext(), this.getString(appNameStringRes));
        DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(this.getContext(),userAgent);
        Uri uriOfContentUrl = Uri.parse(video.getVideoUrl());
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);  // creating a media source

        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
        playerView.setPlayer(player);
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }
}