package com.smhanes.codingbat;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.util.Set;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class CBL {

  private String language;
  private String username;
  private String password;
  private Set<CBT> topics;

  public CBL(String language, String username, String password) throws IOException {
    this.language = language;
    this.username = username;
    this.password = password;
    this.topics = new HashSet<CBT>();

    Connection.Response initial = Jsoup.connect("http://codingbat.com/" + this.language)
        .method(Connection.Method.GET).execute();
    Connection.Response connection = Jsoup.connect("http://codingbat.com/login").data(getLogin())
        .cookies(initial.cookies()).method(Connection.Method.POST).execute();
    Document doc = Jsoup.connect("http://codingbat.com/" + language).cookies(initial.cookies())
        .get();

    Elements elements = doc.select("a[href*=/" + language + "/]");

    for (Element element : elements) {
      String topic = element.text().trim();
      topics.add(new CBT(this.language, topic, initial.cookies()));
    }
  }

  private Map<String, String> getLogin() {
    Map<String, String> login = new HashMap<String, String>();
    login.put("uname", this.username);
    login.put("pw", this.password);
    login.put("dologin", "log+in");
    login.put("fromurl", "/" + this.language);

    return login;
  }

  public String getLanguage() {
    return this.language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public Set<CBT> getTopics() {
    return this.topics;
  }
}