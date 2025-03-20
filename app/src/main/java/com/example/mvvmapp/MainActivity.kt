package com.example.mvvmapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mvvmapp.features.allproducts.AllProductsActivity
import com.example.mvvmapp.features.favoriteproducts.FavoriteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeScreen()
        }
//        CoroutineScope(Dispatchers.IO).launch {
//            testing()
//        }
    }


    @Composable
    fun HomeScreen() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            Button(onClick = {
                startActivity(Intent(this@MainActivity, AllProductsActivity::class.java))
            }) { Text("All Products") }
            Spacer(Modifier.height(12.dp))
            Button(onClick = {
                startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
            }) { Text("Favorite Products") }
        }
    }
}


fun testing() = CoroutineScope(Dispatchers.Default).launch {




        val myFlow = flow {
            for(i in 1..10) {
                Log.i("TAG", "testing: $i")
                emit(i * 2)
                delay(1000)
            }
        }

        myFlow.take(10)
            .collect { value ->
                Log.i("TAG", "testing: $value")
            }
    }