package net.sf.josceleton.connection.api.service;

import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.UserState;
import net.sf.josceleton.core.api.test.TestableUser;

import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public abstract class UserServiceTest extends AbstractUserServiceTest {
	
	@Test public final void fullBlownScenarioCoveringQuietManyPossibilitiesOfDispatchedMessages() {
		// during the test five users will be present:
		//   1 ... delay joint
		//   2 ... delay lost only
		//   3 ... delay skel
		//   4 ... new + lost (no skel)
		//   5 ... regular guy
		// specified IDs in here are osceletonIDs, not internally created unique ID (we dont have control over that ;)
		
		assertScenarios(
			//     { ID }  { ----- ACTION ------ }  { ------------------------------- EXPECTED STATE ------------------------------- }  {getWaitingUsers}   {getProcessingUsers}
			//      "s0" , UNSEEN BY Josceleton!!!! waitingUsers(    3          ), processingUsers(    2     1 ), deadUsers(         )
			newStep("s1" , UserState.WAITING,    4, waitingUsers( 4             ), processingUsers(            ), deadUsers(         ), new int[] { 4    }, new int[] {         }), // <=== /new_user 4
			newStep("s2*", UserState.PROCESSING, 3, waitingUsers( 4, 3          ), processingUsers( 3          ), deadUsers(         ), new int[] { 4    }, new int[] { 3       }), // <=*= /new_skel 3  ++ /new_user 3
			newStep("s3" , UserState.WAITING,    5, waitingUsers( 4, 3, 5       ), processingUsers( 3          ), deadUsers(         ), new int[] { 4, 5 }, new int[] { 3       }), // <=== /new_user 5
			newStep("s4*", UserState.DEAD,       2, waitingUsers( 4, 3, 5, 2    ), processingUsers( 3, 2       ), deadUsers( 2       ), new int[] { 4, 5 }, new int[] { 3       }), // <=*= /lost_user 2 ++ /new_user 2 ++ /new_skel 2
			newStep("s5" , UserState.PROCESSING, 5, waitingUsers( 4, 3, 5, 2    ), processingUsers( 3, 2, 5    ), deadUsers( 2       ), new int[] { 4    }, new int[] { 3, 5    }), // <=== /new_skel 5
			newStep("s6*", null/*jointmessage*/, 1, waitingUsers( 4, 3, 5, 2, 1 ), processingUsers( 3, 2, 5, 1 ), deadUsers( 2       ), new int[] { 4    }, new int[] { 3, 5, 1 }), // <=*= /joint 1     ++ /new_user 1 ++ /new_skel 1
			newStep("s7" , null/*jointmessage*/, 5, waitingUsers( 4, 3, 5, 2, 1 ), processingUsers( 3, 2, 5, 1 ), deadUsers( 2       ), new int[] { 4    }, new int[] { 3, 5, 1 }), // <=== /joint 5
			newStep("s8" , UserState.DEAD      , 4, waitingUsers( 4, 3, 5, 2, 1 ), processingUsers( 3, 2, 5, 1 ), deadUsers( 2, 4    ), new int[] {      }, new int[] { 3, 5, 1 }), // <=== /lost_user 4
			newStep("s9" , UserState.DEAD      , 5, waitingUsers( 4, 3, 5, 2, 1 ), processingUsers( 3, 2, 5, 1 ), deadUsers( 2, 4, 5 ), new int[] {      }, new int[] { 3, 1    })  // <=== /lost_user 5
			// * = artificial dispatch by service (seems as something happened before started listening on osc port)
		);
	}

	@Test(expectedExceptions = RuntimeException.class,
		expectedExceptionsMessageRegExp = "Already add new user with ID \\[12\\].*")
	public final void lookupSameUserWaitingFails() {
		final Integer osceletonId = Integer.valueOf(12);
		final TestableUserServiceDispatcher service = this.createTestableTestee(new User[] { new TestableUser(osceletonId) });
		service.delegateLookupUserMessage(osceletonId, UserState.WAITING);
		service.delegateLookupUserMessage(osceletonId, UserState.WAITING);
	}
}
