package com.zub.covid_19.repo;

import androidx.lifecycle.MutableLiveData;

import com.zub.covid_19.api.globalData.GlobalData;
import com.zub.covid_19.api.globalData.GlobalDataFetch;
import com.zub.covid_19.api.globalData.GlobalDataHolder;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class GlobalDataRepository {

    private static GlobalDataRepository globalDataRepository;

    public static GlobalDataRepository getInstance() {
        if (globalDataRepository == null) {
            globalDataRepository = new GlobalDataRepository();
        }
        return globalDataRepository;
    }

    private static GlobalDataHolder globalDataHolder;

    public GlobalDataRepository() {
        globalDataHolder = GlobalDataFetch.createService(GlobalDataHolder.class);
    }

    public MutableLiveData<GlobalData> getGlobalData() {
        MutableLiveData<GlobalData> globalDataMutableLiveData = new MutableLiveData<>();
        globalDataHolder.getGlobalData().enqueue(new Callback<GlobalData>() {
            @Override
            public void onResponse(@NotNull Call<GlobalData> call, @NotNull Response<GlobalData> response) {
                globalDataMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<GlobalData> call, @NotNull Throwable t) {
                Timber.d(t);
            }
        });
        return globalDataMutableLiveData;
    }

}
