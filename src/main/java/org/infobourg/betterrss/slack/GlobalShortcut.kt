package org.infobourg.betterrss.slack

import com.slack.api.bolt.context.builtin.GlobalShortcutContext
import com.slack.api.bolt.request.builtin.GlobalShortcutRequest
import com.slack.api.bolt.response.Response

abstract class GlobalShortcut(
        callbackId: String,
        override val handler: (req: GlobalShortcutRequest, context: GlobalShortcutContext) -> Response
) : SlackInteractivity<GlobalShortcutRequest, GlobalShortcutContext> {

    override val callback: String = callbackId

}