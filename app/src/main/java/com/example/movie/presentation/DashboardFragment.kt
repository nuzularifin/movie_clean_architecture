package com.example.movie.presentation

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.R
import com.example.movie.core.extentions.goneMultipleViews
import com.example.movie.core.extentions.reObserve
import com.example.movie.core.extentions.showToast
import com.example.movie.core.extentions.visibleMultipleViews
import com.example.movie.data.model.Movie
import com.example.movie.databinding.FragmentFirst2Binding
import com.example.movie.presentation.adapter.ActionAdapter
import com.example.movie.presentation.adapter.LatestAdapter
import com.example.movie.presentation.adapter.MovieRecommendationAdapter
import com.example.movie.presentation.adapter.SearchMovieAdapter
import com.example.movie.presentation.viewmodel.MovieViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentFirst2Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var movieRecommendAdapter: MovieRecommendationAdapter
    lateinit var latestAdapter: LatestAdapter
    lateinit var actionAdapter: ActionAdapter
    lateinit var searchMovieAdapter: SearchMovieAdapter
    private val movies: ArrayList<Movie> = arrayListOf()
    private val latestMovies: ArrayList<Movie> = arrayListOf()
    private val actionMovies: ArrayList<Movie> = arrayListOf()
    private val searchMovie: ArrayList<Movie> = arrayListOf()

    private val viewModel: MovieViewModel by viewModels()

    lateinit var menuHost: MenuHost

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirst2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        menuHost = (requireActivity() as MenuHost)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        movieRecommendAdapter = MovieRecommendationAdapter(
            requireContext(),
            movies
        )
        binding.viewPager.adapter = movieRecommendAdapter
        TabLayoutMediator( binding.tabIndicator, binding.viewPager){ tab, position -> }.attach()
        movieRecommendAdapter.OnClickMovieDetail = {
            openMovieDetails(it.id)
        }

        val latestLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvLatest.layoutManager = latestLayoutManager
        latestAdapter = LatestAdapter(
            requireContext(),
            latestMovies
        )
        binding.rvLatest.adapter = latestAdapter
        latestAdapter.onClickMovieDetail = { movie ->
            openMovieDetails(movie.id)
        }

        val actionLayoutManger = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvAction.layoutManager = actionLayoutManger
        actionAdapter = ActionAdapter(
            requireContext(),
            latestMovies
        )
        binding.rvAction.adapter = actionAdapter
        actionAdapter.onClickMovieDetail = { movie ->
            openMovieDetails(movie.id)
        }

        binding.rvMovieSearch.layoutManager = GridLayoutManager(requireContext(), 2)
        searchMovieAdapter = SearchMovieAdapter(
            requireContext(),
            searchMovie
        )
        binding.rvMovieSearch.adapter = searchMovieAdapter
        searchMovieAdapter.onClickMovieDetail = { movie ->
            openMovieDetails(movie.id)
        }

        viewModel.fetchMovies(page = 1)
        viewModel.fetchLatestMovie(page = 1)
        viewModel.fetchActionMovies(withGenre = 28)

        setupObserver()
    }

    private fun setupToolbar() {
        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
                val myActionMenuItem = menu.findItem(R.id.action_search)
                val searchView = myActionMenuItem.actionView as SearchView?
                searchView?.queryHint = Html.fromHtml("<font color=#FFFFFF> Search Movie </font>")


                val searchEditText = searchView?.context?.resources?.getIdentifier("android:id/search_src_text",null, null)
                val editText: EditText? = searchEditText?.let { searchView.findViewById(it) }
                editText?.setTextColor(resources.getColor(R.color.white))
                editText?.setHintTextColor(resources.getColor(R.color.white))


                val iconClose = searchView?.context?.resources?.getIdentifier("android:id/search_close_btn",null, null)
                val icClose: ImageView? = iconClose?.let { searchView?.findViewById(it) }
                icClose?.setColorFilter(Color.WHITE)

                val iconSearch = searchView?.context?.resources?.getIdentifier("android:id/search_button",null, null)
                val icSearch: ImageView? = iconSearch?.let { searchView?.findViewById(it) }
                icSearch?.setColorFilter(Color.WHITE)

                searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if(!searchView.isIconified) {
                            searchView.isIconified = true;
                        }
                        if (query?.isEmpty() == true){
                            showDashboardItems()
                        } else {
                            query?.let { viewModel.fetchSearchMovie(query = query) }
                        }
                        myActionMenuItem.collapseActionView()
                        return false
                    }

                    override fun onQueryTextChange(query: String?): Boolean {
                        if (query?.isEmpty() == true){
                            showDashboardItems()
                        }
                        return false
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun openMovieDetails(id: Long) {
        val bundle = Bundle()
        bundle.putLong("movie", id)
        findNavController().navigate(com.example.movie.R.id.action_First2Fragment_to_Second2Fragment, bundle)
    }

    private fun setupObserver() {
        with(viewModel) {
            auth.reObserve(requireActivity()) {

            }

            movieList.reObserve(requireActivity()) {
                it.forEach { movie ->
                    movies.add(movie)
                }
                movieRecommendAdapter.notifyDataSetChanged()
            }

            latestMovieList.reObserve(requireActivity()){
                it.forEach { latest ->
                    latestMovies.add(latest)
                }
                latestAdapter.notifyDataSetChanged()
            }

            actionMovieList.reObserve(requireActivity()){
                it.forEach { actions ->
                    actionMovies.add(actions)
                }
                actionAdapter.notifyDataSetChanged()
            }

            searchMovieList.reObserve(requireActivity()){
                if (it.isEmpty()){
                    requireContext().showToast("Movie doesn't exist")
                    hideDashboardItemsShowsEmpty()
                } else {
                    searchMovieAdapter.updateList(it)
                    hideDashboardItems()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hideDashboardItems(){
        visibleMultipleViews(
            binding.rvMovieSearch
        )

        goneMultipleViews(
            binding.rvAction,
            binding.rvLatest,
            binding.viewPager,
            binding.tabIndicator,
            binding.tvTitleAction,
            binding.tvTitleLatest,
            binding.llEmpty
        )

    }

    private fun hideDashboardItemsShowsEmpty(){
        visibleMultipleViews(
            binding.llEmpty
        )

        goneMultipleViews(
            binding.rvMovieSearch,
            binding.rvAction,
            binding.rvLatest,
            binding.viewPager,
            binding.tabIndicator,
            binding.tvTitleAction,
            binding.tvTitleLatest
        )
    }

    private fun showDashboardItems(){
        visibleMultipleViews(
            binding.viewPager,
            binding.tabIndicator,
            binding.rvAction,
            binding.rvLatest,
            binding.tvTitleAction,
            binding.tvTitleLatest
        )

        goneMultipleViews(
            binding.rvMovieSearch,
            binding.llEmpty
        )
    }


}