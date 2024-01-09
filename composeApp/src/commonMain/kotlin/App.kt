import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    var searchText by remember { mutableStateOf("") }

    MaterialTheme {
        Column {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text("Search Dogs") }
            )
            WoofApp(searchText)
        }

    }
}

/*var showContent by remember { mutableStateOf(false) }
var text by remember { mutableStateOf("") }
val greeting = remember { Greeting().greet() }
Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier.padding(top = 100.dp),
        label = { Text("Please enter some value") }

    )
    Button(onClick = { showContent = !showContent }, modifier = Modifier.padding(top = 18.dp)) {
        Text("Click me!")
    }

    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold, color = Color.Magenta,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(text.take(1))
            }
            append(text.drop(1))
        },
        fontSize = 81.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )
}*/

/////////////////////////  Annotated String Builder Code ///////////////////
/*Text(
text = buildAnnotatedString {
    val startIndex = dogName.indexOf(text, ignoreCase = true)
    if (startIndex != -1) {
        append(dogName.substring(0, startIndex))
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                color = Color.Magenta,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(dogName.substring(startIndex, startIndex + text.length))
        }
        append(dogName.substring(startIndex + text.length))
    } else {
        append(dogName)
    }
},
modifier = Modifier.padding(top = 8.dp)
)*/

/////////////////////////  End   ///////////////////

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun WoofApp(text: String) {
    val dogs = listOf(
        Dog(painterResource("koda.jpg"), "Koda", 2, "Eating treats on the terrace"),
        Dog(painterResource("lola.jpg"), "Lola", 16, "Barking at Daddy"),
        Dog(painterResource("frankie.jpg"), "Frankie", 2, "Stealing socks"),
        Dog(painterResource("nox.jpg"), "Nox", 8, "Meeting new animals"),
        Dog(painterResource("faye.jpg"), "Faye", 8, "Digging in the garden"),
        Dog(painterResource("bella.jpg"), "Bella", 14, "Chasing sea foam"),
        Dog(painterResource("moana.jpg"), "Moana", 2, "Bothering her paw-rents"),
        Dog(painterResource("tzeitel.jpg"), "Tzeitel", 7, "Sunbathing"),
        Dog(painterResource("leroy.jpg"), "Leroy", 4, "Sleeping in dangerous places")
    )

    if (text.isEmpty()){
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 400.dp)
        ) {

            items(dogs) {
                DogItem(
                    dog = it,
                    text,
                    modifier = Modifier.padding(8.dp).animateItemPlacement()
                )
            }
        }
    } else {
        LazyColumn() {
            val filteredDogs = dogs.filter {
                it.name.contains(text, ignoreCase = true)
            }

            items(filteredDogs) {
                DogItem(
                    dog = it,
                    text,
                    modifier = Modifier.padding(8.dp).animateItemPlacement()
                )
            }
        }
    }

}

@Composable
fun DogItem(
    dog: Dog,
    text: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (expanded) Color(0xFFc9e7f9)
        else Color(0xFFdbe5dd),
    )
    Card(
        modifier = modifier,
        backgroundColor = color,
        shape = RoundedCornerShape(topEnd = 16.dp, bottomStart = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                DogIcon(dog.imageResourceId, expanded)
                DogInformation(dog.name, dog.age, text)
                Spacer(Modifier.weight(1f))
                DogItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded },
                )
            }
            if (expanded) {
                DogHobby(
                    dog.hobbies, modifier = Modifier.padding(
                        start = 16.dp,
                        top = 8.dp,
                        bottom = 16.dp,
                        end = 8.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun DogItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = "See more or less information about a dog.",
            tint = Color(0xFF4d6357)
        )
    }
}


@Composable
fun DogIcon(
   dogIcon: Painter,
   expanded: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        )
    )
    if (expanded){
        Image(
            modifier = modifier
                .size(64.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(50))
                .graphicsLayer {
                    rotationZ = angle
                },
            contentScale = ContentScale.Crop,
            painter = dogIcon,
            contentDescription = null
        )
    } else {
        Image(
            modifier = modifier
                .size(64.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(50)),
            contentScale = ContentScale.Crop,
            painter = dogIcon,
            contentDescription = null
        )
    }

}

@Composable
fun DogInformation(
    dogName: String,
    dogAge: Int,
    text:String,
    modifier: Modifier = Modifier
) {

    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color(0xFF60DDAD),
        targetValue = Color(0xFF4285F4),
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "color"
    )
    Column(modifier = modifier) {
        if (text.isEmpty()) {
            Text(
                text = dogName,
                modifier = Modifier.padding(top = 8.dp)
            )
        } else {
            BasicText(
                text = dogName,
                color = {animatedColor},
                modifier = Modifier.padding(top = 8.dp),
                style = TextStyle(fontSize = 21.sp, fontWeight = FontWeight.Bold)
            )
        }

        Text(
            text = "$dogAge years old",
        )
    }
}

@Composable
fun DogHobby(
    dogHobby: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "About: ",
        )
        Text(
            text = dogHobby,
        )
    }
}

public val Icons.Filled.ExpandLess: ImageVector
    get() {
        if (_expandLess != null) {
            return _expandLess!!
        }
        _expandLess = materialIcon(name = "Filled.ExpandLess") {
            materialPath {
                moveTo(12.0f, 8.0f)
                lineToRelative(-6.0f, 6.0f)
                lineToRelative(1.41f, 1.41f)
                lineTo(12.0f, 10.83f)
                lineToRelative(4.59f, 4.58f)
                lineTo(18.0f, 14.0f)
                close()
            }
        }
        return _expandLess!!
    }

private var _expandLess: ImageVector? = null

public val Icons.Filled.ExpandMore: ImageVector
    get() {
        if (_expandMore != null) {
            return _expandMore!!
        }
        _expandMore = materialIcon(name = "Filled.ExpandMore") {
            materialPath {
                moveTo(16.59f, 8.59f)
                lineTo(12.0f, 13.17f)
                lineTo(7.41f, 8.59f)
                lineTo(6.0f, 10.0f)
                lineToRelative(6.0f, 6.0f)
                lineToRelative(6.0f, -6.0f)
                close()
            }
        }
        return _expandMore!!
    }

private var _expandMore: ImageVector? = null