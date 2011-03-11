package net.sf.josceleton.connection.api.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.core.api.entity.UserState;

import org.testng.annotations.Test;

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
			//      { ID }  { ----- ACTION ------ }  { ------------------------------- EXPECTED STATE ------------------------------- }
			//       "s0" , UNSEEN BY Josceleton!!!! waitingUsers(    3          ), processingUsers(    2     1 ), deadUsers(         )
			newState("s1" , UserState.WAITING,    4, waitingUsers( 4             ), processingUsers(            ), deadUsers(         )), // <=== /new_user 4
			newState("s2*", UserState.PROCESSING, 3, waitingUsers( 4, 3          ), processingUsers( 3          ), deadUsers(         )), // <=*= /new_skel 3  ++ /new_user 3
			newState("s3" , UserState.WAITING,    5, waitingUsers( 4, 3, 5       ), processingUsers( 3          ), deadUsers(         )), // <=== /new_user 5
			newState("s4*", UserState.DEAD,       2, waitingUsers( 4, 3, 5, 2    ), processingUsers( 3, 2       ), deadUsers( 2       )), // <=*= /lost_user 2 ++ /new_user 2 ++ /new_skel 2
			newState("s5" , UserState.PROCESSING, 5, waitingUsers( 4, 3, 5, 2    ), processingUsers( 3, 2, 5    ), deadUsers( 2       )), // <=== /new_skel 5
			newState("s6*", null/*jointmessage*/, 1, waitingUsers( 4, 3, 5, 2, 1 ), processingUsers( 3, 2, 5, 1 ), deadUsers( 2       )), // <=*= /joint 1     ++ /new_user 1 ++ /new_skel 1
			newState("s7" , null/*jointmessage*/, 5, waitingUsers( 4, 3, 5, 2, 1 ), processingUsers( 3, 2, 5, 1 ), deadUsers( 2       )), // <=== /joint 5
			newState("s8" , UserState.DEAD      , 4, waitingUsers( 4, 3, 5, 2, 1 ), processingUsers( 3, 2, 5, 1 ), deadUsers( 2, 4    )), // <=== /lost_user 4
			newState("s9" , UserState.DEAD      , 5, waitingUsers( 4, 3, 5, 2, 1 ), processingUsers( 3, 2, 5, 1 ), deadUsers( 2, 4, 5 ))  // <=== /lost_user 5
			// * = artificial dispatch by service (seems as something happened before started listening on osc port)
		);
	}

//	@Test
//	public final void testname() {
//		
//		assertThat(null, equalTo(null));
//	}
}
