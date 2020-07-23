package com.goga133.oknaservice.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.goga133.oknaservice.R
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.android.synthetic.main.fragment_auth_mail.*
import kotlinx.android.synthetic.main.fragment_auth_mail.view.*

class AuthMailFragment : Fragment() {

    private val mAuth by lazy { FirebaseAuth.getInstance()}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_auth_mail, container, false)

        root.button_continue.setOnClickListener{
            val email = root.email_textInput.text?.toString()
            val password = root.password_textInput.text?.toString()
            when {
                email.isNullOrEmpty() -> {
                    showError("Ошибка. Заполните, пожалуйста, поле для ввода почты!")
                }
                password.isNullOrEmpty() -> {
                    showError("Ошибка. Заполните, пожалуста, поле для ввода пароля!")
                }
                else -> {
                    setLoading(true)
                    signInWithEmailAndPassword(email, password)
                }
            }
        }

        return root
    }

    private fun signInWithEmailAndPassword(email : String, password : String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful && isVisible) {
                    val navController = findNavController()

                    // Чистим стек фрагментов:
                    navController.popBackStack(R.id.nav_auth_mail, true);
                    navController.popBackStack(R.id.nav_auth_input_phone, true);
                    navController.popBackStack(R.id.nav_auth, true);
                    // Отправляем на нужный фрагмент:

                    navController.navigate(R.id.nav_personal);

                } else {
                    when(task.exception){
                        is FirebaseTooManyRequestsException -> showError("Ошибка. Превышен лимит запросов, повторите попытку позже!")
                        is FirebaseNetworkException -> showError("Ошибка. Проверьте подключение к интернету!")
                        is FirebaseAuthInvalidCredentialsException -> showError("Ошибка. Вы ввели неверные данные для входа!")
                        else -> showError("Ошибка. Попробуйте ещё раз!")
                    }
                    setLoading(false)
                }
            }
    }

    // Выставляем ProgressBar и делаем кнопку включённой или нет:
    private fun setLoading(loading: Boolean) {
        if(isVisible) {
            button_continue.isEnabled = !loading
            when (loading) {
                true -> login_progressbar.visibility = View.VISIBLE
                false -> login_progressbar.visibility = View.INVISIBLE
            }
        }
    }

    private fun showError(error : String){
        if(isVisible) {
            error_textView.visibility = View.VISIBLE
            error_textView.text = error
        }
    }
}



