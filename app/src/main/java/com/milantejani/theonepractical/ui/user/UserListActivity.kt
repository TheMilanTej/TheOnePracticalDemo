package com.milantejani.theonepractical.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.milantejani.theonepractical.R
import com.milantejani.theonepractical.data.model.User
import com.milantejani.theonepractical.databinding.ActivityUserListBinding
import com.milantejani.theonepractical.databinding.DialogDeleteBinding
import com.milantejani.theonepractical.ui.weather.WeatherActivity
import com.milantejani.theonepractical.utils.CustomDialogHelper
import com.milantejani.theonepractical.utils.viewBinding


class UserListActivity : AppCompatActivity() {

    private var deleteData: User? = null
    private val binding by viewBinding(ActivityUserListBinding::inflate)

    private val viewModel: UserViewModel by viewModels()

    private var adapter: UserListAdapter? = null

    var customDialogGeneral: CustomDialogHelper? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvUserList.layoutManager = LinearLayoutManager(this@UserListActivity)

        adapter = UserListAdapter({
            startActivity(Intent(this@UserListActivity, WeatherActivity::class.java))
        }, {
            deleteData = it
            customDialogGeneral?.show()
        })


        generateDeleteDialog()

        viewModel.readAllData.observe(this) {
            if (it.isNotEmpty()) {
                binding.rvUserList.visibility = View.VISIBLE
                binding.grpNoUser.visibility = View.GONE

                adapter?.submitList(it)
                binding.rvUserList.adapter = adapter

            } else {
                binding.rvUserList.visibility = View.GONE
                binding.grpNoUser.visibility = View.VISIBLE
            }
        }

        with(binding.incActionBar) {
            txtMenuBack.setOnClickListener {
                onBackPressed()
            }

            txtMenuAdd.setOnClickListener {
                binding.incActionBar.txtMenuAdd.visibility = View.INVISIBLE
                val addUserFragment = AddUserDialog {
                    binding.incActionBar.txtMenuAdd.visibility = View.VISIBLE
                }
                val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                ft.replace(binding.flContainer.id, addUserFragment)
                ft.addToBackStack(null)
                ft.commit()
                supportFragmentManager.executePendingTransactions()
            }
        }

    }

    private fun generateDeleteDialog() {
        customDialogGeneral = CustomDialogHelper(this@UserListActivity)
        customDialogGeneral?.create(R.layout.dialog_delete, onCancelListener = {

        }).apply {
            this?.let {
                val itemBinding = DialogDeleteBinding.bind(this)
                itemBinding.btnCancel.setOnClickListener {
                    customDialogGeneral?.dismiss()
                }
                itemBinding.btnDelete.setOnClickListener {
                    customDialogGeneral?.dismiss()
                    deleteData?.let {
                        viewModel.deleteUser(it)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            binding.incActionBar.txtMenuAdd.visibility = View.VISIBLE
        } else {
            super.onBackPressed()
        }
    }

}