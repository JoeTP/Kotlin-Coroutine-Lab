package com.example.mvvmapp.features

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch


@OptIn(FlowPreview::class)
@Composable
fun SearchBar(searchQuery: String, onValueChanged: (String) -> Unit) {

    TextField(
        value = searchQuery,
        onValueChange = onValueChanged ,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        label = { Text("Search Products") },
        singleLine = true
    )

//    BasicTextField(
//        value = searchQuery,
//        onValueChange = { newQuery ->
//            searchQuery = newQuery
//            coroutineScope.launch {
//                searchQueryFlow.emit(newQuery)
//            }
//        },
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(bottom = 16.dp),
//        decorationBox = { innerTextField ->
//            Surface(
//                modifier = Modifier.fillMaxWidth(),
//                color = MaterialTheme.colorScheme.surface,
//                shadowElevation = 4.dp
//            ) {
//                Box(
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .fillMaxWidth()
//                ) {
//                    if (searchQuery.isEmpty()) {
//                        Text(
//                            text = "Search...",
//                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
//                        )
//                    }
//                    innerTextField()
//                }
//            }
//        }
//    )
}