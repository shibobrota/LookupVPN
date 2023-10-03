package com.das.vpntestsampleapp

import android.os.Bundle
import android.util.Log
import android.widget.ScrollView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.das.lookupvpn.LookupVPN
import com.das.vpntestsampleapp.ui.theme.VPNTestSampleAppTheme
import kotlin.math.log

class MainActivity : ComponentActivity() {

    val TAG = "MainActivity:"
    lateinit var lookupVpn: LookupVPN
    var serverData = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VPNTestSampleAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var shouldShow : Boolean by remember { mutableStateOf(false) }
                        Log.d(TAG, ":::: " + shouldShow)
                        Button(
                            enabled = !shouldShow,
                            onClick = {
                                serverData = "Loading.."
                                shouldShow = true
                                lookupVpn.fetchServerData({
                                    Log.d(TAG, it)
                                    serverData = it
                                    shouldShow = false
                                }, {
                                    Log.d(TAG, it)
                                    serverData = it
                                    shouldShow = false
                                })
                            },
                            modifier = Modifier.padding(vertical = 10.dp)
                        ) {
                            Text(text = "Fetch Data")
                        }
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            Text(text = serverData.take(10000))
                        }
                        if(shouldShow) activityIndicator()
                    }
                }
            }
        }
        lookupVpn = LookupVPN(this)
    }

    val onServerResponse = fun(res: String){
        Log.d(TAG, res)
        serverData = res
    }

    val onServerError = fun(err: String){
        Log.d(TAG, err)
        serverData = err
    }

    fun onServer(it: String){
        Log.d(TAG, "::::")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun activityIndicator(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VPNTestSampleAppTheme {
        Greeting("Android")
    }
}