package com.example.mvvmapp.features.allproducts

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
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class AllProductsVM(private val repo: ProductRepositoryImp) : ViewModel() {
    private val _mutableProducts: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())
    val products: StateFlow<List<Product>> = _mutableProducts

    private val _mutableMsg: MutableStateFlow<String> = MutableStateFlow("")
    val msg: StateFlow<String> = _mutableMsg

    fun getProducts() = viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.getProducts(true).collect{
                    list ->
                    _mutableProducts.value = list ?: emptyList()
                }
            } catch (e: Exception) {
                _mutableMsg.value = e.message ?: "Error"
            }
        }


    fun addToFavorite(p: Product) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repo.addProduct(p)
            _mutableMsg.value = "Added Successfully"
        } catch (e: Exception) {
            _mutableMsg.value = "Failed To Add"
        }
    }

}

class AllProductsVMFactory(private val repo: ProductRepositoryImp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AllProductsVM(repo) as T
    }
}


