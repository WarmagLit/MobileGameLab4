package com.tsu.mobilegamelab4.video

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {


    private lateinit var binding: ActivityVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.videoView)

        val offlineUri: Uri = Uri.parse("android.resource://$packageName/${R.raw.testvideo}")
        val onlineUri: Uri =
            Uri.parse("https://rr5---sn-4g5edn6k.googlevideo.com/videoplayback?expire=1642682884&ei=pAXpYbbLOO-K6dsP4eefgAM&ip=156.146.60.11&id=o-AH0YW9WYN-f2540pDQZ0ngd9G5BLq9jCZ9mxM7rmfmTu&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=y_mP46LHFunEqMvy3Sze4JkG&gir=yes&clen=117476&ratebypass=yes&dur=4.063&lmt=1640639044620194&fexp=24001373,24007246,24153470&beids=24153470&c=WEB&txp=6310224&n=wldB4iFVxZ8n_Q&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRAIgRJYPbXBzb2iVLKqHMoIz1LNgSdCVbn-HZj5m74RYMh8CIBl8MfEKRnOqhdyXWDNSQufpDEd_xhkZai8unYHys3zP&rm=sn-n02xgoxufvg3-2gbs76&req_id=c4d664aae9baa3ee&redirect_counter=2&cm2rm=sn-2gbek76&cms_redirect=yes&mh=ZS&mip=37.21.193.42&mm=34&mn=sn-4g5edn6k&ms=ltu&mt=1642662040&mv=m&mvi=5&pl=24&lsparams=mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRgIhAIY3A9BruvImvSnSYqZFHxVSDti8vlZ8hr85x-XuaylQAiEApAB01e2OEMVpp1vm3V387IwNlTS2XfhLeBA-7mzoxQA%3D")

        binding.videoView.setMediaController(mediaController)
        binding.videoView.setVideoURI(offlineUri)
        binding.videoView.requestFocus()
        binding.videoView.start()
    }
}