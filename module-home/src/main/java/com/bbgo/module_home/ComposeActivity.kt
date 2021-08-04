package com.bbgo.module_home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.bbgo.common_base.RootApplication
import com.bbgo.module_home.ui.theme.WanAndroidTheme

class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WanAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column() {
//                        Greeting("Android")
//                        MessageCoard(msg = Message("JetPack", "Compose之旅"))

                        val list = mutableListOf<Message>()

                        list.add(Message("JetPack", "this is test"))
                        list.add(Message("JetPack", "this is test2"))
                        list.add(Message("JetPack", "this is test3"))
                        list.add(Message("JetPack", "this is test4"))
                        list.add(Message("JetPack", "this is test5"))
                        list.add(Message("JetPack", "this is test6"))
                        list.add(Message("JetPack", "this is test"))
                        list.add(Message("JetPack", "this is test2"))
                        list.add(Message("JetPack", "this is test3"))
                        list.add(Message("JetPack", "this is test4"))
                        list.add(Message("JetPack", "this is test5"))
                        list.add(Message("JetPack", "this is test6"))

                        MessageCards(list)
                    }


                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!", color = Color.Blue, modifier = Modifier.clickable {
        toast()
    })
}

private fun toast() {
    Toast.makeText(RootApplication.getContext(), "toast", Toast.LENGTH_LONG).show()
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCoard(msg: Message) {
    Surface(shape = MaterialTheme.shapes.medium,
    elevation = 2.dp,
    modifier = Modifier.padding(all = 8.dp)) {
        Row() {

//            Image(painter = rememberImagePainter("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png"),
//                contentDescription = null,
//            modifier = Modifier.size(80.dp))

            Image(
                painter = rememberImagePainter("https://pics4.baidu.com/feed/a686c9177f3e6709874765e9496d7735f9dc5597.jpeg?token=8cacb0f7b17634f04fa78b2e609ddaff",
                builder = {transformations(CircleCropTransformation())}),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )

            Image(painterResource(
                id = R.mipmap.ic_launcher),
                contentDescription = "this is test image",
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, shape = CircleShape)
            )
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Column {
                Text(text = msg.author, color = MaterialTheme.colors.secondaryVariant, modifier = Modifier
                    .border(2.dp, MaterialTheme.colors.secondary, shape = RectangleShape)
                    .background(Color.Black, shape = RectangleShape)
                    .padding(all = 8.dp))
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                if (msg.body.isNotEmpty()) {
                    Text(text = msg.body)
                }

            }
        }
    }
}

@Composable
fun MessageCards(messages: MutableList<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCoard(msg = message)
        }
    }
}

@Preview(name = "Light Mode")
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES,
//showBackground = true,
//name = "Dark Mode")
@Composable
fun DefaultPreview() {
    Column {
        val list = mutableListOf<Message>()

        list.add(Message("JetPack", "this is test"))
        list.add(Message("JetPack", "this is test2"))
        list.add(Message("JetPack", "this is test3"))
        list.add(Message("JetPack", "this is test4"))
        list.add(Message("JetPack", "this is test5"))
        list.add(Message("JetPack", "this is test6"))
        list.add(Message("JetPack", "this is test"))
        list.add(Message("JetPack", "this is test2"))
        list.add(Message("JetPack", "this is test3"))
        list.add(Message("JetPack", "this is test4"))
        list.add(Message("JetPack", "this is test5"))
        list.add(Message("JetPack", "this is test6"))

        MessageCards(list)
    }
}


