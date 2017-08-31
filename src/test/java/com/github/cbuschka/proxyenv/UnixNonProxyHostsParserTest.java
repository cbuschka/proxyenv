package com.github.cbuschka.proxyenv;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class UnixNonProxyHostsParserTest
{
	private UnixNonProxyHostsParser unixNonProxyHostsParser = new UnixNonProxyHostsParser();

	private String noProxy;
	private List<NonProxyHost> result;

	@Test
	public void empty()
	{
		givenNoProxySetting("");

		whenParsed();

		thenResultIsEmpty();
	}

	@Test
	public void localhost()
	{
		givenNoProxySetting("localhost");

		whenParsed();

		thenResultIs(Arrays.asList(new NonProxyHost("localhost")));
	}

	@Test
	public void wildcardPattern()
	{
		givenNoProxySetting("*.domain");

		whenParsed();

		thenResultIs(Arrays.asList(new NonProxyHost("*.domain")));
	}

	@Test
	public void localhostIp4()
	{
		givenNoProxySetting("127.0.0.1");

		whenParsed();

		thenResultIs(Arrays.asList(new NonProxyHost("127.0.0.1")));
	}

	@Test
	public void ip4()
	{
		givenNoProxySetting("44.44.44.44");

		whenParsed();

		thenResultIs(Arrays.asList(new NonProxyHost("44.44.44.44")));
	}

	@Test
	public void domain()
	{
		givenNoProxySetting(".domain");

		whenParsed();

		thenResultIs(Arrays.asList(new NonProxyHost("*.domain")));
	}

	@Test
	public void hostnamePotentiallyDomainSuffix()
	{
		givenNoProxySetting("name");

		whenParsed();

		thenResultIs(Arrays.asList(new NonProxyHost("name"), new NonProxyHost("*.name")));
	}

	private void thenResultIs(List<NonProxyHost> expected)
	{
		assertThat(this.result, is(expected));
	}

	private void givenNoProxySetting(String noProxy)
	{
		this.noProxy = noProxy;
	}

	private void whenParsed()
	{
		this.result = unixNonProxyHostsParser.parse(this.noProxy);
	}

	private void thenResultIsEmpty()
	{
		assertThat(this.result, is(Collections.emptyList()));
	}
}