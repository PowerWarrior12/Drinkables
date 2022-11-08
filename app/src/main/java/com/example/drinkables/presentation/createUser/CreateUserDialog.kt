package com.example.drinkables.presentation.createUser

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.drinkables.R
import com.example.drinkables.databinding.CreateUserDialogBinding
import com.example.drinkables.presentation.DrinksApplication
import com.example.drinkables.presentation.drinksList.DrinksListViewModel
import com.example.drinkables.presentation.mainActivity.MainActivityViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateUserDialog: DialogFragment(R.layout.create_user_dialog) {

    private val binding: CreateUserDialogBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: CreateUserViewModel.CreateUserViewModelFactory

    private val drinksViewModel: CreateUserViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }

    override fun onAttach(context: Context) {
        DrinksApplication.INSTANCE.appComponent.inject(this)
        super.onAttach(context)
    }

    private var name: String = ""

    private fun initViews() {
        dialog?.window?.setLayout(1000, 600)
        binding.apply {
            buttonSave.setOnClickListener {
                lifecycleScope.launch {
                    drinksViewModel.createOrUpdateUser(name)
                }
            }
            editText.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    name = s.toString()
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun observeData() {
        drinksViewModel.resultLiveData.observe(viewLifecycleOwner) {
            if (it == false)
                Toast.makeText(context, "This name already using", Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
        }
        drinksViewModel.nameLiveData.observe(viewLifecycleOwner) {
            binding.editText.setText(it)
        }
    }

    companion object {
        fun newInstance() = CreateUserDialog()
    }
}