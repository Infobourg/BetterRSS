package org.infobourg.betterrss.config

import com.slack.api.bolt.App
import org.infobourg.betterrss.slack.GlobalShortcut
import org.infobourg.betterrss.slack.MessageShortcut
import org.infobourg.betterrss.slack.SlashCommand
import org.infobourg.betterrss.slack.ViewSubmission
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SlackApp {
    @Bean
    open fun initSlackApp(
            slashCommands: List<SlashCommand> = emptyList(),
            globalShortcuts: List<GlobalShortcut> = emptyList(),
            messageShortcuts: List<MessageShortcut> = emptyList(),
            viewSubmissions: List<ViewSubmission> = emptyList()
    ): App {
        val app = App()

        slashCommands.forEach { app.command(it.callback, it.handler) }
        globalShortcuts.forEach { app.globalShortcut(it.callback, it.handler) }
        messageShortcuts.forEach { app.messageShortcut(it.callback, it.handler) }
        viewSubmissions.forEach {
            app.viewSubmission(it.callback, it.onSubmit)
            if (it.onClose != null) { app.viewClosed(it.callback, it.onClose) }
        }

        return app
    }
}

