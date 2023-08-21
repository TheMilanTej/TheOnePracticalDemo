package com.milantejani.theonepractical.ui.user

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.milantejani.theonepractical.R
import com.milantejani.theonepractical.data.model.User
import com.milantejani.theonepractical.databinding.FragmentAddUserBinding
import com.milantejani.theonepractical.utils.isValidEmail
import com.milantejani.theonepractical.utils.viewBinding

class AddUserDialog(val onDismiss: (() -> Unit)? = null) : DialogFragment() {

    private val binding by viewBinding(FragmentAddUserBinding::inflate)

    private val mUserViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddUser.setOnClickListener {
            val fname = binding.edtfname.text.toString().trim()
            val lname = binding.edtlname.text.toString().trim()
            val email = binding.edtemail.text.toString().trim()

            if (fname.isEmpty()) {
                Toast.makeText(
                    requireContext(), getString(R.string.fname_empty), Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (lname.isEmpty()) {
                Toast.makeText(
                    requireContext(), getString(R.string.lname_empty), Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                Toast.makeText(
                    requireContext(), getString(R.string.empty_email), Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (!email.isValidEmail()) {
                Toast.makeText(
                    requireContext(), getString(R.string.invalid_email), Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            mUserViewModel.addUser(User(firstName = fname, lastName = lname, email = email))
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss?.invoke()
    }
}