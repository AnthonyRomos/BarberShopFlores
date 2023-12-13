package com.thomy.barbershopflores.core.data.model.model

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class UserDao {
    private val db= FirebaseFirestore.getInstance()
    private val collection= db.collection("users")
    fun addUser(user: UserGoogle?)
    {
        GlobalScope.launch (Dispatchers.IO){
            user?.let {
                collection.document(user.uid).set(it)
            }
        }
    }
    fun getUserById(id:String): Task<DocumentSnapshot>
    {
        return collection.document(id).get()
    }
}