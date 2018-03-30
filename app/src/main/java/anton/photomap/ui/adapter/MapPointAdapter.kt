package anton.photomap.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import anton.photomap.R
import anton.photomap.entity.MapPoint
import kotlinx.android.synthetic.main.item_list.view.*


class MapPointAdapter(
        private val listPoints: ArrayList<MapPoint>,
        private val onClickItemListener: (MapPoint) -> Unit
) : RecyclerView.Adapter<MapPointViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MapPointViewHolder =
            MapPointViewHolder(LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_list, parent, false), onClickItemListener)

    override fun getItemCount(): Int = listPoints.size

    override fun onBindViewHolder(holder: MapPointViewHolder?, position: Int) {
        holder?.bind(listPoints[position])
    }

    fun addNewList(newList: List<MapPoint>) {
        with(listPoints) {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }
}

class MapPointViewHolder(view: View, private val itemClick: (MapPoint) -> Unit)
    : RecyclerView.ViewHolder(view) {

    private val llMapPointItem = itemView.llMapPointItem
    private val tvDescriptionPoint = itemView.tvDescriptionPoint
    private val tvLatitudePoint = itemView.tvLatitudePoint
    private val tvLongitudePoint = itemView.tvLongitudePoint

    fun bind(item: MapPoint) {
        llMapPointItem.setOnClickListener { itemClick(item) }
        tvDescriptionPoint.text = item.text
        tvLatitudePoint.text = item.latitude.toString()
        tvLongitudePoint.text = item.longtitude.toString()
    }
}

