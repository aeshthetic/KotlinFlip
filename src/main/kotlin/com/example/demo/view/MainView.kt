package com.example.demo.view

import com.example.demo.app.Styles
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Side
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.layout.BackgroundPosition
import javafx.scene.layout.BackgroundSize
import javafx.scene.paint.Paint
import tornadofx.*
import java.awt.Color
import java.net.URI
import java.util.Random

class MainView : View("Voltorb Flip") {
    private var level = 1
    //private var levelChosen = SimpleStringProperty()
    private var board = Board(mutableListOf<MutableList<Int>>(), level)

    private val score = SimpleIntegerProperty(1)


    override val root = vbox {
        style {
            backgroundColor += Paint.valueOf("#31b068")
        }
        gridpane {
            row {
                for (i in 1..(5*level)) {
                    button(" ") {
                        gridpaneConstraints {
                            marginTop = 5.0
                            marginBottom = 5.0
                            marginLeft = 5.0
                            marginRight = 5.0
                        }
                        style {
                            backgroundImage += URI("https://s3.amazonaws.com/vflip/images/cellbg.png")
                        }
                        action {
                            if (this.text == " ") {
                                if (board.rows.first()[i - 1] == 0) {
                                    println("You lost!")
                                }
                                score.value *= board.rows[0][i - 1]
                                this.text = "${board.rows[0][i - 1]}"

                            }
                        }
                    }
                }
                label("${rowSum(board, 0)}/${rowVoltorbs(board, 0)}")
            }
            for (i in 1..(3*level)) {
                row {
                    for (j in 1..(5*level)) {
                        button(" ") {
                            gridpaneConstraints {
                                marginTop = 5.0
                                marginBottom = 5.0
                                marginLeft = 5.0
                                marginRight = 5.0
                            }

                            action {
                                if (this.text == " ") {
                                    if (board.rows[i][j - 1] == 0) {
                                        println("You lost!")
                                    }
                                    score.value *= board.rows[i][j - 1]
                                    this.setText("${board.rows[i][j - 1]}")
                                }
                            }
                        }
                    }
                    label("${rowSum(board, i)}/${rowVoltorbs(board, i)}")
                }
            }
            row {
                for (i in 1..(5*level)) {
                    button(" ") {
                        gridpaneConstraints {
                            marginTop = 5.0
                            marginLeft = 5.0
                            marginRight = 5.0
                        }

                        action {
                            if (this.text == " ") {
                                if (board.rows.last()[i - 1] == 0) {
                                    println("You lost!")
                                }
                                score.value *= board.rows.last()[i - 1]
                                this.setText("${board.rows.last()[i - 1]}")
                            }
                        }
                    }
                }
                label("${rowSum(board, board.rows.size-1)}/${rowVoltorbs(board, board.rows.size-1)}")
            }
            row {
                for (i in 1..(5*level)) {
                    label("${columnSum(board, i-1)}\n─\n${columnVoltorbs(board, i-1)}")
                }
            }



            /*row {
                hbox {
                    label("Level: ")
                    textfield(levelChosen)
                }
                button("Reset") {
                    action {
                    board = Board(mutableListOf<MutableList<Int>>(), levelChosen.value.toInt())
                    score.value = 1
                    }
                }
            }*/

            vboxConstraints {
                marginBottom = 10.0
            }
        }

        hbox {
            label("Score: ")
            label {
                bind(score)
            }
        }
    }
}

class Board constructor(var rows: MutableList<MutableList<Int>>, level: Int){
    init {
        for (i in 1..(5*level)) {
            val currentRow = mutableListOf<Int>()
            for (j in 1..(5*level)) {
                currentRow.add(randrange(0, 3))
            }
            rows.add(currentRow)
        }
    }
}

fun randrange(min: Int, max: Int): Int {
    val r = Random()
    return r.nextInt((max - min) + 1) + min
}

fun rowSum(board: Board, row: Int): Int {
    return board.rows[row].fold(0) {total, next -> total + next}
}

fun rowVoltorbs(board: Board, row: Int): Int {
    return board.rows[row].count {it == 0}
}

fun columnSum(board: Board, column: Int): Int {
    val currentColumn = mutableListOf<Int>()
    for (i in 0..(board.rows.size-1)) {
        currentColumn.add(board.rows[i][column])
    }
    return currentColumn.fold(0) {total, next -> total + next}
}

fun columnVoltorbs(board: Board, column: Int): Int {
    val currentColumn = mutableListOf<Int>()
    for (i in 0..(board.rows.size-1)) {
        currentColumn.add(board.rows[i][column])
    }
    return currentColumn.count {it == 0}
}