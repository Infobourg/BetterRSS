package org.infobourg.betterrss

import org.infobourg.betterrss.config.SlackApp
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@ContextConfiguration(classes = [SlackApp::class])
@RunWith(SpringRunner::class)
internal class BetterRssApplicationTests {
    @Test
    fun contextLoads() {
    }
}
