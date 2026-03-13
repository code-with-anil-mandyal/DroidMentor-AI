package com.droidmentorai.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.droidmentorai.R
import com.droidmentorai.data.room.entity.ChatSessionEntity
import com.droidmentorai.presentation.components.Header
import com.droidmentorai.presentation.components.HomeMenuItem
import com.droidmentorai.presentation.components.menuList
import com.droidmentorai.presentation.ui.theme.AppBackGround
import com.droidmentorai.presentation.ui.theme.DarkInputBorder
import com.droidmentorai.presentation.ui.theme.LightTextPrimary
import com.droidmentorai.presentation.ui.theme.LightYellow

@Composable
fun HomeScreen(
    onMenuClick: (Int) -> Unit,
    onChatClick: (Long) -> Unit,
    onSeeAllClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {


    val recentSession by viewModel.recentSessions.collectAsState(initial = emptyList())
    val sessionCount by viewModel.sessionCount.collectAsState(initial = 0)

    Column(modifier = Modifier
        .fillMaxSize()) {

        Header(
            isShowBack = false,
            isShowRight = false,
            title = stringResource(R.string.droidmentor_ai)
        )



        //Home content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = AppBackGround
                )
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                //Home Menu
                item {
                    HomeMenuSection { id ->
                        onMenuClick(id)
                    }
                }

                //History title
                if(recentSession.isNotEmpty()){
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 10.dp,
                                    top = 30.dp
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                modifier = Modifier.weight(1f),
                                text = stringResource(R.string.chat_history),
                                style = TextStyle(
                                    color = LightTextPrimary,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.W700
                                )
                            )

                            if(sessionCount > 5){
                                Text(
                                    modifier = Modifier.clickable {
                                        onSeeAllClick()
                                    },
                                    text = stringResource(R.string.see_all),
                                    textDecoration = TextDecoration.Underline,
                                    style = TextStyle(
                                        color = LightTextPrimary,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.W600
                                    )
                                )
                            }

                        }

                    }
                }



                //List Section
                items(recentSession){  session ->

                    ChatHistorySection(session = session, onClick = { sessionId ->
                        onChatClick(sessionId)
                    })
                }
            }

        }


    }

}

@Composable
fun HomeMenuSection(onMenuClick: (Int) -> Unit) {

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .padding(top = 11.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            HomeMenuItem(
                title = menuList[0].title,
                id = menuList[0].id,
                color = menuList[0].color,
                modifier = Modifier.weight(1f)
            ) {
                onMenuClick(menuList[0].id)
            }

            HomeMenuItem(
                title = menuList[1].title,
                id = menuList[1].id,
                color = menuList[1].color,
                modifier = Modifier.weight(1f)
            ) {
                onMenuClick(menuList[1].id)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            HomeMenuItem(
                title = menuList[2].title,
                id = menuList[2].id,
                color = menuList[2].color,
                modifier = Modifier.weight(1f)
            ) {
                onMenuClick(menuList[2].id)
            }

            HomeMenuItem(
                title = menuList[3].title,
                id = menuList[3].id,
                color = menuList[3].color,
                modifier = Modifier.weight(1f)
            ) {
                onMenuClick(menuList[3].id)
            }
        }
    }
}



@Composable
fun ChatHistorySection(modifier: Modifier = Modifier,
                       session : ChatSessionEntity,
                       onClick: (sessionId : Long) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick(session.sessionId)
            }
            .height(56.dp)
            .padding(vertical = 2.dp, horizontal = 0.dp)
            .background(LightYellow)
    ) {

        Spacer(Modifier.height(8.dp))

        Text(
            text = session.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                style = TextStyle(
                color = LightTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600
            )
        )

        Spacer(Modifier.height(5.dp))

        Text(
            text = session.lastMessage?:"",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            style = TextStyle(
                color = DarkInputBorder,
                fontSize = 12.sp,
                fontWeight = FontWeight.W400
            )
        )

        Spacer(Modifier.height(8.dp))
    }
}