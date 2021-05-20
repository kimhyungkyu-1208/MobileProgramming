package com.example.myengvocabulary.activity

import android.app.AlertDialog
import android.widget.Button
import com.example.myengvocabulary.viewmodel.CardViewModel as CardViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.viewpager2.widget.ViewPager2
import com.example.myengvocabulary.R
import com.example.myengvocabulary.R.*
import com.example.myengvocabulary.R.drawable.*
import com.example.myengvocabulary.adapter.ViewPagerAdapter
import com.example.myengvocabulary.database.Deck
import com.example.myengvocabulary.viewmodel.DeckViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.floatingactionbutton.FloatingActionButton as FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var viewPager2: ViewPager2
    lateinit var tabLayout: TabLayout
    lateinit var deckFab: FloatingActionButton
    private lateinit var deckViewModel: DeckViewModel
    private lateinit var cardViewModel: CardViewModel
    val tabLayoutTextArray = arrayOf("Deck","Im/Export","Info")
    val tabLayoutIconArray by lazy {
        arrayOf(
            ic_view_list_48px,
            ic_import_export_48px,
            ic_info_48px
    )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar_main))
        deckViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(DeckViewModel::class.java)

        cardViewModel = ViewModelProvider(this, object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CardViewModel(application, -1) as T
            }
        }).get(CardViewModel::class.java)

        tabLayout = findViewById<TabLayout>(R.id.tabLayout_main)
        tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                deckFab = findViewById(R.id.fab_main)
               if(tab == null)
                   deckFab.hide()
                else{
                    if(tab.position == 0)
                        deckFab.show()

                   else
                       deckFab.hide()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
            }
        })

        viewPager2 = findViewById(R.id.viewPager2_main)
        viewPager2.adapter = ViewPagerAdapter(this, deckViewModel, cardViewModel)
        TabLayoutMediator(tabLayout, viewPager2){tab, position ->
            tab.text = tabLayoutTextArray[position]
            tab.setIcon(tabLayoutIconArray[position])
        }.attach()

        deckFab = findViewById<FloatingActionButton>(R.id.fab_main)
        deckFab.setOnClickListener {
            val dialog = AlertDialog.Builder(this,R.style.DialogStyle)
            val dialogView = layoutInflater.inflate(R.layout.dialog_new_deck,null)
            dialog.setView(dialogView).setPositiveButton("OK") {
                    dialog,i->
                val deck = Deck(0,
                    dialogView.findViewById<EditText>(R.id.edittext_new_name).text.toString(),
                    dialogView.findViewById<EditText>(R.id.edittext_new_desc).text.toString(),0)
                deckViewModel.insert(deck)
            }.setNegativeButton("Cancel"){
                    dialog,i->
            }.show()
        }
    }
}