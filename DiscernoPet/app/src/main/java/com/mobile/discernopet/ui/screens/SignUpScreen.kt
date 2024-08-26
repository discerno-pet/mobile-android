package com.mobile.discernopet.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobile.discernopet.AuthViewModel
import com.mobile.discernopet.R


@Composable
fun SignUpPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val buttonWidth = 0.96f
    val distanceBetweenFields = 8.dp

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))

    ) {
        val (name, setName) = rememberSaveable { mutableStateOf("") }
        val (phone, setPhone) = rememberSaveable { mutableStateOf("") }
        val (email, setEmail) = rememberSaveable { mutableStateOf("") }
        val (password, setPassword) = rememberSaveable { mutableStateOf("") }
        val (confirmPassword, setConfirmPassword) = rememberSaveable { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var confirmPasswordVisible by remember { mutableStateOf(false) }

        Image(
            painter = painterResource(id = R.drawable.logo_discerno_pet_escuro),
            contentDescription = "Logo Discerno Pet",
            modifier = Modifier
                .absolutePadding(top = 76.dp)
                .size(180.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .weight(1f)
                .fillMaxWidth()
        )
        Text(
            text = "DISCERNOPET",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 40.sp,
                fontWeight = FontWeight.W700,
                fontFamily = FontFamily.Default,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier
                .padding(vertical = 20.dp)
                .align(Alignment.CenterHorizontally)
        )
        SignUpInputField(
            value = name,
            onValueChange = setName,
            label = "Nome",
            icon = Icons.Default.Person,
            iconDescription = "Nome"
        )
        Spacer(modifier = Modifier.height(distanceBetweenFields))
        SignUpInputField(
            value = phone,
            onValueChange = setPhone,
            label = "Telefone",
            icon = Icons.Default.Phone,
            iconDescription = "Telefone"
        )
        Spacer(modifier = Modifier.height(distanceBetweenFields))
        SignUpInputField(
            value = email,
            onValueChange = setEmail,
            label = "Email",
            icon = Icons.Default.Email,
            iconDescription = "Email"
        )
        Spacer(modifier = Modifier.height(distanceBetweenFields))
        PasswordInputField(
            value = password,
            onValueChange = setPassword,
            label = "Senha",
            passwordVisible = passwordVisible,
            onPasswordToggleClick = { passwordVisible = !passwordVisible }
        )
        Spacer(modifier = Modifier.height(distanceBetweenFields))
        PasswordInputField(
            value = confirmPassword,
            onValueChange = setConfirmPassword,
            label = "Repita a senha",
            passwordVisible = confirmPasswordVisible,
            onPasswordToggleClick = { confirmPasswordVisible = !confirmPasswordVisible }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth(buttonWidth)
                .clip(RoundedCornerShape(0.dp))
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(22.dp)
                ),
            shape = RoundedCornerShape(22.dp),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Cadastrar",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.Default,
                    color = Color.White,
                    letterSpacing = 0.15.sp
                )
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        TextButton(onClick = { }) {
            Text("Já tem Cadastro? Login")
        }
        LegalNotice(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    iconDescription: String
) {
    val inputFieldHeight = 56.dp
    val borderRadius = 14.dp
    val iconSize = 24.dp

    Row(
        modifier = Modifier
            .fillMaxWidth(0.96f)
            .padding(4.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = iconDescription,
                    modifier = Modifier.size(iconSize)
                )
            },
            modifier = Modifier
                .height(inputFieldHeight)
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(borderRadius),
                ),
            shape = RoundedCornerShape(borderRadius),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    passwordVisible: Boolean,
    onPasswordToggleClick: () -> Unit
) {
    val inputFieldHeight = 56.dp
    val borderRadius = 14.dp
    val iconSize = 24.dp
    val blockIconSize = 18.dp

    Row(
        modifier = Modifier
            .fillMaxWidth(0.96f)
            .padding(4.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Ícone de Senha",
                    modifier = Modifier.size(iconSize)
                )
            },
            trailingIcon = {
                IconButton(onClick = onPasswordToggleClick) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Block else Icons.Filled.RemoveRedEye,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        modifier = Modifier.size(if (passwordVisible) blockIconSize else iconSize)
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .height(inputFieldHeight)
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(borderRadius),
                ),
            shape = RoundedCornerShape(borderRadius),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            )
        )
    }
}