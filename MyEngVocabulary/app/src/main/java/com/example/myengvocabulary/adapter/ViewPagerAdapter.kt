package com.example.myengvocabulary.adapter

import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myengvocabulary.fragment.DeckFragment
import com.example.myengvocabulary.fragment.ErrorFragment
import com.example.myengvocabulary.fragment.ImExportFragment
import com.example.myengvocabulary.fragment.InfoFragment
import com.example.myengvocabulary.viewmodel.CardViewModel
import com.example.myengvocabulary.viewmodel.DeckViewModel

class ViewPagerAdapter(fa: FragmentActivity, val deckViewModel: DeckViewModel, val cardViewModel: CardViewModel): FragmentStateAdapter(fa) {

    val PAGE_CNT = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> DeckFragment(deckViewModel)
            1 -> ImExportFragment(deckViewModel,cardViewModel)
            2 -> InfoFragment(deckViewModel)
            else -> ErrorFragment()
        }
    }

    override fun getItemCount(): Int  = PAGE_CNT

}