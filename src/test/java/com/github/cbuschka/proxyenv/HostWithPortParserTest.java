package com.github.cbuschka.proxyenv;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class HostWithPortParserTest
{
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private HostWithPortParser hostWithPortParser = new HostWithPortParser();

	@Test
	public void parseNull()
	{
		expectedException.expect(NullPointerException.class);

		hostWithPortParser.parse(null, 80);
	}

	@Test
	public void parseFullUri()
	{
		HostWithPort hostWithPort = hostWithPortParser.parse("http://hostname:9090", 80);

		assertThat(hostWithPort.getHostName(), is("hostname"));
		assertThat(hostWithPort.getPort(), is(9090));
	}

	@Test
	public void parseHostnameWithPort()
	{
		HostWithPort hostWithPort = hostWithPortParser.parse("hostname:9090", 80);


		assertThat(hostWithPort.getHostName(), is("hostname"));
		assertThat(hostWithPort.getPort(), is(9090));
	}

	@Test
	public void parseHostnameOnly()
	{
		HostWithPort hostWithPort = hostWithPortParser.parse("hostname", 9090);

		assertThat(hostWithPort.getHostName(), is("hostname"));
		assertThat(hostWithPort.getPort(), is(9090));
	}
}