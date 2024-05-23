package com.example.mobilediscernopet

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilediscernopet.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Classe SignUpActivity que estende a classe AppCompatActivity.
 * Esta classe é responsável por gerenciar a tela de cadastro do usuário.
 */
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    /**
     * Método onCreate chamado quando a atividade é criada.
     * Inicializa o binding, a instância do FirebaseAuth e define os listeners para os elementos da interface.
     *
     * @param savedInstanceState Bundle contendo o estado salvo da atividade, se houver.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        // Define o listener para o textView. Quando clicado, inicia a atividade de login.
        binding.textView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        // Define o listener para o botão de cadastro.
        binding.button.setOnClickListener {
            signUpUser()
        }
    }

    /**
     * Método para cadastrar um novo usuário.
     * Valida os campos de entrada, cria o usuário no Firebase Authentication e envia um email de verificação.
     */
    private fun signUpUser() {
        val email = binding.emailEt.text.toString()
        val password = binding.passET.text.toString()
        val confirmPassword = binding.confirmPassEt.text.toString()

        // Validação dos campos de entrada
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Email inválido.", Toast.LENGTH_SHORT).show()
            return
        }
        if (!isPasswordStrong(password)) {
            Toast.makeText(this, "Senha fraca. Use pelo menos 8 caracteres, " +
                    "uma letra maiúscula, uma minúscula, um número e um símbolo. " +
                    "Não use sequências de números.", Toast.LENGTH_LONG).show()
            return
        }
        if (password != confirmPassword) {
            Toast.makeText(this, "Senhas não conferem.", Toast.LENGTH_SHORT).show()
            return
        }

        // Criação do usuário no Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Cadastro bem-sucedido, envia email de verificação
                    sendVerificationEmail(task.result?.user)
                    // Redireciona para a tela de login
                    startActivity(Intent(this, SignInActivity::class.java))
                    finish()
                } else {
                    // Exibe mensagem de erro
                    Toast.makeText(baseContext, "Falha no cadastro: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    /**
     * Envia um email de verificação para o usuário.
     *
     * @param user Objeto FirebaseUser representando o usuário que receberá o email.
     */
    private fun sendVerificationEmail(user: FirebaseUser?) {
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Email de verificação enviado.",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "Falha ao enviar email de verificação.",
                        Toast.LENGTH_SHORT).show()
                }
            }
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

    /**
     * Verifica se a senha fornecida atende aos critérios de segurança:
     * - Mínimo de 8 caracteres
     * - Pelo menos uma letra maiúscula
     * - Pelo menos uma letra minúscula
     * - Pelo menos um número
     * - Pelo menos um símbolo
     * - Sem sequências de números
     *
     * @param password Senha a ser verificada.
     * @return True se a senha for forte, False caso contrário.
     */
    private fun isPasswordStrong(password: String): Boolean {
        if (password.length < 8) return false
        if (!password.contains(Regex("[A-Z]"))) return false
        if (!password.contains(Regex("[a-z]"))) return false
        if (!password.contains(Regex("[0-9]"))) return false
        if (!password.contains(Regex("[!@#\$%^&*()_\\-+=<>?{}\\[\\]~]"))) return false
        if (password.contains(Regex("012|123|234|345|456|567|678|789|890|901"))) return false
        return true
    }
}