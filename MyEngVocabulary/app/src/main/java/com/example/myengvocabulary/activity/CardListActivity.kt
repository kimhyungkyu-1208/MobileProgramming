package com.example.myengvocabulary.activity

import android.animation.Animator
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myengvocabulary.R
import com.example.myengvocabulary.adapter.CardAdapter
import com.example.myengvocabulary.database.Card
import com.example.myengvocabulary.viewmodel.CardViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Math.hypot

class CardListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cardListAdapter: CardAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var cardViewModel: CardViewModel

    private var deckId:Int = 0

    var isAllFlip:Boolean = false

    var isAddCardOpen:Boolean = false

    var isEditCard:Boolean = false
    var selectedEditCardId:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar_card_list))
        supportActionBar?.title = "Card List"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_48px)

        findViewById<LinearLayout>(R.id.linearlayout_card_list).visibility = View.INVISIBLE
        deckId = intent.getIntExtra("deckId",0)

        cardViewModel = ViewModelProvider(this,
            object: ViewModelProvider.Factory{
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return CardViewModel(application,deckId) as T
                }
            }).get(CardViewModel::class.java)

        cardListAdapter = CardAdapter(null,0,::openAddCard)
        viewManager = GridLayoutManager(applicationContext,2)

        cardViewModel.cardList.observe(this,
            Observer {
                    cards->cardListAdapter.setCards(cards)
            }
        )

        recyclerView = findViewById<RecyclerView>(R.id.recyclerview_card).apply{
            setHasFixedSize(true)
            adapter = cardListAdapter
            layoutManager = viewManager
        }

        findViewById<Button>(R.id.fab_card).setOnClickListener {
            openAddCard(false,0)
            /*
            val dialog = AlertDialog.Builder(this,R.style.DialogFullScreen)
            val dialogView = layoutInflater.inflate(R.layout.dialog_new_card,null)

            dialogView.toolbar_new_card.apply{
                title = "New Card"
                setNavigationIcon(R.drawable.ic_close_48px)
                setNavigationOnClickListener {

                }
            }
            dialog.setOnDismissListener {
                recyclerview_card.visibility = View.VISIBLE
                fab_card.show()
            }
            dialog.setView(dialogView).show()
                //recyclerView.scrollToPosition(cardListAdapter.itemCount)

             */
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(!isAddCardOpen)
            menuInflater.inflate(R.menu.menu_card_list,menu)
        else
            if(isEditCard)
                menuInflater.inflate(R.menu.menu_card_list_edit, menu)
            else
                menuInflater.inflate(R.menu.menu_card_list_add, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val edittext_card_add_front = findViewById<EditText>(R.id.edittext_card_add_front)
        val edittext_card_add_back = findViewById<EditText>(R.id.edittext_card_add_back)
        when(item.itemId){
            android.R.id.home -> {
                if(isAddCardOpen)
                    closeAddCard()
                else
                    super.onBackPressed()
                return true
            }
            R.id.menu_card_list_flip -> {
                cardListAdapter.flipAllCards()
                return true
            }
            R.id.menu_card_list_add->{
                cardViewModel.insert(Card(0,edittext_card_add_front.text.toString(),edittext_card_add_back.text.toString(),deckId))
                closeAddCard()
                return true
            }
            R.id.menu_card_list_delete->{
                if(selectedEditCardId!=-1)
                    cardViewModel.delete(selectedEditCardId)
                closeAddCard()
                return true
            }
            R.id.menu_card_list_save->{
                if(selectedEditCardId!=-1)
                    cardViewModel.updateCard(selectedEditCardId,edittext_card_add_front.text.toString(),edittext_card_add_back.text.toString())
                closeAddCard()
                return true
            }

            else-> return super.onOptionsItemSelected(item)
        }
    }
    override fun onBackPressed() {
        if(isAddCardOpen)
            closeAddCard()
        else
            super.onBackPressed()
    }
    fun openAddCard(isEdit:Boolean,cardId:Int = 0){
        isEditCard = isEdit
        selectedEditCardId = cardId
        isAddCardOpen = true
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar_card_list_add))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_48px)

        if(isEdit){
            cardViewModel.selectCard(cardId).observe(this, object:Observer<Card>{
                override fun onChanged(t: Card?) {
                    if(t!=null){
                        findViewById<EditText>(R.id.edittext_card_add_front).setText(t.front)
                        findViewById<EditText>(R.id.edittext_card_add_back).setText(t.back)
                    }
                    cardViewModel.selectCard(cardId).removeObserver(this)
                }
            })
        }
        val linearlayout_card_list = findViewById<LinearLayout>(R.id.linearlayout_card_list)
        val coordinatorlayout_card_list = findViewById<CoordinatorLayout>(R.id.coordinatorlayout_card_list)
        var openAddCardAnimation = ViewAnimationUtils.createCircularReveal(
            linearlayout_card_list,
            coordinatorlayout_card_list.right,
            coordinatorlayout_card_list.bottom,
            0f,
            hypot(
                coordinatorlayout_card_list.width.toDouble(),
                coordinatorlayout_card_list.height.toDouble()
            ).toFloat()
        )
        linearlayout_card_list.visibility = View.VISIBLE
        openAddCardAnimation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                findViewById<RecyclerView>(R.id.recyclerview_card).visibility = View.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animator?) {}

            override fun onAnimationStart(animation: Animator?) {}
        })
        findViewById<FloatingActionButton>(R.id.fab_card).hide()
        openAddCardAnimation.start()
    }

    fun closeAddCard(){
        val IMS: InputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        IMS.hideSoftInputFromWindow(this.currentFocus?.windowToken,0)
        isEditCard = false
        selectedEditCardId = -1
        isAddCardOpen = false
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar_card_list))
        supportActionBar?.title = "Card List"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_48px)
        var closeAddCardAnimation = ViewAnimationUtils.createCircularReveal(
            findViewById<LinearLayout>(R.id.linearlayout_card_list),
            findViewById<CoordinatorLayout>(R.id.coordinatorlayout_card_list).right,
            findViewById<CoordinatorLayout>(R.id.coordinatorlayout_card_list).bottom,
            Math.max(findViewById<LinearLayout>(R.id.linearlayout_card_list).width,
                findViewById<LinearLayout>(R.id.linearlayout_card_list).height).toFloat(),
            0f)
        closeAddCardAnimation.addListener(object: Animator.AnimatorListener{
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                findViewById<LinearLayout>(R.id.linearlayout_card_list).visibility = View.INVISIBLE
                findViewById<EditText>(R.id.edittext_card_add_back).text.clear()
                findViewById<EditText>(R.id.edittext_card_add_front).text.clear()
                findViewById<FloatingActionButton>(R.id.fab_card).setImageResource(R.drawable.ic_create_48px)
                findViewById<FloatingActionButton>(R.id.fab_card).show()
            }
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
        })
        closeAddCardAnimation.start()
        findViewById<RecyclerView>(R.id.recyclerview_card).visibility = View.VISIBLE
    }
}