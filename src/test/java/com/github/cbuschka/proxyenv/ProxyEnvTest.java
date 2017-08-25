package com.github.cbuschka.proxyenv;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
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
		String javaOpts = proxyEnv.toJavaOpts();

		assertThat(javaOpts, is(""));
	}

}