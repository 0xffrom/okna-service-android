package com.goga133.oknaservice.ui.auth

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.goga133.oknaservice.R
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.fragment_auth.view.*
import kotlinx.android.synthetic.main.fragment_auth_code.*
import kotlinx.android.synthetic.main.fragment_auth_code.view.*
import java.lang.Exception

class AuthMobCodeFragment : Fragment() {

    private val mAuth by lazy { FirebaseAuth.getInstance()}

    private lateinit var verificationId : String
    private lateinit var phoneNumber : String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_auth_code, container, false)
        val authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        verificationId = this.arguments?.getString("verificationId").orEmpty()
        phoneNumber = this.arguments?.getString("phoneNumber").orEmpty()
        root.phone_textView.text = "Код выслан на номер $phoneNumber"

        root.button_otp.setOnClickListener{
            setLoading(true)

            val otp = root.otp_editText.text.toString()
            if (otp.isEmpty()){
                setLoading(false)
                root.error_textView.visibility = View.VISIBLE
                root.error_textView.text = "Ошибка. Поле ввода пусто."
            }
            else if (otp.length < 6){
                setLoading(false)
                root.error_textView.visibility = View.VISIBLE
                root.error_textView.text = "Ошибка. Введите шестизначный код из смс!"
            }
            else{
                // Убрать клавиатуру:
                root.otp_editText.isFocusable = false;
                root.otp_editText.isFocusableInTouchMode = true;

                try {
                    val credential = PhoneAuthProvider.getCredential(verificationId, otp)
                    signInWithPhoneAuthCredential(credential)
                    setLoading(false)
                }
                catch (e : Exception){
                    root.error_textView.visibility = View.VISIBLE
                    root.error_textView.text = "Произошла техническая ошибка."
                }
            }
        }
        return root
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = task.result?.user

                    val navController = findNavController()

                    // Чистим стек фрагментов:
                    navController.popBackStack(R.id.nav_auth_input_phone, true);
                    navController.popBackStack(R.id.nav_auth, true);
                    // Отправляем на нужный фрагмент:
                    navController.navigate(R.id.nav_personal);

                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        error_textView.visibility = View.VISIBLE
                        error_textView.text = "Неверный код. Попробуйте ещё раз."
                    }
                }
            }
    }

    private fun setLoading(loading : Boolean){
        button_otp.isEnabled = !loading
        when(loading){
            true -> otp_progressbar.visibility = View.VISIBLE
            false -> otp_progressbar.visibility = View.INVISIBLE
        }
    }
}



