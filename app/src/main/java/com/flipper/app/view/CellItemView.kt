package com.flipper.app.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.flipper.app.R
import com.flipper.app.entity.CellItem
import kotlinx.android.synthetic.main.cell_item_view.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
internal class CellItemView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
  private var model: CellItem? = null

  init {
    elevation = 8f
    setBackgroundResource(R.color.colorPrimaryDark)
    View.inflate(context, R.layout.cell_item_view, this)
  }

  @ModelProp
  fun setModel(model: CellItem) {
    this.model = model
    cell_item_text.text = model.text
  }
}

