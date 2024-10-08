package com.example.meowieskotlin.design

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium

@Composable
fun styledCheckBox(checked: MutableState<Boolean>) {
    Checkbox(
        checked = checked.value,
        onCheckedChange = { checked.value = it },
        colors = CheckboxColors(
            checkedCheckmarkColor = fontDark,
            uncheckedCheckmarkColor = fontDark,
            checkedBoxColor = Color.White,
            uncheckedBoxColor = Color.Transparent,
            disabledCheckedBoxColor = fontLight,
            disabledUncheckedBoxColor = fontLight,
            disabledIndeterminateBoxColor = fontLight,
            checkedBorderColor = fontDark,
            uncheckedBorderColor = fontDark,
            disabledBorderColor = fontLight,
            disabledUncheckedBorderColor = fontLight,
            disabledIndeterminateBorderColor = fontLight
        ),
        modifier = Modifier.height(25.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun styledDatePicker(datePickerState: DatePickerState, modifier: Modifier){
    DatePicker(
        modifier = modifier,
        state = datePickerState,
        showModeToggle = false,
        title = null,
        headline = null,
        colors = DatePickerColors(
            containerColor = fontLight,
            titleContentColor = fontMedium,
            headlineContentColor = fontDark,
            weekdayContentColor = fontDark,
            subheadContentColor = fontMedium,
            navigationContentColor = fontMedium,
            yearContentColor = fontDark,
            disabledYearContentColor = fontDark,
            currentYearContentColor = fontDark,
            selectedYearContentColor = fontLight,
            disabledSelectedYearContentColor = fontMedium,
            selectedYearContainerColor = fontDark,
            disabledSelectedYearContainerColor = fontDark,
            dayContentColor = fontDark,
            disabledDayContentColor = fontMedium,
            selectedDayContentColor = fontLight,
            disabledSelectedDayContentColor = fontLight,
            selectedDayContainerColor = fontDark,
            disabledSelectedDayContainerColor = fontDark,
            todayContentColor = fontDark,
            todayDateBorderColor = fontDark,
            dayInSelectionRangeContainerColor = fontDark,
            dayInSelectionRangeContentColor = fontLight,
            dividerColor = fontMedium,
            dateTextFieldColors = TextFieldDefaults.colors(
                focusedTextColor = fontDark,
                unfocusedTextColor = fontDark
            )
        )
    )
}

@Composable
fun styledTextField(value: MutableState<String>, hint: String, focusManager: FocusManager, image: Int, keyboardType: KeyboardType) {
    OutlinedTextField(
        value = value.value,
        onValueChange = { value.value = it },
        shape = RoundedCornerShape(20.dp),
        label = {
            Text(
                text = hint
            )
        },

        singleLine = true,

        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = keyboardType),

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
            Image (
                painter = painterResource(id = image),
                contentDescription = hint,
                modifier = Modifier.height(20.dp)
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}
