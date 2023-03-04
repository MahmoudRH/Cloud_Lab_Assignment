package com.mahmoudhabib.firstfirebaseassignment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AddNewUserScreen(onSaveButtonCLick: (user:User) -> Unit) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize().background(Color(0xFFF2EDFA)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Add New User",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("First name") },
                value = firstName,
                onValueChange = { firstName = it })
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Last name") },
                value = lastName,
                onValueChange = { lastName = it })
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Age") },
                value = age,
                onValueChange = { age = it })
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { onSaveButtonCLick(User("",firstName, lastName, age.toIntOrNull() ?: 0)) },
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "SAVE")
        }
    }
}