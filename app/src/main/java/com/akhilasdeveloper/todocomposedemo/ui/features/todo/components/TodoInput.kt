package com.akhilasdeveloper.todocomposedemo.ui.features.todo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TodoInput(
    modifier: Modifier = Modifier,
    title: String,
    value: String = "",
    onOk: (String) -> Unit,
    onCancel: () -> Unit
) {

    var text by remember(value) { mutableStateOf(value) }

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background,
                RoundedCornerShape(12.dp)
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text, onValueChange = {
                text = it
            })

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    onCancel()
            }) {
                Text("Cancel")
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    onOk(text)
                }) {
                Text("OK")
            }
        }
    }
}

@Preview(
    showBackground = true, device = Devices.PHONE
)
@Composable
fun TodoPreview() {
    TodoInput(
        modifier = Modifier.fillMaxWidth(),
        title = "Todo Input",
        onOk = {

        }, onCancel = {

        })
}