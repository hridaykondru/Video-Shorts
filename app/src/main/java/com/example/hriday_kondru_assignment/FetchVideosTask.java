package com.example.hriday_kondru_assignment;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchVideosTask extends AsyncTask<Void, Void, List<Video>> {
    private String apiUrl;
    private VideoAdapter adapter;
    private SharedViewModel sharedViewModel;

    public FetchVideosTask(String apiUrl, VideoAdapter adapter, SharedViewModel sharedViewModel){
        this.adapter = adapter;
        this.apiUrl = apiUrl;
        this.sharedViewModel = sharedViewModel;
    }

    @Override
    protected List<Video> doInBackground(Void... voids) {
        // Perform network operation to fetch video data
        sharedViewModel.postIsFetchingData(true);
        List<Video> videos = new ArrayList<>();

        try {
            // Create a URL object from the API URL string
            URL url = new URL(apiUrl);

            // Create a HttpURLConnection object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response data
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON response
                videos = parseVideoData(response.toString());
            }

            // Disconnect the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return videos;
    }

    @Override
    protected void onPostExecute(List<Video> videos) {
        // Update UI with the fetched videos
        List<Video> videoList = sharedViewModel.getVideoList();
        videoList.addAll(videos);
        sharedViewModel.postVideoList(videoList);
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
        sharedViewModel.postIsFetchingData(false);
    }

    private List<Video> parseVideoData(String json) {
        List<Video> videos = new ArrayList<>();
        try {
            JSONObject ret = new JSONObject(json);
            JSONObject data = ret.getJSONObject("data");
            JSONArray posts = data.getJSONArray("posts");
            for (int i = 0; i < posts.length(); i++) {
                JSONObject videoJson = posts.getJSONObject(i);
                JSONObject videoSubmissionJson = videoJson.getJSONObject("submission");
                String videoId = videoJson.getString("postId");
                String videoTitle = videoSubmissionJson.getString("title");
                String videoThumbnailUrl = videoSubmissionJson.getString("thumbnail");
                String mediaUrl = videoSubmissionJson.getString("mediaUrl");
                String description = videoSubmissionJson.getString("description");
                // Create a Video object with the parsed data
                Video video = new Video(videoId, videoTitle, videoThumbnailUrl, mediaUrl, description);
                videos.add(video);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return videos;
    }
}
