package com.example.hriday_kondru_assignment;

import android.os.Bundle;
import android.util.MutableBoolean;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VideoAdapter adapter;
    private List<Video> videoList = new ArrayList<>();
    private int currentPage = 0;
    private MutableBoolean isFetchingData = new MutableBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoAdapter(videoList);
        recyclerView.setAdapter(adapter);

        // Set up scroll listener for pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                if (!isFetchingData.value && lastVisibleItemPosition == totalItemCount - 1) {
                    // Load the next page of data
                    currentPage++;
                    fetchData();
                }
            }
        });

        // Fetch initial data
        fetchData();
    }

    private void fetchData() {
        String apiUrl = "https://internship-service.onrender.com/videos?page=" + currentPage;
        FetchVideosTask fetchVideosTask = new FetchVideosTask(apiUrl,adapter,videoList,isFetchingData);
        fetchVideosTask.execute();
//        isFetchingData = true;
//
//        // Construct the API URL with the current page value
//        String apiUrl = "https://internship-service.onrender.com/videos?page=" + currentPage;
//
//        try {
//            // Create a URL object from the API URL string
//            URL url = new URL(apiUrl);
//
//            // Create a HttpURLConnection object
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//
//            // Get the response code
//            int responseCode = connection.getResponseCode();
//
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                // Read the response data
//                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                reader.close();
//
//                // Parse the JSON response
//                List<Video> newData = parseVideoData(response.toString());
//
//                // Update the videoList with the fetched data
//                videoList.addAll(newData);
//                adapter.notifyDataSetChanged();
//            }
//
//            // Disconnect the connection
//            connection.disconnect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        isFetchingData = false;
    }

//    private List<Video> parseVideoData(String json) {
//        List<Video> videos = new ArrayList<>();
//        try {
//            JSONObject ret = new JSONObject(json);
//            JSONObject data = ret.getJSONObject("data");
//            JSONArray posts = data.getJSONArray("posts");
//            for (int i = 0; i < posts.length(); i++) {
//                JSONObject videoJson = posts.getJSONObject(i);
//                JSONObject videoSubmissionJson = videoJson.getJSONObject("submission");
//                String videoId = videoJson.getString("postId");
//                String videoTitle = videoSubmissionJson.getString("title");
//                String videoThumbnailUrl = videoSubmissionJson.getString("thumbnail");
//                String mediaUrl = videoSubmissionJson.getString("mediaUrl");
//                // Create a Video object with the parsed data
//                Video video = new Video(videoId, videoTitle, videoThumbnailUrl, mediaUrl);
//                videos.add(video);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return videos;
//    }

    // Handle video item click event
    void onVideoItemClick(int position) {
        Toast.makeText(this, "clicked "+position, Toast.LENGTH_SHORT).show();
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

    // Handle swipe vertically to navigate through videos
    private void onVerticalSwipe() {
        // Implement vertical swipe functionality to navigate through videos
        // You can use GestureDetector or other gesture recognition libraries to detect vertical swipe gestures
    }
}

