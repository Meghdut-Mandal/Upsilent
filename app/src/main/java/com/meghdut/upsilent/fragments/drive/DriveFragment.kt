package com.meghdut.upsilent.fragments.drive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meghdut.upsilent.databinding.FragmentDrive2Binding
import com.meghdut.upsilent.databinding.FragmentDriveBinding


class DriveFragment : Fragment() {

    private lateinit var binding: FragmentDrive2Binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentDrive2Binding.inflate(inflater)

        return binding.root
    }

}