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
	public void macOSXIsUnixoid() throws Exception
	{
		givenOsName("Mac OS X");

		assertThat(osDetector.isUnixoid(), is(true));
	}

	@Test
	public void sunOsIsUnixoid() throws Exception
	{
		givenOsName("SunOS");

		assertThat(osDetector.isUnixoid(), is(true));
	}


	@Test
	public void freeBsdIsUnixoid() throws Exception
	{
		givenOsName("FreeBSD");

		assertThat(osDetector.isUnixoid(), is(true));
	}

	@Test
	public void linuxIsUnixoid() throws Exception
	{
		givenOsName("Linux");

		assertThat(osDetector.isUnixoid(), is(true));
	}

	@Test
	public void windowsIsNotUnixoid() throws Exception
	{
		givenOsName("Windows");

		assertThat(osDetector.isUnixoid(), is(false));
	}

	private void givenOsName(String osName)
	{
		this.osName = osName;
	}

}