package ie.setu.hotels.firebase.database

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import ie.setu.hotels.data.rules.Constants.HOTEL_COLLECTION
import ie.setu.hotels.data.rules.Constants.USER_EMAIL
import ie.setu.hotels.firebase.services.AuthService
import ie.setu.hotels.firebase.services.Hotel
import ie.setu.hotels.firebase.services.Hotels
import ie.setu.hotels.firebase.services.FirestoreService
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.Date
import javax.inject.Inject


class FirestoreRepository
@Inject constructor(private val auth: AuthService,
                    private val firestore: FirebaseFirestore
) : FirestoreService {

    override suspend fun getAll(email: String): Hotels {
        return firestore.collection(HOTEL_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .dataObjects()
    }
    override suspend fun get(email: String,
                             hotelId: String): Hotel? {
        return firestore.collection(HOTEL_COLLECTION)
                .document(hotelId).get().await().toObject()
    }

    override suspend fun insert(email: String, hotel: Hotel) {
        val hotelWithEmailAndImage =
            hotel.copy(
                email = email,
                imageUri = auth.customPhotoUri!!.toString()
            )

        firestore.collection(HOTEL_COLLECTION)
            .add(hotelWithEmailAndImage)
            .await()
    }

    override suspend fun update(email: String,
                                hotel: Hotel) {
        val hotelWithModifiedDate =
                    hotel.copy(dateModified = Date())

        firestore.collection(HOTEL_COLLECTION)
            .document(hotel._id)
            .set(hotelWithModifiedDate).await()
    }

    override suspend fun delete(email: String,
                                hotelId: String) {
            firestore.collection(HOTEL_COLLECTION)
                .document(hotelId)
                .delete().await()
    }

    override suspend fun updateUserPhotoUris(email: String, uri: Uri) {
        firestore.collection(HOTEL_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    firestore.collection(HOTEL_COLLECTION)
                        .document(document.id)
                        .update("imageUri", uri.toString())
                }
            }
            .addOnFailureListener { exception ->
                Timber.i("Error $exception")
            }
    }
}