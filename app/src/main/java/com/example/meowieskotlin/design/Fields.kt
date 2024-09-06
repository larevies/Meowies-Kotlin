package com.example.meowieskotlin.design

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meowieskotlin.R
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontLight
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun textFieldAligned(text: String, size: Int, color: Color) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            color,
            fontSize = size.sp,
            textAlign = TextAlign.Center
        )
    )
}
@Composable
fun textField(text: String, size: Int, color: Color) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            color,
            fontSize = size.sp
        )
    )
}

@Composable
fun textFieldOneIcon(text: String, value: MutableState<String>, hint: String, focusManager: FocusManager, image: Int, keyboardType: KeyboardType) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            fontLight,
            fontSize = 23.sp
        )
    )
    Spacer(modifier = Modifier.padding(10.dp))
    styledTextField(value = value, hint = hint, focusManager = focusManager, image = image, keyboardType = keyboardType)
}

@Composable
fun passwordField(value: MutableState<String>, isVisible: MutableState<Boolean>, text: String, focusManager: FocusManager) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = {
            Text(
                text = text,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
        },
        value = value.value,
        onValueChange = {
            value.value = it
        },

        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),

        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = fontDark,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            cursorColor = fontLight,
            focusedBorderColor = fontLight,
            unfocusedBorderColor = fontLight,
            focusedLabelColor = fontDark,
            unfocusedLabelColor = fontDark
        ),

        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.key),
                contentDescription = text,
                modifier = Modifier.size(20.dp)
            )
        },

        trailingIcon = {
            val icon = if (isVisible.value) {
                R.drawable.witness
            } else {
                R.drawable.visible
            }

            val description = if (isVisible.value) {
                "Visibility On"
            } else {
                "Visibility Off"
            }

            IconButton(onClick = { isVisible.value = !isVisible.value }) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = description,
                    modifier = Modifier.height(20.dp)
                )
            }
        },

        visualTransformation =
        if (isVisible.value) VisualTransformation.None
        else PasswordVisualTransformation()

    )
}

@Composable
fun dateField(selectedDate: String, showDatePicker: MutableState<Boolean>, focusManager: FocusManager) {

    val interactionSource = remember {
        object : MutableInteractionSource {
            override val interactions = MutableSharedFlow<Interaction>(
                extraBufferCapacity = 16,
                onBufferOverflow = BufferOverflow.DROP_OLDEST,
            )

            override suspend fun emit(interaction: Interaction) {
                if (interaction is PressInteraction.Release) {
                    showDatePicker.value = true
                }

                interactions.emit(interaction)
            }

            override fun tryEmit(interaction: Interaction): Boolean {
                return interactions.tryEmit(interaction)
            }
        }
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = {
            Text(
                text = "Birthday",
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
        },
        interactionSource = interactionSource,
        readOnly = true,
        value = selectedDate,
        trailingIcon = {
            IconButton(
                onClick = {
                    showDatePicker.value = !showDatePicker.value
                }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select Date",
                    tint = fontDark
                )
            }
        },

        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        onValueChange = { },
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = fontDark,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            cursorColor = fontLight,
            focusedBorderColor = fontLight,
            unfocusedBorderColor = fontLight,
            focusedLabelColor = fontDark,
            unfocusedLabelColor = fontDark,
        ),
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.balloon),
                contentDescription = "Birthday",
                modifier = Modifier.size(20.dp)
            )
        }
    )
}
