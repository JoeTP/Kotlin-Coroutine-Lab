package com.example.mvvmapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.labworkmanager.Product

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductRow(product: Product, btnString: String, onClick: () -> Unit) {
    Row {
        Box(modifier = Modifier.size(90.dp)) {
            GlideImage(product.thumbnail, contentDescription = product.title)
        }
        Spacer(Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Text(product.title, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(5.dp))
            Text(product.price.toString())
            Spacer(Modifier.height(5.dp))
            Button(onClick = onClick) {
                Text(btnString)
            }
        }
    }
}