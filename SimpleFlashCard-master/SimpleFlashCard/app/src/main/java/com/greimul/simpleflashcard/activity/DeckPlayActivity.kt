package com.greimul.simpleflashcard.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.greimul.simpleflashcard.R
import com.greimul.simpleflashcard.adapter.CardAdapter
import com.greimul.simpleflashcard.viewmodel.CardViewModel
import kotlinx.android.synthetic.main.activity_deck_play.*
import kotlinx.android.synthetic.main.fragment_deck.*
import java.lang.Math.abs

class DeckPlayActivity: AppCompatActivity() {

    private lateinit var cardViewModel:CardViewModel
    private lateinit var deckPlayAdapter: CardAdapter

    var isAllFlip = false

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                super.onBackPressed()
                return true
            }
            else-> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck_play)
        setSupportActionBar(toolbar_deck_play)
        val deckId = intent.getIntExtra("deckId",0)
        val deckName = intent.getStringExtra("deckName")

        supportActionBar?.title = deckName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_48px)


        deckPlayAdapter = CardAdapter(seekbar_deck_play,1) { a, b->}

        cardViewModel = ViewModelProvider(this,
            object:ViewModelProvider.Factory{
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return CardViewModel(application,deckId) as T
                }
            })
            .get(CardViewModel::class.java)

        cardViewModel.cardList.observe(this,
            Observer {
                    cards -> deckPlayAdapter.setCards(cards)
            }
        )

        val previewPx = resources.getDimension(R.dimen.viewpager2_preview)
        val pageMarginPx = resources.getDimension(R.dimen.viewpager2_page_margin)

        //deckPlayAdapter.setHasStableIds(true) //to solve distorted card when call notifyDataSetChanged
        viewpager2_deck_play.apply{
            adapter = deckPlayAdapter
            clipToPadding = false
            offscreenPageLimit = 1
            setPageTransformer{
                page,position->
                page.translationX = -(previewPx+pageMarginPx)*position
                page.scaleY = 1-(0.25f*abs(position))
            }
            registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    seekbar_deck_play.progress = position
                }
            })
        }

        seekbar_deck_play.apply{
            max = 0
            setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    viewpager2_deck_play.currentItem = progress
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }

        button_flip_all.setOnClickListener {
            if(deckPlayAdapter.itemCount!=0)
                deckPlayAdapter.flipAllCards()
            isAllFlip = !isAllFlip
            if(isAllFlip)
                button_flip_all.text = "All:\nBack"
            else
                button_flip_all.text = "All:\nFront"
        }

        button_bottom_left.setOnClickListener {
            viewpager2_deck_play.currentItem--
        }
        button_bottom_right.setOnClickListener {
            viewpager2_deck_play.currentItem++
        }
        button_bottom_flip.setOnClickListener {
            deckPlayAdapter.flipCard(viewpager2_deck_play.currentItem)
        }
        button_random.setOnClickListener {
            if(deckPlayAdapter.itemCount!=0) {
                var pos = viewpager2_deck_play.currentItem
                while(pos==viewpager2_deck_play.currentItem)
                    pos = (0 until deckPlayAdapter.itemCount).random()
                viewpager2_deck_play.currentItem = pos
            }
        }
        button_shuffle.setOnClickListener {
            deckPlayAdapter.shuffleCards()
        }
    }
}