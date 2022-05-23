package `in`.aatechnologies.tictactoe

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private var playerOneTurn = true
    private var playerOneMoves = mutableListOf<Int>()
    private var playerTwoMoves = mutableListOf<Int>()


    private val possibleWins : Array<List<Int>> = arrayOf(

        // Horizontal Wins
        listOf(1,2,3),
        listOf(4,5,6),
        listOf(7,8,9),

        // Vertical Wins
        listOf(1,4,7),
        listOf(2,5,8),
        listOf(3,6,9),

        // Diagonal Wins
        listOf(1,5,9),
        listOf(3,5,7)
    )




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBoard()
    }

    private fun setupBoard() {
        var counter = 1

        val params1 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0
        )

        val params2 = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT,
        )

        for(i in 1..3) {
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.layoutParams = params1
            params1.weight = 1.0F

            for(j in 1..3) {
                val button = Button(this)
                button.id = counter
                button.textSize = 42.0F
                button.setTextColor(ContextCompat.getColor(this, R.color.purple_700))

                button.layoutParams = params2
                params2.weight = 1.0F
                button.setOnClickListener {
                    recordMove(it)
                }
                linearLayout.addView(button)
                counter++
            }
            board.addView(linearLayout)
        }
    }

    private fun recordMove(view: View) {
        val button = view as Button
        val id = button.id

        if(playerOneTurn) {
            playerOneMoves.add(id)
            button.text = "O"
            button.isEnabled = false

            if(checkWin(playerOneMoves)) {
                showWinMessage(player_one_edit_text)
            }
            else {
                playerOneTurn = false
                togglePlayerTurn(player_two_text_view, player_one_text_view)
            }
        }
        else {
            playerTwoMoves.add(id)
            button.text = "X"
            if(checkWin(playerTwoMoves)) {
                showWinMessage(player_two_edit_text)
            }
            else {
                playerOneTurn = true
                togglePlayerTurn(player_one_text_view, player_two_text_view)
            }
        }

    }

    /**
     * TODO : After a player has won or no more moves applicable reset the board
     */
    private fun resetBoard() {
    }

    private fun checkWin(moves : List<Int>) : Boolean {
        var won = false
        if(moves.size >= 3) {
            run loop@{
                possibleWins.forEach {
                    if(moves.containsAll(it)) {
                        won = true
                        return@loop
                    }
                }
            }
        }
        return won
    }

    private fun showWinMessage(player : EditText) {
        var playerName = player.text.toString()
        if(playerName.isBlank()) {
            playerName = player.hint.toString()
        }

        Toast.makeText(this, "Congratulations! $playerName You Won", Toast.LENGTH_SHORT).show()
    }

    private fun togglePlayerTurn(playerOn : TextView, playerOff : TextView) {
        playerOff.setTextColor(
            ContextCompat.getColor(this, R.color.black))
        playerOff.setTypeface(null, Typeface.NORMAL)
        playerOn.setTextColor(
            ContextCompat.getColor(this, R.color.purple_700))
        playerOn.setTypeface(null, Typeface.BOLD)
    }
}