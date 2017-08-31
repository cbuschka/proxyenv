package com.github.cbuschka.proxyenv;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProxyEnvTest
{
	@InjectMocks
	private ProxyEnv proxyEnv;

	@Mock
	private UnixProxyEnvAdapter envAdapter1;

	@Mock
	private ProxyConfig proxyConfig;

	@Before
	public void setUp()
	{
		proxyEnv.envAdapter = envAdapter1;
		when(envAdapter1.extract()).thenReturn(proxyConfig);
	}

	@Test
	public void asksHandlingEnvAdapter() throws Exception
	{
		ProxyConfig result = proxyEnv.getProxyConfig();

		assertThat(result, is(this.proxyConfig));
	}
}