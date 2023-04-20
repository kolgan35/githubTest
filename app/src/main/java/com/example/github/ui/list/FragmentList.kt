package com.example.github.ui.list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.github.R
import com.example.github.data.config.Storage.ACCESS_TOKEN
import com.example.github.databinding.FragmentListBinding
import com.example.github.domain.models.GitHubItem
import com.example.github.domain.models.TypeItem
import com.example.github.ui.adapter.item.ItemAdapter
import com.example.github.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentList: Fragment(R.layout.fragment_list), ItemAdapter.OnItemClickListener {

private val viewModel by viewModels<ListViewModel>()
    private var itemAdapter: ItemAdapter by autoCleared()
    private val binding by viewBinding(FragmentListBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleLive()
        initView()
        Log.d("TOKEN", ACCESS_TOKEN)
    }

    private fun initView() {
        itemAdapter = ItemAdapter(this)
        with(binding.rv) {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
        }
        binding.searchBtn.setOnClickListener {
            viewModel.getInfo(binding.nameET.text.toString())

        }
        binding.nameET.doAfterTextChanged {
            binding.searchBtn.isEnabled = binding.nameET.text.length > 2
        }
        binding.tryAgeinBtn.setOnClickListener {
            viewModel.getInfo(binding.nameET.text.toString())
        }

    }


    private fun handleLive() {
        viewModel.itemsList.observe(viewLifecycleOwner) {
            itemAdapter.submitList(it)
        }
        viewModel.searchLive.observe(viewLifecycleOwner) {
            binding.searchBtn.isEnabled = !it
            binding.nameET.isEnabled = !it
            binding.progress.isGone = !it
        }
        viewModel.failLive.observe(viewLifecycleOwner) {
            if (it.second) {
                binding.errMsg.text = it.first
                binding.tryAgeinBtn.isGone = false
                binding.errMsg.isGone = false
                binding.rv.isGone = true
            } else {
                binding.rv.isGone = false
                binding.tryAgeinBtn.isGone = true
                binding.errMsg.isGone = true
            }
        }
    }

    override fun onItemClick(view: View, item: GitHubItem) {
        when(item.getViewType()) {
            TypeItem.User -> {
                val currentItem = item as GitHubItem.User
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(currentItem.htmlUrl ?: "")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                requireActivity().startActivity(intent)
            }
            TypeItem.Repository -> {
                val currentItem = item as GitHubItem.Repository
                findNavController().navigate(
                    FragmentListDirections.actionFragmentListToContentListFragment(
                        currentItem.owner.login,
                        currentItem.name
                    )
                )
            }
        }
    }


}