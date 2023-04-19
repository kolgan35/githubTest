package com.example.github.ui.content

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.github.R
import com.example.github.databinding.FragmentContentListBinding
import com.example.github.domain.models.Content
import com.example.github.ui.adapter.content.ContentAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContentListFragment : Fragment(R.layout.fragment_content_list),
    ContentAdapter.OnContentClickListener {

    private val binding by viewBinding(FragmentContentListBinding::bind)
    private val viewModel by viewModels<ContentListViewModel>()
    private val args: ContentListFragmentArgs by navArgs()
    var callback: OnBackPressedCallback? = null
    private val contentAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ContentAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        if (viewModel.dirStack.size < 1) {
            viewModel.pushDir("")
            val path = viewModel.peepDir()
            viewModel.getContent(args.owner, args.repo, path)
        } else {
            val path = viewModel.peepDir()
            viewModel.getContent(args.owner, args.repo, path)
        }
        handleData()
        onBackPressed()
        handleClick()
    }

    private fun onBackPressed() {
        callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.popDir()
            val path = viewModel.peepDir()
            if (path != null) {
                viewModel.getContent(args.owner, args.repo, path)
            } else {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }
    private fun handleClick() {
        binding.tryAgeinBtn.setOnClickListener {
            if (viewModel.dirStack.size < 1) {
                viewModel.pushDir("")
                val path = viewModel.peepDir()
                viewModel.getContent(args.owner, args.repo, path)
            } else {
                val path = viewModel.peepDir()
                viewModel.getContent(args.owner, args.repo, path)
            }
        }
    }

    private fun initView() {
        with(binding.rv) {
            adapter = contentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
        }

    }


    private fun handleData() {
        viewModel.contentLive.observe(viewLifecycleOwner) {
            contentAdapter.submitList(it)
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


    override fun onContentClick(view: View, item: Content) {
        when (item.type) {
            "dir" -> {
                viewModel.pushDir(item.path)
                viewModel.getContent(args.owner, args.repo, item.path)
            }
            else -> {
                findNavController().navigate(
                    ContentListFragmentDirections.actionContentListFragmentToWebViewFragment(
                        uri = item.htmlUrl
                    )
                )
            }
        }
    }
}