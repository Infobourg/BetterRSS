package org.infobourg.betterrss.slack

import com.slack.api.model.block.InputBlock
import com.slack.api.model.block.LayoutBlock
import com.slack.api.model.block.SectionBlock
import com.slack.api.model.block.composition.OptionObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.ConversationsSelectElement
import com.slack.api.model.block.element.PlainTextInputElement
import com.slack.api.model.block.element.StaticSelectElement
import com.slack.api.model.view.View
import com.slack.api.model.view.ViewClose
import com.slack.api.model.view.ViewSubmit
import com.slack.api.model.view.ViewTitle

fun view(builder: View.() -> Unit): View {
    return View().apply(builder)
}

fun block(builder: BlockBuilder.() -> Unit): List<LayoutBlock> {
    return BlockBuilder().apply(builder).layouts;
}

fun View.title(builder: ViewTitle.() -> Unit) {
    val title = this.title ?: ViewTitle.builder().type("plain_text").text("").build()
    this.title = title.apply(builder)
}

fun View.submit(builder: ViewSubmit.() -> Unit) {
    val submit = this.submit ?: ViewSubmit.builder().type("plain_text").text("").build()
    this.submit = submit.apply(builder)
}

fun View.close(builder: ViewClose.() -> Unit) {
    val close = this.close ?: ViewClose.builder().type("plain_text").text("").build()
    this.close = close.apply(builder)
}

fun View.blocks(builder: BlockBuilder.() -> Unit) {
    this.blocks = BlockBuilder().apply(builder).layouts
}

class BlockBuilder {
    internal val layouts = mutableListOf<LayoutBlock>()

    fun input(builder: InputBlock.() -> Unit) {
        layouts.add(InputBlock().apply(builder))
    }

    fun section(builder: SectionBlock.() -> Unit) {
        layouts.add(SectionBlock().apply(builder))
    }
}

fun plainTextInput(builder: PlainTextInputElement.() -> Unit): PlainTextInputElement {
    return PlainTextInputElement().apply(builder)
}

fun plainText(builder: PlainTextObject.() -> Unit): PlainTextObject {
    return PlainTextObject().apply(builder)
}

fun conversationsSelect(builder: ConversationsSelectElement.() -> Unit): ConversationsSelectElement {
    return ConversationsSelectElement().apply(builder)
}

fun staticSelect(builder: StaticSelectElement.() -> Unit): StaticSelectElement {
    return StaticSelectElement().apply(builder)
}

fun option(build: OptionObject.() -> Unit): OptionObject {
    return OptionObject().apply(build)
}
