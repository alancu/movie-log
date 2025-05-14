import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.alejandro.movielog.repository.MovieRepository
import com.alejandro.movielog.utils.ApiKeyProvider
import com.alejandro.movielog.viewmodel.MovieViewModel
import com.alejandro.movielog.viewmodel.MovieViewModelFactory

object ViewModelProviderUtil {
    private fun getRepository(context: Context): MovieRepository =
        MovieRepository(ApiKeyProvider.getApiKey(context))

    fun provideMovieViewModel(owner: ViewModelStoreOwner, context: Context): MovieViewModel =
        ViewModelProvider(
            owner,
            MovieViewModelFactory(getRepository(context))
        )[MovieViewModel::class.java]
}
