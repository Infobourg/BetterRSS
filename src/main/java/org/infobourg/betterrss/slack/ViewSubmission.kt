package org.infobourg.betterrss.slack

import com.slack.api.bolt.context.builtin.DefaultContext
import com.slack.api.bolt.context.builtin.MessageShortcutContext
import com.slack.api.bolt.context.builtin.ViewSubmissionContext
import com.slack.api.bolt.request.builtin.MessageShortcutRequest
import com.slack.api.bolt.request.builtin.ViewClosedRequest
import com.slack.api.bolt.request.builtin.ViewSubmissionRequest
import com.slack.api.bolt.response.Response

abstract class ViewSubmission(
        callbackId: String,
        val onSubmit: (ViewSubmissionRequest, ViewSubmissionContext) -> Response,
        val onClose: ((ViewClosedRequest, DefaultContext) -> Response)? = null
) {

    val callback: String = callbackId

}