package org.infobourg.betterrss.hello

import org.infobourg.betterrss.slack.SlashCommand
import org.springframework.stereotype.Component

@Component
class HelloSlashCommand : SlashCommand(
        command = "/hello",
        handler = { _, context ->
            context.ack("Hello!")
        }
)