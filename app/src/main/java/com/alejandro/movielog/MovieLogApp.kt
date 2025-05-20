package com.alejandro.movielog

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Esta classe s’executa abans que qualsevol altra part de l’app.
 * S’utilitza per a inicialitzar Hilt (injecció de dependències).
 */
@HiltAndroidApp // Indica a Hilt que use aquesta classe per inicialitzar la injecció de dependències
class MovieLogApp : Application()
