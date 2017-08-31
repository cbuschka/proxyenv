package com.github.cbuschka.proxyenv;

public class ProxyEnv
{
	/* visible for test */ UnixProxyEnvAdapter envAdapter = new UnixProxyEnvAdapter();

	public ProxyEnv()
	{
	}

	public ProxyConfig getProxyConfig()
	{
		return envAdapter.extract();
	}
}
