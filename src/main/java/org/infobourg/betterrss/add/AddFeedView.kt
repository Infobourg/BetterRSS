package org.infobourg.betterrss.add

import org.infobourg.betterrss.models.RssFeed
import org.infobourg.betterrss.services.RssFeedService
import org.infobourg.betterrss.slack.ViewSubmission
import org.springframework.stereotype.Component
import kotlin.collections.set

@Component
class AddFeedView(
        rssParser: RssParser,
        rssFeedService: RssFeedService
) : ViewSubmission (
        callbackId = AddFeed.Modal.Id,
        onSubmit = { req, context ->
            val values = req.payload.view.state.values
            val url = values[AddFeed.Modal.Url.BlockId]?.get(AddFeed.Modal.Url.ActionId)?.value
            val workspaceId = context.teamId
            val conversationId = values[AddFeed.Modal.Conversation.BlockId]
                    ?.get(AddFeed.Modal.Conversation.ActionId)
                    ?.selectedConversation
            val errors = mutableMapOf<String, String>()
            if (url.isNullOrEmpty()) {
                errors[AddFeed.Modal.Url.BlockId] = "URL must not be empty."
            } else {
                val isRssFeed = rssParser.isRssFeed(url)

                if (!isRssFeed) {
                    errors[AddFeed.Modal.Url.BlockId] = "Invalid RSS feed."
                }
            }

            if (conversationId == null) {
                errors[AddFeed.Modal.Conversation.BlockId] = "You must choose a conversation."
            }

            if (errors.isNotEmpty()) {
                context.ack { it.responseAction("errors").errors(errors) }
            } else {
                rssFeedService.save(RssFeed(idWorkspace = workspaceId, idChannel = conversationId!!, link = url!!))
                context.ack()
            }
        }
)
