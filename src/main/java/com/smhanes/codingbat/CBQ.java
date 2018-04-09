package com.smhanes.codingbat;

import java.util.List;
import java.util.Map;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.io.IOException;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.lang.AssertionError;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class CBQ {

  private String language;
  private String name;
  private String topic;
  private String problem_id;
  private String question;
  private String code;
  private List<String> tests;

  public CBQ(String language, String topic, String name, String problem_id,
      Map<String, String> cookies) throws IOException {
    this.language = language;
    this.name = name;
    this.topic = topic;
    this.problem_id = problem_id;
    this.tests = new ArrayList<String>();

    Document doc = Jsoup.connect("http://codingbat.com/prob/" + this.problem_id).cookies(cookies)
        .get();
    this.question = doc.select("div.minh p.max2").first().text().trim();

    List<String> data = new ArrayList<String>();
    Elements elements = doc.select("form[name=codeform] input[type=hidden]");
    elements.forEach(x -> data.add(encodeURIComponent(x.attr("name"), x.attr("value"))));

    this.code = doc.select("form[name=codeform] div#ace_div").text().trim();
    data.add(encodeURIComponent("code", code));

    doc = Jsoup.connect("http://codingbat.com/run")
        .requestBody(encodedQueryString(data))
        .header("Content-Type", "application/x-www-form-urlencoded")
        .postDataCharset("UTF-8")
        .cookies(cookies)
        .method(Connection.Method.POST)
        .execute()
        .parse();

    doc.select("#tests table tbody tr td:eq(0)").forEach(x -> this.tests.add(x.text()));
  }

  private String encodeURIComponent(String name, String value) throws AssertionError {
    try {
      return URLEncoder.encode(name, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8") + "&";
    } catch (UnsupportedEncodingException UEE) {
      throw new AssertionError(UEE);
    }
  }

  private String encodedQueryString(List<String> data) {
    StringBuilder encoded = new StringBuilder();
    data.forEach(x -> encoded.append(x));

    return encoded.toString();
  }

  public String getLanguage() {
    return this.language;
  }

  public String getTopic() {
    return this.topic;
  }

  public String getProblemId() {
    return this.problem_id;
  }

  public String getQuestionText() {
    return this.question;
  }

  public String getName() {
    return this.name;
  }

  public String getCode() {
    return this.code;
  }

  public List<String> getTests() {
    return this.tests;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public void setProblemId(String problem_id) {
    this.problem_id = problem_id;
  }

  public void setQuestionText(String question) {
    this.question = question;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setTests(List<String> tests) {
    this.tests = tests;
  }
}