package org.infobourg.betterrss.add

import org.infobourg.betterrss.slack.ViewSubmission
import org.springframework.stereotype.Component

@Component
class AddFeedView : ViewSubmission(
        callbackId = AddFeed.Modal.Id,
        onSubmit = { req, context ->
            val values = req.payload.view.state.values
            val url = values[AddFeed.Modal.Url.BlockId]?.get(AddFeed.Modal.Url.ActionId)?.value

            // TODO Check content is a RSS Feed

            // TODO Save url

            context.ack()
        }
)
