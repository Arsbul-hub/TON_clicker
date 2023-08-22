package com.star_wormwood.ton_clicker.Screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.star_wormwood.ton_clicker.R
import com.star_wormwood.ton_clicker.databinding.FragmentGamesScreenBinding


class GamesScreenFragment : Fragment() {
    private var _binding: FragmentGamesScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


}