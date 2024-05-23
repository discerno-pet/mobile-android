package com.example.mobilediscernopet

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
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
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        // Configura listeners para os botões
        binding.textView.setOnClickListener { navigateToSignUp() }
        binding.button.setOnClickListener { signInUser() }
    }

    /**
     * Método chamado quando a Activity está prestes a se tornar visível.
     */
    override fun onStart() {
        super.onStart()
        // Se o usuário já estiver logado, navega para a MainActivity
        if (auth.currentUser != null) {
            navigateToMain()
        }
    }

    /**
     * Realiza o login do usuário com email e senha.
     */
    private fun signInUser() {
        val email = binding.emailEt.text.toString()
        val password = binding.passET.text.toString()

        // Valida email e senha
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Email inválido.", Toast.LENGTH_SHORT).show()
            return
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Senha obrigatória.", Toast.LENGTH_SHORT).show()
            return
        }

        // Tenta realizar o login
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    navigateToMain()
                } else {
                    // Exibe mensagem de erro em caso de falha no login
                    Toast.makeText(
                        baseContext, "Falha no login: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    /**
     * Navega para a MainActivity.
     */
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Impede o usuário de voltar para a tela de login após o login
    }

    /**
     * Navega para a SignUpActivity.
     */
    private fun navigateToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    /**
     * Verifica se o email fornecido é válido.
     *
     * @param email Endereço de email a ser verificado.
     * @return True se o email for válido, False caso contrário.
     */
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}