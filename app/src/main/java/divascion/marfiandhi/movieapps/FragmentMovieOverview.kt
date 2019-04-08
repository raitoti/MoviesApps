package divascion.marfiandhi.movieapps

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import divascion.marfiandhi.movieapps.model.ApiRepository
import divascion.marfiandhi.movieapps.model.credits.CastOfMovies
import divascion.marfiandhi.movieapps.model.credits.CrewOfMovies
import divascion.marfiandhi.movieapps.model.movies.ListOfMovies
import divascion.marfiandhi.movieapps.presenter.credits.CreditsPresenter
import kotlinx.android.synthetic.main.fragment_movie_overview.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.support.v4.toast

class FragmentMovieOverview : Fragment(), MovieOverviewView {

    private var crew: MutableList<CrewOfMovies> = mutableListOf()
    private var cast: MutableList<CastOfMovies> = mutableListOf()
    private lateinit var movies: ListOfMovies
    private lateinit var presenter : CreditsPresenter
    private var genre = ""
    private var i = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val rate = (movies.score!! /10)*5
        detail_rate_text.text = rate.toString()
        detail_rate.rating = rate
        detail_movie_overview.text = movies.overview
        var temp = movies.popularity.toString()
        for(index in temp.indices) {
            if(temp.toCharArray()[index-i] == '.') {
                i += 1
            }
        }
        when (i) {
            3 -> temp += "0"
            2 -> temp += "00"
        }
        text_popularity.text = temp
        getGenre()
        detail_genre_text.text = genre
        val request = ApiRepository()
        val gson = Gson()
        presenter = CreditsPresenter(this, request, gson)
        presenter.getCreditsMovie(movies.id.toString())

        swipe_overview_detail.onRefresh {
            presenter.getCreditsMovie(movies.id.toString())
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        movies = arguments?.getParcelable("item")!!
        return inflater.inflate(R.layout.fragment_movie_overview, container, false)
    }

    override fun showLoading() {
        swipe_overview_detail.isRefreshing = true
    }

    override fun hideLoading() {
        swipe_overview_detail.isRefreshing = false
    }

    override fun getCast(data: List<CastOfMovies>) {
        cast.clear()
        cast.addAll(data)
    }

    override fun getCrew(data: List<CrewOfMovies>) {
        crew.clear()
        crew.addAll(data)
    }

    override fun loadView() {
        name_one.text = cast[0].name
        cast_one.text = cast[0].character
        name_two.text = cast[1].name
        cast_two.text = cast[1].character
        name_three.text = cast[2].name
        cast_three.text = cast[2].character
        name_four.text = cast[3].name
        cast_four.text = cast[3].character

        crew_one.text = crew[0].job
        crew_one_name.text = crew[0].name
        crew_two.text = crew[1].job
        crew_two_name.text = crew[1].name
        crew_three.text = crew[2].job
        crew_three_name.text = crew[2].name
    }

    private fun getGenre() {
        i = 0
        for(index in movies.genre!!.indices) {
            if(i==0){
                genre += when(movies.genre!![index]) {
                    28 -> "Action"
                    12 -> "Adventure"
                    16 -> "Animation"
                    35 -> "Comedy"
                    80 -> "Crime"
                    99 -> "Documentary"
                    18 -> "Drama"
                    10751 -> "Family"
                    14 -> "Fantasy"
                    36 -> "History"
                    27 -> "Horror"
                    10402 -> "Music"
                    9648 -> "Mystery"
                    10749 -> "Romance"
                    878 -> "Science Fiction"
                    10770 -> "TV Movie"
                    53 -> "Thriller"
                    10752 -> "War"
                    37 -> "Western"
                    else -> ""
                }
            } else {
                try {
                    movies.genre!![index+1]
                    genre += when(movies.genre!![index]) {
                        28 -> ", Action"
                        12 -> ", Adventure"
                        16 -> ", Animation"
                        35 -> ", Comedy"
                        80 -> ", Crime"
                        99 -> ", Documentary"
                        18 -> ", Drama"
                        10751 -> ", Family"
                        14 -> ", Fantasy"
                        36 -> ", History"
                        27 -> ", Horror"
                        10402 -> ", Music"
                        9648 -> ", Mystery"
                        10749 -> ", Romance"
                        878 -> ", Science Fiction"
                        10770 -> ", TV Movie"
                        53 -> ", Thriller"
                        10752 -> ", War"
                        37 -> ", Western"
                        else -> ""
                    }
                }catch(e: Exception) {
                    genre += when(movies.genre!![index]) {
                        28 -> ", Action."
                        12 -> ", Adventure."
                        16 -> ", Animation."
                        35 -> ", Comedy."
                        80 -> ", Crime."
                        99 -> ", Documentary."
                        18 -> ", Drama."
                        10751 -> ", Family."
                        14 -> ", Fantasy."
                        36 -> ", History."
                        27 -> ", Horror."
                        10402 -> ", Music."
                        9648 -> ", Mystery."
                        10749 -> ", Romance."
                        878 -> ", Science Fiction."
                        10770 -> ", TV Movie."
                        53 -> ", Thriller."
                        10752 -> ", War."
                        37 -> ", Western."
                        else -> "."
                    }
                }
            }
            i += 1
        }
    }
}