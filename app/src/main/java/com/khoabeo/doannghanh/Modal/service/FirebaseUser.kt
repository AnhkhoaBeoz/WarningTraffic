package com.khoabeo.doannghanh.Modal.service

import com.google.firebase.auth.FirebaseAuth

class FirebaseUser {
   fun createUserWithEmailAndPass(email: String, password: String): Boolean {
        var flag = false
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                    flag = true
                    }
                }
        return flag

    }
}