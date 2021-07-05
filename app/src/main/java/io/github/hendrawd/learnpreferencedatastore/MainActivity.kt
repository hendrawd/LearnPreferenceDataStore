package io.github.hendrawd.learnpreferencedatastore

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import io.github.hendrawd.learnpreferencedatastore.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

// region TODO 2 Create
private const val PREFERENCES_NAME = "some_preferences"
private val Context.dataStore by preferencesDataStore(
        name = PREFERENCES_NAME
)
// endregion

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // TODO 5 setOnClickListener will save the value
        binding.bSave.setOnClickListener {
            lifecycleScope.launch {
                save(binding.etText.text?.toString() ?: "")
            }
        }

        // TODO 6
        observe()
    }

    // TODO 4 Create observe function for pref value changes
    private fun observe() {
        val prefFlow: Flow<String> = dataStore.data
                .catch { exception ->
                    // dataStore.data throws an IOException when an error is encountered when reading data
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[PreferencesKeys.text] ?: "No saved value!"
                }
        prefFlow.asLiveData()
                .observe(this) {
                    binding.tvText.text = it
                }
    }

    // TODO 3 Edit
    private suspend fun save(text: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.text] = text
        }
    }
}