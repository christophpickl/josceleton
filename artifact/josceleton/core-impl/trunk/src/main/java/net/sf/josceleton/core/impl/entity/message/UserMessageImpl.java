package net.sf.josceleton.core.impl.entity.message;

import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.api.entity.user.User;
import net.sf.josceleton.core.api.entity.user.UserState;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class UserMessageImpl extends GenericMessageImpl implements UserMessage {
	
	private final UserState userState;
	
	@Inject UserMessageImpl(
			@Assisted final User user,
			@Assisted final UserState userState) {
		super(user);
		this.userState = userState;
	}

	/** {@inheritDoc} from {@link UserMessage} */
	@Override public final UserState getUserState() {
		return this.userState;
	}
	
	@Override public final boolean equals(final Object other) {
		if(this == other) { return true; }
		if((other instanceof UserMessage) == false) { return false; }
		final UserMessage that = (UserMessage) other;
		return	this.getUser().equals(that.getUser()) && 
				this.getUserState().equals(that.getUserState());
	}
	
	@Override public final int hashCode() {
		return this.getUser().hashCode();
	}
	
	@Override public final String toString() {
		return "UserMessageImpl[user=" + this.getUser() + ", userState=" + this.userState + "]";
	}

}
