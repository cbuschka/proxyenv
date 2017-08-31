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
	private EnvAdapter envAdapter0;

	@Mock
	private EnvAdapter envAdapter1;

	@Mock
	private EnvAdapter envAdapter2;

	@Mock
	private ProxyConfig proxyConfig;

	@Before
	public void setUp()
	{
		proxyEnv.envAdapters = Arrays.asList(envAdapter0, envAdapter1, envAdapter2);
		when(envAdapter0.handles()).thenReturn(false);
		when(envAdapter1.handles()).thenReturn(true);
		when(envAdapter2.handles()).thenReturn(true);
		when(envAdapter1.extract()).thenReturn(proxyConfig);
	}

	@Test
	public void asksHandlingEnvAdapter() throws Exception
	{
		ProxyConfig result = proxyEnv.getProxyConfig();

		assertThat(result, is(this.proxyConfig));
	}

}