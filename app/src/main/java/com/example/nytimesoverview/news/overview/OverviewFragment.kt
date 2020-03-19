package com.example.nytimesoverview.news.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.nytimesoverview.R
import com.example.nytimesoverview.databinding.OverviewFragmentBinding
import com.example.nytimesoverview.network.NewsPeriodFilter
import com.example.nytimesoverview.news.overview.model.NewsViewModel
import kotlinx.android.synthetic.main.overview_fragment.*

class OverviewFragment : Fragment() {

    private val viewModel: NewsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, NewsViewModel.Factory(activity.application))
            .get(NewsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = OverviewFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        binding.viewModel = viewModel
        binding.newsList.adapter = NewsGridAdapter(NewsGridAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedProperty.observe(this, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    OverviewFragmentDirections.actionOverviewFragmentToNewsDetailsFragment(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        viewModel.filterPeriod.observe(this, Observer {
            when(it) {
                NewsPeriodFilter.LAST_DAY -> radio_one_day.toggle()
                NewsPeriodFilter.SEVEN_DAYS -> radio_seven_days.toggle()
                else -> radio_thirty_days.toggle()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.news_period_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        viewModel.updateFilter(when(item.itemId) {
            R.id.seven_days_menu ->  NewsPeriodFilter.SEVEN_DAYS
            R.id.last_day_menu -> NewsPeriodFilter.LAST_DAY
            else -> NewsPeriodFilter.THIRTY_DAYS
        })
        return true;
    }

}
