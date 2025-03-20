package com.example.mvvmapp.features.favoriteproducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.labworkmanager.Product
import com.example.mvvmapp.data.repository.ProductRepositoryImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteVM(private val repo: ProductRepositoryImp) : ViewModel() {
    private val _mutableProducts = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _mutableProducts

    private val _mutableMsg: MutableStateFlow<String> = MutableStateFlow("")
    val msg: StateFlow<String> = _mutableMsg

    fun getProducts() =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.getProducts(false)
                    .collect { list ->
                        _mutableProducts.value = list ?: emptyList()
                    }
            } catch (e: Exception) {
                _mutableMsg.value = e.message ?: "Error"
            }
        }

    fun removeProduct(p: Product) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repo.deleteProduct(p)
            getProducts()
            _mutableMsg.value = "Deleted Successfully"
        } catch (e: Exception) {
            _mutableMsg.value = "Failed To Delete"
        }
    }
}


class FavoriteVMFactory(private val repo: ProductRepositoryImp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteVM(repo) as T
    }
}

