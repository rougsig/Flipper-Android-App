package com.flipper.app.entity

internal data class CellItem(
  val id: String,
  val text: String,
  val size: Size
) {
  enum class Size(val width: Int, val height: Int) {
    ONE_ONE(1, 1),
    ONE_TWO(1, 2),
    TWO_ONE(2, 1),
    TWO_TWO(2, 2),
  }
}
