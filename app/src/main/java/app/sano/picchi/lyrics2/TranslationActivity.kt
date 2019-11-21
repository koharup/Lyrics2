package app.sano.picchi.lyrics2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import io.realm.Realm

class TranslationActivity : AppCompatActivity() {

    //realm型の変数を宣言
    lateinit var realm: Realm

    //EditText型の変数宣言
    lateinit var titleText: TextView
    lateinit var contentText: TextView
    lateinit var kashiText: TextView

    //realm型の変数を宣言


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation)

        //realmを開く
        Realm.init(this)
        realm = Realm.getDefaultInstance()

        //関連付け
        titleText = findViewById(R.id.titleEditText) as TextView
        contentText = findViewById(R.id.contentEditText) as TextView
        kashiText = findViewById(R.id.word1EditText) as TextView

        showData()

    }

    fun showData() {
        val memo = realm.where(Memo::class.java).equalTo(
            "updateDate",
            intent.getStringExtra("updateDate")
        ).findFirst()

        titleText.setText(memo.title)
        contentText.setText(memo.content)
        kashiText.setText(memo.word1)

    }




    override fun onDestroy() {
        super.onDestroy()

        //realmを閉じる
        realm.close()
    }
}
