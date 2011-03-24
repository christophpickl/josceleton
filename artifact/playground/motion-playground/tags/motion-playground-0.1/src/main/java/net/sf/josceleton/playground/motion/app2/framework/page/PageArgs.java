package net.sf.josceleton.playground.motion.app2.framework.page;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PageArgs {
	
	private final Map<String, Object> data = new HashMap<String, Object>();
	
	PageArgs() {
		// only via builder
	}
	
	void set(String key, Object val) {
		this.data.put(key, val);
	}
	
	public <T> T get(String key, Class<T> type) {
		final Object val = this.data.get(key);
		if(val == null) {
			return null;
		}
		return type.cast(val);
	}
	
	@Override public String toString() {
		return "PageArgs[" + Arrays.toString(this.data.entrySet().toArray()) + "]";
	}
	
	public static class PageArgsBuilder {
		
		private PageArgs arg = new PageArgs();
		
		public PageArgsBuilder keyVal(String key, Object val) {
			this.arg.set(key, val);
			return this;
		}
		
		public PageArgs build() {
			PageArgs builded = this.arg;
			this.arg = new PageArgs(); // "so it shall be reused"
			return builded;
		}
		
	}

	public static PageArgsBuilder set() {
		return new PageArgsBuilder();
	}
	
}
