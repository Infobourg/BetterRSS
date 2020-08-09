package org.infobourg.betterrss.add

import com.slack.api.bolt.response.Response
import com.slack.api.model.view.View
import org.infobourg.betterrss.slack.GlobalShortcut
import org.infobourg.betterrss.slack.blocks
import org.infobourg.betterrss.slack.close
import org.infobourg.betterrss.slack.conversationsSelect
import org.infobourg.betterrss.slack.plainText
import org.infobourg.betterrss.slack.plainTextInput
import org.infobourg.betterrss.slack.submit
import org.infobourg.betterrss.slack.title
import org.infobourg.betterrss.slack.view
import org.springframework.stereotype.Component

@Component
class AddFeedGlobalShortcut : GlobalShortcut(
    callbackId = AddFeed.GlobalShortcutId,
    handler = { _, context ->
        val view = context.client().viewsOpen { request ->
            request.view(addFeedModal())
                .triggerId(context.triggerId)
        }

        if (view.isOk) context.ack()
        Response.builder().statusCode(500).body(view.error).build()
    }
)

fun addFeedModal(): View {
    return view {
        callbackId = AddFeed.Modal.Id
        type = "modal"
        notifyOnClose = true
        clearOnClose = true
        title {
            text = "Add a RSS feed"
        }
        submit {
            text = "Add"
        }
        close {
            text = "Cancel"
        }
        blocks {
            input {
                blockId = AddFeed.Modal.Url.BlockId
                element = plainTextInput {
                    actionId = AddFeed.Modal.Url.ActionId
                    placeholder = plainText { text = "https://…" }
                }
                label = plainText {
                    text = "Feed URL"
                }
            }
            input {
                blockId = AddFeed.Modal.Conversation.BlockId
                label = plainText {
                    text = "Choose the channel to post new entries"
                }
                element = conversationsSelect {
                    actionId = AddFeed.Modal.Conversation.ActionId
                    defaultToCurrentConversation = true
                }
            }
        }
    }
}
