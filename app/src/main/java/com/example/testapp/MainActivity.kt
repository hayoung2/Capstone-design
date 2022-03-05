package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction

import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.fragments.HomeFragment
import com.example.testapp.fragments.ScheduleFragment
import com.example.testapp.fragments.SearchFragment


class MainActivity : AppCompatActivity() {


    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!
    lateinit var homeFragment: HomeFragment
    lateinit var SearchFragment:SearchFragment
    lateinit var ScheduleFragment:ScheduleFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeFragment= HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout,homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        binding.BottomNavMenu.setOnNavigationItemSelectedListener { item->
            when(item.itemId){
                R.id.home->{
                    homeFragment= HomeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.search->{
                    SearchFragment= SearchFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,SearchFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.schdule->{
                    ScheduleFragment= ScheduleFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,ScheduleFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }

            true
        }

    }
}