package org.infobourg.betterrss.slack

import com.slack.api.bolt.context.Context
import com.slack.api.bolt.request.Request
import com.slack.api.bolt.response.Response

interface SlackInteractivity<R : Request<C>, C : Context> {
    val callback: String

    val handler: (req: R, context: C) -> Response
}
