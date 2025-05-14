package com.alejandro.movielog

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Classe d’aplicació principal
 * Esta classe s’executa abans que qualsevol altra part de l’app
 * i s’utilitza per a inicialitzar biblioteques globals com Hilt.
 */
@HiltAndroidApp // indica que Hilt ha de gestionar la injecció de dependències en tota l'app
class MovieLogApp : Application()
