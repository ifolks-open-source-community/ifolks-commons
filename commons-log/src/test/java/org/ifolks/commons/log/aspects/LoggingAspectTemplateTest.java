package org.ifolks.commons.log.aspects;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoggingAspectTemplateTest {

	static class DummyLoggingAspect extends LoggingAspectTemplate {
		@Override
		protected void onPointcut() {}

		@Override
		protected String getFallbackTransactionType(Method proxiedMethod) {
			return "test";
		}
	}

	@Test
	public void testIsAccessDeniedExceptionDirect() {
		DummyLoggingAspect aspect = new DummyLoggingAspect();
		Exception e = new org.springframework.security.access.AccessDeniedException("denied");
		Assertions.assertTrue(aspect.isAccessDeniedException(e));
	}

	@Test
	public void testIsAccessDeniedExceptionSubclass() {
		DummyLoggingAspect aspect = new DummyLoggingAspect();
		Exception customAccessDenied = new CustomAccessDeniedException("custom");
		Assertions.assertTrue(aspect.isAccessDeniedException(customAccessDenied));
	}

	@Test
	public void testIsAccessDeniedExceptionOther() {
		DummyLoggingAspect aspect = new DummyLoggingAspect();
		Assertions.assertFalse(aspect.isAccessDeniedException(new RuntimeException("other")));
		Assertions.assertFalse(aspect.isAccessDeniedException(null));
	}

	static class CustomAccessDeniedException extends org.springframework.security.access.AccessDeniedException {
		public CustomAccessDeniedException(String msg) {
			super(msg);
		}
	}
}
