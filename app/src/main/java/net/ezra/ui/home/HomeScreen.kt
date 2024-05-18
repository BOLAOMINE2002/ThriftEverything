package net.ezra.ui.home



import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.google.api.Context
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import hilt_aggregated_deps._dagger_hilt_android_internal_modules_ApplicationContextModule
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import net.ezra.R
import net.ezra.navigation.ROUTE_ADD_STUDENTS
import net.ezra.navigation.ROUTE_DASHBOARD
import net.ezra.navigation.ROUTE_HOME
import net.ezra.navigation.ROUTE_LOGIN
import net.ezra.navigation.ROUTE_SEARCH
import net.ezra.navigation.ROUTE_VIEW_PRODUCTS
import net.ezra.navigation.ROUTE_VIEW_STUDENTS
import net.ezra.navigation.ROUTE_VIEW_PRODUCTS
import net.ezra.navigation.ROUTE_ADD_PRODUCT
import net.ezra.navigation.ROUTE_CART
import net.ezra.navigation.ROUTE_CONTACT
import net.ezra.navigation.ROUTE_DETAILS
import net.ezra.navigation.ROUTE_MAIN
import net.ezra.navigation.ROUTE_UPDATE_PRODUCTS
import net.ezra.ui.SplashScreen
import net.ezra.ui.main.ImageSlideshow
import net.ezra.ui.products.Product
import net.ezra.ui.products.fetchProducts
import net.ezra.ui.students.YourDataClass



data class Product(
    var id: String = "",
    val name: String = "",
    val description: String ="",
    val price: Double = 0.0,
    var imageUrl: String = ""
)













