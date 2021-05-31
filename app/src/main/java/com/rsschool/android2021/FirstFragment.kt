package com.rsschool.android2021

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var someFragmentChanger: FragmentChanger? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentChanger) {
            someFragmentChanger = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        someFragmentChanger = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "PREVIOUS RESULT: ${result.toString()}"

        val minValue = view.findViewById<EditText>(R.id.min_value)
        val maxValue = view.findViewById<EditText>(R.id.max_value)

        minValue.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == "") {
                    minValue.error = "Please, enter the value!"
                    generateButton?.isEnabled = false
                }
                else if (s.toString().toInt() > 999999) {
                    minValue.error = "The value is too big!"
                    generateButton?.isEnabled = false
                }
                else {
                    if (maxValue.text.toString() != "" )
                        generateButton?.isEnabled = maxValue.text.toString().toInt() <= 999999
                }
            }
        })

        maxValue.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == "") {
                    maxValue.error = "Please, enter the value!"
                    generateButton?.isEnabled = false
                }
                else if (s.toString().toInt() > 999999) {
                    maxValue.error = "The value is too big!"
                    generateButton?.isEnabled = false
                }
                else {
                    if (minValue.text.toString() != "" )
                    generateButton?.isEnabled = minValue.text.toString().toInt() <= 999999
                }
            }

        })

        generateButton?.setOnClickListener {
            if (minValue.text.toString() != "" && maxValue.text.toString() != "") {
                if (minValue.text.toString().toInt() <= maxValue.text.toString().toInt()) {
                    val min = minValue.text.toString().toInt()
                    val max = maxValue.text.toString().toInt()
                    someFragmentChanger?.openSecondFragment(min, max)
                } else {
                    minValue.error = "The value must be less then maximum!"
                    generateButton?.isEnabled = false
                }
            } else {
                if (minValue.text.toString() == "") minValue.error = "Please, enter the value!"
                if (maxValue.text.toString() == "") maxValue.error = "Please, enter the value!"
                generateButton?.isEnabled = false
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}