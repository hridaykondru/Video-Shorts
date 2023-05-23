package com.example.hriday_kondru_assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VideoList extends Fragment {

    private RecyclerView recyclerView;
    private VideoAdapter adapter;
    private SharedViewModel sharedViewModel;

    public VideoList() {

    }

    public static VideoList newInstance() {
        VideoList fragment = new VideoList();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);
        this.recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new VideoAdapter(sharedViewModel.getVideoList());
        recyclerView.setAdapter(adapter);

        // Set up scroll listener for pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                if (!sharedViewModel.getIsFetchingData() && lastVisibleItemPosition == totalItemCount - 1) {
                    // Load the next page of data
                    int currentPage = sharedViewModel.getCurrentPage();
                    currentPage++;
                    sharedViewModel.setCurrentPage(currentPage);
                    fetchData();
                }
            }
        });

        // Fetch initial data
        fetchData();
    }

    public void fetchData() {
        String apiUrl = "https://internship-service.onrender.com/videos?page=" + sharedViewModel.getCurrentPage();
        FetchVideosTask fetchVideosTask = new FetchVideosTask(apiUrl, adapter, sharedViewModel);
        fetchVideosTask.execute();
    }

    // Handle swipe vertically to navigate through videos
    private void onVerticalSwipe() {
        // Implement vertical swipe functionality to navigate through videos
        // You can use GestureDetector or other gesture recognition libraries to detect vertical swipe gestures
    }

}