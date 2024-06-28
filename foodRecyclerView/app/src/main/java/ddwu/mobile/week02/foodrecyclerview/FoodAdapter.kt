package ddwu.mobile.week02.foodrecyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ddwu.mobile.week02.foodrecyclerview.databinding.ActivityMainBinding
import ddwu.mobile.week02.foodrecyclerview.databinding.ListItemBinding

class FoodAdapter(val foods: ArrayList<FoodDto>)
    : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun getItemCount(): Int = foods.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.itemBinding.ivPhoto.setImageResource(foods[position].photo)
        holder.itemBinding.tvFood.text = foods[position].food
        holder.itemBinding.tvCount.text = foods[position].count.toString()
    }

    inner class FoodViewHolder(val itemBinding: ListItemBinding)
        : RecyclerView.ViewHolder(itemBinding.root){
            val TAG = "FoodViewHolder"
           init {
               itemBinding.root.setOnLongClickListener{
                   //롱클릭 리스너 멤버함수 호출
                   longClickListener.onItemLongClick(it, adapterPosition)//true or false 반환
               }
           }
        }

    interface OnItemLongClickListener{
        fun onItemLongClick(view: View, position: Int): Boolean
    }
    lateinit var longClickListener: OnItemLongClickListener
    fun setOnItemLongClickListener(listener: OnItemLongClickListener){
        this.longClickListener = listener
    }

}