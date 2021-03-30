package com.johnlennonlobo.gamercolletion.ui.gameList

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.johnlennonlobo.gamercolletion.R
import com.johnlennonlobo.gamercolletion.data.db.entity.GameEntity

class GameListAdapter(val games :List<GameEntity>): RecyclerView.Adapter<GameListAdapter.MyGameHolder>() {

    var onItemClinck: ((entity: GameEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGameHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_item,parent,false)
        return MyGameHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyGameHolder, position: Int) {
        holder.bindView(games[position])
    }

    override fun getItemCount(): Int {
      return  games.size
    }

   inner class MyGameHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titulo = itemView.findViewById<TextView>(R.id.tituloItemView)
        val nota = itemView.findViewById<RatingBar>(R.id.ratingBarID)
        val imagem = itemView.findViewById<ImageView>(R.id.imageItem)

        fun bindView(gameEntity : GameEntity){
            titulo.text = gameEntity.title
            nota.rating = gameEntity.nota
            if(gameEntity.imagem != null){
                val imagebyteArray = gameEntity.imagem
                imagem.setImageBitmap(byteArrayToBitmap(imagebyteArray!!))
            }else{
                imagem.setImageResource(R.drawable.ic_add_photo)
            }



            itemView.setOnClickListener {
                onItemClinck?.invoke(gameEntity)
            }

        }

        private fun byteArrayToBitmap(imagem: ByteArray): Bitmap? {
            val bitmap = BitmapFactory.decodeByteArray(imagem, 0, imagem.size)
            return bitmap
        }
    }
}