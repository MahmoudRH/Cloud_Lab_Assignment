package com.mahmoudhabib.firstfirebaseassignment

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mahmoudhabib.firstfirebaseassignment.ui.theme.FirstFirebaseAssignmentTheme

class MainActivity : ComponentActivity() {
    private val db = Firebase.firestore
    private val usersList = mutableStateListOf<User>()
    var isDialogVisible by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchUsersFromFireStore()
        setContent {
            FirstFirebaseAssignmentTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            isDialogVisible = true
                        }) {
                            Icon(imageVector = Icons.Default.Add, null)
                        }
                    }
                ) {

                    LazyColumn(Modifier.padding(it)) {
                        item {

                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                                text = "All Users:",
                                style = MaterialTheme.typography.h4,
                                fontWeight = FontWeight.Light
                            )
                        }
                        items(usersList) { user ->
                            UserItem(user = user) {
                                deleteUser(user)
                            }
                        }
                    }
                    AnimatedVisibility(
                        visible = isDialogVisible,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        AddNewUserScreen(::addUser)
                    }
                    AnimatedVisibility(
                        visible = usersList.isEmpty(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(text = "Add Some Users!", color = Color.LightGray)
                        }
                    }

                }
            }
        }
    }

    private fun fetchUsersFromFireStore() {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val documentId: String = document.id
                    val firstName: String = document.data["first"].toString()
                    val lastName: String = document.data["last"].toString()
                    val age: Int = document.data["age"].toString().toIntOrNull() ?: 0
                    usersList.add(User(documentId, firstName, lastName, age))
                }
                Log.e("Mah", "getUsers: loaded Successfully, ${usersList.size}")
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Couldn't fetch data. please check your connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun addUser(user: User) {
        if (isValidData(user)) {
            // Create a new user
            val userDocument = hashMapOf(
                "first" to user.firstName,
                "last" to user.lastName,
                "age" to user.age
            )
            // Add a new document to the collection
            db.collection("users")
                .add(userDocument)
                .addOnSuccessListener { _ ->
                    //add it also to usersList
                    usersList.add(user)
                    Toast.makeText(
                        this,
                        "new user added successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error adding document, ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
        } else {
            Toast.makeText(this, "invalid user data", Toast.LENGTH_SHORT).show()
        }
        isDialogVisible = false
    }

    private fun deleteUser(user: User) {
        db.collection("users").document(user.documentId)
            .delete()
            .addOnSuccessListener {
                //also remove the user from the usersList
                usersList.remove(user)
                Toast.makeText(
                    this,
                    "user ${user.firstName} deleted Successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { _ ->
                Toast.makeText(
                    this,
                    "failed to delete user",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun isValidData(user: User): Boolean {
        return user.run { firstName.isNotEmpty() && lastName.isNotEmpty() || age > 0 }
    }
}
