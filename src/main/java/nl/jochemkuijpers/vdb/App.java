package nl.jochemkuijpers.vdb;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import nl.jochemkuijpers.vdb.bot.Bot;

import static nl.jochemkuijpers.vdb.secret.Secret.BOT_TOKEN;

public class App {

	public static void main(String[] args) throws Exception {
		JDABuilder.createLight(BOT_TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
				.addEventListeners(new Bot())
				.build();

	}
}
