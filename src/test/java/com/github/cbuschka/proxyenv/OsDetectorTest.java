package com.github.cbuschka.proxyenv;

import org.junit.Test;

import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class OsDetectorTest
{
	private OsDetector osDetector = new OsDetector(new Function<String, String>()
	{
		@Override
		public String apply(String s)
		{
			return osName;
		}
	});

	private String osName;

	@Test
	public void shouldDetectMaxOSX() throws Exception
	{
		givenOsName("Mac OS X");

		assertThat(osDetector.isUnixoid(), is(true));
		assertThat(osDetector.isWindows(), is(false));
	}

	@Test
	public void shouldDetectSunOS() throws Exception
	{
		givenOsName("SunOS");

		assertThat(osDetector.isUnixoid(), is(true));
		assertThat(osDetector.isWindows(), is(false));
	}


	@Test
	public void shouldDetectFreeBSD() throws Exception
	{
		givenOsName("FreeBSD");

		assertThat(osDetector.isUnixoid(), is(true));
		assertThat(osDetector.isWindows(), is(false));
	}

	@Test
	public void shouldDetectLinux() throws Exception
	{
		givenOsName("Linux");

		assertThat(osDetector.isUnixoid(), is(true));
		assertThat(osDetector.isWindows(), is(false));
	}

	@Test
	public void shouldDetectWindows() throws Exception
	{
		givenOsName("Windows 2000");

		assertThat(osDetector.isUnixoid(), is(false));
		assertThat(osDetector.isWindows(), is(true));
	}

	private void givenOsName(String osName)
	{
		this.osName = osName;
	}

}