package org.infobourg.betterrss.commands

import org.infobourg.betterrss.slack.SlashCommand
import org.springframework.stereotype.Component

@Component
class RssFeedSlashCommand(private val commandHandler: CommandHandler) : SlashCommand(
    command = "/rss",
    handler = {
        request, context ->
        commandHandler.handleCommand(request)(context)
    }
)
