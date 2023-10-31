import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.TileMode
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.time.format.TextStyle
import androidx.compose.ui.graphics.graphicsLayer

fun main() = singleWindowApplication {
    var result by remember { mutableStateOf("") }
    val buttons = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "+", "-", "*", "/", "=")
    val points = remember { mutableStateListOf<Offset>() }

    Box(modifier = Modifier.fillMaxSize().background(Brush.linearGradient(
        colors = listOf(Color(0xFFEB92BE), Color(0xFFFFEF78), Color(0xFF63C9B4)),
        start = Offset(0f, 0f),
        end = Offset(1000f, 1000f),
        tileMode = TileMode.Clamp
    )), contentAlignment = Alignment.Center)

    {
        Box(
            modifier = Modifier
                .wrapContentSize(align = Alignment.TopStart)
                .size(350.dp, 430.dp) // Фиксированный размер
                .background(Color(0xfffaf8f8).copy(alpha = 0.5f), shape = RoundedCornerShape(16.dp)) // Установка альфа-компонента на 0.5 (50% прозрачности)
                .blur(50.dp) // Добавление эффекта размытия
        )


        Column(

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.graphicsLayer {
                this.alpha = 0.5f // Прозрачность от 0.0 до 1.0, где 1.0 - полностью непрозрачный, а 0.0 - полностью прозрачный.
                //this.clip = true // Обрезать содержимое по границам слоя.
                //this.shape = RoundedCornerShape(16.dp) // Форма слоя.


            }
        ) {

            Box(
                modifier = Modifier
                    .wrapContentSize(align = Alignment.TopStart)
                    .size(300.dp, 50.dp) // Фиксированный размер
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
            ) {
                Text(
                    text = if (result.isBlank()) " " else result,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            buttons.chunked(4).forEach { row ->
                Row {
                    row.forEach { button ->
                        Button(onClick = {
                            when (button) {
                                "=" -> result = try {
                                    result.split(" ").let { if (it.size < 3) "" else it[0].toInt().let { first ->
                                        it[2].toInt().let { second ->
                                            when (it[1]) {
                                                "+" -> (first + second).toString()
                                                "-" -> (first - second).toString()
                                                "*" -> (first * second).toString()
                                                "/" -> if (second != 0) (first / second).toString() else "Error"
                                                else -> ""
                                            }
                                        }
                                    } }
                                } catch (e: Exception) {
                                    "Error"
                                }
                                else -> result += if (button in listOf("+", "-", "*", "/")) " $button " else button
                            }
                        }) {
                            Text(text = button)
                        }

                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(onClick = { result = "" }) {
                Text(text = "Clear")
            }
        }
    }
}
