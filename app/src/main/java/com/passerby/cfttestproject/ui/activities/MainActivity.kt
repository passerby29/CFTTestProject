package com.passerby.cfttestproject.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.passerby.cfttestproject.bussiness.BaseResponse
import com.passerby.cfttestproject.bussiness.room.RequestsEntity
import com.passerby.cfttestproject.databinding.ActivityMainBinding
import com.passerby.cfttestproject.ui.adapters.RequestsRVAdapter
import com.passerby.cfttestproject.ui.viewmodels.MainViewModel


class MainActivity : AppCompatActivity(), RequestsRVAdapter.RequestClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var requestsRVAdapter: RequestsRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchLayout.setEndIconOnClickListener {
            val bin = binding.searchLayout.editText?.text.toString()
            if (bin.isNotEmpty() && bin.length > 3) {
                viewModel.insert(RequestsEntity(bin))
                viewModel.getBIN(bin)
            }

            viewModel.result.observe(this) {
                when (it) {
                    is BaseResponse.Loading -> {
                        showLoading()
                    }

                    is BaseResponse.Success -> {
                        stopLoading()
                        binding.container.visibility = View.VISIBLE
                        if (bin.length == 8) {
                            binding.apply {
                                schemeTv.text = it.data?.scheme?.titleCaseFirstChar()
                                brandTv.text = it.data?.brand
                                lengthTv.text = it.data?.number?.length.toString()
                                luhnTv.text = if (it.data?.number?.luhn == true) {
                                    "Yes"
                                } else {
                                    "No"
                                }
                                typeTv.text = it.data?.type?.titleCaseFirstChar()
                                prepaidTv.text = if (it.data?.prepaid == true) {
                                    "Yes"
                                } else {
                                    "No"
                                }
                                countryMainTv.text =
                                    java.lang.StringBuilder().append(it.data?.country?.name)
                                        .append(", ")
                                        .append(it.data?.country?.emoji).append(", ")
                                        .append(it.data?.country?.currency)
                                countryCoordinatesTv.apply {
                                    text = StringBuilder().append("(latitude: ")
                                        .append(it.data?.country?.latitude).append(", ")
                                        .append("longitude").append(it.data?.country?.longitude)
                                        .append(")")

                                    val url =
                                        "geo:" + it.data?.country?.latitude.toString() + "," +
                                                it.data?.country?.longitude + "?z=22&q=" +
                                                it.data?.country?.latitude.toString() + "," +
                                                it.data?.country?.longitude

                                    setOnClickListener {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                        startActivity(intent)
                                    }
                                }
                                bankNameTv.text = it.data?.bank?.name
                                bankUrlTv.text = it.data?.bank?.url
                                bankPhoneTv.text = it.data?.bank?.phone
                            }
                        }
                    }

                    is BaseResponse.Error -> {
                        processError(it.msg)
                        stopLoading()
                    }
                    else -> {
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MainViewModel::class.java]

        requestsRVAdapter = RequestsRVAdapter(this)

        binding.searchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(value: Editable?) {
                if (!value.isNullOrEmpty()) {
                    binding.predictionsContainer.visibility = View.VISIBLE
                    viewModel.requestList.observe(this@MainActivity) { list ->
                        list?.let { requestsRVAdapter.updateList(it) }
                        binding.requestsRv.apply {
                            layoutManager = LinearLayoutManager(
                                this@MainActivity, LinearLayoutManager.VERTICAL, false
                            )
                            adapter = requestsRVAdapter
                        }
                    }
                } else {
                    binding.predictionsContainer.visibility = View.GONE
                }
            }
        })
    }

    private fun processError(msg: String?) {
        showToast("Error:$msg")
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        binding.loading.visibility = View.GONE
    }

    override fun onRequestClickListener(item: RequestsEntity) {
        binding.searchField.setText(item.request)
    }

    private fun String.titleCaseFirstChar() = replaceFirstChar(Char::titlecase)
}
