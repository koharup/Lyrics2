package app.sano.picchi.lyrics2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import io.realm.Realm

class MainActivity : AppCompatActivity() {

    lateinit var realm: Realm
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //realmを開く
        //これないと落ちる
        Realm.init(this)
        realm = Realm.getDefaultInstance()

        listView = findViewById(R.id.listView) as ListView

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val memo = parent.getItemAtPosition(position) as Memo
                val intent = Intent(this@MainActivity, TranslationActivity::class.java)
                intent.putExtra("updateDate", memo.updateDate)
                startActivity(intent)
            }

    }



    fun create(view: View) {
        val intent = Intent(this, AddActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        setMemoList()
    }


    fun setMemoList() {
        //realmから読み取る
        val results = realm.where(Memo::class.java).findAll()
        val items = realm.copyFromRealm(results)

        val adapter = MemoAdapter(this, R.layout.layout_item_memo, items)

        listView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()

        //一応いれとく
        //super.onDestroy()
        //realmを閉じる
        realm.close()
    }
}
