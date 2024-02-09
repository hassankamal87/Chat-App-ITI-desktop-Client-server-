package com.whisper.server.business.services.interfaces;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.*;
public class ChatterBotService {
    public static String getChatterBotResponse(String textline) throws Exception {

        ChatterBotFactory factory = new ChatterBotFactory();

        ChatterBot bot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
        ChatterBotSession session = bot.createSession();

        String responseMessage = textline;
        responseMessage = session.think(responseMessage);

        return responseMessage;

    }
}
