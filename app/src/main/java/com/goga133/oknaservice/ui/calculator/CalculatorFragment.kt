package com.goga133.oknaservice.ui.calculator

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.goga133.oknaservice.R
import com.goga133.oknaservice.adapters.SliderAdapter
import com.goga133.oknaservice.listeners.OnSeekBarChangeListener
import com.goga133.oknaservice.models.Calculator
import com.goga133.oknaservice.models.Product
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderPager
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_calculator.view.*
import kotlinx.android.synthetic.main.value_setter_dialog.*


class CalculatorFragment : Fragment() {

    private lateinit var widthSeekBar: SeekBar
    private lateinit var heightSeekBar: SeekBar
    private lateinit var widthTextView: TextView
    private lateinit var heightTextView: TextView

    private var currentWindow: Calculator.Window = Calculator().chooseWindow("1-1")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_calculator, container, false)
        val calculatorViewModel = ViewModelProvider(this)[CalculatorViewModel::class.java]

        // TODO: Добавить биндинги.
        widthSeekBar = root.seekBar_width
        heightSeekBar = root.seekBar_height
        widthTextView = root.text_view_bar_width
        heightTextView = root.text_view_bar_height

        val listener = OnSeekBarChangeListener(fun(seekBar: SeekBar?, progress: Int): Unit {
            when (seekBar) {
                widthSeekBar -> {
                    widthTextView.text = "Длина: ${progress + (currentWindow.minW)} мм."
                    w = progress + currentWindow.minW
                }
                heightSeekBar -> {
                    heightTextView.text = "Высота: ${progress + (currentWindow.minH)} мм."
                    h = progress + currentWindow.minH
                }
            }
            updateSummaryPrice(root)
        })


        heightSeekBar.setOnSeekBarChangeListener(listener)
        widthSeekBar.setOnSeekBarChangeListener(listener)

        val sliderAdapter = getSliderAdapter(root)

        // При изменении коллекции изменяется содержимое адаптера.
        calculatorViewModel.arraySliders.observe(viewLifecycleOwner, Observer { array ->
            sliderAdapter.renewItems(array)
        })

        setDialogAlert(root)

        root.toggle_button_group_p.addOnButtonCheckedListener { _: MaterialButtonToggleGroup, i: Int, b: Boolean ->
            if (b) {
                when (i) {
                    R.id.p1_button -> this.typeProfile = "p1"
                    R.id.p2_button -> this.typeProfile = "p2"
                    R.id.p3_button -> this.typeProfile = "p3"
                }
                updateSummaryPrice(root)
            }
        }

        root.toggle_button_group_glass.addOnButtonCheckedListener { _: MaterialButtonToggleGroup, i: Int, b: Boolean ->
            if (b) {
                when (i) {
                    R.id.glass1_button -> this.typeGlass = "prOne"
                    R.id.glass2_button -> this.typeGlass = "prTwo"
                }
                updateSummaryPrice(root)
            }
        }

        root.toggle_button_group_house.addOnButtonCheckedListener { _: MaterialButtonToggleGroup, i: Int, b: Boolean ->
            if (b) {
                when (i) {
                    R.id.panel_button -> this.typeHome = "panel"
                    R.id.brick_button -> this.typeHome = "brick"
                }
                updateSummaryPrice(root)
            }
        }

        root.toggle_button_group_options.addOnButtonCheckedListener { _: MaterialButtonToggleGroup, i: Int, b: Boolean ->
            when (i) {
                R.id.options_grid_button -> this.isWinGrid = b
                R.id.options_sill_button -> this.isWinSill = b
                R.id.options_slope_button -> this.isWinSlope = b
                R.id.options_tide_button -> this.isWinTide = b
            }
            updateSummaryPrice(root)
        }

        root.toggle_button_group_services.addOnButtonCheckedListener { _: MaterialButtonToggleGroup, i: Int, b: Boolean ->
            when (i) {
                R.id.services_delivery_button -> this.isWinDelivery = b
                R.id.services_montage_button -> this.isWinInstall = b
            }
            updateSummaryPrice(root)
        }

        root.add_cart_button.setOnClickListener {
            val operation = calculatorViewModel.insertProduct(
                Product(
                    windowId,
                    sliderAdapter.getImageByWindowId(windowId),
                    h,
                    w,
                    typeProfile,
                    typeGlass,
                    typeHome,
                    isWinSill,
                    isWinTide,
                    isWinSlope,
                    isWinGrid,
                    isWinInstall,
                    isWinDelivery,
                    summaryPrice.sum,
                    summaryPrice.sumW,
                    summaryPrice.sumO,
                    summaryPrice.sumM,
                    summaryPrice.sumD
                )
            )

            Snackbar.make(root, "Товар был успешно добавлен в козину!", Snackbar.LENGTH_SHORT)
                .show()

        }



        return root
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
                "Высота окна (от ${currentWindow.minH} до ${currentWindow.maxH} мм.)"
            mAlertDialog.text_view_dialog_width.hint =
                "Длина окна (от ${currentWindow.minW} до ${currentWindow.maxW} мм.)"

            mAlertDialog.text_view_dialog_width.validate(
                "Задайте значение из нужного диапазона.",
                mAlertDialog.dialog_input_value_width
            )
            { s -> isCorrectInput(s, currentWindow.minW, currentWindow.maxW) }
            mAlertDialog.text_view_dialog_height.validate(
                "Задайте значение из нужного диапазона.",
                mAlertDialog.dialog_input_value_height
            )
            { s -> isCorrectInput(s, currentWindow.minH, currentWindow.maxH) }

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
                        .toInt() - currentWindow.minW
                    heightSeekBar.progress = mAlertDialog.dialog_input_value_height.text.toString()
                        .toInt() - currentWindow.minH

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
                    currentWindow =
                        Calculator().chooseWindow(sliderAdapter.getItemAt(position).windowId)
                    windowId = sliderAdapter.getItemAt(position).windowId
                    heightSeekBar.max = currentWindow.maxH - currentWindow.minH
                    widthSeekBar.max = currentWindow.maxW - currentWindow.minW

                    heightSeekBar.progress = 0
                    widthSeekBar.progress = 0
                }
            })

        return sliderAdapter
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

    private lateinit var summaryPrice: Calculator.SummaryPrice

    // ==== Default Settings Window Calculator ==== //
    private var h: Int = 0
    private var w: Int = 0
    private var windowId: String = "1-1"
    private var typeProfile: String = "p1"
    private var typeGlass: String = "prOne"
    private var typeHome: String = "panel"
    private var isWinSill: Boolean = false
    private var isWinSlope: Boolean = false
    private var isWinTide: Boolean = false
    private var isWinGrid: Boolean = false
    private var isWinInstall: Boolean = false
    private var isWinDelivery: Boolean = false
    // ==== Default Settings Window Calculator ==== //

    private fun updateSummaryPrice(root: View) {
        summaryPrice = Calculator().calculatePrice(
            currentWindow, h, w, typeProfile, typeGlass, typeHome, isWinSill, isWinTide,
            isWinSlope, isWinGrid, isWinInstall, isWinDelivery
        )

        root.cart_price_textView.text =
            "${summaryPrice.sum + summaryPrice.sumD} ₽"
        root.text_view_sumW.text = "Стоимость окна:\t ${summaryPrice.sumW} ₽"
        root.text_view_sumO.text = "Стоимость опций:\t ${summaryPrice.sumO} ₽"
        root.text_view_sumD.text = "Стоимость доставки:\t ${summaryPrice.sumD} ₽"
        root.text_view_sumM.text = "Стоимость монтажа:\t ${summaryPrice.sumM} ₽"
    }


}