data class Screen(val title: String, val icon: Int)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    var isLoading by remember { mutableStateOf(true) }
    var productList by remember { mutableStateOf(emptyList<Product>()) }
    var displayedProductCount by remember { mutableStateOf(8) }
    var progress by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        fetchProducts { fetchedProducts ->
            productList = fetchedProducts
            isLoading = false
        }
    }



    var isDrawerOpen by remember { mutableStateOf(false) }

    val callLauncher: ManagedActivityResultLauncher<Intent, ActivityResult> =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { _ ->

        }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {

                    Text(text = " EverythingThrift",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                    )



                   // Text(text = stringResource(id = R.string.apen))
                },
                navigationIcon = @Composable {
                    if (!isDrawerOpen) {
                        IconButton(onClick = { isDrawerOpen = true }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                                )
                        }
                    }
                },

                actions = {
                    IconButton(onClick = {
                        navController.navigate(ROUTE_LOGIN) {
                            popUpTo(ROUTE_HOME) { inclusive = true }
                        }

                    }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,

                )

            )
        },

        content = @Composable {

            Box(

                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        if (isDrawerOpen) {
                            isDrawerOpen = false
                        }
                    }
            ) {


                Spacer(modifier = Modifier.height(20.dp))




        Spacer(modifier = Modifier.width(285.dp))




                LazyColumn {

                    item {

Spacer(modifier = Modifier.height(100.dp))
                        Column(Modifier.fillMaxSize()) {



                            val images = listOf(

                                R.drawable.sw,
                                R.drawable.sw1,
                                R.drawable.s1,
                                R.drawable.s2,
                                R.drawable.s3,

                                R.drawable.s4,
                                R.drawable.p1,
                                R.drawable.p2,
                                R.drawable.p3,
                                R.drawable.p4,


                                R.drawable.p5,
                                R.drawable.w1,
                                R.drawable.w2,
                                R.drawable.w3,
                                R.drawable.w4,


                                R.drawable.w5,
                                R.drawable.cad1,
                                R.drawable.cad2,
                                R.drawable.hoodie1,
                                R.drawable.hod2,


                                R.drawable.hod3,
                                R.drawable.w4,
                                R.drawable.sw1,
                                R.drawable.f1,
                                R.drawable.f2,

                                )

//
//                            ImageSlideshow(imageList = images, modifier = Modifier
//                              //  .size(300.dp)
//                                .fillMaxSize(),)


                            //Image(
                                //painter = painterResource(id = R.drawable.bolin),
                               // contentDescription = "",
                               // Modifier.fillMaxSize(),
                               // contentScale = ContentScale.Crop

                           // )
                        }
                        Spacer(modifier = Modifier.height(15.dp))


                        Row {
                            Row {


                                androidx.compose.material.IconButton(onClick = {
                                    navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                        popUpTo(ROUTE_HOME) { inclusive = true }
                                    }
                                }) {
                                    Text(
                                        text = "All Products",
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Cursive,
                                        fontSize = (20.sp)
                                    )
                                }




                                Spacer(modifier = Modifier.width(170.dp))




                                androidx.compose.material.IconButton(onClick = {
                                    navController.navigate(ROUTE_MAIN) {
                                        popUpTo(ROUTE_HOME) { inclusive = true }
                                    }
                                }) {
                                    Text(
                                        text = "About Us",
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Cursive,
                                        fontSize = (20.sp)
                                    )
                                }




                                Spacer(modifier = Modifier.width(50.dp))


                            }


                        }

                        Spacer(modifier = Modifier.width(50.dp))


                    }

                    item {




                        LazyRow {
                            item {







                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {



                                        val images = listOf(

                                            R.drawable.hod2,
                                            R.drawable.hoodie1 ,
                                            R.drawable.v3,

                                            )


                                            ImageSlideshow(imageList = images, modifier = Modifier
                                                //  .size(300.dp)
                                                .fillMaxSize(),)






                                    }
                                )
                                Spacer(modifier = Modifier.width(5.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    //shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//

                                        val images = listOf(
                                            R.drawable.sw,
                                            R.drawable.sw1,
                                            R.drawable.w1,
                                            R.drawable.w2 ,
                                            R.drawable.w3,

                                            )


                                        ImageSlideshow(imageList = images, modifier = Modifier
                                            //  .size(300.dp)
                                            .fillMaxSize(),)


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {
//


                                        val images = listOf(
                                            R.drawable.sw,
                                            R.drawable.sw1,
                                            R.drawable.w1,
                                            R.drawable.w2 ,


                                            )


                                        ImageSlideshow(imageList = images, modifier = Modifier
                                            //  .size(300.dp)
                                            .fillMaxSize(),)


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    //shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

//

                                        val images = listOf(

                                            R.drawable.cad2,
                                            R.drawable.hod3,
                                            R.drawable.hod2,
                                            R.drawable.hoodie1 ,


                                            )


                                        ImageSlideshow(imageList = images, modifier = Modifier
                                            //  .size(300.dp)
                                            .fillMaxSize(),)

//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Trousers",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )
//


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {



                                        val images = listOf(

                                            R.drawable.w2 ,
                                            R.drawable.s1 ,
                                            R.drawable.s2,


                                            )


                                        ImageSlideshow(imageList = images, modifier = Modifier
                                            //  .size(300.dp)
                                            .fillMaxSize(),)

//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "T-Shirts",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        .size(190.dp)
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {


                                        val images = listOf(

                                            R.drawable.f1,
                                            R.drawable.cad1 ,
                                            R.drawable.cad2,


                                            )


                                        ImageSlideshow(imageList = images, modifier = Modifier
                                            //  .size(300.dp)
                                            .fillMaxSize(),)
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Belts",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )
//
//

                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    //  shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {


                                        val images = listOf(

                                            R.drawable.w2 ,
                                            R.drawable.w3,
                                            R.drawable.w4,

                                            )


                                        ImageSlideshow(imageList = images, modifier = Modifier
                                            //  .size(300.dp)
                                            .fillMaxSize(),)

//
//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Hats",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {


                                        val images = listOf(
                                            R.drawable.sw,
                                            R.drawable.sw1,
                                            R.drawable.w1,

                                            )


                                        ImageSlideshow(imageList = images, modifier = Modifier
                                            //  .size(300.dp)
                                            .fillMaxSize(),)
//
//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )

//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Shirts",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))


                            }
                        }




                        Spacer(modifier = Modifier.height(10.dp))


                        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {



                            Text(text = "New",fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,)

                        }



                        LazyRow(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            item {


                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {



                                        val images = listOf(
                                            R.drawable.f1,
                                            R.drawable.hoodie1,
                                            R.drawable.cad1,
                                        )


                                        ImageSlideshow(imageList = images, modifier = Modifier
                                            //  .size(300.dp)
                                            .fillMaxSize(),)

//
//                                        Image(
//                                            painter = painterResource(id = R.drawable.blazer3),
//                                            contentDescription = "",
//                                            modifier = Modifier
//                                                .fillMaxSize(),
//                                            contentScale = ContentScale.Crop
//                                        )

//
//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Flannels",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(1.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    //shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        val images = listOf(
                                            R.drawable.w1,
                                            R.drawable.s1,
                                            R.drawable.p1,
                                        )


                                        ImageSlideshow(imageList = images, modifier = Modifier
                                            //  .size(300.dp)
                                            .fillMaxSize(),
                                           // contentScale = ContentScale.Fit

                                            )

//


//
//                                        Image(
//                                            painter = painterResource(id = R.drawable.blazer1),
//                                            contentDescription = "",
//                                            modifier = Modifier
//                                                .fillMaxSize(),
//                                            contentScale = ContentScale.Crop
//                                        )

//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Blazers",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        val images = listOf(
                                            R.drawable.f1,
                                            R.drawable.hoodie1,
                                            R.drawable.hoodie1,
                                        )


                                        ImageSlideshow(imageList = images, modifier = Modifier
                                            //  .size(300.dp)
                                            .fillMaxSize(),)

//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )

//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Jackets",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    //shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        val images = listOf(
                                            R.drawable.f1,
                                            R.drawable.hoodie1,
                                            R.drawable.cad1,
                                        )


                                        ImageSlideshow(imageList = images, modifier = Modifier
                                            //  .size(300.dp)
                                            .fillMaxSize(),)

//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Trousers",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )
//


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        val images = listOf(
                                            R.drawable.jacjet2,
                                            R.drawable.f1,
                                            R.drawable.sw1,
                                        )


                                        ImageSlideshow(imageList = images, modifier = Modifier
                                            //  .size(300.dp)
                                            .fillMaxSize(),)

//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
////
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "T-Shirts",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        .size(190.dp)
                                        //.fillMaxWidth()
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_HOME) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        val images = listOf(
                                            R.drawable.cad2,
                                            R.drawable.hod2,
                                            R.drawable.p1,
                                        )


                                        ImageSlideshow(imageList = images, modifier = Modifier
                                            //  .size(300.dp)
                                            .fillMaxSize(),)
//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Belts",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )
//
//

                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        .size(190.dp)
                                        //.fillMaxWidth()
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    //  shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        val images = listOf(
                                            R.drawable.f2,
                                            R.drawable.s1,
                                            R.drawable.w5,
                                        )


                                        ImageSlideshow(imageList = images, modifier = Modifier
                                            //  .size(300.dp)
                                            .fillMaxSize(),)
//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Hats",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        val images = listOf(
                                            R.drawable.f1,
                                            R.drawable.p5,
                                            R.drawable.v1,
                                        )


                                        ImageSlideshow(imageList = images, modifier = Modifier
                                            //  .size(300.dp)
                                            .fillMaxSize(),)

//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )

//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Shirts",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))


                            }


                        }



                        Spacer(modifier = Modifier.height(10.dp))


                        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {



                            Text(text = "Recent",fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,)

                        }



                        LazyRow {
                            item {


                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {


                                        Image(
                                            painter = painterResource(id = R.drawable.sw),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        .size(190.dp)
                                        //.fillMaxWidth()
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    //shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.hod2),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )

//

//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Blazers",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.hod2),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )


//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )

//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Jackets",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    //shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.hoodie1),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )


//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
////
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Trousers",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )
//


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {


                                        Image(
                                            painter = painterResource(id = R.drawable.sw),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )

//
//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "T-Shirts",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.s1),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )

//
//
//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
////
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Belts",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )
//
//

                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    //  shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.s2),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )


//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
////
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Hats",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.s3),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )

//
//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )

//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Shirts",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))


                            }


                        }
                        Spacer(modifier = Modifier.height(10.dp))


                        Spacer(modifier = Modifier.height(10.dp))

Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {



    Text(text = "Trending",fontSize = 20.sp,
   fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center,)

 }
//                        Row(modifier = Modifier.fillMaxSize()) {
//
//                            Column(modifier = Modifier.padding(10.dp)) {
//                                Text(text = "Trending", color = Color.Black)
//                            }
//                            Spacer(modifier = Modifier.width(270.dp))
//                            Column {
//
//
//                                Icon(
//                                    Icons.Rounded.ArrowForward,
//                                    contentDescription = stringResource(id = R.string.shopping_cart_content_desc),
//                                    modifier = Modifier.size(35.dp)
//                                )
//                            }
//                        }


                        LazyRow {
                            item {


                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {


                                        Image(
                                            painter = painterResource(id = R.drawable.s4),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )

//
//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Flannels",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        .size(190.dp)
                                        //.fillMaxWidth()
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    //shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.w1),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )

//

//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Blazers",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.w2),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )


//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )

//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Jackets",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    //shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.w3),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )


//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
////
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Trousers",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )
//


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {


                                        Image(
                                            painter = painterResource(id = R.drawable.w3),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )

//
//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "T-Shirts",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.w4),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )

//
//
//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
////
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Belts",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )
//
//

                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    //  shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.w5),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )


//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
////
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Hats",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.p1),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )

//
//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )

//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Shirts",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))


                            }


                        }
                        Spacer(modifier = Modifier.height(10.dp))


                        Spacer(modifier = Modifier.height(10.dp))



                        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {



                            Text(text = "Popular",fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,)

                        }

                        LazyRow {
                            item {


                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {


                                        Image(
                                            painter = painterResource(id = R.drawable.p2),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )

//
//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Flannels",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        .size(190.dp)
                                        //.fillMaxWidth()
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    //shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.p3),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )

//

//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Blazers",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.p4),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )


//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )

//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Jackets",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    //shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.p5),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )


//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
////
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Trousers",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )
//


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {


                                        Image(
                                            painter = painterResource(id = R.drawable.v3),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )

//
//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
//
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "T-Shirts",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.v4),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )

//
//
//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
////
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Belts",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )
//
//

                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    //  shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.v2),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )


//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )
////
//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Hats",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Card(
                                    modifier = Modifier
                                        //.fillMaxSize()
                                        //.fillMaxWidth()
                                        .size(190.dp)
                                        .clip(shape = RectangleShape)
                                        .clickable {
                                            navController.navigate(ROUTE_VIEW_PRODUCTS) {
                                                popUpTo(ROUTE_HOME) { inclusive = true }
                                            }
                                        }
                                        .padding(4.dp),

                                    // shape = RoundedCornerShape(100.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    ),

                                    content = {

                                        Image(
                                            painter = painterResource(id = R.drawable.v1),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )

//
//
//                                    Image(
//                                        painter = painterResource(id = R.drawable.logo),
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .fillMaxSize(),
//                                        contentScale= ContentScale.Crop
//                                    )

//                            Text(
//                                color = Color.Black,
//                                textAlign=TextAlign.Center,
//                                text = "Shirts",
//
//                                style = MaterialTheme.typography.headlineLarge,
//
//                                modifier = Modifier.padding(bottom = 26.dp)
//
//                            )


                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))


                            }


                        }
                        Spacer(modifier = Modifier.height(10.dp))


                    }
                }





        }





        },

        bottomBar = { BottomBar(navController = navController) }

    )








    AnimatedDrawer(
        isOpen = isDrawerOpen,
        onClose = { isDrawerOpen = false }
    )
}

