import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

data class Dog(
    val imageResourceId: Painter,
    val name: String,
    val age: Int,
    val hobbies: String
)



