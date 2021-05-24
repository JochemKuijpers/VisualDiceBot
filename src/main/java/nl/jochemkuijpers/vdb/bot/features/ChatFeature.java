package nl.jochemkuijpers.vdb.bot.features;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

/** A feature of the Discord bot, activated by a received message */
public abstract class ChatFeature {

	/** The feature prefix. Messages starting with this string will be handled by this feature, excluding the prefix. */
	private final String prefix;

	/** Create a new ChatFeature with given prefix. */
	public ChatFeature(@Nonnull String prefix) {
		this.prefix = prefix;
	}

	@Nonnull
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Processes the message (only called when canProcess returned true.
	 *
	 * @param event	the event to process
	 * @param input	the raw event text without the prefix (stripped of leading and trailing whitespace).
	 */
	public abstract void processMessage(@Nonnull MessageReceivedEvent event, @Nonnull String input);
}
