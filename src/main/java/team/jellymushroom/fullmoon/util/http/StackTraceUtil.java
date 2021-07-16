package team.jellymushroom.fullmoon.util.http;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * StackTrace工具类
 * copy自
 * http://gitlab.alibaba-inc.com/sm-xss/admin-services
 *
 * @author: unknown
 * @create: 2020.02.17 20:52
 **/
public class StackTraceUtil {

  private Throwable e;

  public static String getStackTrace(Throwable e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    return sw.toString();
  }

  public static String getStackTrace2(Throwable e) {
    StackTraceUtil stackTraceUtils = new StackTraceUtil(e);
    return stackTraceUtils.getCompactStackTrace();
  }

  public StackTraceUtil(Throwable e) {
    this.e = e;
  }

  public String getCompactStackTrace() {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    pw.println(this.e);
    StackTraceElement[] trace = this.e.getStackTrace();

    for (int i = 0; i < Math.min(trace.length, 5); ++i) {
      pw.println("\tat " + trace[i]);
    }

    this.callPrintCausedStackTrace(pw, trace);
    return sw.toString();
  }

  public void printCausedStackTrace(PrintWriter pw, StackTraceElement[] causedTrace) {
    StackTraceElement[] trace = this.e.getStackTrace();
    int m = trace.length - 1;

    for (int n = causedTrace.length - 1; m >= 0 && n >= 0 && trace[m].equals(causedTrace[n]); --n) {
      --m;
    }

    int framesInCommon = trace.length - 1 - m;
    pw.println("Caused by: " + this.e);

    for (int i = 0; i <= Math.min(m, 4); ++i) {
      pw.println("\tat " + trace[i]);
    }

    if (framesInCommon != 0) {
      pw.println("\t... " + framesInCommon + " more");
    }

    this.callPrintCausedStackTrace(pw, trace);
  }

  private void callPrintCausedStackTrace(PrintWriter pw, StackTraceElement[] trace) {
    Throwable ourCause = this.e.getCause();
    if (ourCause != null) {
      StackTraceUtil st = new StackTraceUtil(ourCause);
      st.printCausedStackTrace(pw, trace);
    }

  }
}