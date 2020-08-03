package com.goga133.oknaservice.ui.personal_cab

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.goga133.oknaservice.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.android.synthetic.main.fragment_personal_info.*
import kotlinx.android.synthetic.main.fragment_personal_info.view.*
import java.lang.Exception

class PersonalInfoFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mCurrentUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_personal_info, container, false)
        root.personal_info_save_changes_button.setOnClickListener {
            val emailField = root.personal_info_email_editText
            val nameField = root.personal_info_name_editText
            val surnameField = root.personal_info_surname_editText

            val oldEmail = mCurrentUser.email;
            val newEmail = emailField.text.toString();
            // Если имя и фамилия пусты:
            if (nameField.text.isNullOrEmpty() && surnameField.text.isNullOrEmpty()) {
                // Поле от почты тоже пусто -> Ошибка.
                if (emailField.text.isNullOrEmpty())
                    showMessage("Ошибка. Вы ничего не заполнили!")
                // Почта не пуста:
                else {
                    // Почту можно обновить? Да -> обновляем, нет -> ошибка.
                    if (oldEmail != newEmail && !emailField.text.isNullOrEmpty())
                        updateEmail(emailField.text.toString())
                    else
                        showMessage("Ошибка. Вы ничего не заполнили!")
                }
            }
            // Если поле для имени некорректно:
            else if (!validationName(nameField.text))
                nameField.error = "Введите Ваше реальное имя."
            // Если поле для фамилии некорректно:
            else if (!validationName(surnameField.text))
                surnameField.error = "Введите Вашу реальную фамилию."
            // Если имя и фамилия заполнены:
            else {
                // Полное имя = "<Имя> <Фамилия>"
                val newName =
                    "${root.personal_info_name_editText.text} ${root.personal_info_surname_editText.text}"
                // Поле от почты пусто -> обновляем имя.
                if ((emailField.text.isNullOrEmpty() || newEmail == oldEmail) && newName != mCurrentUser.displayName) {
                        updateName(newName)
                }
                // Поле от почты не пусто:
                else {
                    // Поле от почты можно обновить -> обновляем почту и имя.
                    if (newEmail != oldEmail && !emailField.text.isNullOrEmpty()) {
                        if (newName != mCurrentUser.displayName)
                            updateName(newName)
                        updateEmail(emailField.text.toString())
                    }
                    // Поле от почты нельзя обновить -> обновляем только имя.
                    else updateName(newName)
                }
            }
        }

        return root
    }

    private fun updateName(newName: String) {
        if (mCurrentUser.displayName != newName) {
            val profileUpdates = userProfileChangeRequest {
                displayName = newName
            }
            mCurrentUser.updateProfile(profileUpdates).addOnCompleteListener { task ->
                if (isVisible) {
                    if (task.isSuccessful) {
                        showMessage("Имя успешно обновлено!")
                    }
                    if (task.isCanceled) {
                        showMessage("Ошибка. Не удалось обновить имя!")
                    }
                }
            }
        }
    }

    private fun showMessage(message: String, isLong: Boolean = false) {
        if (isVisible) {
            Log.d("Message for user", "Отправлено сообщение: $message")
            Snackbar.make(
                requireView(), message, when (isLong) {
                    true -> Snackbar.LENGTH_LONG
                    false -> Snackbar.LENGTH_SHORT
                }
            ).show()
        }
    }

    private fun updateEmail(newEmail: String) {
        if (isValidEmail(newEmail)) {
            mCurrentUser.updateEmail(newEmail.replace(" ", "")).addOnCompleteListener { task ->
                if (isCorrectView()) {
                    if (task.isSuccessful) {
                        showMessage("Почта была успешно добавлена!", true)
                        updateUI(mCurrentUser)
                    } else if (task.isCanceled) {
                        showMessage("Ошибка. Повторите попытку позже!", true)
                    }
                }
            }.addOnSuccessListener {
                updateUI(mCurrentUser)
            }.addOnFailureListener {
                if (isCorrectView()) {
                    when (it) {
                        is FirebaseAuthUserCollisionException -> showMessage("Ошибка. Почта занята.")
                        is FirebaseAuthInvalidUserException -> showMessage("Ошибка. Аккаунт был заблокирован администратором.")
                        is FirebaseAuthInvalidCredentialsException -> showMessage("Ошибка. Неверный формат почты.")
                    }
                }
            }
        }
        else{
            // Убрать галочку:
            personal_info_email_textInputLayout.apply {
                endIconMode = TextInputLayout.END_ICON_NONE
            }
            showMessage("Ошибка. Введите Вашу реальную почту.")
        }
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return target.isNullOrEmpty() || !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(
            target
        ).matches()
    }

// TODO: Event для валидации почты.

    private fun validationName(text: Editable?) = when {
        text.isNullOrEmpty() -> false
        text.matches(Regex("[a-zA-Zа-яА-Я]+")) -> true
        else -> false
    }


    private fun updateUI(user: FirebaseUser) {
        if (isCorrectView()) {
            val name = user.displayName?.split(' ')
            if (name?.size == 2) {
                personal_info_name_editText.setText(name[0])
                personal_info_surname_editText.setText(name[1])
            }
            val email = user.email
            if (!email.isNullOrEmpty()) {
                personal_info_email_editText.apply {
                    setText(email)
                }
                personal_info_email_textInputLayout.apply {
                    endIconMode = TextInputLayout.END_ICON_CUSTOM
                    endIconDrawable = resources.getDrawable(R.drawable.ic_check)
                    setEndIconTintList(ColorStateList.valueOf(resources.getColor(R.color.colorPrimary)))
                }
            }

            personal_info_phone_editText.setText(user.phoneNumber)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser == null) {
            // TODO: Обработать ошибку.
        } else
            mCurrentUser = mAuth.currentUser!!

        mCurrentUser?.let {
            updateUI(it)
        }

    }

    private fun isCorrectView() : Boolean{
        return try {
            findNavController().currentDestination?.id == R.id.nav_personal
        } catch (e : Exception){
            false
        }
    }

}