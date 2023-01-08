package com.example.sig2022_app.ui.suggestion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SuggestionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SuggestionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is suggestion fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}