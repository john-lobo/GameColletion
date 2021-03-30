package com.johnlennonlobo.gamercolletion.ui.game

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.johnlennonlobo.gamercolletion.R
import com.johnlennonlobo.gamercolletion.data.db.AppDataBase
import com.johnlennonlobo.gamercolletion.data.db.dao.GameDAO
import com.johnlennonlobo.gamercolletion.databinding.GameFragmentBinding
import com.johnlennonlobo.gamercolletion.repository.DatabaseDataSource
import com.johnlennonlobo.gamercolletion.repository.GamerRepository
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

class GameFragment : Fragment() {


    private var bitmap : Bitmap? = null
    private val agrs : GameFragmentArgs by navArgs()

    private var _binding: GameFragmentBinding? = null
    private val binding get() = _binding!!


    private val viewModel: GameViewModel by viewModels {
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val gamerDAO: GameDAO = AppDataBase.getInstance(requireContext()).gamerDAO

                val repository: GamerRepository = DatabaseDataSource(gamerDAO)
                return GameViewModel(repository, requireContext()) as T
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = GameFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        itemViewAdapter()
        setListeners()
        observerEvents()

        return view
    }

    private fun itemViewAdapter() {
        agrs.gamer?.let { game ->

            binding.btnAdiciona.text = "Atualizar"
            binding.editInputTitulo.setText(game.title)
            binding.ratingBarEdit.rating = game.nota

//          bitmap = BitmapFactory.decodeByteArray(game.imagem, 0, game.imagem!!.size)
//          binding.imagemPhoto.setImageBitmap(bitmap)
            
            binding.btnDelete.visibility = View.VISIBLE

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, url: Intent?) {
        super.onActivityResult(requestCode, resultCode, url)

        if(url != null){
            val contentResolver = activity?.contentResolver
            val inputStream = contentResolver?.openInputStream(url.data!!)
            bitmap = BitmapFactory.decodeStream(inputStream)
            binding.imagemPhoto.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.imagemPhoto.setImageBitmap(bitmap)

        }
    }

    private fun observerEvents(){
        viewModel.gameEventData.observe(viewLifecycleOwner){gameState ->
            when(gameState){
                is GameViewModel.GameState.Inserted,
                is GameViewModel.GameState.Update,
                is GameViewModel.GameState.Delete ->{
                    findNavController().navigate(R.id.gameListFragment)
                }
            }
        }
        viewModel.messageStateEventData.observe(viewLifecycleOwner) { stringRes ->
            Snackbar.make(requireView(), stringRes, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setListeners(){

        binding.btnAdiciona.setOnClickListener {
            val titulo = binding.editInputTitulo.text.toString()
            val nota = binding.ratingBarEdit.rating

            viewModel.addOrUpdateGame(title = titulo,nota = nota,imagem = bitmap,id = agrs.gamer?.id?:0)

        }

        binding.btnAddFoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent,500)
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteSubscriber(agrs.gamer?.id?: 0)

        }
    }


}