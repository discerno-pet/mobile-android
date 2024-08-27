package com.mobile.discernopet

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if (auth.currentUser != null) {
            _authState.value = AuthState.Authenticated(auth.currentUser)
        } else {
            _authState.value = AuthState.Unauthenticated
        }
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Email and password are required")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _authState.value = AuthState.Error("Invalid email format")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated(auth.currentUser)
                } else {
                    val errorMessage = when (task.exception) {
                        is FirebaseAuthInvalidCredentialsException -> "Invalid email or password"
                        else -> task.exception?.message ?: "Login failed"
                    }
                    _authState.value = AuthState.Error(errorMessage)
                }
            }
    }

    fun signUp(email: String, password: String, name: String, phone: String) {
        Log.d("AuthViewModel", "signUp called with email: $email, name: $name, phone: $phone")

        if (email.isBlank() || password.isBlank() || name.isBlank() || phone.isBlank()) {
            _authState.value = AuthState.Error("All fields are required")
            Log.e("AuthViewModel", "Validation failed: All fields are required")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _authState.value = AuthState.Error("Invalid email format")
            Log.e("AuthViewModel", "Validation failed: Invalid email format")
            return
        }

        _authState.value = AuthState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("AuthViewModel", "User created successfully")
                    val user = hashMapOf(
                        "name" to name,
                        "phone" to phone,
                        "email" to email
                    )

                    auth.currentUser?.let { firebaseUser ->
                        FirebaseFirestore.getInstance().collection("users")
                            .document(firebaseUser.uid)
                            .set(user)
                            .addOnSuccessListener {
                                _authState.value = AuthState.Authenticated(task.result?.user)
                                Log.d("AuthViewModel", "User info saved successfully in Firestore")
                            }
                            .addOnFailureListener { e ->
                                val errorMessage = e.message ?: "Error saving user info"
                                _authState.value = AuthState.Error(errorMessage)
                                Log.e("AuthViewModel", "Error saving user info: $errorMessage")
                            }
                    } ?: run {
                        _authState.value = AuthState.Error("Unexpected error: User is null")
                        Log.e("AuthViewModel", "Unexpected error: User is null")
                    }
                } else {
                    val errorMessage = when (task.exception) {
                        is FirebaseAuthWeakPasswordException -> "Password should be at least 6 characters"
                        is FirebaseAuthInvalidCredentialsException -> "Invalid email format"
                        is FirebaseAuthUserCollisionException -> "Email already in use"
                        else -> task.exception?.message ?: "Sign up failed"
                    }
                    _authState.value = AuthState.Error(errorMessage)
                    Log.e("AuthViewModel", "Sign up failed: $errorMessage")
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
}

sealed class AuthState {
    object Unauthenticated : AuthState()
    data class Authenticated(val user: FirebaseUser?) : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}