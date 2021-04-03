package com.meghdut.upsilent.fragments.drive

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.meghdut.upsilent.models.DriveFile
import com.meghdut.upsilent.models.GoogleDriveResponse
import com.meghdut.upsilent.network.GoogleDriveApi
import com.meghdut.upsilent.network.Query
import com.meghdut.upsilent.network.URLConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.net.URLEncoder


sealed class CurrentState<out T> {

    class Error(val exception: Exception) : CurrentState<Nothing>()
    class Success<T>(val result: T) : CurrentState<T>()
    object Loading : CurrentState<Nothing>()

}


class DriveViewModel(application: Application) : AndroidViewModel(application) {
    private val retrofit = Retrofit.Builder()
            .baseUrl(URLConstants.DRIVE_ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    private val googleDriveApi = retrofit.create(GoogleDriveApi::class.java)

    private val filesCache = hashMapOf<String, GoogleDriveResponse>()

    val folderPath = arrayListOf<String>()
    val fileList = MutableLiveData<List<DriveFile>>(listOf())
    val currentState = MutableLiveData<CurrentState<GoogleDriveResponse>>()


    fun explorePath(path: String) {
        if (path.isEmpty() && folderPath.isNotEmpty()) return
        var mainPath = folderPath.joinToString("/") { android.net.Uri.encode(it, "utf-8") }
        mainPath += "/${android.net.Uri.encode(path, "utf-8")}"
        val query = Query()
        currentState.postValue(CurrentState.Loading)
        googleDriveApi.getData(mainPath, query).enqueue(object : Callback<GoogleDriveResponse> {
            override fun onResponse(call: Call<GoogleDriveResponse>, response: Response<GoogleDriveResponse>) {
                if (response.isSuccessful) {
                    val driveResponse = response.body()!!
                    fileList.postValue(driveResponse.data.files)
                    currentState.postValue(CurrentState.Success(driveResponse))
                    folderPath.add(path)
                    filesCache[mainPath] = driveResponse
                } else {
                    currentState.postValue(CurrentState.Error(Exception("Server error ${response.code()} ")))
                }
            }

            override fun onFailure(call: Call<GoogleDriveResponse>, t: Throwable) {
                currentState.postValue(CurrentState.Error(Exception(t.localizedMessage)))
            }

        })
    }

    fun goBack() {
        if (folderPath.size == 1) return
        val last = folderPath.lastIndex
        folderPath.removeAt(last)
        var mainPath = folderPath.joinToString("/") { android.net.Uri.encode(it, "utf-8") }
        if (mainPath.isEmpty()) mainPath = "/"
        val driveResponse = filesCache[mainPath] ?: return
        fileList.postValue(driveResponse.data.files)
        currentState.postValue(CurrentState.Success(driveResponse))
    }


}