package fr.unice.polytech.ogl.islda.utils;

import fr.unice.polytech.ogl.islda.answers.Answer;
import fr.unice.polytech.ogl.islda.decisions.*;
import fr.unice.polytech.ogl.islda.map.Direction;
import fr.unice.polytech.ogl.islda.parameters.LandParameters;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Lucas MARTINEZ
 * @author Pascal Tung
 * @version 14/03/15
 */
public class DecisionManager {
    private Queue<Decision> decisions;

    public DecisionManager() {
        decisions = new LinkedList<>();
    }

    public String getStopJson() {
        decisions.offer(new StopDec());
        return getJSONDecision();
    }

    public String getLandJson(LandParameters lParam) {
        decisions.offer(new LandDec(lParam));
        return getJSONDecision();
    }

    public String getExploreJson() {
        decisions.offer(new ExploreDec());
        return getJSONDecision();
    }

    public String getExploitJson(String resource) {
        decisions.offer(new ExploitDec(resource));
        return getJSONDecision();
    }

    public String getScoutJson(Direction direction) {
        decisions.offer(new ScoutDec(direction));
        return getJSONDecision();
    }

    public String getGlimpseJson(Direction direction, int range) {
        decisions.offer(new GlimpseDec(direction, range));
        return getJSONDecision();
    }

    public String getMoveJson(Direction direction) {
        decisions.offer(new MoveDec(direction));
        return getJSONDecision();
    }

    private String getJSONDecision() {
        return decisions.peek().toJSON();
    }

    /**
     * Check if the queue decisions is empty
     * @return True if the queue decisions is empty
     */
    public boolean isEmpty() {
        return decisions.isEmpty();
    }

    /**
     * Parse results with the last decision
     *
     * @param results The response JSON of the island engine
     * @return The Answer which is the result of the response JSON
     */

    public Answer parseResults(String results) {
        Decision lastDecision = decisions.poll();
        if (lastDecision != null) {
            return lastDecision.parseResults(results);
        }

        return null;
    }

    /**
     * Return the json of the first decision
     *
     * @return The JSON of the first decision of the queue decisions
     */

    // this is only a cheat for week15 (allows us to stay a little longer)
    public String getFirstDecisionJson() {
        if (decisions.isEmpty()) {
            return getStopJson();
        }

        return decisions.peek().toJSON();
    }

    /**
     * Add decision to decisions queue
     *
     * @param decision The decision to add in the queue decisions
     */
    public void addDecisionToQueue(Decision decision) {
        decisions.offer(decision);
    }

    /**
     * Count the decisions to do
     * @return Size of the queue decisions
     */
    public int count() {
        return decisions.size();
    }

    /**
     * Clean the decisions queue
     */
    public void clean() {
        decisions.clear();
    }
}
