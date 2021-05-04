package kz.smartideagroup.jumys.common.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.utils.TEL
import org.jetbrains.anko.support.v4.alert

open class BaseFragment(private val resource: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(resource, container, false)
    }

    fun showErrorDialog(errorTitle: String, errorMessage: String) {
        alert {
            title = errorTitle
            message = errorMessage
            isCancelable = false
            negativeButton(getString(R.string.dialog_exit)) {
                activity?.finish()
            }
        }.show()
    }

    fun showLongToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    fun showShortToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun navigateTo(navDirections: Int) {
        requireActivity().findNavController(R.id.container)
            .navigate(navDirections)
    }

    fun call(phone: String) {
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