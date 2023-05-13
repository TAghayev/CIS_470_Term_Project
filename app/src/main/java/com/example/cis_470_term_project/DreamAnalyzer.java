package com.example.cis_470_term_project;

import java.util.Locale;

public class DreamAnalyzer {

    public static String analyze(String description) {
        String analysis = "Analysis: ";
        String lowerDescription = description.toLowerCase(Locale.ROOT);
        int count = 0;

        if (lowerDescription.contains("water")) {
            analysis += "\n- Water: Represents emotions and the unconscious mind.";
            count++;
        }

        if (lowerDescription.contains("falling")) {
            analysis += "\n- Falling: Indicates feelings of insecurity or anxiety.";
            count++;
        }

        if (lowerDescription.contains("flying")) {
            analysis += "\n- Flying: Symbolizes freedom, ambition, and escaping limitations.";
            count++;
        }

        if (lowerDescription.contains("teeth")) {
            analysis += "\n- Teeth: Represents concerns about self-image or fear of rejection.";
            count++;
        }

        if (count == 0) {
            analysis += "No specific keywords detected. This dream might be a reflection of your day-to-day experiences or emotions.";
        }

        return analysis;
    }
}