package net.karneim.pojobuilder.testenv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestBase {
  public static final File TESTDATA_DIRECTORY = new File("src/testdata/java");;

  private static final boolean DEBUG_LOG_ENABLED = false;

  protected void logDebug(String message) {
    if (DEBUG_LOG_ENABLED) {
      System.out.println(message);
    }
  }

  public String loadResourceFromClasspath(String name) throws IOException {
    return getContent(this.getClass().getResourceAsStream(name));
  }

  public String loadResourceFromFilesystem(File directory, String name) throws IOException {
    File file = new File(directory, name);
    return loadResource(file);
  }

  private String loadResource(File file) throws FileNotFoundException {
    return getContent(new FileInputStream(file));
  }

  protected String getSourceFilename(File srcDir, Class<?> aClass) {
    return new File(srcDir, getSourceFilename(aClass.getName())).getPath();
  }

  protected String getSourceFilename(File srcDir, String fullQualifiedClassname) {
    return new File(srcDir, getSourceFilename(fullQualifiedClassname)).getPath();
  }

  protected String getSourceFilename(Class<?> aClass) {
    return getSourceFilename(aClass.getName());
  }

  public String getSourceFilename(String fullQualifiedClassname) {
    String result = fullQualifiedClassname.replace('.', '/').concat(".java");
    return result;
  }

  public static String getContent(java.io.InputStream is) {
    if (is == null) {
      return null;
    }
    @SuppressWarnings("resource")
    java.util.Scanner scanner = new java.util.Scanner(is, "UTF-8").useDelimiter("\\A");
    try {
      String result = scanner.hasNext() ? scanner.next() : "";
      result = result.replaceAll("\r\n", "\n").replaceAll(String.valueOf((char) 65279), "");
      return result;
    } finally {
      scanner.close();
    }
  }
}
