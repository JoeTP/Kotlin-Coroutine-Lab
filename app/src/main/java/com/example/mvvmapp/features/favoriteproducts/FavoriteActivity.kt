package com.example.mvvmapp.features.favoriteproducts

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mvvmapp.ProductRow
import com.example.mvvmapp.data.local.LocalProductDataSourceImpl
import com.example.mvvmapp.data.remote.RemoteProductDataSourceImpl
import com.example.mvvmapp.data.repository.ProductRepositoryImp
import com.example.mvvmapp.db.ProductDataBase
import com.example.mvvmapp.features.SearchBar
import com.example.mvvmapp.network.ProductClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class FavoriteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FavoriteScreen(
                viewModel(
                    factory = FavoriteVMFactory(
                        ProductRepositoryImp.getInstance(
                            RemoteProductDataSourceImpl(ProductClient.productsAPIService),
                            LocalProductDataSourceImpl.getInstance(
                                ProductDataBase.getInstance(this).getProductDao()
                            )
                        )
                    )
                )
            )
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun FavoriteScreen(viewModel: FavoriteVM) {

        val productsState by viewModel.products.collectAsState()

        val msgState by viewModel.msg.collectAsState()

        LaunchedEffect(Unit) { viewModel.getProducts() }

        val snackbarHostState = remember { SnackbarHostState() }

        val scope = rememberCoroutineScope()

        val searchQueryFlow = remember { MutableSharedFlow<String>(replay = 1) }
        var searchQuery by remember { mutableStateOf("") }
        var filteredProducts by remember { mutableStateOf(productsState) }

        LaunchedEffect(productsState) { filteredProducts = productsState }

        LaunchedEffect(searchQueryFlow) {
            searchQueryFlow
                .debounce(300)
                .collectLatest { query ->
                    filteredProducts = productsState?.filter { product ->
                        product.title.contains(query, ignoreCase = true)
                    } ?: emptyList()
                }
        }

        val onValueChanged: (String) -> Unit = { query ->
            searchQuery = query
            scope.launch {
                searchQueryFlow.emit(query)
            }
        }

        Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) },
            modifier = Modifier.padding(20.dp)) {
        Column {
                SearchBar(
                    onValueChanged = onValueChanged,
                    searchQuery = searchQuery
                )
                Spacer(Modifier.height(16.dp))

            if(filteredProducts.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ) {
                    Text("No products found")
                }
            }

            else {
                LazyColumn {
                    items(filteredProducts.size) {
                            index ->
                        val product = filteredProducts[index]
                        ProductRow(product, "Remove") {
                            viewModel.removeProduct(product)
                            scope.launch {
                                msgState.let { message ->
                                    snackbarHostState.showSnackbar(
                                        message = message,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        }
                    }
                }
            }
            }
        }
    }
}

