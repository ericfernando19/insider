package com.dicoding.malanginsider.ui


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.data.adapter.RecommendationsAdapter
import com.dicoding.malanginsider.data.pref.RecommendationsItem
import com.dicoding.malanginsider.data.pref.RequestBody
import com.dicoding.malanginsider.data.pref.ResponseWisata
import com.dicoding.malanginsider.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private lateinit var edtSearch: EditText
    private lateinit var btnSearch: Button
    private lateinit var rvWisatacari: RecyclerView
    private lateinit var wisataCariAdapter: RecommendationsAdapter
    private var listWisata = mutableListOf<RecommendationsItem>()
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        edtSearch = findViewById(R.id.edtSearch)
        btnSearch = findViewById(R.id.btnSearch)
        rvWisatacari = findViewById(R.id.rvWisatacari)


        rvWisatacari.layoutManager = LinearLayoutManager(this)
        wisataCariAdapter = RecommendationsAdapter(listWisata)
        rvWisatacari.adapter = wisataCariAdapter
        progressBar = findViewById(R.id.progressBar)




        btnSearch.setOnClickListener {
            val query = edtSearch.text.toString()
            if (query.isNotEmpty()) {
                searchWisata(query)
            }
        }
    }


    private fun searchWisata(query: String) {
        val requestBody = RequestBody(name = query)


        Log.d("SearchActivity", "Request Body: $requestBody")


        progressBar.visibility = View.VISIBLE


        ApiConfig.apiService.getRecommendations(requestBody).enqueue(object :
            Callback<ResponseWisata<RecommendationsItem>> {
            override fun onResponse(
                call: Call<ResponseWisata<RecommendationsItem>>,
                response: Response<ResponseWisata<RecommendationsItem>>
            ) {
                // Log respons JSON untuk debugging
                Log.d("SearchActivity", "Response: ${response.body()}")


                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.recommendations.isNotEmpty()) {
                        listWisata.clear()
                        listWisata.addAll(responseBody.recommendations)
                        wisataCariAdapter.notifyDataSetChanged()
                    } else {
                        // Menampilkan pesan jika tidak ada data
                        Toast.makeText(this@SearchActivity, "Tidak ada data wisata", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("SearchActivity", "Response failed with code: ${response.code()}")
                    Toast.makeText(this@SearchActivity, "Gagal memuat data. Status Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
                progressBar.visibility = View.GONE
            }


            override fun onFailure(call: Call<ResponseWisata<RecommendationsItem>>, t: Throwable) {
                progressBar.visibility = View.GONE


                Log.e("SearchActivity", "Error: ${t.message}")

                Toast.makeText(this@SearchActivity, "Koneksi gagal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
