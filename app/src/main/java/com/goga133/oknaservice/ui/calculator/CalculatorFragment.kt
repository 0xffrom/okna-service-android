package com.goga133.oknaservice.ui.calculator

import android.app.AlertDialog
import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.goga133.oknaservice.R
import com.goga133.oknaservice.adapters.SliderAdapter
import com.goga133.oknaservice.listeners.OnSeekBarChangeListener
import com.goga133.oknaservice.models.Calculator
import com.goga133.oknaservice.models.Status
import com.goga133.oknaservice.models.price.Price
import com.goga133.oknaservice.models.price.TotalCost
import com.goga133.oknaservice.models.price.UserWindow
import com.goga133.oknaservice.models.price.Window
import com.goga133.oknaservice.models.product.Product
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderPager
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_calculator.*
import kotlinx.android.synthetic.main.fragment_calculator.view.*
import kotlinx.android.synthetic.main.value_setter_dialog.*
import java.util.*


class CalculatorFragment : Fragment() {

    private lateinit var calculatorViewModel: CalculatorViewModel
    private lateinit var currentWindow: Window
    private lateinit var totalCost: TotalCost
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var data: Price

    private lateinit var widthSeekBar: SeekBar
    private lateinit var heightSeekBar: SeekBar

    private val userWindowLiveData by lazy {
        MutableLiveData<UserWindow>().apply {
            observe(viewLifecycleOwner, Observer {
                Log.d(ContentValues.TAG, "Я ПОМЕНЯЛСЯ БЕЗ ИФА")
                if (it != null) {
                    Log.d(ContentValues.TAG, "$it")
                    updateSummaryPrice(it)
                }
            }
            )
        }
    }
    private fun getUserWindow() = userWindowLiveData.value

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_calculator, container, false)

        calculatorViewModel = ViewModelProvider(this)[CalculatorViewModel::class.java]

        calculatorViewModel.priceLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> viewOneLoading()
                Status.SUCCESS -> viewOneSuccess(it.data)
                Status.ERROR -> viewOneError(it.error)
            }
        })

        widthSeekBar = root.seekBar_width
        heightSeekBar = root.seekBar_height

        val widthTextView = root.text_view_bar_width
        val heightTextView = root.text_view_bar_height

        val listener = OnSeekBarChangeListener(fun(seekBar: SeekBar?, progress: Int): Unit {
            when (seekBar) {
                widthSeekBar -> {
                    widthTextView.text = "Длина: ${progress + (currentWindow.minWidth)} мм."
                    userWindowLiveData.value?.width =
                        progress + currentWindow.minWidth
                }
                heightSeekBar -> {
                    heightTextView.text = "Высота: ${progress + (currentWindow.minHeight)} мм."
                    userWindowLiveData.value?.height =
                        progress + currentWindow.minHeight
                }
            }
            userWindowLiveData.value = getUserWindow()
        })


        heightSeekBar.setOnSeekBarChangeListener(listener)
        widthSeekBar.setOnSeekBarChangeListener(listener)

        sliderAdapter = getSliderAdapter(root)

        setDialogAlert(root)

        root.toggle_button_group_p.addOnButtonCheckedListener { _: MaterialButtonToggleGroup, i: Int, b: Boolean ->
            if (b) {
                when (i) {
                    R.id.p1_button -> {
                        userWindowLiveData.value?.profile = data.profiles[0]
                        root.profiles_description_textView.text = data.profiles[0].description
                    }
                    R.id.p2_button -> {
                        userWindowLiveData.value?.profile = data.profiles[1]
                        root.profiles_description_textView.text = data.profiles[1].description
                    }
                    R.id.p3_button -> {
                        userWindowLiveData.value?.profile = data.profiles[2]
                        root.profiles_description_textView.text = data.profiles[2].description
                    }
                }
                userWindowLiveData.value = getUserWindow()
            }
        }

        root.toggle_button_group_glass.addOnButtonCheckedListener { _: MaterialButtonToggleGroup, i: Int, b: Boolean ->
            if (b) {
                when (i) {
                    R.id.glass1_button -> userWindowLiveData.value?.typeGlass =
                        "prOne"
                    R.id.glass2_button -> userWindowLiveData.value?.typeGlass =
                        "prTwo"
                }
                userWindowLiveData.value = getUserWindow()
            }
        }

        root.toggle_button_group_house.addOnButtonCheckedListener { _: MaterialButtonToggleGroup, i: Int, b: Boolean ->
            if (b) {
                when (i) {
                    R.id.panel_button -> userWindowLiveData.value?.typeHome =
                        "panel"
                    R.id.brick_button -> userWindowLiveData.value?.typeHome =
                        "brick"
                }
                userWindowLiveData.value = getUserWindow()
            }
        }

        root.toggle_button_group_options.addOnButtonCheckedListener { _: MaterialButtonToggleGroup, i: Int, b: Boolean ->
            when (i) {
                R.id.options_grid_button -> userWindowLiveData.value?.extras?.find {
                    it.name.toLowerCase(
                        Locale.ROOT
                    ).contains("москитная сетка")
                }?.selected = b
                R.id.options_sill_button -> userWindowLiveData.value?.extras?.find {
                    it.name.toLowerCase(
                        Locale.ROOT
                    ).contains("подоконник")
                }?.selected = b
                R.id.options_slope_button -> userWindowLiveData.value?.extras?.find {
                    it.name.toLowerCase(
                        Locale.ROOT
                    ).contains("откос")
                }?.selected = b
                R.id.options_tide_button -> userWindowLiveData.value?.extras?.find {
                    it.name.toLowerCase(
                        Locale.ROOT
                    ).contains("отлив")
                }?.selected = b
            }
            userWindowLiveData.value = getUserWindow()
        }

        root.toggle_button_group_services.addOnButtonCheckedListener { _: MaterialButtonToggleGroup, i: Int, b: Boolean ->
            when (i) {
                R.id.services_delivery_button -> userWindowLiveData.value?.isWinDelivery =
                    b
                R.id.services_montage_button -> userWindowLiveData.value?.isWinInstall =
                    b
            }
            userWindowLiveData.value = getUserWindow()
        }

        root.add_cart_button.setOnClickListener {
            try {
                val userWindow = userWindowLiveData.value
                if (userWindow != null) {
                    calculatorViewModel.insertProduct(
                        Product(userWindow, totalCost)
                    )
                }

                Snackbar.make(root, "Товар был успешно добавлен в козину!", Snackbar.LENGTH_SHORT)
                    .show()
            } catch (e: Exception) {
                Snackbar.make(
                    root,
                    "Произошла ошибка. Повторите запрос позже",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
        }

        calculatorViewModel.getPrice()

        return root
    }


    private fun viewOneLoading() {
        if (isVisible) {
            progress_calculatorView.visibility = View.VISIBLE
            mainContent_linearLayout_calculatorView.visibility = View.INVISIBLE
        }
    }

    private fun viewOneSuccess(data: Price?) {
        if (data == null) {
            if (isVisible)
                Snackbar.make(
                    this.requireView(),
                    "Произошла ошибка. Повторите запрос позже",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            return
        }
        if (isVisible) {
            progress_calculatorView.visibility = View.INVISIBLE
            mainContent_linearLayout_calculatorView.visibility = View.VISIBLE
        }

        if (data.windows.isEmpty()) {
            return
        }
        this.data = data

        sliderAdapter.renewItems(data.windows)
        Log.d(ContentValues.TAG, "$data")
        userWindowLiveData.value = UserWindow(
            currentWindow,
            currentWindow.minHeight,
            currentWindow.minHeight,
            data.profiles[0],
            data.additionalPrice,
            data.extras,
            "prOne",
            "panel",
            isWinInstall = false,
            isWinDelivery = false
        )

        if (data.profiles.size < 3)
            return

        p1_button.text = data.profiles[0].name
        if (isVisible)
            profiles_description_textView.text = data.profiles[0].description
        p2_button.text = data.profiles[1].name
        p3_button.text = data.profiles[2].name
    }

    private fun viewOneError(error: Throwable?) {
        Log.d(ContentValues.TAG, "Error")

        if (isVisible) {
            Toast.makeText(
                context, when (error) {
                    is java.net.SocketTimeoutException -> "Проверьте подключение к интернету!"
                    else -> "Ошибка загрузки данных!"
                }, Toast.LENGTH_LONG
            ).show()
        }
        calculatorViewModel.getPrice()
    }

    private fun setDialogAlert(root: View) {
        root.hand_input_button.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(root.context).inflate(R.layout.value_setter_dialog, null)
            val mBuilder = AlertDialog.Builder(root.context)
                .setView(mDialogView)
                .setTitle("Ввод данных")
            val mAlertDialog = mBuilder.show()

            mAlertDialog.text_view_dialog_height.hint =
                "Высота окна (от ${currentWindow.minHeight} до ${currentWindow.maxHeight} мм.)"
            mAlertDialog.text_view_dialog_width.hint =
                "Длина окна (от ${currentWindow.minWidth} до ${currentWindow.maxWidth} мм.)"

            mAlertDialog.text_view_dialog_width.validate(
                "Задайте значение из нужного диапазона.",
                mAlertDialog.dialog_input_value_width
            )
            { s -> isCorrectInput(s, currentWindow.minWidth, currentWindow.maxWidth) }
            mAlertDialog.text_view_dialog_height.validate(
                "Задайте значение из нужного диапазона.",
                mAlertDialog.dialog_input_value_height
            )
            { s -> isCorrectInput(s, currentWindow.minHeight, currentWindow.maxHeight) }

            mAlertDialog.dialog_button_set.setOnClickListener {
                if (mAlertDialog.text_view_dialog_width.error != null ||
                    mAlertDialog.dialog_input_value_width.text.toString()
                        .isEmpty() ||
                    mAlertDialog.text_view_dialog_height.error != null ||
                    mAlertDialog.dialog_input_value_height.text.toString()
                        .isEmpty()
                )
                    Toast.makeText(
                        root.context,
                        "Пожалуйста, введите размеры из заданного диапазона",
                        Toast.LENGTH_LONG
                    ).show()
                else {
                    widthSeekBar.progress = mAlertDialog.dialog_input_value_width.text.toString()
                        .toInt() - currentWindow.minWidth
                    heightSeekBar.progress = mAlertDialog.dialog_input_value_height.text.toString()
                        .toInt() - currentWindow.minHeight

                    mAlertDialog.dismiss()
                }
            }
        }
    }

    private fun getSliderAdapter(root: View): SliderAdapter {
        val sliderView: SliderView = root.imageSlider
        val sliderAdapter = SliderAdapter(root.context)
        sliderView.apply {
            setSliderAdapter(sliderAdapter)
            setIndicatorAnimation(IndicatorAnimationType.WORM)
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            isAutoCycle = false
            indicatorRadius = 1
            indicatorSelectedColor = Color.WHITE
            indicatorUnselectedColor = Color.GRAY
        }
        sliderView.sliderPager.addOnPageChangeListener(
            object : SliderPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                    //
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    //
                }

                override fun onPageSelected(position: Int) {
                    currentWindow = sliderAdapter.getItemAt(position)

                    heightSeekBar.max = currentWindow.maxHeight - currentWindow.minHeight
                    widthSeekBar.max = currentWindow.maxWidth - currentWindow.minWidth

                    heightSeekBar.progress = 0
                    widthSeekBar.progress = 0
                }
            })

        return sliderAdapter
    }


    // Обновление цены
    private fun updateSummaryPrice(userWindow: UserWindow) {
        val totalCost: TotalCost = Calculator.calculatePrice(userWindow)

        // TODO: Сделать по-человечески
        if (isVisible) {
            cart_price_textView.text =
                "${totalCost.sum + totalCost.sumD} ₽"
            text_view_sumW.text = "Стоимость окна:\t ${totalCost.sumW} ₽"
            text_view_sumO.text = "Стоимость опций:\t ${totalCost.sumO} ₽"
            text_view_sumD.text = "Стоимость доставки:\t ${totalCost.sumD} ₽"
            text_view_sumM.text = "Стоимость монтажа:\t ${totalCost.sumM} ₽"
        }
    }

    private fun isCorrectInput(target: CharSequence?, minValue: Int, maxValue: Int): Boolean {
        return (target.toString().toIntOrNull() ?: -1) >= minValue && (target.toString()
            .toIntOrNull() ?: -1) <= maxValue
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


}





