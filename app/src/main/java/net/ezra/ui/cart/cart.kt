package net.ezra.ui.cart



import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShoppingCartWithLikes() {
    val likedItems = remember { mutableStateListOf<String>() }
    val cartItems = remember { mutableStateListOf<String>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping Cart", color = Color.Black) },
                backgroundColor = Color.White
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            ItemList(likedItems, addToCart = { item -> cartItems.add(item) })
            Spacer(modifier = Modifier.height(16.dp))
            CartItemList(cartItems)
        }
    }
}

@Composable
fun ItemList(likedItems: MutableList<String>, addToCart: (String) -> Unit) {
    val items = listOf("Item 1", "Item 2", "Item 3")

    LazyColumn {
        items(items) { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(text = item, style = MaterialTheme.typography.body1, modifier = Modifier.weight(1f))
                IconButton(onClick = { likedItems.add(item) }) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Add to favorites"
                    )
                }
                IconButton(onClick = { addToCart(item) }) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = ("Add to cart")
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemList(cartItems: List<String>) {
    LazyColumn {
        items(cartItems) { item ->
            Text(text = item, style = MaterialTheme.typography.body1)
            Divider()
        }
    }
}
