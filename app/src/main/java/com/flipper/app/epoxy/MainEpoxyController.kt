package com.flipper.app.epoxy

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyViewHolder
import com.airbnb.epoxy.TypedEpoxyController
import com.flipper.app.entity.CellItem
import com.flipper.app.view.CellItemViewModel_
import com.flipper.app.view.cellItemView

internal class MainEpoxyController : TypedEpoxyController<List<CellItem>>() {
  override fun buildModels(items: List<CellItem>) {
    items.forEach { item ->
      cellItemView {
        id(item.id)
        model(item)
      }
    }
  }

  override fun onModelBound(
    holder: EpoxyViewHolder,
    boundModel: EpoxyModel<*>,
    position: Int,
    previouslyBoundModel: EpoxyModel<*>?
  ) {
    val lp = StaggeredGridLayoutManager.LayoutParams(holder.itemView.layoutParams)
    lp.marginEnd = 32
    lp.marginStart = 32
    lp.topMargin = 32
    lp.bottomMargin = 32

    lp.height = when ((boundModel as CellItemViewModel_).model().size) {
      CellItem.Size.ONE_ONE, CellItem.Size.TWO_ONE -> 200
      CellItem.Size.ONE_TWO, CellItem.Size.TWO_TWO -> 400
    }

    holder.itemView.layoutParams = lp
  }
}
