package com.mahmoudhabib.firstfirebaseassignment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun UserItem(user: User, onClickRemoveButton:()->Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()){
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDBCEF2))
                    .padding(16.dp)
            ) {
                Text(text = "First name: ${user.firstName}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Last name: ${user.lastName}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Age: ${user.age}")
            }
            IconButton(onClick = onClickRemoveButton, modifier = Modifier
                .align(Alignment.TopEnd)) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }



    }
}