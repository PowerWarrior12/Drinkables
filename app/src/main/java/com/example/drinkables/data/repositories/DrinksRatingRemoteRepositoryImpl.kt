package com.example.drinkables.data.repositories

import android.util.Log
import com.example.drinkables.domain.entities.PropertyModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject
import com.example.drinkables.domain.common.Result

const val TABLE_RATINGS = "rating"
const val TABLE_USERS = "users"
private val TAG = DrinksRatingRemoteRepositoryImpl::class.java.simpleName

class DrinksRatingRemoteRepositoryImpl @Inject constructor() : DrinksRatingRemoteRepository {

    private val database: FirebaseDatabase = Firebase.database
    private val myRef: DatabaseReference = database.getReference(TABLE_RATINGS)
    private val myRefUsers: DatabaseReference = database.getReference(TABLE_USERS)

    override fun loadAllRatings(): Flow<List<PropertyModel.PropertyUserRatingModel>> = channelFlow {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                launch {
                    val resultWrapper =
                        dataSnapshot.getValue<Map<String, Map<String, PropertyModel.PropertyRatingModel>>>()
                    Log.d("MyTag", "Value is: $resultWrapper")
                    val result = mutableListOf<PropertyModel.PropertyUserRatingModel>()
                    resultWrapper?.forEach { userRatings ->
                        result.addAll(userRatings.value.map { ratings ->
                            PropertyModel.PropertyUserRatingModel(userRatings.key, ratings.value)
                        })
                    }
                    Log.d("MyTag", "Result is: $result")
                    send(result)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", error.toException())
            }
        })
        awaitClose()
    }

    override suspend fun addOrUpdateRating(rating: PropertyModel.PropertyRatingModel, userName: String) {
        myRef.child(userName).orderByChild("id").equalTo(rating.id.toDouble())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        myRef.child(userName).child("!${rating.id}").updateChildren(
                            mapOf(
                                Pair("value", rating.value)
                            )
                        )
                    } else {
                        myRef.child(userName).child("!${rating.id}").setValue(
                            mapOf(
                                Pair("id", rating.id),
                                Pair("value", rating.value)
                            )
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    override suspend fun tryToCreateUser(userName: String): Flow<Result<Boolean>> = channelFlow {
        myRefUsers.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var mapResult = snapshot.getValue<MutableMap<String, Boolean>>()
                if (mapResult?.containsKey(userName) == true) {
                    launch {
                        send(Result.Success(false))
                    }
                } else {
                    if (mapResult == null) mapResult = mutableMapOf()
                    mapResult[userName] = true
                    myRefUsers.setValue(mapResult)
                    launch {
                        send(Result.Success(true))
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                launch {
                    send(Result.Error(error.toException()))
                }
            }
        })

        awaitClose()
    }

    override suspend fun updateUser(lastUserName: String, newUserName: String): Flow<Result<Boolean>> = channelFlow {
        suspend fun updateUserTable() {
            Log.d("MyTag", "Update USERS")
            myRefUsers.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var mapResult = snapshot.getValue<MutableMap<String, Boolean>>()
                    if (mapResult == null) mapResult = mutableMapOf()
                    mapResult[newUserName] = mapResult[lastUserName]!!
                    mapResult.remove(lastUserName)
                    myRefUsers.setValue(mapResult)
                }

                override fun onCancelled(error: DatabaseError) {
                    launch {
                        send(Result.Error(error.toException()))
                    }
                }
            })
        }

        suspend fun updateRatingTable() {
            Log.d("MyTag", "Update RATING")
            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var mapResult =
                        snapshot.getValue<MutableMap<String, Map<String, PropertyModel.PropertyRatingModel>>>()
                    if (mapResult?.containsKey(lastUserName) == true) {
                        if (mapResult == null) mapResult = mutableMapOf()
                        mapResult!![newUserName] = mapResult!![lastUserName]!!
                        mapResult!!.remove(lastUserName)
                        myRef.setValue(mapResult)
                    } else {
                        launch {
                            send(Result.Success(false))
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    launch {
                        send(Result.Error(error.toException()))
                    }
                }
            })
        }

        updateUserTable()
        updateRatingTable()
        send(Result.Success(true))

        awaitClose()
    }
}