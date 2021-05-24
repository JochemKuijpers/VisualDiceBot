package nl.jochemkuijpers.vdb.bot.features;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class HelpFeature extends ChatFeature {

	public HelpFeature() {
		super("?help");
	}

	@Override
	public void processMessage(@NotNull MessageReceivedEvent event, @NotNull String input) {
		final String response = """
`?help` \u2014 Shows this help text.
`?roll <expr>` \u2014 Rolls the expression `<expr>`, for example: `?roll d20 + 4`.
""";
		event.getMessage()
				.reply(response)
				.mentionRepliedUser(false)
				.queue();
	}
}
