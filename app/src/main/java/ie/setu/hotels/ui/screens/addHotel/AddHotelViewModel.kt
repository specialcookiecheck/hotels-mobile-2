package ie.setu.hotels.ui.screens.addHotel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.hotels.data.model.HotelModel
import ie.setu.hotels.firebase.services.AuthService
import ie.setu.hotels.firebase.services.FirestoreService
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.Timber.Forest.i
import javax.inject.Inject

@HiltViewModel
class AddHotelViewModel @Inject
constructor(private val repository: FirestoreService,
    private val authService: AuthService)
    : ViewModel() {
    var isErr = mutableStateOf(false)
    var error = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

    fun insert(hotel: HotelModel) =
        viewModelScope.launch {
        try {
            i("trying AddHotelViewModel insert")
            isLoading.value = true
            repository.insert(authService.email!!,hotel)
            isErr.value = false
            isLoading.value = false
            i("trying AddHotelViewModel insert completed")
        } catch (e: Exception) {
            i("catching AddHotelViewModel insert")
            isErr.value = true
            error.value = e
            isLoading.value = false
        }
            Timber.i("AddHotel Insert Message = : ${error.value.message} and isError ${isErr.value}")
    }
}
