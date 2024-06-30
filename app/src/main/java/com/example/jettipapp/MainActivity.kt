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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.jettipapp.utils.calculateTotalBillPerPerson
import com.example.jettipapp.utils.calculateTotalTip
import com.example.jettipapp.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CreateJetTipApp { innerPadding ->
                val totalBillPerPerson = remember {
                    mutableFloatStateOf(0f)
                }
                TopHeader(totalBillPerPerson,innerPadding = innerPadding)
                MainContent(totalBillPerPerson)
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
fun TopHeader(totalBillPerPerson: MutableFloatState = mutableFloatStateOf(0f), innerPadding: PaddingValues = PaddingValues()) {
    val total = "%.2f".format(totalBillPerPerson.floatValue)
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
fun MainContent(totalBillPerPerson: MutableFloatState = mutableFloatStateOf(0f)) {
    BillForm(totalBillPerPerson) { billAmt ->
        Log.d("AMT", "MainContent: $billAmt")
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(
    totalBillPerPerson: MutableFloatState,
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

    val tipAmountState = remember {
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
            if (validState) {
                SplitAndTipOptions(totalBillState, tipAmountState, sliderPositionState,totalBillPerPerson)
            }
            else {
                totalBillPerPerson.floatValue = 0f
            }
        }
    }

}

@Composable
fun SplitAndTipOptions(
    totalBillState: MutableState<String>,
    tipAmountState: MutableFloatState,
    sliderPositionState: MutableFloatState,
    totalBillPerPerson: MutableFloatState
) {
    val splitByState = remember {
        mutableIntStateOf(1)
    }
//    Split Row
    Row(
        modifier = Modifier.padding(bottom = 10.dp, start = 18.dp, end = 18.dp),
        horizontalArrangement = Arrangement.Start
    ) {

        val validBillState = remember(totalBillState.value.trim()) {
            totalBillState.value.trim().isNotEmpty() and
                    !(totalBillState.value.trim().contains(' ')
                            or totalBillState.value.trim().contains(',')
                            or totalBillState.value.trim().contains('-'))
        }

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
                onClick = {
                    if (splitByState.intValue > 1) splitByState.intValue -= 1
                    totalBillPerPerson.floatValue = calculateTotalBillPerPerson(
                        totalBill = totalBillState.value.toFloat(),
                        splitBy = splitByState.intValue,
                        tipRatio = sliderPositionState.floatValue
                    )
                }
            )
            Text(
                text = "${splitByState.intValue}",
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(start = 9.dp, end = 9.dp)
            )
            RoundIconButton(
                imageVector = Icons.Default.Add,
                onClick = {
                    if (splitByState.intValue < 100) splitByState.intValue += 1
                    totalBillPerPerson.floatValue = calculateTotalBillPerPerson(
                        totalBill = totalBillState.value.toFloat(),
                        splitBy = splitByState.intValue,
                        tipRatio = sliderPositionState.floatValue
                    )
                }
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
        Text(text = "$${"%.2f".format(tipAmountState.floatValue).toFloat()}")
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
            text = "${("%.2f".format(sliderPositionState.floatValue).toFloat() * 100).toInt()}%",
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Slider(
            value = sliderPositionState.floatValue,
            steps = 100,
            onValueChange = { newValue ->
                sliderPositionState.floatValue = newValue
                Log.d("TipRatio",newValue.toString())
                tipAmountState.floatValue = calculateTotalTip(
                    tipRatio = sliderPositionState.floatValue,
                    totalBill = totalBillState.value.toFloat()
                )
                totalBillPerPerson.floatValue = calculateTotalBillPerPerson(
                    totalBill = totalBillState.value.toFloat(),
                    splitBy = splitByState.intValue,
                    tipRatio = sliderPositionState.floatValue
                )
            }
        )
    }
}

