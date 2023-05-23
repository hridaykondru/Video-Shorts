package com.example.hriday_kondru_assignment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<List<Video>> videoList = new MutableLiveData<>();
    private MutableLiveData<Integer> currentPage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFetchingData = new MutableLiveData<>();
    public void setVideoList(List<Video> data) {
        videoList.setValue(data);
    }
    public void postVideoList(List<Video> data) {
        videoList.postValue(data);
    }
    public List<Video> getVideoList() {
        return videoList.getValue();
    }

    public void setCurrentPage(Integer data) {
        currentPage.setValue(data);
    }

    public void postCurrentPage(Integer data) {
        currentPage.postValue(data);
    }

    public Integer getCurrentPage() {
        return currentPage.getValue();
    }

    public void setIsFetchingData(Boolean data) {
        isFetchingData.setValue(data);
    }

    public void postIsFetchingData(Boolean data) {
        isFetchingData.postValue(data);
    }

    public Boolean getIsFetchingData() {
        return isFetchingData.getValue();
    }
}
