package com.example.jjapps.form080.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Vibrator
import android.util.Base64
import android.view.LayoutInflater
import android.widget.EditText
import xyz.goldapp.jjapps.form080.BuildConfig
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

import java.util.LinkedHashMap;

/**
 * Created by jgutierrez on 3/19/2018.
 */

class GeneralHelper{
    companion object {

        fun getVersionBuldin(): String{
            val versionName = BuildConfig.VERSION_NAME
            return versionName
        }

        fun simpleAlerts(context: Context, title: String, sms:String){
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle(title)
            alertDialog.setMessage("\n"+sms)
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",DialogInterface.OnClickListener {dialog, which -> dialog.dismiss() })
            alertDialog.show()
        }


        fun alertsRemoveActivity(context: Context, title: String, sms:String){
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle(title)
            alertDialog.setMessage("\n"+sms)
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",DialogInterface.OnClickListener {dialog, which -> (context as Activity).finish()})
            alertDialog.show()
        }


        fun snackbarConectivity(context : Context, layout: LayoutInflater){


        }

        fun vibrateStandar(context: Context){
            val vibratorService = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(500)
        }

        fun vibratePersonalize(context: Context, seconds: Long){
            val vibratorService = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(seconds)
        }

        fun starActivitySimple(mContext: Context, act: Class<*>) {
            val intent = Intent(mContext, act)
            mContext.startActivity(intent)
        }

        fun closeActivitySimple(mContext: Activity, returnActivity: Class<*>){
                var intent: Intent = Intent(mContext,returnActivity).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                mContext.startActivity(intent)
                mContext.finish()
            }

        fun closeActivityBundle(mContext: Activity, returnActivity: Class<*>,data: Bundle,keyBundle:String){
            val intent: Intent = Intent(mContext,returnActivity).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra(keyBundle,data)
            mContext.startActivity(intent)
            mContext.finish()
        }



        fun validateEditText(edt:EditText):String{
          var resp: String = "";

            if(edt.text.length > 0){
                resp = edt.text.toString()
            }

            return  resp
        }

//        fun setContentEmpty(mActivity: Activity, isVisible: Boolean){
//
//            val contentEmpty = mActivity.findViewById<RelativeLayout>(R.id.ly_empty)
//            if(isVisible) {
//                contentEmpty.setVisibility(View.VISIBLE)
//            }else{
//                contentEmpty.setVisibility(View.GONE)
//            }
//        }

        fun getLetterString (stn:String):String{
            return stn[0].toString()
        }

        // title: getStringImage
        // description: obtine el una cadena que contiene codigo en byte de una imagen.
        // programmer: jGutierrez
        fun getStringImage(bmp: Bitmap): String {
            val baos = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG, 45, baos)
            val imageBytes = baos.toByteArray()
            return Base64.encodeToString(imageBytes, Base64.DEFAULT)

        }

        // title: getUriImage
        // description: obtiene una ruta dinamica para guardar la imagen.
        // programmer: jGutierrez
        fun getUriImage():Uri{

            val root = File(Environment.getExternalStorageDirectory().toString() + File.separator + "ServTecIMG" + File.separator)

            if(!root.exists())
                root.mkdirs()

            val fname = "img_" + "tank_001" + ".jpg"
            val sdImageMainDirectory = File(root, fname)
            val mImageUri = Uri.fromFile(sdImageMainDirectory)

            return  mImageUri
        }

        // title: getUriImageBarcode
        // description: genera una ruta para guardar un imagen.
        // programmer: jGutierrez
        fun getUriImageBarCode():Uri{

            val root = File(Environment.getExternalStorageDirectory().toString() + File.separator + "ServTecIMG" + File.separator)

            if(!root.exists())
                root.mkdirs()

            val fname = "img_" + "bar_code" + ".jpg"
            val sdImageMainDirectory = File(root, fname)
            val mImageUri = Uri.fromFile(sdImageMainDirectory)

            return  mImageUri
        }

        // title: getDateNow
        // description:  obtiene la fecha actual del sistema android.
        // programmer: jGutierrez
        fun getDateNow():String{
            val format: String = "yyyy-MM-dd HH:mm:ss";
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat(format)
            return sdf.format(cal.getTime())
        }

        // title: isValidMail
        // description: hace una validdacion usando una exprecion regular.
        // programmer: jGutierrez
        fun isValidEmail(email: String): Boolean {
            val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

            val pattern = Pattern.compile(EMAIL_PATTERN)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }

        // title: RomanNumerals & repeat
        // description: Genera un numeros romanos en bace al numero que envias a este metodo.
        // programmer: gitHub
        fun RomanNumerals(Int: Int): String {
            var Int = Int
            val roman_numerals = LinkedHashMap<String, Int>()
            roman_numerals["M"] = 1000
            roman_numerals["CM"] = 900
            roman_numerals["D"] = 500
            roman_numerals["CD"] = 400
            roman_numerals["C"] = 100
            roman_numerals["XC"] = 90
            roman_numerals["L"] = 50
            roman_numerals["XL"] = 40
            roman_numerals["X"] = 10
            roman_numerals["IX"] = 9
            roman_numerals["V"] = 5
            roman_numerals["IV"] = 4
            roman_numerals["I"] = 1
            var res = ""
            for ((key, value) in roman_numerals) {
                val matches = Int / value
                res += repeat(key, matches)
                Int = Int % value
            }
            return res
        }

        fun repeat(s: String?, n: Int): String? {
            if (s == null) {
                return null
            }
            val sb = StringBuilder()
            for (i in 0 until n) {
                sb.append(s)
            }
            return sb.toString()
        }

        // title: RomanNumerals & repeat
        // description: Genera un numeros romanos en bace al numero que envias a este metodo.
        // programmer: gitHub

        fun keyBundle (): String{
            return "KeyBundle"
        }

        fun validateIdPanama(idClient: String): Boolean {
            val CEDULA = "^[a-zA-Z0-9-]{4,}$"
            val arrayStr = idClient.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var isValidate = false

            if (arrayStr.size >= 3) {

                val patN = Pattern.compile(CEDULA)
                val matN = patN.matcher(idClient)

                isValidate = matN.find()

            }

            return isValidate

        }

    }
}