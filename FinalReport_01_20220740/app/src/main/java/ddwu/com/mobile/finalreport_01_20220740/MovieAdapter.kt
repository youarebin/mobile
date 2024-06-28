package ddwu.com.mobile.finalreport_01_20220740

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.finalreport_01_20220740.data.MovieDto
import ddwu.com.mobile.finalreport_01_20220740.databinding.ListItemBinding
import java.util.logging.Filter


class MovieAdapter(val movies : ArrayList<MovieDto>)
    : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(), Filterable {

    val TAG = "MovieAdapter"

    //필터링을 위해 필요한 변수
    private var filteredMovies: ArrayList<MovieDto> = movies

    override fun getItemCount(): Int = filteredMovies.size

    /*재정의 필수 - 각 item view 의 view holder 생성 시 호출*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    /*재정의 필수 - 각 item view 의 항목에 데이터 결합 시 호출*/
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.itemBinding.tvMovie.text = filteredMovies[position].movie
        holder.itemBinding.tvDirector.text = filteredMovies[position].director
        holder.itemBinding.tvRate.text = filteredMovies[position].rate.toString()
    }

    inner class MovieViewHolder(val itemBinding: ListItemBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
            init {
                itemBinding.root.setOnClickListener{
                    listener?.onItemClick(it, adapterPosition)  // RecyclerView 항목 클릭 시 외부에서 지정한 리스너 호출
                    Log.d(TAG, movies[adapterPosition].toString())
                }

                itemBinding.root.setOnLongClickListener {
                    Log.d(TAG, "long Click")
                    val result = longClickListener?.onItemLongClick(it, adapterPosition)
                    true
                }
            }
        }

    override fun getFilter(): android.widget.Filter? {
        return object : android.widget.Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                filteredMovies = if (charString.isEmpty()) {//검색어 없으면 원본 데이터 표시
                    movies
                } else {//검색어 있을때
                    val filteredList = ArrayList<MovieDto>()
                    if(movies != null){
                        for(movie in movies){
                            if(movie.movie.contains(charString, true)){
                                filteredList.add(movie)
                            }
                        }
                    }
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = filteredMovies
                Log.d("Filter", "performFiltering: $filteredMovies")
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredMovies = results?.values as ArrayList<MovieDto>
                notifyDataSetChanged()
            }
        }
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int) : Boolean
    }
    var longClickListener: OnItemLongClickListener? = null

    fun setOnItemLongClickListener (listener: OnItemLongClickListener?) {
        this.longClickListener = listener
    }

    var listener : OnItemClickListener? = null  // listener 를 사용하지 않을 때도 있으므로 null

    interface OnItemClickListener {
        fun onItemClick(view : View, position : Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

}