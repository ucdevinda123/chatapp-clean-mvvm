package com.app.chopechatdemo.presentation.ui.friends

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.chopechatdemo.R
import com.app.chopechatdemo.databinding.FragmentFriendsBinding
import com.app.chopechatdemo.presentation.ui.adapter.FriendsAdapter
import com.app.chopechatdemo.presentation.viewmodel.FriendsViewModel
import com.app.chopechatdemo.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FriendsFragment : Fragment(R.layout.fragment_friends) {

    private val friendsViewModel: FriendsViewModel by viewModels()
    private lateinit var friendsFragmentBinding: FragmentFriendsBinding

    @Inject
    lateinit var friendsAdapter: FriendsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        friendsFragmentBinding = FragmentFriendsBinding.bind(view)
        initFriendsRecyclerView()
        initSwipeToRefresh()
        lifecycleScope.launch {
            friendsViewModel.getFriendsList()
        }
        friendsViewModel.chatRoomFriends.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    print("Loading...")
                }

                is Resource.Success -> {
                    val userList = it.data
                    print(userList)
                    friendsAdapter.submitList(userList)
                    hidePullToRefreshLoader()
                }

                is Resource.Failure -> {
                    hidePullToRefreshLoader()
                    Toast.makeText(context, it.throwable.localizedMessage, Toast.LENGTH_SHORT)
                        .show()

                }
            }
        })
    }


    private fun initFriendsRecyclerView() {
        handleNavigationClick()
        friendsFragmentBinding.apply {
            rvFriendsList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvFriendsList.adapter = friendsAdapter
            var itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            context?.let { it ->
                AppCompatResources.getDrawable(it,
                    R.drawable.divider)?.let { itemDecoration.setDrawable(it) }
            }
            rvFriendsList.addItemDecoration(itemDecoration)
        }
    }

    private fun initSwipeToRefresh() {
        friendsFragmentBinding.apply {
            friendsSwipeToRefresh.setOnRefreshListener {
                lifecycleScope.launch {
                    friendsViewModel.getFriendsList()
                }
            }
        }
    }

    private fun handleNavigationClick() {
        friendsAdapter.onItemClick = { user ->
            val action = FriendsFragmentDirections.actionFriendsFragmentToChatRoomFragment(user)
            findNavController().navigate(action)
        }
    }

    private fun hidePullToRefreshLoader() {
        friendsFragmentBinding.friendsSwipeToRefresh.isRefreshing = false
    }
}