package com.company.behaviours.utils;

import jade.lang.acl.ACLMessage;
import java.io.PrintStream;

public class AgentLogger {
    public AgentLogger() {
    }

    public static void log(ACLMessage message) {
        if (message != null) {
            PrintStream a = System.out;
            String b = message.getSender().getLocalName();
            a.println("\n" + b + "> " + message.getContent());
        }

    }
}