package fr.unice.polytech.ogl.islda;

import eu.ace_design.island.bot.IExplorerRaid;
import fr.unice.polytech.ogl.islda.answers.Answer;
import fr.unice.polytech.ogl.islda.model.Context;
import fr.unice.polytech.ogl.islda.utils.LaunchBot;
import fr.unice.polytech.ogl.islda.utils.Utils;

/**
 * @author Pascal Tung
 * @version 14/03/15
 */
public class Explorer implements IExplorerRaid {
    LaunchBot launcher;
    Answer result;
    Context contextObject;

    @Override
    public void initialize(String context) {
        Utils.init();
        contextObject = Utils.json.fromJson(context, Context.class);
        launcher = new LaunchBot(contextObject);
    }

    @Override
    public String takeDecision() {
        return launcher.takeDecision(result);
    }

    @Override
    public void acknowledgeResults(String results) {
        result = launcher.handleResults(results);
    }
}
