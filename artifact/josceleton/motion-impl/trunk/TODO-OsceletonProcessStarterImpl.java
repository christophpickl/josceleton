
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.pulseproject.mkinector.josceleton.api.OsceletonProcessStarter;

public class OsceletonProcessStarterImpl implements OsceletonProcessStarter {

	

	public static void main(final String[] args) {
		new OsceletonProcessStarterImpl().start();
	}

	// code from http://www.devdaily.com/java/java-exec-processbuilder-process-3

	@Override public final void start() {
//		executeCommand("say", "hello");
		executeCommand("/apps/bin/osceleton");
//		executeCommand("/apps/bin/tut");
		
		
		// FIXME also show gui with "quit/kill" button to nicely exit (and not to kill JVM)
	}
	
	
	// TODO oustource
	private static int executeCommand(final String commandName) {
		return executeCommand(commandName, Collections.<String>emptyList());
	}
	
	private static int executeCommand(final String commandName, final List<String> arguments/*interactive message, eg for sudo*/) {
		System.out.println("executing [" + commandName.toString() + "] with arguments: " + Arrays.toString(arguments.toArray()));
		
		try {
			final List<String> pbArgs = new LinkedList<String>(arguments);
			pbArgs.add(0, commandName);
			final ProcessBuilder pb = new ProcessBuilder(pbArgs);
			System.out.println("starting ...");
			
//			Map<String, String> env = pb.environment();
//			final String osceletonParentFolderPath = "/apps/bin";
//			final String oldPath = env.get("PATH");
//			final String newPath = oldPath + ":" + osceletonParentFolderPath;
//			System.out.println("registering new path: " + newPath);
//			env.put("PATH", newPath);
//			for (Entry<String, String> e : env.entrySet()) {
//				System.out.println("ENV: " + e.getKey() + " => " + e.getValue());
//			}
			
			final Process process = pb.start();
			
			InputStream inputStream = process.getInputStream();
			InputStream errorStream = process.getErrorStream();
			
			// interactive message
//			OutputStream stdOutput = process.getOutputStream();
//			ThreadedStreamHandler outputStreamHandler = ThreadedStreamHandler.newWithInteraction(
//					"OUT*", inputStream, stdOutput, interactiveMessage); // "sudo" admin password
			
			ThreadedStreamHandler outputStreamHandler = ThreadedStreamHandler.newSimple("OUT", inputStream);
			ThreadedStreamHandler errorStreamHandler = ThreadedStreamHandler.newSimple("ERR", errorStream);

			// TODO the inputStreamHandler has a nasty side-effect of hanging if the password is wrong; fix it
			outputStreamHandler.start();
			errorStreamHandler.start();
			
			System.out.println("command running");
			final int exitValue = process.waitFor();

			// TODO a better way to do this?
			outputStreamHandler.interrupt();
			errorStreamHandler.interrupt();
			outputStreamHandler.join();
			errorStreamHandler.join();
			
			System.out.println("finished. exitValue: " + exitValue);
			return exitValue;
			
		} catch (final Exception e) {
			// catch exceptions separately, instead global try block
			throw new RuntimeException("executing command failed", e);
		}
	}

	private static class ThreadedStreamHandler extends Thread {
		private final String name;
		private final InputStream inputStream;
		private final String interactionInputString;
		
		private final StringBuilder outputBuffer = new StringBuilder();
		private final OutputStream outputStream;

		private ThreadedStreamHandler(
				String name,
				InputStream inputStream,
				OutputStream outputStream,
				String interactionInputString) {
			this.name = name;
			this.inputStream = inputStream;
			this.outputStream = outputStream;
			this.interactionInputString = interactionInputString;
		}
		
		static ThreadedStreamHandler newWithInteraction(
				String name,
				InputStream inputStream,
				OutputStream outputStream,
				String interactionInputString) {
			// ifnull outputStream && interactionInputString
			return new ThreadedStreamHandler(name, inputStream, outputStream, interactionInputString);
		}
		static ThreadedStreamHandler newSimple(
				String name,
				InputStream inputStream) {
			
			return new ThreadedStreamHandler(name, inputStream, null, null);
		}

		public void run() {
			// on mac os x 10.5.x, when i run a 'sudo' command, i need to write
			// the admin password out immediately; that's why this code is here.
			
			if (interactionInputString != null) {
				System.out.println(">> [" + this.name + "] SUDO REQUESTED, entering password");
				// doSleep(500);
				final PrintWriter outputStreamWriter = new PrintWriter(outputStream);
				outputStreamWriter.println(interactionInputString);
				outputStreamWriter.flush();
			}

			BufferedReader bufferedReader = null;
			try {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				String line = null;
				System.out.println("[" + this.name + "] Waiting for input ...");
				while ((line = bufferedReader.readLine()) != null) {
					System.out.println("[" + this.name + "]: " + line);
					outputBuffer.append(line + "\n");
				}
			} catch (IOException ioe) {
				// TODO handle this better; users won't want the code doing this
				ioe.printStackTrace();
			} catch (Throwable t) {
				// TODO handle this better; users won't want the code doing this
				t.printStackTrace();
			} finally {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// ignore this one
				}
			}
		}

		private void doSleep(long millis) {
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				// ignore
			}
		}

		public StringBuilder getOutputBuffer() {
			return outputBuffer;
		}

	}
}
