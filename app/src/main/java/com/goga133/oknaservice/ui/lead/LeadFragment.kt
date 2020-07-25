package com.goga133.oknaservice.ui.lead

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goga133.oknaservice.R
import com.goga133.oknaservice.adapters.ProductsAdapter
import com.goga133.oknaservice.models.Calculator
import com.goga133.oknaservice.models.Product
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_lead.view.*
import kotlinx.android.synthetic.main.send_mail_dialog.*


class LeadFragment : Fragment() {

    private lateinit var leadViewModel: LeadViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_lead, container, false)
        root.main_info_layout.visibility = View.INVISIBLE

        leadViewModel = ViewModelProviders.of(this).get(LeadViewModel::class.java)

        root.list_products.layoutManager = LinearLayoutManager(root.context)
        root.list_products.setHasFixedSize(false)
        val adapter = ProductsAdapter(root.context, leadViewModel)

        leadViewModel.getProducts().observe(viewLifecycleOwner,
            Observer<List<Product>> { t ->
                adapter.submitList(t)
                // Проверка на количество элементов:
                // Если элементов 0 => Корзина пуста.
                if (t != null && t.isEmpty()) {
                    root.cart_null_textView.visibility = View.VISIBLE
                    root.main_info_layout.visibility = View.INVISIBLE
                } else {
                    root.cart_null_textView.visibility = View.INVISIBLE
                    root.main_info_layout.visibility = View.VISIBLE
                    root.isDelivery_textView.text = when (getProductsDelivery(t)) {
                        true -> {
                            root.address_textInputLayout.isEnabled = true
                            "Доставка: Требуется."
                        }
                        false -> {
                            root.address_textInputLayout.isEnabled = false
                            "Доставка: Не требуется."
                        }
                    }

                    root.sum_textView.text = "Итоговая сумма к оплате: ${getProductsSum(t)} рублей."
                }
            })

        root.list_products.adapter = adapter

        // Удаление по свайпу:
        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
            ) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    Snackbar.make(
                        root,
                        "Выбранное окно успешно убрано из корзины!",
                        Snackbar.LENGTH_LONG
                    ).show()
                    leadViewModel.deleteProduct(adapter.getProductAt(viewHolder.adapterPosition))
                }
            }).attachToRecyclerView(root.list_products)

        // Валидатор введённых данных //
        root.mail_textInputLayout.validate(
            "Некорректный E-Mail!",
            root.mail_textView
        ) { s -> isValidEmail(s) }
        root.phone_textInputLayout.validate(
            "Некорректный номер мобильного телефона!",
            root.phone_textView
        ) { s -> isValidPhoneNumber(s) }
        // Валидатор введённых данных //

        // Обработка нажатия "Оформить заказ":
        root.make_lead_button.setOnClickListener {
            // Проверка на корректность введённого имени:
            if (root.name_textView.text.isNullOrEmpty())
                Snackbar.make(root, "Пожалуйста, укажите своё имя.", Snackbar.LENGTH_LONG).show()
            else if (root.mail_textView.text.isNullOrEmpty() && root.phone_textView.text.isNullOrEmpty()) {
                Snackbar.make(
                    root,
                    "Пожалуйста, укажите адрес электронной почты или свой мобильный телефон.",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            // Если где-то валидатор выскочил:
            else if (root.mail_textInputLayout.error != null || root.phone_textInputLayout.error != null)
                Snackbar.make(
                    root,
                    "Пожалуйста, укажите верный номер мобильного телефона или почты.",
                    Snackbar.LENGTH_LONG
                ).show()
            else {
                val mDialogView =
                    LayoutInflater.from(root.context).inflate(R.layout.send_mail_dialog, null)
                val mBuilder = AlertDialog.Builder(root.context)
                    .setView(mDialogView)
                    .setTitle("Отправка заявки")
                val mAlertDialog = mBuilder.show()


                mAlertDialog.send_mail_by_auto_button.setOnClickListener {

                    // TODO: Подрубить API для отправки.

                    Snackbar.make(
                        root,
                        "К сожалению, отправка в автоматическом режиме сейчас не работает :(",
                        Snackbar.LENGTH_SHORT
                    ).show()
/*                    if (mAlertDialog.is_delete_cart_checkBox.isChecked) {
                        // Очищение корзины
                        leadViewModel.deleteAllProduct()
                    }*/
                }

                mAlertDialog.send_mail_by_user_button.setOnClickListener {
                    // Открытие почтового сервиса для отправки письма юзером:

                    val emailIntent = Intent(
                        Intent.ACTION_SEND).apply {
                        data = Uri.parse("mailto:")
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_EMAIL,
                            arrayOf("os@okna-servise.com")
                        )
                        putExtra(
                            Intent.EXTRA_TEXT, getMailText(
                                adapter,
                                root.name_textView.text,
                                root.mail_textView?.text,
                                root.phone_textView?.text,
                                root.address_textView?.text,
                                root.comment_textView?.text
                            )
                        )
                        putExtra(Intent.EXTRA_SUBJECT, "Бизнес заявка")
                    }
                    // Скопировать текст в буфер.
                    (root.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).apply {
                        setPrimaryClip(ClipData.newPlainText("Бизнес заявка", getMailText(
                            adapter,
                            root.name_textView.text,
                            root.mail_textView?.text,
                            root.phone_textView?.text,
                            root.address_textView?.text,
                            root.comment_textView?.text
                        )))
                    }

                    try {
                        if (emailIntent.resolveActivity(root.context.packageManager) != null) {
                            startActivity(emailIntent)
                        }
                    } catch (e: Exception) {
                        // Ooooops
                    }

                    if (mAlertDialog.is_delete_cart_checkBox.isChecked) {
                        // Очищение корзины
                        leadViewModel.deleteAllProduct()
                    }
                }
            }
        }

        return root
    }

    private fun getMailText(
        adapter: ProductsAdapter,
        name: Editable?,
        email: Editable?,
        phoneNumber: Editable?,
        address: Editable?,
        comment: Editable?
    ): String {
        val stringBuilder = StringBuilder()
        val sysLineSeparator = "\n"
        if (!name.isNullOrEmpty()) stringBuilder.append("Имя: $name.$sysLineSeparator")
        if (!email.isNullOrEmpty()) stringBuilder.append("Почта: $email.$sysLineSeparator")
        if (!phoneNumber.isNullOrEmpty()) stringBuilder.append("Телефон: $phoneNumber.$sysLineSeparator")

        stringBuilder.append("Заказ:")
        val elements = adapter.getElements()
        for (i in elements.indices) {
            stringBuilder.append("$sysLineSeparator$sysLineSeparator<------ №${i + 1} ------>$sysLineSeparator")
            stringBuilder.append(elements[i].toString().replace("\n", sysLineSeparator))
            stringBuilder.append("$sysLineSeparator<------ №${i + 1} ------>$sysLineSeparator$sysLineSeparator")
        }

        if (!address.isNullOrEmpty())
            stringBuilder.append("Доставка по адресу: $address.$sysLineSeparator")
        else
            stringBuilder.append("Доставка не требуется.$sysLineSeparator")

        stringBuilder.append(
            "Автоматически высчитаная стоимость с учётом доставки: ${getProductsSum(
                elements
            )} рублей.$sysLineSeparator"
        )
        if (!comment.isNullOrEmpty()) stringBuilder.append("Дополнительный комментарий клиента: $comment.")
        return stringBuilder.toString()
    }

    private fun getProductsDelivery(products: List<Product>): Boolean {
        for (i in products.indices) {
            if (products[i].isWinDelivery)
                return true
        }
        return false
    }

    // Проверка на валидный Email:
    private fun isValidEmail(target: CharSequence?): Boolean {
        return target.isNullOrEmpty() || !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(
            target
        ).matches()
    }

    // Проверка на валидный номер мобильного телефона:
    private fun isValidPhoneNumber(target: CharSequence?): Boolean {
        return target.isNullOrEmpty() || !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target)
            .matches()
    }

    private fun TextInputLayout.validate(
        message: String,
        editText: EditText,
        validator: (String) -> Boolean
    ) {
        editText.afterTextChanged {
            this.error = if (validator(it)) null else message
        }
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                afterTextChanged.invoke(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun getProductsSum(products: List<Product>): Int {
        var sum = 0
        for (i in products.indices)
            sum += products[i].priceSum

        // Если есть доставка, то плюсуем только одну цену за доставку:
        if (getProductsDelivery(products))
            sum += Calculator.Price().delivery
        return sum
    }
}