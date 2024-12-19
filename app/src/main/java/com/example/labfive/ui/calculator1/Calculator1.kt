package com.example.labfive.ui.calculator1

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color


@Composable
fun Calculator1(
    goBack: () -> Unit
) {
    var wOC by remember { mutableStateOf(0.0) }
    var tWOC by remember { mutableStateOf(0.0) }
    var kAOC by remember { mutableStateOf(0.0) }
    var kPOC by remember { mutableStateOf(0.0) }
    var wDK by remember { mutableStateOf(0.0) }
    var wDC by remember { mutableStateOf("") }
    var kPMax by remember { mutableStateOf(0.0) }

    var powerLineLength by remember { mutableStateOf("") }
    val formattedPowerLineLength = powerLineLength.toDoubleOrNull() ?: 0.0
    var accession by remember { mutableStateOf("") }
    val formattedAccession = accession.toDoubleOrNull() ?: 0.0
    var switchCount by remember { mutableStateOf("") }
    val formattedSwitchCount = switchCount.toDoubleOrNull() ?: 0.0

    var powerLineW by remember { mutableStateOf(0.0) }
    var switchW by remember { mutableStateOf(0.0) }
    var transformerW by remember { mutableStateOf(0.0) }

    var powerLineTV by remember { mutableStateOf(0.0) }
    var switchTV by remember { mutableStateOf(0.0) }
    var transformerTV by remember { mutableStateOf(0.0) }

    var powerLineM by remember { mutableStateOf(0.0) }
    var switchM by remember { mutableStateOf(0.0) }
    var transformerM by remember { mutableStateOf(0.0) }

    var powerLineTP by remember { mutableStateOf(0.0) }
    var switchTP by remember { mutableStateOf(0.0) }
    var transformerTP by remember { mutableStateOf(0.0) }

    val powerLine = listOf(
        "ПЛ-100 кВ",
        "ПЛ-35 кВ",
        "ПЛ-10 кВ",
        "КЛ-10 кВ (траншея)",
        "КЛ-10 кВ (кабельний канал)"
    )
    val switch = listOf("В-110 кВ (елегазовий)", "В-10 кВ (малооливний)", "В-10 кВ (вакуумний)")
    val transformer =
        listOf("Т-110 кВ", "Т-35 кВ", "Т-10кВ (кабельна мережа", "Т-10кВ (повітряна мережа)")

    val powerLineWList = listOf(0.007, 0.02, 0.02, 0.03, 0.005)
    val switchWList = listOf(0.01, 0.02, 0.01)
    val transformerWList = listOf(0.015, 0.02, 0.005, 0.05)

    val powerLineTVList = listOf(10.0, 8.0, 10.0, 44.0, 17.5)
    val switchTVList = listOf(30.0, 15.0, 15.0)
    val transformerTVList = listOf(100.0, 80.0, 60.0, 60.0)

    val powerLineMList = listOf(0.167, 0.167, 0.167, 1.0, 1.0)
    val switchMList = listOf(0.1, 0.33, 0.33)
    val transformerMList = listOf(1.0, 1.0, 0.5, 0.5)

    val powerLineTPList = listOf(35.0, 35.0, 35.0, 9.0, 9.0)
    val switchTPList = listOf(30.0, 15.0, 15.0)
    val transformerTPList = listOf(43.0, 28.0, 10.0, 10.0)


    // Перевірка чи відкритий випадаючий список та який варінт обраний
    var expanded1 by remember { mutableStateOf(false) }
    var selectedIndex1 by remember { mutableStateOf(0) }

    var expanded2 by remember { mutableStateOf(false) }
    var selectedIndex2 by remember { mutableStateOf(0) }

    var expanded3 by remember { mutableStateOf(false) }
    var selectedIndex3 by remember { mutableStateOf(0) }

    fun calculate(): Unit {

        wOC =
            ((powerLineW * formattedPowerLineLength) + switchW + transformerW + (0.02 * formattedSwitchCount)
                    + (accession.toDouble() * 0.03))
        tWOC =
            ((powerLineW * formattedPowerLineLength) * powerLineTV + switchW * switchTV + transformerW *
                    transformerTV + (0.02 * 15.0 * formattedSwitchCount) + formattedAccession * 0.03 * 2.0) / wOC
        kAOC = wOC * tWOC / 8760.0
        kPMax = maxOf(powerLineM * powerLineTP, switchM * switchTP, transformerM * transformerTP)
        kPOC = 1.2 * (kPMax / 8760.0)
        wDK = 2.0 * wOC * (kAOC + kPOC)
        wDC = (wDK + 0.02).toString()
    }

    val sharedModifier = Modifier
        .width(400.dp)
        .wrapContentSize(Alignment.TopStart)
        .padding(10.dp)

    Column() {
        Box(
            modifier = sharedModifier
        ) {
            DropdownMenuComponent(
                items = powerLine,
                expanded = expanded1,
                selectedIndex = selectedIndex1,
                onItemSelected = { index ->
                    selectedIndex1 = index
                    powerLineW = powerLineWList[index]
                    powerLineTV = powerLineTVList[index]
                    powerLineM = powerLineMList[index]
                    powerLineTP = powerLineTPList[index]
                },
                onExpandedChange = { expanded1 = it }
            )
        }
        Box(
            modifier = sharedModifier
        ) {
            TextField(
                value = powerLineLength,
                onValueChange = { powerLineLength = it },
                label = { Text("Довжина ліній електропередач, км") },
                maxLines = 1,
                modifier = sharedModifier
                    .fillMaxWidth()
            )
        }
        Box(
            modifier = sharedModifier
        ) {
            DropdownMenuComponent(
                items = switch,
                expanded = expanded2,
                selectedIndex = selectedIndex2,
                onItemSelected = { index ->
                    selectedIndex2 = index
                    switchW = switchWList[index]
                    switchTV = switchTVList[index]
                    switchM = switchMList[index]
                    switchTP = switchTPList[index]
                },
                onExpandedChange = { expanded2 = it }
            )
        }
        Box(
            modifier = sharedModifier
        ) {
            DropdownMenuComponent(
                items = transformer,
                expanded = expanded3,
                selectedIndex = selectedIndex3,
                onItemSelected = { index ->
                    selectedIndex3 = index
                    transformerW = transformerWList[index]
                    transformerTV = transformerTVList[index]
                    transformerM = transformerMList[index]
                    transformerTP = transformerTPList[index]
                },
                onExpandedChange = { expanded3 = it }
            )
        }
        Box(
            modifier = sharedModifier
        ) {
            TextField(
                value = accession,
                onValueChange = { accession = it },
                label = { Text("Кількість приєднань 10 кВ") },
                maxLines = 1,
                modifier = sharedModifier
                    .fillMaxWidth()

            )
        }
        Box(
            modifier = sharedModifier
        ) {
            TextField(
                value = switchCount,
                onValueChange = { switchCount = it },
                label = { Text("Кількість ввідних вимикачів 10 кВ") },
                maxLines = 1,
                modifier = sharedModifier
                    .fillMaxWidth()
            )
        }
        Box(
            modifier = Modifier.padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { calculate() }
                ) {
                    Text("Calculate")
                }
                Button(
                    onClick = goBack
                ) {
                    Text("Go back")
                }
            }
        }
        if (wDC.isNotEmpty()) {
            Text(
                "Частота відмов одноколової системи: $wOC рік-1 \n" +
                        "Середня тривалість відновлення: $tWOC год. \n" +
                        "Коефіцієнт аварійного простою одноколової системи: $kAOC \n" +
                        "Коефіцієнт планового простою одноколової системи: $kPOC \n" +
                        "Частота відмов одночасно двох кіл двоколової системи: $wDK рік-1 \n" +
                        "Частота відмов двоколової системи з урахуванням секційного вимикача: $wDC рік-1",
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}


// Dropdown Menu для багаторазового використання
@Composable
fun DropdownMenuComponent(
    items: List<String>,
    expanded: Boolean,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    onExpandedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .width(400.dp)
            .wrapContentSize(Alignment.TopStart)
            .padding(16.dp)
    ) {
        Text(
            text = items[selectedIndex],
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onExpandedChange(true) }
                .background(Color(0xFFE6DDFA))
                .padding(16.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier
                .width(300.dp)
                .background(Color(0xFFE6DDFA))
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(index)
                        onExpandedChange(false)
                    },
                    text = { Text(text = item) }
                )
            }
        }
    }
}


