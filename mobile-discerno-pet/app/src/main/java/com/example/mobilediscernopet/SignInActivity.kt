package com.example.mobilediscernopet

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilediscernopet.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Activity responsável por gerenciar o login do usuário.
 */
class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    /**
     * Método chamado quando a Activity é criada.
     * Inicializa a autenticação Firebase, bindings e configura listeners para os botões.
     * @param savedInstanceState Estado salvo da instância anterior da Activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.textView.setOnClickListener { navigateToActivity(SignUpActivity::class.java) }
        binding.button.setOnClickListener { signInUser() }
    }

    /**
     * Método chamado quando a Activity está prestes a se tornar visível.
     * Verifica se o usuário já está logado e, em caso afirmativo, navega para a MainActivity.
     */
    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            navigateToActivity(MainActivity::class.java)
        }
    }

    /**
     * Realiza o login do usuário com email e senha.
     * Obtém o email e senha dos campos de texto, valida as credenciais e, se válidas,
     * tenta autenticar o usuário com o Firebase Authentication.
     * Em caso de sucesso, navega para a MainActivity. Caso contrário, exibe uma mensagem de erro.
     */
    private fun signInUser() {
        val email = binding.emailEt.text.toString()
        val password = binding.passET.text.toString()

        if (areCredentialsValid(email, password)) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        navigateToActivity(MainActivity::class.java)
                    } else {
                        showError("Usuário ou senha incorretos")
                    }
                }
        }
    }

    /**
     * Valida se as credenciais fornecidas (email e senha) são válidas.
     * @param email Endereço de email a ser validado.
     * @param password Senha a ser validada.
     * @return `true` se as credenciais forem válidas, `false` caso contrário.
     */
    private fun areCredentialsValid(email: String, password: String): Boolean {
        when {
            email.isEmpty() && password.isEmpty() -> showError("Email e senha obrigatórios")
            email.isEmpty() -> showError("Email obrigatório")
            password.isEmpty() -> showError("Senha obrigatória")
            !isValidEmail(email) -> showError("Email inválido")
            else -> return true
        }
        return false
    }

    /**
     * Exibe uma mensagem de erro na tela.
     * @param message Mensagem de erro a ser exibida.
     */
    private fun showError(message: String) {
        binding.errorTextView.text = message
        binding.errorTextView.setTextColor(resources.getColor(R.color.red, null))
    }

    /**
     * Navega para a Activity especificada.
     * @param activityClass Classe da Activity de destino.
     */
    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
        finish()
    }

    /**
     * Verifica se o email fornecido é válido usando uma expressão regular.
     * @param email Endereço de email a ser verificado.
     * @return `true` se o email for válido, `false` caso contrário.
     */
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}