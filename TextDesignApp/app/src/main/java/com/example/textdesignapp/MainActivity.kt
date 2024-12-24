package com.example.textdesignapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toOffset
import com.example.textdesignapp.ui.theme.TextDesignAppTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            TextDesignAppTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = 16.dp,                // Padding from the status bar
                            bottom = 16.dp               // Padding from the bottom system UI
                        )
                ) {
                    MovableButtonApp()
                }
            }
        }
    }
}
@Composable
fun FontDropdown() {
    var expanded by remember { mutableStateOf(false) }
    val fontOptions = listOf("Arial", "Roboto", "Times New Roman")
    var selectedFont by remember { mutableStateOf("Font") }

    Card(
        modifier = Modifier.padding(horizontal = 8.dp).width(150.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row (modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp), horizontalArrangement = Arrangement.SpaceAround){
                Text(text = selectedFont, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.arrowdown),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp).clickable {
                        expanded=true
                    }
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                fontOptions.forEach { font ->
                    DropdownMenuItem(
                        text = {
                            Text(text = font)
                        },
                        onClick = {
                            selectedFont = font
                            expanded = false
                        }
                    )
                }
            }

    }
}
@Composable
fun MovableButtonApp() {
    var tsize by remember { mutableIntStateOf(40) }
    var bold by remember { mutableStateOf(false) }
    var italic by remember { mutableStateOf(false) }
    var underline by remember { mutableStateOf(false) }
    var composableCount by remember { mutableStateOf(1) }
    val maxComposables = 5
    var positions by remember { mutableStateOf(List(composableCount) { Offset(50f, 50f) }) }
    var undoarr = remember { mutableStateListOf<String>() }
    var redoarr = remember { mutableStateListOf<String>() }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.turnback),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp).clickable {
                        if(undoarr.size>0) {
                            if (undoarr[undoarr.size - 1] == "bplus") {
                                tsize--
                            } else if (undoarr[undoarr.size - 1] == "bminus") {
                                tsize++
                            } else if (undoarr[undoarr.size - 1] == "bold") {
                                bold = !bold
                            } else if (undoarr[undoarr.size - 1] == "italic") {
                                italic = !italic
                            } else if (undoarr[undoarr.size - 1] == "underline") {
                                underline = !underline
                            }
                            redoarr.add(undoarr[undoarr.size - 1])
                        }
                        if(undoarr.size>0)undoarr.removeAt(undoarr.size-1)
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Undo", style = MaterialTheme.typography.bodyMedium)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.forward),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                        .clickable {
                            if(redoarr.size>0) {
                                if (redoarr[redoarr.size - 1] == "bplus") {
                                    tsize--
                                } else if (redoarr[redoarr.size - 1] == "bminus") {
                                    tsize++
                                } else if (redoarr[redoarr.size - 1] == "bold") {
                                    bold = !bold
                                } else if (redoarr[redoarr.size - 1] == "italic") {
                                    italic = !italic
                                } else if (redoarr[redoarr.size - 1] == "underline") {
                                    underline = !underline
                                }
                                undoarr.add(redoarr[redoarr.size - 1])
                            }
                            if(redoarr.size>0)redoarr.removeAt(redoarr.size-1)
                        }
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Redo", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Box(
            modifier = Modifier
                .weight(8f)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.Gray)
        ) {
            repeat(composableCount) { index ->
                Button(
                    onClick = {},
                    modifier = Modifier
                        .offset(
                            x = with(LocalDensity.current) { positions[index].x.toDp() },
                            y = with(LocalDensity.current) { positions[index].y.toDp() }
                        )
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                positions = positions.toMutableList().apply {
                                    this[index] = Offset(
                                        x = (this[index].x + dragAmount.x).coerceIn(0f, 800f),
                                        y = (this[index].y + dragAmount.y).coerceIn(0f, 800f)
                                    )
                                }
                            }
                        }
                    ,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(
                        text = "Move Me $index",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = tsize.sp,
                            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
                            fontStyle = if (italic) FontStyle.Italic else FontStyle.Normal,
                            textDecoration = if (underline) TextDecoration.Underline else TextDecoration.None
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Row(modifier = Modifier.weight(3f)) {
                FontDropdown()
            }

            Card(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .width(100.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Image(
                        painter = painterResource(R.drawable.minussign),
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { tsize -= if (tsize > 0) 1 else 0
                                undoarr.add("bminus")},
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = tsize.toString(), style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.width(5.dp))
                    Image(
                        painter = painterResource(R.drawable.add),
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { tsize += 1

                                    undoarr.add("bplus")
                                }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .weight(3f),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = painterResource(R.drawable.letterb),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            bold = !bold
                            undoarr.add("bold")
                        }
                )
                Image(
                    painter = painterResource(R.drawable.italicfont),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            italic = !italic
                            undoarr.add("italic")
                        }
                )
                Image(
                    painter = painterResource(R.drawable.text),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)

                )
                Image(
                    painter = painterResource(R.drawable.underline),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { underline = !underline
                            undoarr.add("underline")
                         }
                )
            }
        }
        Button(
            onClick = {
                if (composableCount < maxComposables) {
                    composableCount++
                    positions = positions.toMutableList().apply {
                        add(
                            Offset(
                                x = Random.nextFloat() * (500f - 50f) + 50f,
                                y = Random.nextFloat() * (500f - 50f) + 50f
                            )

                        )
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("Add Text")
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MovableButtonPreview() {
    TextDesignAppTheme {
        MovableButtonApp()
    }
}
