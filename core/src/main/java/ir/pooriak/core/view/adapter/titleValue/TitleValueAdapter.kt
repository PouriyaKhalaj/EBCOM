package ir.pooriak.core.view.adapter.titleValue

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.pooriak.core.R
import ir.pooriak.core.databinding.ItemListTitleValueViewBinding
import ir.pooriak.core.view.utils.setTextAppearanceEx
import ir.pooriak.core.view.utils.toDp

/**
 * Created by POORIAK on 17,September,2023
 */
class TitleValueAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<TitleValue> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemListTitleValueViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = if (items.isEmpty()) 0 else items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).onBind(items[position])
    }

    fun addItems(items: List<TitleValue>) {
        this.items = items
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: ItemListTitleValueViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val titleViewIds = arrayListOf<Int>()
        private val valueViewIds = arrayListOf<Int>()
        private var horizontalDividerId: Int? = null

        fun onBind(item: TitleValue) {
            when (item) {
                is TitleValue.Spannable -> fillSpannable(item)
                is TitleValue.SpannableType -> fillSpannableType(item)
                is TitleValue.StringStyle -> fillStringStyle(item)
            }
            setConstraintViews()
        }

        private fun fillStringStyle(item: TitleValue.StringStyle) {
            with(binding) {
                initTextView.apply {
                    titleViewIds.add(id)
                    text = item.title
                    setTextAppearanceEx(item.titleAppearance)
                    root.addView(this)
                }
                initTextView.apply {
                    valueViewIds.add(id)
                    text = item.value
                    setTextAppearanceEx(item.valueAppearance)
                    root.addView(this)
                }
                checkToCreateHorizontalDivider(item.endLine)
            }
        }

        private fun checkToCreateHorizontalDivider(endLine: Boolean) {
            binding.root.addView(initHorizontalDividerView)
        }

        private fun fillSpannable(item: TitleValue.Spannable) {
            with(binding) {
                root.addView(createTitleTextView(item.title))
                root.addView(createValueTextView(item.value))
                checkToCreateHorizontalDivider(item.endLine)
            }
        }

        private fun fillSpannableType(item: TitleValue.SpannableType) {
            with(binding) {
                item.spans.forEach {
                    when (it) {
                        is SpanType.Title -> createTitleTextView(it.title)
                        is SpanType.Value -> createValueTextView(it.value)
                    }.also { view ->
                        root.addView(view)
                    }
                }
                checkToCreateHorizontalDivider(item.endLine)
            }
        }

        private fun createTitleTextView(spannable: SpannableString) = initTextView.apply {
            titleViewIds.add(id)
            setTextAppearanceEx(R.style.ebcom_Text_EnMedium14sp)
            text = spannable
        }

        private fun createValueTextView(spannable: SpannableString) = initTextView.apply {
            valueViewIds.add(id)
            setTextAppearanceEx(R.style.ebcom_Text_EnBold14sp)
            text = spannable
        }

        private val initTextView: AppCompatTextView
            get() = AppCompatTextView(binding.root.context).apply {
                id = View.generateViewId()
                layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
            }

        private val initHorizontalDividerView: View
            get() = View(binding.root.context).apply {
                id = View.generateViewId()
                horizontalDividerId = id
                layoutParams = ConstraintLayout.LayoutParams(0.toDp, 2.toDp)
                background =
                    ContextCompat.getDrawable(binding.root.context, R.drawable.dashed_line_new)
            }

        private fun setConstraintViews() {
            ConstraintSet().run {
                clone(binding.root)
                setConstraintTitleViews(this, titleViewIds)
                setConstraintValueViews(this, valueViewIds)
                horizontalDividerId?.let { setConstraintBottomParent(this, it) }
                applyTo(binding.root)
            }
        }

        private fun setConstraintTitleViews(set: ConstraintSet, ids: List<Int>) {
            ids.forEachIndexed { index, id ->
                if (index == 0) setConstraintStartParent(set, id)
                else setConstraintStartToEnd(set, id, ids[index - 1])
            }
        }

        private fun setConstraintValueViews(set: ConstraintSet, ids: List<Int>) {
            ids.forEachIndexed { index, id ->
                if (index == 0) setConstraintEndParent(set, id)
                else setConstraintEndToStart(set, id, ids[index - 1])
            }
        }

        private fun setConstraintStartToEnd(set: ConstraintSet, currentId: Int, previousId: Int) {
            set.run {
                // start of view connect to end
                connect(
                    currentId,
                    ConstraintSet.START,
                    previousId,
                    ConstraintSet.END
                )

                // top of view connect to parent
                connect(
                    currentId,
                    ConstraintSet.TOP,
                    binding.root.id,
                    ConstraintSet.TOP
                )

                // bottom of view connect to parent
                connect(
                    currentId,
                    ConstraintSet.BOTTOM,
                    binding.root.id,
                    ConstraintSet.BOTTOM
                )
            }
        }

        private fun setConstraintStartParent(set: ConstraintSet, id: Int) {
            set.run {
                // start of view connect to parent
                connect(
                    id,
                    ConstraintSet.START,
                    binding.root.id,
                    ConstraintSet.START
                )

                // top of view connect to parent
                connect(
                    id,
                    ConstraintSet.TOP,
                    binding.root.id,
                    ConstraintSet.TOP
                )

                // bottom of view connect to parent
                connect(
                    id,
                    ConstraintSet.BOTTOM,
                    binding.root.id,
                    ConstraintSet.BOTTOM
                )
            }
        }

        private fun setConstraintEndParent(set: ConstraintSet, id: Int) {
            set.run {
                // end of view connect to parent
                connect(
                    id,
                    ConstraintSet.END,
                    binding.root.id,
                    ConstraintSet.END
                )

                // top of view connect to parent
                connect(
                    id,
                    ConstraintSet.TOP,
                    binding.root.id,
                    ConstraintSet.TOP
                )

                // bottom of view connect to parent
                connect(
                    id,
                    ConstraintSet.BOTTOM,
                    binding.root.id,
                    ConstraintSet.BOTTOM
                )
            }
        }

        private fun setConstraintEndToStart(set: ConstraintSet, currentId: Int, previousId: Int) {
            set.run {
                // start of view connect to end
                connect(
                    currentId,
                    ConstraintSet.END,
                    previousId,
                    ConstraintSet.START
                )

                // top of view connect to parent
                connect(
                    currentId,
                    ConstraintSet.TOP,
                    binding.root.id,
                    ConstraintSet.TOP
                )

                // bottom of view connect to parent
                connect(
                    currentId,
                    ConstraintSet.BOTTOM,
                    binding.root.id,
                    ConstraintSet.BOTTOM
                )
            }
        }

        private fun setConstraintBottomParent(set: ConstraintSet, id: Int) {
            set.run {
                // end of view connect to parent
                connect(
                    id,
                    ConstraintSet.BOTTOM,
                    binding.root.id,
                    ConstraintSet.BOTTOM
                )

                // top of view connect to parent
                connect(
                    id,
                    ConstraintSet.TOP,
                    binding.root.id,
                    ConstraintSet.TOP
                )

                // bottom of view connect to parent
                connect(
                    id,
                    ConstraintSet.BOTTOM,
                    binding.root.id,
                    ConstraintSet.BOTTOM
                )
            }
        }
    }
}