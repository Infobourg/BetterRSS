package org.infobourg.betterrss.commands

import com.slack.api.bolt.context.builtin.SlashCommandContext
import com.slack.api.bolt.request.builtin.SlashCommandRequest
import com.slack.api.bolt.response.Response

interface CommandHandler {
    fun handleCommand(request:SlashCommandRequest): (SlashCommandContext) -> Response;
}