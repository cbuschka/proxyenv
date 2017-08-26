package com.github.cbuschka.proxyenv;

import org.junit.Test;

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
		String javaOpts = new JavaOptsBuilder().withHttpProxyHost(new HostWithPort("host",80)).buildString();

		assertThat(javaOpts, is("-Dhttp.proxyHost=host -Dhttp.proxyPort=80"));
	}
}