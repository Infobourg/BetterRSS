package org.infobourg.betterrss.commands

import com.slack.api.bolt.context.builtin.SlashCommandContext
import com.slack.api.bolt.request.builtin.SlashCommandRequest
import com.slack.api.bolt.response.Response
import com.slack.api.model.block.Blocks.*
import com.slack.api.model.block.composition.BlockCompositions.*
import com.slack.api.model.block.element.BlockElements.*
import org.bson.types.ObjectId
import org.infobourg.betterrss.services.RssFeedService
import org.springframework.stereotype.Component

@Component
class RssFeedCommandHandler(private val rssFeedService: RssFeedService) : CommandHandler {

    private val commandHandle: Array<CommandHandle> = arrayOf(
            CommandHandle(commandType = CommandEnum.LIST, handle = this::list),
            CommandHandle(commandType = CommandEnum.REMOVE, handle = this::remove)
    )

    override fun handleCommand(request: SlashCommandRequest): (SlashCommandContext) -> Response {
        val args = request.payload?.text?.split(" ") ?: listOf()
        if (args.isEmpty()) return this.help();
        val cmd = commandHandle.find { c -> c.commandType.command == args[0] }
        return if (cmd != null) cmd.handle(args, request) else this.help();
    }

    private fun list(args: List<String>, request: SlashCommandRequest): (SlashCommandContext) -> Response {
        val rssFeed = rssFeedService.findAll()
        return { context ->
            context.ack(asBlocks(
                    section { s -> s.text(plainText("List of rss flux")) },
                    section { s -> s.text(markdownText(rssFeed.joinToString("\n") { rss -> "- ${rss.id} : ${rss.link}" })) }
            ))
        }
    }

    private fun remove(args: List<String>, request: SlashCommandRequest): (SlashCommandContext) -> Response {
        if(args.size < 2) {
            val rssFeed = rssFeedService.findAll()
            return { context ->
                context.ack(asBlocks(
                        section { section ->
                            section
                                    .text(markdownText("Choose the rss feed to remove"))
                                    .accessory(
                                            staticSelect { staticSelect ->
                                                staticSelect
                                                        .placeholder(plainText { pt -> pt.text("Choose feed").emoji(true) })
                                                        .options(rssFeed.map { rss ->
                                                            option { option ->
                                                                option
                                                                        .text(plainText(rss.link))
                                                                        .value(rss.id)
                                                            }
                                                        })
                                            }
                                    )
                        }
                ))
            }
        } else {
            val arg = args[1];
            if(ObjectId.isValid(arg)) {
                println("$arg is valid")
                // If is an id
                rssFeedService.removeByIdRssFeed((arg))
            } else {
                // Looking or url
                rssFeedService.removeByLink(arg)
            }
            return { context -> context.ack("Rss Feed removed")}
        }
    }

    private fun help(): (SlashCommandContext) -> Response {
        return { context -> context.ack("Error in the command") }
    }
}

data class CommandHandle(
        val commandType: CommandEnum,
        val handle: (List<String>, SlashCommandRequest) -> (SlashCommandContext) -> Response
)