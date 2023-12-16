package com.serj.recommend.android.ui.screens.authentication.signUp

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.R
import com.serj.recommend.android.common.ext.basicButton
import com.serj.recommend.android.common.ext.fieldModifier
import com.serj.recommend.android.ui.screens.authentication.signIn.BasicButton
import com.serj.recommend.android.ui.screens.authentication.signIn.EmailField
import com.serj.recommend.android.ui.screens.authentication.signIn.PasswordField

@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = { viewModel.onSignUpClick(openAndPopUp) }
    )
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit
) {
    val fieldModifier = Modifier.fieldModifier()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(text = "Sign Up", modifier = Modifier.padding(30.dp))

        EmailField(uiState.email, onEmailChange, fieldModifier)

        PasswordField(uiState.password, onPasswordChange, fieldModifier)

        RepeatPasswordField(uiState.repeatPassword, onRepeatPasswordChange, fieldModifier)

        BasicButton(R.string.create_account, Modifier.basicButton()) {
            onSignUpClick()
        }
    }
}

@Composable
fun Title(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        color = Color.Black,
        fontSize = 26.sp,
        maxLines = 1,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun RepeatPasswordField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PasswordField(value, R.string.repeat_password, onNewValue, modifier)
}

@Composable
private fun PasswordField(
    value: String,
    @StringRes placeholder: Int,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    val icon =
        if (isVisible)
            painterResource(R.drawable.ic_visibility_on)
        else
            painterResource(R.drawable.ic_visibility_off)

    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(text = stringResource(placeholder)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock") },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(painter = icon, contentDescription = "Visibility")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation
    )
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    val uiState = SignUpUiState(
        email = "email@test.com"
    )

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = { },
        onPasswordChange = { },
        onRepeatPasswordChange = { },
        onSignUpClick = { }
    )
}