package net.sf.josceleton.core.impl.async;

import net.sf.josceleton.core.api.async.Async;
import net.sf.josceleton.core.api.async.Closeable;
import net.sf.josceleton.core.api.async.Listener;

/**
 * Merger interface for {@link CloseableAndAsyncSkeleton} only.
 */
public interface CloseableAsync<L extends Listener> extends Async<L>, Closeable { /* deliberately empty */ }
