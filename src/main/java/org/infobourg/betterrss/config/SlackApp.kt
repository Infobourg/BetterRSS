package org.infobourg.betterrss.config

import com.slack.api.bolt.App
import kotlinx.coroutines.runBlocking
import org.infobourg.betterrss.slack.GlobalShortcut
import org.infobourg.betterrss.slack.MessageShortcut
import org.infobourg.betterrss.slack.SlashCommand
import org.infobourg.betterrss.slack.ViewSubmission
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SlackApp {
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    open fun initSlackApp(
        slashCommands: List<SlashCommand>?,
        globalShortcuts: List<GlobalShortcut>?,
        messageShortcuts: List<MessageShortcut>?,
        viewSubmissions: List<ViewSubmission>?
    ): App {
        val app = App()

        slashCommands?.forEach { app.command(it.callback, it.handler) }
        globalShortcuts?.forEach { app.globalShortcut(it.callback, it.handler) }
        messageShortcuts?.forEach { app.messageShortcut(it.callback, it.handler) }
        viewSubmissions?.forEach {
            app.viewSubmission(it.callback) { request, context ->
                runBlocking { it.onSubmit(request, context) }
            }
            if (it.onClose != null) {
                app.viewClosed(it.callback, it.onClose)
            }
        }

        return app
    }
}
