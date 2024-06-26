package com.example.jettipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettipapp.ui.theme.JetTipAppTheme
import com.example.jettipapp.ui.theme.poppinsFontFamily

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CreateJetTipApp { innerPadding ->
                TopHeader(innerPadding = innerPadding)
                MainContent()
            }
        }
    }
}

@Composable
fun CreateJetTipApp(content: @Composable (innerPadding: PaddingValues) -> Unit) {
    JetTipAppTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}

@Preview
@Composable
fun TopHeader(totalPerSon: Double = 0.0, innerPadding: PaddingValues = PaddingValues()) {
    val total = "%.2f".format(totalPerSon)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(innerPadding)
            .clip(shape = RoundedCornerShape(24.dp)),
        color = Color(0xFFE9D7F7)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Per Person",
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 23.sp
            )
            Text(
                text = "$$total",
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp
            )
        }
    }
}

@Preview
@Composable
fun MainContent() {
    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column {

        }
    }
}