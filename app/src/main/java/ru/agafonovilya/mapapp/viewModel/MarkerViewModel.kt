package ru.agafonovilya.mapapp.viewModel

import androidx.lifecycle.*
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.launch
import ru.agafonovilya.mapapp.model.markersDataBase.MyMarker
import ru.agafonovilya.mapapp.repository.MarkersRepository

class MarkerViewModel(private val repository: MarkersRepository) : ViewModel() {

    private val liveData: LiveData<List<MyMarker>> = repository.allMarkers.asLiveData()

    fun subscribeToLiveData(): LiveData<List<MyMarker>> {
        return liveData
    }

    fun insertMarker(marker: Marker) {
        viewModelScope.launch {
            val myMarker = MyMarker(latitude = marker.position.latitude,
                                    longitude = marker.position.longitude)
            repository.insertMarker(myMarker)
        }
    }

    fun updateMarker(myMarker: MyMarker) {
        viewModelScope.launch {
            repository.updateMarker(myMarker)
        }
    }

    fun removeMarker(myMarker: MyMarker) {
        viewModelScope.launch {
            repository.deleteMarker(myMarker)
        }
    }

}

class MainViewModelFactory(private val repository: MarkersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarkerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MarkerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}