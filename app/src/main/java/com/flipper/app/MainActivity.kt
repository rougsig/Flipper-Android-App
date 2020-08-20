package com.flipper.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.epoxy.EpoxyTouchHelper
import com.airbnb.epoxy.EpoxyTouchHelper.DragCallbacks
import com.flipper.app.entity.CellItem
import com.flipper.app.entity.CellItem.Size
import com.flipper.app.epoxy.MainEpoxyController
import com.flipper.app.view.CellItemViewModel_
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  private val sizeGenerator = generateSequence(Size.ONE_ONE) { size ->
    when (size) {
      Size.ONE_ONE -> Size.ONE_TWO
      Size.ONE_TWO -> Size.TWO_ONE
      Size.TWO_ONE -> Size.TWO_TWO
      Size.TWO_TWO -> Size.ONE_ONE
    }
  }.iterator()

  private val epoxyController = MainEpoxyController()
  private var items: MutableList<CellItem> = mutableListOf()
    set(value) {
      field = value
      epoxyController.setData(items)
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val spanCount = 2
    val layoutManager = StaggeredGridLayoutManager(spanCount, RecyclerView.VERTICAL)
    epoxyController.spanCount = spanCount
    recycler_view.layoutManager = layoutManager
    recycler_view.setController(epoxyController)

    items = MutableList(100) { i ->
      CellItem(
        id = "$i",
        text = "$i",
        size = sizeGenerator.next()
      )
    }

    EpoxyTouchHelper.initDragging(epoxyController)
      .withRecyclerView(recycler_view)
      .forGrid()
      .withTarget(CellItemViewModel_::class.java)
      .andCallbacks(object : DragCallbacks<CellItemViewModel_>() {
        override fun onModelMoved(
          fromPosition: Int,
          toPosition: Int,
          modelBeingMoved: CellItemViewModel_,
          itemView: View
        ) {
          val carouselIndex = items.indexOf(modelBeingMoved.model())
          items.add(carouselIndex + (toPosition - fromPosition), items.removeAt(carouselIndex))
          epoxyController.setData(items)
        }

        override fun onDragStarted(
          model: CellItemViewModel_,
          itemView: View,
          adapterPosition: Int
        ) {
          itemView
            .animate()
            .scaleX(1.2f)
            .scaleY(1.2f)

          itemView.setBackgroundResource(R.color.colorAccent)
        }

        override fun onDragReleased(model: CellItemViewModel_, itemView: View) {
          itemView
            .animate()
            .scaleX(1f)
            .scaleY(1f)

          itemView.setBackgroundResource(R.color.colorPrimaryDark)
        }

        override fun clearView(model: CellItemViewModel_, itemView: View) {
          onDragReleased(model, itemView)
        }
      })
  }
}
