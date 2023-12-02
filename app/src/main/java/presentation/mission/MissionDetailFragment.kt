package presentation.mission

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.tome_aos.R
import com.example.tome_aos.databinding.FragmentMissionDetailBinding
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import presentation.MainActivity
import presentation.mission.decibel.MissionDecibelFragment
import presentation.mission.photo.MissionPhotoFragment
import presentation.mission.text.MissionTextFragment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MissionDetailFragment : Fragment() {
    private lateinit var binding: FragmentMissionDetailBinding
    private lateinit var goButton: Button
    private lateinit var backButton: Button
    private lateinit var missionImage: ImageView
    private lateinit var typeText: TextView
    private lateinit var detailText: TextView

    companion object {
        private const val TEXT_TYPE = 0
        private const val PHOTO_TYPE = 1
        private const val DECIBEL_TYPE = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMissionDetailBinding.inflate(inflater, container, false).apply {
            goButton = goMissionBtn
            backButton = backMissionBtn
            missionImage = detailImageView
            typeText = missionTypeText
            detailText = missionDetailText
        }
        var missionDetail = arguments?.getString("missionTitle")
        val missionType = arguments?.getInt("missionType")
        val missionID = arguments?.getInt("missionID")

        val missionFragment = MissionFragment()
        val missionTextFragment = MissionTextFragment()
        val missionDecibelFragment = MissionDecibelFragment()
        val missionPhotoFragment = MissionPhotoFragment()
        val transaction = parentFragmentManager.beginTransaction()

        val bundle = Bundle(2)
        bundle.putString("missionTitle", missionDetail)
        bundle.putInt("missionID", missionID!!)

        missionDecibelFragment.arguments = bundle
        missionTextFragment.arguments = bundle
        missionPhotoFragment.arguments = bundle

        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavigation(true)


        detailText.text = missionDetail
        when(missionType){
            PHOTO_TYPE -> {
                typeText.text = "찰칵 미션"
                missionImage.setImageResource(R.drawable.img_mission_camera)
            }
            DECIBEL_TYPE -> {
                typeText.text = "데시벨 미션"
                missionImage.setImageResource(R.drawable.img_mission_decibel)
            }
            TEXT_TYPE -> {
                typeText.text = "텍스트 미션"
                missionImage.setImageResource(R.drawable.img_mission_text)
            }
        }

        goButton.setOnClickListener {
            when(missionType){
                PHOTO_TYPE -> {
                    transaction.replace(R.id.main_frameLayout, missionPhotoFragment)
                    transaction.addToBackStack(null);
                    transaction.commit()
                }
                DECIBEL_TYPE -> {
                    transaction.replace(R.id.main_frameLayout, missionDecibelFragment)
                    transaction.addToBackStack(null);
                    transaction.commit() }
                TEXT_TYPE -> {
                    transaction.replace(R.id.main_frameLayout, missionTextFragment)
                    transaction.addToBackStack(null)
                    transaction.commit() }
            }
        }

        backButton.setOnClickListener {
            transaction.replace(R.id.main_frameLayout, missionFragment)
            transaction.addToBackStack(null);
            transaction.commit()
        }

        return binding.root
    }

}