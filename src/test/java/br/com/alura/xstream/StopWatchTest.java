package br.com.alura.xstream;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.AssumptionViolatedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StopWatchTest {
	private static final Logger logger = LoggerFactory.getLogger(StopWatchTest.class);

	private static void logInfo(Description description, String status, long nanos) {
		String test = description.getMethodName();
		logger.info(String.format("Test %s %s, spent %d microseconds", test, status,
				TimeUnit.NANOSECONDS.toMicros(nanos)));
	}

	@Rule
	public Stopwatch stopwatch = new Stopwatch() {
		@Override
		protected void succeeded(long nanos, Description description) {
			logInfo(description, "succeeded", nanos);
		}

		@Override
		protected void failed(long nanos, Throwable e, Description description) {
			logInfo(description, "failed", nanos);
		}

		@Override
		protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
			logInfo(description, "skipped", nanos);
		}

		@Override
		protected void finished(long nanos, Description description) {
			logInfo(description, "finished", nanos);
		}
	};

	@Test
	public void succeeds() {
	}

	@Test
	public void fails() {
		Assert.fail();
	}

	@Test
	public void skips() {
		Assume.assumeTrue(false);
	}

	@Test
	public void performanceTest() throws InterruptedException {
		double delta = 20.0;
		Thread.sleep(300L);
		Assert.assertEquals(300d, stopwatch.runtime(TimeUnit.MILLISECONDS), delta);
		Thread.sleep(500L);
		Assert.assertEquals(800d, stopwatch.runtime(TimeUnit.MILLISECONDS), delta);
	}
}