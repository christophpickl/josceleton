package net.sf.josceleton.core.impl.async;

import net.sf.josceleton.core.api.async.Listener;

/**
 * Child should protect any non-private method with a pre-condtion by invoking validateNotYetClosed() as the first stmt!
 */
public abstract class CloseableAndAsyncSkeleton<L extends Listener>
	implements CloseableAsync<L> {

	private final AsyncDelegator<L> asyncDelegator = new SkeletonAsyncDelegator<L>(this);
	
	/** state-full property */
	private boolean yetClosed = false;


	/** {@inheritDoc} from {@link Async} */
	@Override public final void addListener(final L listener) {
		this.asyncDelegator.addListener(listener);
	}

	/** {@inheritDoc} from {@link Async} */
	@Override public final void removeListener(final L listener) {
		this.asyncDelegator.removeListener(listener);
	}

	protected final Iterable<L> getListeners() {
		return this.asyncDelegator.getListeners();
	}
	
	/** {@inheritDoc} from {@link Closeable} */
	@Override public final void close() {
		// assert(yetClosed == false); AOP
		this.validateNotYetClosed();
		this.prepareToClose();
		this.yetClosed = true;
	}
	
	/** Template method which will be invoked at most one-time when {@link #close()} is invoked. */
	protected abstract void prepareToClose();
	
	protected final void validateNotYetClosed() {
		if(this.yetClosed == true) {
			throw new IllegalStateException("Connection already closed!");
		}
	}
	
	protected final boolean isYetClosed() {
		return this.yetClosed;
	}
	

	private static class SkeletonAsyncDelegator<L extends Listener> extends AsyncDelegator<L> {
		
		private final CloseableAndAsyncSkeleton<L> skeleton;
		
		SkeletonAsyncDelegator(final CloseableAndAsyncSkeleton<L> skeleton) {
			this.skeleton = skeleton;
		}
		
		/** {@inheritDoc} from {@link AsyncDelegator} */
		@Override protected final void beforeAddListener(final L listener) {
			this.skeleton.validateNotYetClosed();
		}

		/** {@inheritDoc} from {@link AsyncDelegator} */
		@Override protected final void beforeRemoveListener(final L listener) {
			this.skeleton.validateNotYetClosed();
		}
	}
}
