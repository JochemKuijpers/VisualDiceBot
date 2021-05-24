package nl.jochemkuijpers.vdb.bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import nl.jochemkuijpers.vdb.bot.features.ChatFeature;
import nl.jochemkuijpers.vdb.bot.features.HelpFeature;
import nl.jochemkuijpers.vdb.bot.features.RollFeature;
import nl.jochemkuijpers.vdb.dice.DiceRoller;
import nl.jochemkuijpers.vdb.dice.RandomDiceRoller;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Bot extends ListenerAdapter {
	/** The SelfUser of this bot. */
	private SelfUser self;

	List<ChatFeature> features;

	public Bot() {
		final DiceRoller diceRoller = new RandomDiceRoller();

		// list of supported features
		this.features = List.of(
				new HelpFeature(),
				new RollFeature(diceRoller)
		);
	}

	@Override
	public void onReady(@NotNull ReadyEvent event) {
		self = event.getJDA().getSelfUser();
	}

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {
		final Message message = event.getMessage();
		final User author = message.getAuthor();

		// ignore bots
		if (author.isBot()) return;

		// process non-empty text messages
		final String text = message.getContentRaw();
		if (!text.isEmpty()) {
			for (ChatFeature feature : features) {
				final String prefix = feature.getPrefix();

				// test feature prefix
				if (!text.substring(0, prefix.length()).toLowerCase().equals(prefix)) {
					continue;
				}

				// execute the feature
				final String input = text.substring(prefix.length()).trim();
				feature.processMessage(event, input);
				break;
			}
		}
	}
}
