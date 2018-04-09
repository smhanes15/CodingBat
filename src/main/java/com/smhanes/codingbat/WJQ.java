package com.smhanes.codingbat;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.googlejavaformat.java.*;

public class WJQ {

  private static final String fss = FileSystems.getDefault().getSeparator();
  private String lang, topic, question_name, class_name, code_body;
  private List<String> tests, question_body;

  public WJQ(CBQ question) throws IOException {
    this.lang = question.getLanguage();
    this.topic = question.getTopic();
    this.question_name = question.getName();
    this.class_name = capitalize(this.question_name);
    this.code_body = question.getCode();
    this.tests = question.getTests();
    this.question_body = formatStringWithLengthCap(question.getQuestionText(), 100);

    Path dir = Paths.get(String.join(this.fss, this.lang, this.topic));
    Path file = Paths.get(String.join(this.fss, this.lang, this.topic, this.class_name + ".java"));
    writeOutFile(dir, file);
  }

  private static String capitalize(String s) {
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }

  private static List<String> formatStringWithLengthCap(String s, int cap) {
    s = s.replaceAll("(?:\\s*)(.{1," + cap + "})(?:\\s+|\\s*$)", "$1\n");
    return Arrays.asList(s.split("\n"));
  }

  private void writeOutFile(Path dir, Path file) throws IOException {
    Files.createDirectories(dir);
    Files.deleteIfExists(file);
    file = Files.createFile(file);

    List<String> lines = new ArrayList<String>();
    lines.add("/*\n");
    lines.add("*  " + topic + " --> " + question_name + "\n");
    lines.add("*\n");
    question_body.forEach(line -> lines.add("*    " + line + "\n"));
    lines.add("*\n");
    lines.add("*  Tests:\n");
    tests.forEach(line -> lines.add("*    " + line + "\n"));
    lines.add("*/\n");
    lines.add("public class " + class_name + " {\n");
    lines.add(code_body + "\n");
    lines.add("}");
    lines.add(
        "// Delete the line below to have this file overwritten with current CodingBat content.");
    lines.add("// %FINISHED% //");

    String unformatted = String.join(" ", lines);

    try {
      String formatted = new Formatter().formatSource(unformatted);
      if (!formatted.equals(unformatted)) {
        Files.write(file, formatted.getBytes(), StandardOpenOption.APPEND);
      }

    } catch (FormatterException fe) {
      System.out.println("Error in: " + topic + " Question: " + question_name + " at: " + fe);
      Files.deleteIfExists(file);
    }
  }
}