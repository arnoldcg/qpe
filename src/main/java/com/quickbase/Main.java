package com.quickbase;

import com.quickbase.domain.service.Orchestrator;
import com.quickbase.domain.service.impl.OrchestratorImpl;

/**
 * The main method of the executable JAR generated from this repository. This is to let you
 * execute something from the command-line or IDE for the purposes of demonstration, but you can choose
 * to demonstrate in a different way (e.g. if you're using a framework)
 */
public class Main {
    public static void main(String args[]) {
        try {
            Orchestrator orchestrator = new OrchestratorImpl();
            orchestrator.configureApplication();
            orchestrator.checkServiceHealth();
            orchestrator.retrieveTotalPopulationByCountry();
            orchestrator.closeApplication();
        } catch (Exception e) {
            System.out.println("Failed execution of the program.");
            System.exit(1);
        }
    }
}