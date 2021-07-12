package kz.smartideagroup.jumys.specialist.view.apply_claim.bottom_sheet

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.specialist.model.response.apply_claim.AddressOrderResponse
import kz.smartideagroup.jumys.common.utils.TEL
import kz.smartideagroup.jumys.common.utils.setImage

class OrderBottomSheet : BottomSheetDialogFragment() {

    private var orderResponse: AddressOrderResponse? = null

    fun setOrder(orderResponse: AddressOrderResponse) {
        this.orderResponse = orderResponse
    }

    companion object {
        fun newInstance(): OrderBottomSheet {
            val args = Bundle()
            val bottomSheetFragment: OrderBottomSheet = OrderBottomSheet()
            bottomSheetFragment.arguments = args
            return bottomSheetFragment
        }
    }


    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val view = View.inflate(context, R.layout.order_bottom_sheet, null)
        dialog.setContentView(view)

        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvDescription = view.findViewById<TextView>(R.id.tv_description_bottom_sheet)
        val tvAddress = view.findViewById<TextView>(R.id.tv_address_bottom_sheet)
        val tvPrice = view.findViewById<TextView>(R.id.tv_price)
        val btnCall = view.findViewById<MaterialButton>(R.id.btn_call)
        val ivClose = view.findViewById<ImageView>(R.id.iv_close)
        val ivImage = view.findViewById<ImageView>(R.id.iv_image)


        ivClose.setOnClickListener {
            dialog.dismiss()
        }

        orderResponse.let {
            tvName.text = it!!.name
            tvDescription.text = it.description
            tvAddress.text = it.address
            ivImage.setImage(it.img)
            tvPrice.text = it.price!!.toLong().toString()
        }

        btnCall.setOnClickListener {
            call(orderResponse!!.number.toString())
        }
    }

    private fun call(phone: String) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.CALL_PHONE
                )
            ) {
                activity?.runOnUiThread {
                    Toast.makeText(
                        activity?.applicationContext,
                        getString(R.string.call),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42
                )
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CALL_PHONE
                    )
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse(TEL + phone)
                    startActivity(intent)
                }
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42
                )
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CALL_PHONE
                    )
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse(TEL + phone)
                    startActivity(intent)
                }
            }
        } else {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse(TEL + phone)
            startActivity(intent)
            // Permission has already been granted
        }
    }

}