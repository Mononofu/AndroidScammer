package org.furidamu.androidscammer

import android.view.View
import android.widget.TextView


object Constants {
	implicit def ViewToRichView(v: View) = new RichView(v)
	implicit def TextViewToRichTextView(v: TextView) = new RichTextview(v)
}

import Constants._


class RichView(view: View) { 
	def onClick = throw new Exception
	 def onClick_= (f: () => Unit) { 
		 view.setOnClickListener(
		 new View.OnClickListener() {
		 	def onClick(v: View) {
		 		f()
		 	}
		 } ) 
	 }
}

class RichTextview(tv: TextView) {
	def text = tv.getText()
	def text_= (t: String) = tv.setText(t)
}