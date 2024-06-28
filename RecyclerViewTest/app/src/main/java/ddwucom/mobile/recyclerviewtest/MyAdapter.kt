package ddwucom.mobile.recyclerviewtest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val context: Context, val layout: Int, val list: ArrayList<String>)
    : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvText.text = list[position]
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvText : TextView = view.findViewById(R.id.tvText)
        init {
            view.setOnClickListener{
                Toast.makeText(view.context, "항목: $adapterPosition view 터치", Toast.LENGTH_SHORT).show()
            }

            /*롱클릭시 항목 삭제*/
//            view.setOnLongClickListener{
//                list.removeAt(adapterPosition)
//                this@MyAdapter.notifyDataSetChanged()
//                true
//            }

            tvText.setOnClickListener{
                Toast.makeText(view.context, "TextView Click", Toast.LENGTH_SHORT).show()
            }
        }
    }


}