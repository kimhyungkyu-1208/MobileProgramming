package com.example.myengvocabulary.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.myengvocabulary.R
import com.example.myengvocabulary.R.id.toolbar_deck_play
import com.example.myengvocabulary.R.id.viewpager2_deck_play
import com.example.myengvocabulary.adapter.CardAdapter
import com.example.myengvocabulary.viewmodel.CardViewModel
import java.lang.Math.abs

class DeckPlayActivity : AppCompatActivity() {

    private lateinit var cardViewModel: CardViewModel
    private lateinit var deckPlayAdapter: CardAdapter


    var isAllFlip = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck_play)

        setSupportActionBar(findViewById(R.id.toolbar_deck_play))

        val deckId = intent.getIntExtra("deckId",0)
        val deckName = intent.getStringExtra("deckName")

        supportActionBar?.title = deckName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_48px)

        deckPlayAdapter = CardAdapter(findViewById(R.id.seekbar_deck_play),1) { a, b->}

        cardViewModel = ViewModelProvider(this,
            object: ViewModelProvider.Factory{
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

        viewpager2_deck_play.apply {
            val adapter = deckPlayAdapter
            val clipToPadding = false
            val offscreenPageLimit = 1
            lateinit var ViewPager2:ViewPager2
            ViewPager2.setPageTransformer { page, position ->
                page.translationX = -(previewPx + pageMarginPx) * position
                page.scaleY = 1 - (0.25f * abs(position))
            }
            ViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    findViewById<SeekBar>(R.id.seekbar_deck_play).progress = position
                }
            })
        }

        findViewById<SeekBar>(R.id.seekbar_deck_play).apply{
            max = 0
            setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    findViewById<ViewPager2>(R.id.viewpager2_deck_play).currentItem = progress
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }
        val button_flip_all = findViewById<Button>(R.id.button_flip_all)
        button_flip_all.setOnClickListener {
            if(deckPlayAdapter.itemCount!=0)
                deckPlayAdapter.flipAllCards()
            isAllFlip = !isAllFlip
            if(isAllFlip)
                button_flip_all.text = "All:\nBack"
            else
                button_flip_all.text = "All:\nFront"
        }

        val viewpager2_deck_play = findViewById<ViewPager2>(R.id.viewpager2_deck_play)
        findViewById<Button>(R.id.button_bottom_left).setOnClickListener {
            viewpager2_deck_play.currentItem--
        }
        findViewById<Button>(R.id.button_bottom_right).setOnClickListener {
            viewpager2_deck_play.currentItem++
        }
        findViewById<Button>(R.id.button_bottom_flip).setOnClickListener {
            deckPlayAdapter.flipCard(viewpager2_deck_play.currentItem)
        }
        findViewById<Button>(R.id.button_random).setOnClickListener {
            if(deckPlayAdapter.itemCount!=0) {
                var pos = viewpager2_deck_play.currentItem
                while(pos==viewpager2_deck_play.currentItem)
                    pos = (0 until deckPlayAdapter.itemCount).random()
                viewpager2_deck_play.currentItem = pos
            }
        }
        findViewById<Button>(R.id.button_shuffle).setOnClickListener {
            deckPlayAdapter.shuffleCards()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                super.onBackPressed()
                return true
            }
            else-> return super.onOptionsItemSelected(item)
        }
    }
}