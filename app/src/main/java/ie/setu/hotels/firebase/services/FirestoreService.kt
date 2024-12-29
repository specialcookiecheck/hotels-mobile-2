package ie.setu.hotels.firebase.services

import android.net.Uri
import ie.setu.hotels.data.model.HotelModel
import kotlinx.coroutines.flow.Flow

typealias Hotel = HotelModel
typealias Hotels = Flow<List<Hotel>>

interface FirestoreService {

    suspend fun getAll(email: String) : Hotels
    suspend fun get(email: String, hotelId: String) : Hotel?
    suspend fun insert(email: String, hotel: Hotel)
    suspend fun update(email: String, hotel: Hotel)
    suspend fun delete(email: String, hotelId: String)
    suspend fun updateUserPhotoUris(email: String, uri: Uri)
    // suspend fun updateHotelImageUris(email: String, uri: Uri)
}