package org.infobourg.betterrss.slack

import com.slack.api.bolt.context.builtin.MessageShortcutContext
import com.slack.api.bolt.request.builtin.MessageShortcutRequest
import com.slack.api.bolt.response.Response

abstract class MessageShortcut(
    callbackId: String,
    override val handler: (req: MessageShortcutRequest, context: MessageShortcutContext) -> Response
) : SlackInteractivity<MessageShortcutRequest, MessageShortcutContext> {
    override val callback: String = callbackId
}
