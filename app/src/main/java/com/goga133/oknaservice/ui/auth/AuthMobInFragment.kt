package com.goga133.oknaservice.ui.auth

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.goga133.oknaservice.R
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_auth_input_phone.*
import kotlinx.android.synthetic.main.fragment_auth_input_phone.view.*
import java.util.concurrent.TimeUnit

class AuthMobInFragment : Fragment() {

    private val mAuth by lazy { FirebaseAuth.getInstance() }
    private val mCurrentUser by lazy { mAuth.currentUser }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_auth_input_phone, container, false)
        val authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]


        root.textInput_phone.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event != null) {
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                        sendMobileCode(root)
                        return true
                    }
                }
                return false
            }
        })

        // Кнопка далее:
        root.button_continue.setOnClickListener {
            // Если нет ошибки, связанной с валидацией номера и текст не пуст:
            sendMobileCode(root)
        }
        return root
    }

    private fun sendMobileCode(root: View) {
        if (!root.textInput_phone.text.isNullOrEmpty() && root.textInput_phone.error == null && isCorrectRusNumber(
                root.textInput_phone.text
            )
        ) {
            root.button_continue.isEnabled = false
            root.login_progressbar.visibility = View.VISIBLE

            val phoneNumber = "+7" + root.textInput_phone.text

            val phoneAuthProvider = PhoneAuthProvider.getInstance()
            phoneAuthProvider.verifyPhoneNumber(phoneNumber,
                120L,
                TimeUnit.SECONDS,
                requireActivity(),
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        signInWithPhoneAuthCredential(credential)

                        Log.d(TAG, "onVerificationCompleted:$credential")


                    }

                    override fun onVerificationFailed(e: FirebaseException) {

                        Log.w(TAG, "onVerificationFailed", e)
                        root.textView_error.visibility = View.VISIBLE

                        when (e) {
                            is FirebaseTooManyRequestsException -> {
                                root.textView_error.text =
                                    "Ошибка. Превышен лимит запросов, повторите попытку позже."
                            }
                            is FirebaseNetworkException -> {
                                root.textView_error.text =
                                    "Ошибка. Проверьте подключение к интернету."
                            }
                            else -> {
                                root.textView_error.text =
                                    "Ошибка. Попробуйте повторить запрос позже."
                            }
                        }

                        root.button_continue.isEnabled = true
                        root.login_progressbar.visibility = View.INVISIBLE
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        root.textView_error.visibility = View.INVISIBLE
                        super.onCodeSent(verificationId, token)
                        android.os.Handler().postDelayed(
                            {
                                val args = Bundle().apply {
                                    putString("verificationId", verificationId)
                                    putString("phoneNumber", phoneNumber)
                                }

                                root.findNavController()
                                    .navigate(R.id.nav_auth_code_phone, args)
                            }, 10000);
                    }
                })
        } else {
            root.textView_error.visibility = View.VISIBLE
            root.textView_error.text = "Ошибка. Попробуйте повторить запрос позже."
        }
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
                        textView_error.visibility = View.VISIBLE
                        textView_error.text = "Неверный код. Попробуйте ещё раз."
                    }
                }
            }
    }

    private fun isCorrectRusNumber(target: CharSequence?): Boolean {
        return target.toString().matches(Regex("\\d{10}"))
    }

}



