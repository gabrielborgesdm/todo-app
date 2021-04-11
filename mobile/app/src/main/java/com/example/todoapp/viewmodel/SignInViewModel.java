package com.example.todoapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoapp.R;
import com.example.todoapp.service.HeaderModel;
import com.example.todoapp.service.constants.PersonConstants;
import com.example.todoapp.service.listener.ApiListener;
import com.example.todoapp.service.repository.PersonRepository;
import com.example.todoapp.service.repository.local.SecurityPreferences;

import static com.example.todoapp.service.constants.PersonConstants.PERSON_MESSAGE;
import static com.example.todoapp.service.constants.PersonConstants.PERSON_SUCCESS;
import static com.example.todoapp.service.constants.PersonConstants.PERSON_TOKEN;

public class SignInViewModel extends AndroidViewModel {
    PersonRepository mPersonRepository = new PersonRepository(getApplication().getApplicationContext());
    private final SecurityPreferences mSharedPreferences;

    private final MutableLiveData<Bundle> mLogin = new MutableLiveData<Bundle>();
    public LiveData<Bundle> login = mLogin;

    private final MutableLiveData<Boolean> mLoggedUser = new MutableLiveData<Boolean>();
    public LiveData<Boolean> loggedUser = mLoggedUser;

    public SignInViewModel(@NonNull Application application) {
        super(application);
        mSharedPreferences = new SecurityPreferences(application.getApplicationContext());
    }

    public void doLogin(String email, String password) {
        mPersonRepository.login(email, password, new ApiListener() {

            @Override
            public void onSuccess(HeaderModel model) {
                Bundle login = new Bundle();
                if(model.status.equals(PersonConstants.PERSON_FOUND)){
                    mSharedPreferences.store(PersonConstants.PERSON_TOKEN, model.token);
                    login.putBoolean(PERSON_SUCCESS, true);
                    login.putString(PERSON_MESSAGE, getApplication().getApplicationContext().getString(R.string.person_found_with_success));
                } else if(model.status.equals(PersonConstants.PERSON_NOT_FOUND)){
                    login.putBoolean(PERSON_SUCCESS, false);
                    login.putString(PERSON_MESSAGE, getApplication().getApplicationContext().getString(R.string.person_not_found));
                } else {
                    login.putBoolean(PERSON_SUCCESS, false);
                    login.putString(PERSON_MESSAGE, getApplication().getApplicationContext().getString(R.string.something_went_wrong));
                }

                mLogin.setValue(login);
            }

            @Override
            public void onFailure(String message) {
                Bundle login = new Bundle();
                login.putBoolean(PERSON_SUCCESS, false);
                login.putString(PersonConstants.PERSON_MESSAGE, message);
                mLogin.setValue(login);
            }
        });
    }

    public void verifyLoggedUser() {
        String token = mSharedPreferences.get(PERSON_TOKEN);
        mLoggedUser.setValue(!token.equals(""));
    }
}
