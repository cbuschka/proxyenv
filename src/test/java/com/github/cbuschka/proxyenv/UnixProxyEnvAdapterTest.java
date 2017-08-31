package com.github.cbuschka.proxyenv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UnixProxyEnvAdapterTest
{
	@InjectMocks
	private UnixProxyEnvAdapter envAdapter = new UnixProxyEnvAdapter();

	@Mock
	private Function<String, String> envAccessor;

	private ProxyConfig proxyConfig;

	@Test
	public void httpProxyWithPort()
	{
		when(this.envAccessor.apply("http_proxy")).thenReturn("http://proxyhost:81");

		proxyConfig = this.envAdapter.extract();

		assertThat(proxyConfig.getHttpProxyHost(), is(new HostWithPort("proxyhost", 81)));
		assertThat(proxyConfig.getHttpNonProxyHosts(), is(Collections.emptyList()));
		thenNoHttpsProxySet();
		thenNoFtpSettings();
	}

	@Test
	public void httpProxyWithDefaultPort()
	{
		when(this.envAccessor.apply("http_proxy")).thenReturn("http://proxyhost");

		proxyConfig = this.envAdapter.extract();

		assertThat(proxyConfig.getHttpProxyHost(), is(new HostWithPort("proxyhost", 80)));
		assertThat(proxyConfig.getHttpNonProxyHosts(), is(Collections.emptyList()));
		thenNoHttpsProxySet();
		thenNoFtpSettings();
	}


	@Test
	public void httpProxyHostnameOnly()
	{
		when(this.envAccessor.apply("http_proxy")).thenReturn("proxyhost");

		proxyConfig = this.envAdapter.extract();

		assertThat(proxyConfig.getHttpProxyHost(), is(new HostWithPort("proxyhost", 80)));
		assertThat(proxyConfig.getHttpNonProxyHosts(), is(Collections.emptyList()));
		thenNoHttpsProxySet();
		thenNoFtpSettings();
	}

	private void thenNoHttpsProxySet()
	{
		assertThat(proxyConfig.getHttpsProxyHost(), is(nullValue()));
	}

	private void thenNoFtpSettings()
	{
		assertThat(proxyConfig.getFtpProxyHost(), is(nullValue()));
		assertThat(proxyConfig.getFtpNonProxyHosts(), is(Collections.emptyList()));
	}
}