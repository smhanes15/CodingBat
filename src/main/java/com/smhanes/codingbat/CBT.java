package com.smhanes.codingbat;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.Set;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class CBT {

  private static final String fss = FileSystems.getDefault().getSeparator();
  private String language;
  private String topic;
  private Set<CBQ> questions;

  public CBT(String language, String topic, Map<String, String> cookies) throws IOException {
    this.language = language;
    this.topic = topic;
    questions = new HashSet<CBQ>();

    Document doc = Jsoup.connect("http://codingbat.com/" + this.language + "/" + this.topic)
        .cookies(cookies).get();
    Elements elements = doc.select("a[href*=/prob/]");

    for (Element element : elements) {
      String problem_id = element.attr("href").substring(6);
      String name = element.text();

      Path file = Paths
          .get(String.join(fss, this.language, this.topic, capitalize(name) + ".java"));
      boolean fetchQuestion = true;
      if (Files.exists(file)) {
        List<String> fileLines = Files.readAllLines(file);
        int numLines = fileLines.size();
        if (numLines > 0 && fileLines.get(numLines - 1).contains("%FINISHED%")) {
          fetchQuestion = false;
        }
      }

      if (fetchQuestion) {
        questions.add(new CBQ(this.language, this.topic, name, problem_id, cookies));
      }
    }
  }

  public String getLanguage() {
    return this.language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getTopic() {
    return this.topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public Set<CBQ> getQuestions() {
    return this.questions;
  }

  private static String capitalize(String s) {
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }
}