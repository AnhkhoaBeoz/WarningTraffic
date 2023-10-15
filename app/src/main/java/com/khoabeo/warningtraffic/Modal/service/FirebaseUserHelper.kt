package com.khoabeo.warningtraffic.Modal.service

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.khoabeo.warningtraffic.HomeActivity
import com.khoabeo.warningtraffic.Modal.entitis.User
import com.khoabeo.warningtraffic.MyProgressDialog
import java.util.Date
import java.util.concurrent.TimeUnit

class FirebaseUserHelper {
    private var auth: FirebaseAuth = Firebase.auth
    private var databaseReference: DatabaseReference = Firebase.database.getReference("Users")
    private lateinit var progessDialog: MyProgressDialog
    private var storedVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    private val TAG = "111"

    fun getStoredVerificationId(): String? {
        return storedVerificationId
    }


    fun getResendToken(): PhoneAuthProvider.ForceResendingToken? {
        return resendToken
    }

    fun getCurrentUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }


    fun createUser(email: String, password: String, context: Context) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val newUser: User = User(
                        user?.uid.toString(), null, null, email, password, null, null, null,
                        Date(), null
                    )
                    databaseReference.child(newUser.id.toString()).setValue(newUser)
                    startHomeActivity(context)
                } else {
                    handleAuthFailure(task.exception, context)
                }
            }
    }


    fun restPassword(email: String, context: Context) {
        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Email Sent.", Toast.LENGTH_SHORT).show()
                }

            }
    }

    fun getDataOnce(uuid: String) {
        databaseReference.child("Users").child(uuid).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }

    fun updateProfileUser(FName: String, LName: String, Phone: String) {
        val user = getCurrentUser()
        user?.let { currentUser ->
            val userId = currentUser.uid
            val updateData = HashMap<String, Any>()
            updateData["/$userId/edtFName"] = FName
            updateData["/$userId/edtLName"] = LName
            updateData["/$userId/edtPhone"] = Phone
            updateData["/$userId/pointReward"] = 0.0
            updateData["/$userId/updatedAt"] = Date()

            databaseReference.updateChildren(updateData)
        }
    }

    fun signInWithEmail(email: String, password: String, context: Context) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startHomeActivity(context)
                } else {
                    handleAuthFailure(task.exception, context)
                }
            }
    }

    fun sigOutUser() {
        auth.signOut()
    }

    private fun startHomeActivity(context: Context) {
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
        (context as Activity).finish()
    }

    private fun handleAuthFailure(exception: Exception?, context: Context) {
        Toast.makeText(
            context as Activity,
            "Authentication failed: ${exception?.message}",
            Toast.LENGTH_SHORT
        ).show()
    }
}
