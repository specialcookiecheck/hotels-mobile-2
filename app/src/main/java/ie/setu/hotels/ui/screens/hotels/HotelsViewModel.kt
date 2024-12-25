package ie.setu.hotels.ui.screens.hotels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.hotels.data.model.HotelModel
import ie.setu.hotels.firebase.services.AuthService
import ie.setu.hotels.firebase.services.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HotelsViewModel @Inject
constructor(private val repository: FirestoreService,
            private val authService: AuthService
) : ViewModel() {
    private val _hotels
            = MutableStateFlow<List<HotelModel>>(emptyList())
    val uiHotels: StateFlow<List<HotelModel>>
            = _hotels.asStateFlow()
    var iserror = mutableStateOf(false)
    var isloading = mutableStateOf(false)
    var error = mutableStateOf(Exception())

    init { getHotels() }

    fun getHotels() {
        viewModelScope.launch {
            try {
                isloading.value = true
                repository.getAll(authService.email!!).collect{ items ->
                        _hotels.value = items
                        iserror.value = false
                        isloading.value = false
                    }
                Timber.i("DVM RVM = : ${_hotels.value}")
            }
            catch(e:Exception) {
                iserror.value = true
                isloading.value = false
                error.value = e
                Timber.i("RVM Error ${e.message}")
            }
        }
    }

    fun deleteHotel(hotel: HotelModel)
        = viewModelScope.launch {
            repository.delete(authService.email!!,hotel._id)
        }
}

