package summer.example.debt

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import summer.example.R

class MainActivity : AppCompatActivity() {

    private val debtFragment = DebtFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        supportFragmentManager.beginTransaction()
            .replace(R.id.contentContainer, debtFragment)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
        if (item != null && item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
}