package io.datawire.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;

import static java.nio.charset.Charset.*;

/**
 * Indicates the filesystem path lookup the Java Archive ("JAR") that contains the loaded class. This class does not
 * work if the class was not originally loaded from a JAR.
 *
 * @author plombardi@datawire.io
 */

public class Jar {

  private final static String JAR_URI_SCHEME = "jar:file:";

  private final String path;

  private Jar(String path) {
    Objects.requireNonNull(path);

    this.path = path;
  }

  public String getPath() {
    return path;
  }

  @Override public String toString() {
    return "Jar(" + getPath() + ")";
  }

  private static String parseClassFilename(String fullyQualifiedName) {
    Objects.requireNonNull(fullyQualifiedName, "Fully qualified name is null");
    int index = fullyQualifiedName.lastIndexOf('.');
    return (index == -1 ? fullyQualifiedName : fullyQualifiedName.substring(index + 1)) + ".class";
  }

  /**
   * Returns a new instance of {@link Jar} by inspecting the {@link Jar} class itself.
   *
   * @return an instance of the {@link Jar} or null.
   */
  public static Jar lookup() {
    return lookup(Jar.class);
  }

  /**
   * Returns a new instance of {@link Jar} if the provided class is contained in a Jar otherwise it will return null.
   *
   * @param clazz the class to inspect.
   * @return an instance of the {@link Jar} or null.
   */
  public static Jar lookup(Class<?> clazz) {
    Objects.requireNonNull(clazz, "Class<?> is null");
    String qualifiedName = clazz.getName();
    String classURI = parseClassFilename(qualifiedName);

    if (!classURI.startsWith(JAR_URI_SCHEME)) {
      return null;
    }

    int index = classURI.indexOf('!');
    if (index == -1) {
      return null;
    }

    try {
      String fileName = URLDecoder.decode(classURI.substring(JAR_URI_SCHEME.length(), index), defaultCharset().name());
      return new Jar(new File(fileName).getAbsolutePath());
    } catch (UnsupportedEncodingException ex) {
      throw new InternalError("Default charset does not exist. Your JVM is either non-compliant with the JVM " +
          "specification or broken");
    }
  }
}
