package com.github.cbuschka.proxyenv;

import java.util.Arrays;
import java.util.List;

public class ProxyEnv
{
	/* for test */ List<EnvAdapter> envAdapters = Arrays.asList(new UnixProxyEnvAdapter());

	public ProxyEnv()
	{
	}

	public ProxyConfig getProxyConfig()
	{
		EnvAdapter envAdapter = getEnvAdapter();
		return envAdapter.extract();
	}

	private EnvAdapter getEnvAdapter()
	{
		for (EnvAdapter envAdapter : envAdapters)
		{
			if (envAdapter.handles())
			{
				return envAdapter;
			}
		}

		throw new IllegalStateException("No env adapter found.");
	}
}
