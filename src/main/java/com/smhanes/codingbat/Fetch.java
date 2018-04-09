package com.smhanes.codingbat;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fetch {

  public static void main(String[] args) {
    try {
      Path CONFIG_FILE = Paths.get("config.yml");

      if (!Files.exists(CONFIG_FILE)) {
        Files.createFile(CONFIG_FILE);
        Files.write(CONFIG_FILE, "USERNAME: \n".getBytes(), StandardOpenOption.APPEND);
        Files.write(CONFIG_FILE, "PASSWORD: \n".getBytes(), StandardOpenOption.APPEND);
        System.out.println("\n\n     Configuration file has been created.");
        System.out.println("\n");
        System.out.println("     Please update it with your CodingBat username and password.");
        System.out.println("\n");
        System.out.println(
            "     Since you will be storing your password in the configuration file just created, it");
        System.out.println(
            "     is recommended that you first change your password on the CodingBat website to");
        System.out.println("     something completely random, long, and obscure.");
        System.out.println("\n");
        System.out.println(
            "     This site generates strong random passwords: https://www.grc.com/passwords.htm");
      } else {
        List<String> config = Files.readAllLines(CONFIG_FILE);

        if (validConfig(config)) {
          String username = getUsername(config.get(0));
          String password = getPassword(config.get(1));

          CBL java = new CBL("java", username, password);
          Set<CBT> java_topics = java.getTopics();
          Set<CBQ> java_questions = new HashSet<CBQ>();
          java_topics.forEach(topic -> java_questions.addAll(topic.getQuestions()));
          java_questions.forEach(question -> {
            try {
              new WJQ(question);
            } catch (IOException ioe) {
            }
          });

          //CBL python = new CBL("python");
          //Set<CBT> python_topics = python.getTopics();
          //Set<CBQ> python_questions = new HashSet<CBQ>();
          //python_topics.forEach(topic -> python_questions.addAll(topic.getQuestions()));
          //python_questions.forEach(question -> { try { new WriteQuestion(question); } catch (IOException ioe) { } } );
        }
      }
    } catch (IOException ioe) {
      System.out.println(ioe);
    }
  }

  private static boolean validConfig(List<String> config) {
    boolean result = false;
    String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    if (config.size() == 2) {
      if (config.get(0).length() > 10 && config.get(1).length() > 10) {
        String username = getUsername(config.get(0));
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(username);
        if (matcher.matches()) {
          result = true;
        }
      }
    }
    if (!result) {
      System.out.println("\n\n    Configuration File is Invalid.");
      System.out.println("\n    Please delete it and run this program again to recreate it.");
    }

    return result;
  }

  private static String getUsername(String s) {
    return s.substring(10);
  }

  private static String getPassword(String s) {
    return s.substring(10);
  }
}