@Composable
fun AnimatedDrawer(isOpen: Boolean, onClose: () -> Unit) {
    val drawerWidth = remember { Animatable(if (isOpen) 250f else 0f) }

    LaunchedEffect(isOpen) {
        drawerWidth.animateTo(if (isOpen) 250f else 0f, animationSpec = tween(durationMillis = 300))
    }

    @Composable
    fun SocialMediaIcon() {

    }
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .width(drawerWidth.value.dp)
            ,
        color=Color.White,
       // color = Color.LightGray,
//        elevation = 16.dp
    ) {






        val mContext = LocalContext.current
       val  navController: NavHostController
        Column {
//            Text(
//                text = "Drawer Item 1"
//
//            )
//            Text(
//                text = "Drawer Item 2"
//            )
//            Text(
//                text = "Drawer Item 3",
//                modifier = Modifier.clickable {  }
//            )




            Spacer(modifier = Modifier.height(16.dp))






            OutlinedButton(
                onClick = {

                    val simToolKitLaunchIntent =
                        mContext.packageManager.getLaunchIntentForPackage("com.android.stk")
                    simToolKitLaunchIntent?.let { mContext.startActivity(it) }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Icon(imageVector = Icons.Default.Send,"",tint = Color.White)
                Text(text = "Mpesa", color = Color.Black)

            }
            Spacer(modifier = Modifier.height(15.dp))

            OutlinedButton(
                onClick = {

                    val cameraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (cameraIntent.resolveActivity(mContext.packageManager)!=null){
                        mContext.startActivity(cameraIntent)
                    }else{
                        println("Camera app is not available")
                    }


                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Icon(imageVector = Icons.Default.AddCircle,"",tint = Color.White)
                Text(text = "Camera", color = Color.Black)

            }
            Spacer(modifier = Modifier.height(15.dp))

            OutlinedButton(
                onClick = {

                    val smsIntent=Intent(Intent.ACTION_SENDTO)
                    smsIntent.data="smsto:0769184414".toUri()
                    smsIntent.putExtra("sms_body","Hello customer,how may we be of your help?")
                    mContext.startActivity(smsIntent)


                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {

                Icon(imageVector = Icons.Default.MailOutline,"",tint = Color.Black)
                //Text(text = "Sms", color = Color.Black)

            }
            Spacer(modifier = Modifier.height(15.dp))

            OutlinedButton(
                onClick = {

                    val callIntent=Intent(Intent.ACTION_DIAL)
                    callIntent.data="tel:0793936644".toUri()
                    mContext.startActivity(callIntent)

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {

                Icon(imageVector = Icons.Default.Call,"",tint = Color.Black)
                //Text(text = "Call", color = Color.Black)

            }
            Spacer(modifier = Modifier.height(15.dp))

            OutlinedButton(
                onClick = {

                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("wilddog1738@gmail.com"))
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "subject")
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Welcome to our business application")
                    mContext.startActivity(shareIntent)



                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {

                Icon(imageVector = Icons.Default.Email,"",tint = Color.Black)
                //Text(text = "email", color = Color.Black)

            }
            Spacer(modifier = Modifier.height(15.dp))

            OutlinedButton(
                onClick = {
                    val shareIntent=Intent(Intent.ACTION_SEND)
                    shareIntent.type="text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out our stock content")
                    mContext.startActivity(Intent.createChooser(shareIntent, "Share"))

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {

                Icon(imageVector = Icons.Default.Share,"",tint = Color.Black)
                //Text(text = "Share", color = Color.Black)

            }
           // Spacer(modifier = Modifier.height(15.dp))
           // Text(text = stringResource(id = R.string.developer))

        }

    }
        }





@Composable
fun BottomBar(navController:NavHostController) {
    val selectedIndex = remember { mutableStateOf(0) }
    BottomNavigation(elevation = 10.dp,
        backgroundColor = Color.Gray

    ) {

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Home,"")
        },
            label = { Text(text = "Home", color = Color.White) },
            selected = (selectedIndex.value == 0),
            onClick = {
                navController.navigate(ROUTE_DASHBOARD) {
                    popUpTo(ROUTE_HOME) { inclusive = true }
                }

            })

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.AddCircle,"")
        },
            label = { Text(text = "Products", color = Color.White) },
            selected = (selectedIndex.value == 1),
            onClick = {
                navController.navigate(ROUTE_ADD_PRODUCT) {
                    popUpTo(ROUTE_HOME) { inclusive = true }
                }

            })




        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Search,"")
        },
            label = { Text(text = "Explore", color = Color.White) },
            selected = (selectedIndex.value == 2),
            onClick = {
                navController.navigate(ROUTE_SEARCH) {
                    popUpTo(ROUTE_HOME) { inclusive = true }
                }

            })



        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Person,"")
        },
            label = { Text(text = "Profile", color = Color.White) },
            selected = (selectedIndex.value == 2),
            onClick = {
                navController.navigate(ROUTE_DASHBOARD) {
                    popUpTo(ROUTE_HOME) { inclusive = true }
                }

            })
    }
}


































