package io.datawire.util.concurrent;


import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A ThreadFactory builder that allows any of the following features to be configured.

 * @author plombardi@datawire.io
 * @since 4.0
 */

public class ThreadFactoryBuilder {

  private String name;
  private Boolean daemon;
  private Integer priority;
  private ThreadFactory delegate;
  private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
  private ClassLoader contextClassLoader;

  /**
   * Set the name of the thread.
   *
   * @param name the name of the new thread.
   * @return the builder updated with the given format.
   */
  public ThreadFactoryBuilder withName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Configures the
   *
   * @param priority the priority of new threads created by the resulting factory.
   * @return the builder updated with the given priority.
   */
  public ThreadFactoryBuilder withPriority(int priority) {
    this.priority = priority;
    return this;
  }

  /**
   * Configures whether new threads are daemonized or not.
   *
   * @param daemon indicates whether new threads are created as daemon threads or not.
   * @return the builder updated with the given daemon status flag.
   */
  public ThreadFactoryBuilder withDaemon(boolean daemon) {
    this.daemon = daemon;
    return this;
  }

  /**
   * Configures the delegated thread factory which is used to create the new thread before modifying it.
   *
   * @param delegate the {@link ThreadFactory} to delegate thread creation to.
   * @return the builder updated with the given {@link ThreadFactory}.
   */
  public ThreadFactoryBuilder withDelegateThreadFactory(ThreadFactory delegate) {
    this.delegate = delegate;
    return this;
  }

  /**
   * Configures the uncaught exception handler that will be assigned to created threads.
   *
   * @param handler the {@link java.lang.Thread.UncaughtExceptionHandler} implementation.
   * @return the builder updated with the given {@link java.lang.Thread.UncaughtExceptionHandler}.
   */
  public ThreadFactoryBuilder withUncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
    this.uncaughtExceptionHandler = handler;
    return this;
  }

  /**
   * Configures the context class loader that will be assigned to created threads.
   *
   * @param classLoader the {@link ClassLoader} to assign to new classes.
   * @return the builder updated with the given {@link ClassLoader}.
   */
  public ThreadFactoryBuilder withContextClassLoader(ClassLoader classLoader) {
    this.contextClassLoader = classLoader;
    return this;
  }

  /**
   * Returns a new {@link ThreadFactory} using the options supplied during the building process.
   *
   * @return the fully constructed {@link ThreadFactory}.
   */
  ThreadFactory build() {
    final String name = this.name;
    final Boolean daemon = this.daemon;
    final Integer priority = this.priority;
    final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.uncaughtExceptionHandler;
    final AtomicLong threadCount = name != null ? new AtomicLong(0) : null;
    final ClassLoader contextClassLoader = this.contextClassLoader;
    final ThreadFactory parent = this.delegate != null ? this.delegate : Executors.defaultThreadFactory();

    return new ThreadFactory() {
      @Override public Thread newThread(Runnable runnable) {
        Thread result = parent.newThread(runnable);

        if (name != null) {
          result.setName(String.format(Locale.ROOT, name, threadCount.getAndIncrement()));
        }

        if (daemon != null) {
          result.setDaemon(daemon);
        }

        if (priority != null) {
          result.setPriority(priority);
        }

        if (uncaughtExceptionHandler != null) {
          result.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        }

        if (contextClassLoader != null) {
          result.setContextClassLoader(contextClassLoader);
        }

        return result;
      }
    };
  }
}
