package org.infobourg.betterrss.commands

import com.slack.api.bolt.context.builtin.SlashCommandContext
import com.slack.api.bolt.request.builtin.SlashCommandRequest
import com.slack.api.bolt.response.Response
import com.slack.api.model.block.LayoutBlock
import org.bson.types.ObjectId
import org.infobourg.betterrss.models.RssFeed
import org.infobourg.betterrss.services.RssFeedService
import org.infobourg.betterrss.slack.block
import org.infobourg.betterrss.slack.option
import org.infobourg.betterrss.slack.plainText
import org.infobourg.betterrss.slack.staticSelect
import org.springframework.stereotype.Component

typealias  Handle = (args: List<String>, request: SlashCommandRequest) -> (SlashCommandContext) -> Response

@Component
class RssFeedCommandHandler(private val rssFeedService: RssFeedService) : CommandHandler {


    override fun handleCommand(request: SlashCommandRequest): (SlashCommandContext) -> Response {
        val args = request.payload?.text?.split(" ") ?: listOf()
        if (args.isEmpty()) return this.help(args, request);
        val cmd = commandHandle.find { c -> c.commandType.command == args[0] }
        return if (cmd != null) cmd.handle(args, request) else this.help(args, request)
    }

    private val list: Handle = list@{ _: List<String>, _: SlashCommandRequest ->
        val rssFeed = rssFeedService.findAll()
        return@list { context: SlashCommandContext ->
            context.ack(block {
                section { text = plainText { text = "List of rss flux" } }
                section { text = plainText { text = rssFeed.joinToString("\n") { rss -> "- ${rss.id} : ${rss.link}" } } }
            })
        }
    }

    private val remove: Handle = remove@{ args: List<String>, _: SlashCommandRequest ->
        if (args.size < 2) {
            val rssFeeds = rssFeedService.findAll()
            return@remove { context ->
                context.ack(removeSelectionBlock(rssFeeds))
            }
        } else {
            val arg = args[1];
            if (ObjectId.isValid(arg)) {
                println("$arg is valid")
                // If is an id
                if (!rssFeedService.existsById(arg))
                    return@remove { ctx -> ctx.ack("No Rss Feed with this id") }
                rssFeedService.removeByIdRssFeed((arg))
            } else {
                // Looking or url
                if (!rssFeedService.existsByLink(arg))
                    return@remove { ctx -> ctx.ack("No Rss Feed with this link") }
                rssFeedService.removeByLink(arg)
            }
            return@remove { context -> context.ack("Rss Feed removed") }
        }
    }

    private val help: Handle = help@{ _: List<String>, _: SlashCommandRequest ->
        return@help { context -> context.ack("Error in the command") }
    }

    private val commandHandle: Array<CommandHandle> = arrayOf(
            CommandHandle(commandType = CommandEnum.LIST, handle = this.list),
            CommandHandle(commandType = CommandEnum.REMOVE, handle = this.remove)
    )

    private fun removeSelectionBlock(rssFeeds: List<RssFeed>): List<LayoutBlock> {
        return block {
            section {
                text = plainText { text = "Choose the rss feed to remove" }
                accessory = staticSelect {
                    placeholder = plainText {
                        text = "Choose feed"
                    }
                    options = rssFeeds.map { rss ->
                        option {
                            text = plainText { text = rss.link }
                            value = rss.id
                        }
                    }

                }
            }
        }
    }
}

data class CommandHandle(
        val commandType: CommandEnum,
        val handle: (List<String>, SlashCommandRequest) -> (SlashCommandContext) -> Response
)