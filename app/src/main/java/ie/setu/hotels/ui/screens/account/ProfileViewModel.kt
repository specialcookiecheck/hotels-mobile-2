package ie.setu.hotels.ui.screens.account

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.hotels.firebase.services.AuthService
import ie.setu.hotels.firebase.services.FirestoreService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authService: AuthService,
    private val firestoreService: FirestoreService,
) : ViewModel() {

    val displayName get() = authService.currentUser?.displayName.toString()
    val photoUri get() = authService.customPhotoUri
    val email get() = authService.email.toString()

    fun signOut() {
        viewModelScope.launch { authService.signOut() }
    }

    fun updateUserPhotoUri(uri: Uri) {
        viewModelScope.launch {
            authService.updateUserPhoto(uri)
            firestoreService.updateUserPhotoUris(email,photoUri!!)
        }
    }
}