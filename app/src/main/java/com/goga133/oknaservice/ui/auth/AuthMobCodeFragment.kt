package com.goga133.oknaservice.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.goga133.oknaservice.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_auth_code.*
import kotlinx.android.synthetic.main.fragment_auth_code.view.*
import kotlinx.android.synthetic.main.fragment_auth_input_phone.*

class AuthMobCodeFragment : Fragment() {

    private val mAuth by lazy { FirebaseAuth.getInstance() }

    private lateinit var verificationId: String
    private lateinit var phoneNumber: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_auth_code, container, false)

        verificationId = this.arguments?.getString("verificationId").orEmpty()
        phoneNumber = this.arguments?.getString("phoneNumber").orEmpty()

        root.phone_textView.text = getString(R.string.auth_ui_phone, phoneNumber)

        root.button_otp.setOnClickListener {
            onCheckCode()
        }

        return root
    }

    private fun onCheckCode() {
        // otp - код, который ввёл пользователь.
        val otp = otp_editText.text.toString()

        when {
            otp.isEmpty() -> {
                showError("Ошибка. Поле ввода не может быть пусто!")
            }
            otp.length < 6 -> {
                showError("Ошибка. Введите шестизначный код из смс!")
            }
            else -> {
                setLoading(true)
                // Убрать клавиатуру:
                otp_editText.isFocusable = false;
                otp_editText.isFocusableInTouchMode = true;

                // Проверка на валидность кода:
                try {
                    val credential = PhoneAuthProvider.getCredential(verificationId, otp)
                    signInWithPhoneAuthCredential(credential)
                } catch (e: Exception) {
                    // На всякий случай.
                    showError("Ошибка. Попробуйте ещё раз!")
                }

                setLoading(false)
            }
        }
    }

    // Отправка кода и его проверка:
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this.requireActivity()) { task ->
                // Если код подтверждения верный:
                if (task.isSuccessful) {
                    val navController = findNavController()

                    // Чистим стек фрагментов:
                    navController.popBackStack(R.id.nav_auth_input_phone, true);
                    navController.popBackStack(R.id.nav_auth, true);
                    // Отправляем на нужный фрагмент:
                    navController.navigate(R.id.nav_personal);

                }

                // Если код подтверждения неверный:
                else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        showError("Ошибка. Вы ввели неверный код!")
                    }
                    else{
                        showError("Ошибка. Попробуйте ещё раз!")
                    }
                }
            }
    }

    // Выставляем ProgressBar и делаем кнопку включённой или нет:
    private fun setLoading(loading: Boolean) {
        button_otp.isEnabled = !loading
        when (loading) {
            true -> otp_progressbar.visibility = View.VISIBLE
            false -> otp_progressbar.visibility = View.INVISIBLE
        }
    }

    private fun showError(error : String){
        error_textView.visibility = View.VISIBLE
        error_textView.text = error
    }
}



