package com.star_wormwood.ton_clicker.Screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.star_wormwood.ton_clicker.Managers
import com.star_wormwood.ton_clicker.databinding.FragmentMiningScreenBinding


class MiningScreenFragment : Fragment() {
    private var _binding: FragmentMiningScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMiningScreenBinding.inflate(inflater, container, false)

        updateScreen()

        binding.mineButton.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    val pixels = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        190f,
                        resources.displayMetrics
                    )
                    val params = binding.mineButton.layoutParams
                    params.height = pixels.toInt()
                    params.width = pixels.toInt()
                    binding.mineButton.layoutParams = params




                    Managers.userManager.mine()
                    updateScreen()
                }

                MotionEvent.ACTION_UP -> {
                    val pixels = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        200f,
                        resources.displayMetrics
                    )
                    val params = binding.mineButton.layoutParams
                    params.height = pixels.toInt()
                    params.width = pixels.toInt()
                    binding.mineButton.layoutParams = params
                }
            }
            v?.onTouchEvent(event) ?: true
        }

        return binding.root
    }
    @SuppressLint("SetTextI18n")
    fun updateScreen() {
        if (Managers.userManager.ton == 0f) {
            binding.tonValue.text = "TON: " + "0"
        } else {
            binding.tonValue.text = "TON: " + String.format("%.06f", Managers.userManager.ton)
        }


    }
}