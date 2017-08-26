package com.github.cbuschka.proxyenv;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProxyEnvTest
{
	@InjectMocks
	private ProxyEnv proxyEnv;

	@Mock
	private EnvAdapter envAdapter;

	@Mock
	private ProxyConfig proxyEnvModel;

	@Before
	public void setUp()
	{
		proxyEnv.envAdapters = Arrays.asList(envAdapter);
		when(envAdapter.handles()).thenReturn(true);
		when(envAdapter.extract()).thenReturn(proxyEnvModel);
	}

	@Test
	public void noProxySettings() throws Exception
	{
		ProxyConfig proxyConfig = proxyEnv.getProxyConfig();

		assertThat(proxyConfig.getHttpProxyHost(), is(nullValue()));
		assertThat(proxyConfig.getHttpNonProxyHosts(), is(Collections.emptyList()));
		assertThat(proxyConfig.getHttpsProxyHost(), is(nullValue()));
		assertThat(proxyConfig.getFtpProxyHost(), is(nullValue()));
		assertThat(proxyConfig.getFtpNonProxyHosts(), is(Collections.emptyList()));
	}

}