@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.jettipapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettipapp.components.InputField
import com.example.jettipapp.ui.theme.JetTipAppTheme
import com.example.jettipapp.ui.theme.poppinsFontFamily
import com.example.jettipapp.widgets.RoundIconButton

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                content(innerPadding)
            }
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
    BillForm() { billAmt ->
        Log.d("AMT", "MainContent: $billAmt")
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    onValChange: (String) -> Unit = {}
) {
    val totalBillState = remember {
        mutableStateOf("")
    }

    val validState = remember(totalBillState.value.trim()) {
        totalBillState.value.trim().isNotEmpty() and
                !(totalBillState.value.trim().contains(' ')
                        or totalBillState.value.trim().contains(',')
                        or totalBillState.value.trim().contains('-'))
    }

    val sliderPositionState = remember {
        mutableFloatStateOf(0f)
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            InputField(
                valueState = totalBillState,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    onValChange(totalBillState.value.trim())
                    keyboardController?.hide()
                }
            )
//            if (validState) {
//                SplitAndTipOptions(sliderPositionState) TODO -> But not done yet
//            }
            SplitAndTipOptions(sliderPositionState)
        }
    }

}

@Composable
fun SplitAndTipOptions(sliderPositionState: MutableFloatState) {
//    Split Row
    Row(
        modifier = Modifier.padding(bottom = 10.dp, start = 18.dp, end = 18.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "Split",
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(120.dp))
        Row(
            modifier = Modifier.padding(3.dp),
            horizontalArrangement = Arrangement.End
        ) {
            RoundIconButton(
                imageVector = Icons.Default.Remove,
                onClick = { /*TODO*/ }
            )
            Text(
                text = "2",
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(start = 9.dp, end = 9.dp)
            )
            RoundIconButton(
                imageVector = Icons.Default.Add,
                onClick = { /*TODO*/ }
            )
        }
    }
//    Tip Row
    Row(
        modifier = Modifier
            .padding(bottom = 10.dp, start = 18.dp, end = 18.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(text = "Tip")
        Spacer(Modifier.width(170.dp))
        Text(text = "$33.00")
    }

//    Slider Column
    Column(
        modifier = Modifier
            .padding(18.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${(sliderPositionState.floatValue * 100).toInt()}%",
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Slider(
            value = sliderPositionState.floatValue,
            onValueChange = { newValue ->
                sliderPositionState.floatValue = newValue
            }
        )
    }
}