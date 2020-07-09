package org.infobourg.betterrss.slack

import com.slack.api.bolt.context.builtin.SlashCommandContext
import com.slack.api.bolt.request.builtin.SlashCommandRequest
import com.slack.api.bolt.response.Response
import org.infobourg.betterrss.rss.RssCommandHandler

abstract class SlashCommand(
        command: String,
        override val handler: (req: SlashCommandRequest, context: SlashCommandContext) -> Response
) : SlackInteractivity<SlashCommandRequest, SlashCommandContext> {

    override val callback: String = command

}