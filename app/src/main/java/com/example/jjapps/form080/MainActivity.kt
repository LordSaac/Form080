package com.example.jjapps.form080

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.jjapps.form080.utils.FontManager
import com.example.jjapps.form080.utils.GeneralHelper
import org.json.JSONArray
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_main.*
import com.example.jjapps.form080.anim.AnimationEditText
import com.example.jjapps.form080.utils.VolleySingleton
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds


class MainActivity : AppCompatActivity() {

    private lateinit var search: EditText
    private lateinit var iconPerson: TextView
    private lateinit var  buttonValidate: Button
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search = findViewById(R.id.edt_search)
        iconPerson = findViewById(R.id.icon_person)
        buttonValidate = findViewById(R.id.button)

        val typeface = Typeface.createFromAsset(this.assets, FontManager.getRuteFASolid())
        iconPerson.setTypeface(typeface)
        iconPerson.text = FontManager.fontUser


        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        activityCreateToolbar()

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.info_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.menu_info) {
            GeneralHelper.simpleAlerts(this,"Acerca de","Esta app esta desarollodado por Grupo JJ APPS \n\n Contacto: example@gmail.com" )
            true
        } else super.onOptionsItemSelected(item)
    }

    // title: createToolbar
    // description: generar un menu personalizado con botones a la derecha para el toolbar.
    // programmer: jGutierrez
    private fun activityCreateToolbar() {
        val mToolbar = this.findViewById<View>(R.id.toolbar) as Toolbar
        mToolbar.setTitle(getString(R.string.app_name))
        mToolbar.inflateMenu(R.menu.info_menu)
        setSupportActionBar(mToolbar)

    }


    fun clickSearch(view:View){


        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.")
        }

        var ID: String = edt_search.text.toString()
        var isID:Boolean = GeneralHelper.validateIdPanama(ID)

        if(isID){
            buttonValidate.isEnabled = false
            getResult(ID)
        }
        else
            AnimationEditText.animateVibration(search,this)
    }

    //--------------------------------- User download data -------------------------------------------------------------//
    private fun getResult(ID: String) {

        val link = "http://planillaws.azurewebsites.net/api/user/load/$ID"

        val postRequest = object : StringRequest(Request.Method.GET, link,
                Response.Listener { response ->
                    //      Log.d("Response", response);
                    if (response != "" && response != "null") {
                        try {

                            val array = JSONArray(response)
                            var progressCount: Double = 0.0
                            if (array.length() > 0) {
                                        val `object` = array.getJSONObject(0)
                                        var resp: String = `object`.getString("cedula")

                                        buttonValidate.isEnabled = true

                                        if(!resp.equals("0"))
                                             dialog(`object`.getString("nombre"),`object`.getString("apellido"),`object`.getString("cedula"),`object`.getString("salario"),`object`.getString("cargo"),`object`.getString("planilla"))
                                        else
                                            GeneralHelper.simpleAlerts(this,"Alerta","Esta identificación no aparece en la PLANILLA 080")

                            }

                        } catch (ex: Exception) {
                            buttonValidate.isEnabled = true
                          GeneralHelper.simpleAlerts(this,"Error","Lo sentimos, ocurrio un error :(")
                        }


                    }
                },
                Response.ErrorListener { error ->
                    buttonValidate.isEnabled = true
                    GeneralHelper.simpleAlerts(this,"Error","Lo sentimos, ocurrio un error :(")
                }
        ) {

            override fun getBodyContentType(): String {
                return super.getBodyContentType()
            }
        }

        postRequest.retryPolicy = DefaultRetryPolicy(
                40000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
              VolleySingleton.getInstance(applicationContext).addToRequestque(postRequest)
    }


    // title: dialogComment
    // description: presenta informacion del web service .
    // programmer: jGutierrez
    private fun dialog(nombreDL:String, apellidoDL: String, cedulaDL: String, salarioDL: String, cargoDL:String, planillaDL: String) {
        val factory = LayoutInflater.from(this)
        val deleteDialogView = factory.inflate(R.layout.dialoginfo, null)
        val deleteDialog = AlertDialog.Builder(this, R.style.DialogTheme).create()
        deleteDialog.setTitle("Identificación encontrada en PLANILLA 080")
        val nombre:TextView = deleteDialogView.findViewById(R.id.tv_name)
        val cedula:TextView = deleteDialogView.findViewById(R.id.tv_cedula)
        val salario:TextView = deleteDialogView.findViewById(R.id.tv_salario)
        val cargo:TextView = deleteDialogView.findViewById(R.id.tv_cargo)
        val planilla:TextView = deleteDialogView.findViewById(R.id.tv_planilla)
        val icon:TextView = deleteDialogView.findViewById(R.id.icon_person2)

        val btnOk:Button = deleteDialogView.findViewById(R.id.btn_ok)

        nombre.text = nombreDL + " " + apellidoDL
        cedula.text = cedulaDL
        salario.text = salarioDL + " $"
        cargo.text = cargoDL
        planilla.text = planillaDL

        val typeface = Typeface.createFromAsset(this.assets, FontManager.getRuteFASolid())
        icon.setTypeface(typeface)

        icon.text = FontManager.fontUser

        deleteDialog.setView(deleteDialogView)
        deleteDialog.setCancelable(false)


        btnOk.setOnClickListener(View.OnClickListener {
            //your business logic
            deleteDialog.dismiss()
        })

        deleteDialog.show()
    }



}
