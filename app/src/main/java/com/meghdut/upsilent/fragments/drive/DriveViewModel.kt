package com.meghdut.upsilent.fragments.drive

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.meghdut.upsilent.network.GoogleDriveApi
import com.meghdut.upsilent.network.URLConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DriveViewModel(application: Application) : AndroidViewModel(application) {
    private val retrofit = Retrofit.Builder()
            .baseUrl(URLConstants.DRIVE_ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    private val googleDriveApi = retrofit.create(GoogleDriveApi::class.java)





}