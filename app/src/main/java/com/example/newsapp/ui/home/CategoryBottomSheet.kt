package com.example.newsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.R
import com.example.newsapp.databinding.BottomSheetCategoriesBinding
import com.example.newsapp.domain.model.Category
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoryBottomSheet(
    private val onCategorySelected: (Category) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.categoryGeneral.setOnClickListener { 
            onCategorySelected(Category.GENERAL)
            dismiss()
        }
        binding.categoryBusiness.setOnClickListener { 
            onCategorySelected(Category.BUSINESS)
            dismiss()
        }
        binding.categoryEntertainment.setOnClickListener { 
            onCategorySelected(Category.ENTERTAINMENT)
            dismiss()
        }
        binding.categoryHealth.setOnClickListener { 
            onCategorySelected(Category.HEALTH)
            dismiss()
        }
        binding.categoryScience.setOnClickListener { 
            onCategorySelected(Category.SCIENCE)
            dismiss()
        }
        binding.categorySports.setOnClickListener { 
            onCategorySelected(Category.SPORTS)
            dismiss()
        }
        binding.categoryTechnology.setOnClickListener { 
            onCategorySelected(Category.TECHNOLOGY)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}