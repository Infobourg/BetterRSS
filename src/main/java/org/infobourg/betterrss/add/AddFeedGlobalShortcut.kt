package org.infobourg.betterrss.add

import com.slack.api.bolt.response.Response
import com.slack.api.model.view.View
import org.infobourg.betterrss.slack.*
import org.springframework.stereotype.Component

@Component
class AddFeedGlobalShortcut : GlobalShortcut(
        callbackId = "global_shortcut_add_feed",
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
        callbackId = "modal_add_feed"
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
                blockId = "block_feed_url"
                element = plainTextInput {
                    actionId = "action_feed_url"
                    placeholder = plainText { text = "https://…" }
                }
                label = plainText {
                    text = "Feed URL"
                }
            }
            input {
                label = plainText {
                    text = "Choose the channel to post new entries"
                }
                element = conversationsSelect {
                    actionId = "action_conversation_select"
                    defaultToCurrentConversation = true
                }
            }
        }
    }
}



