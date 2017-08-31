package com.github.cbuschka.proxyenv;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class JavaOptsBuilderTest
{
	@Test
	public void noProxySettings() throws Exception
	{
		ProxyConfig proxyConfig = new ProxyConfig();

		String javaOpts = new JavaOptsBuilder().withProxyConfig(proxyConfig).buildString();

		assertThat(javaOpts, is(""));
	}

	@Test
	public void withHttpProxy() throws Exception
	{
		String javaOpts = new JavaOptsBuilder().withHttpProxyHost(new HostWithPort("host", 80)).buildString();

		assertThat(javaOpts, is("-Dhttp.proxyHost=host -Dhttp.proxyPort=80"));
	}

	@Test
	public void withHttpsProxy() throws Exception
	{
		String javaOpts = new JavaOptsBuilder().withHttpsProxyHost(new HostWithPort("host", 443)).buildString();

		assertThat(javaOpts, is("-Dhttps.proxyHost=host -Dhttps.proxyPort=443"));
	}

	@Test
	public void withHttpNonProxyHost() throws Exception
	{
		String javaOpts = new JavaOptsBuilder().withHttpNonProxyHosts(Arrays.asList((NonProxyHost) NonProxyHost.valueOf("localhost"))).buildString();

		assertThat(javaOpts, is("-Dhttp.nonProxyHosts=localhost"));
	}
}