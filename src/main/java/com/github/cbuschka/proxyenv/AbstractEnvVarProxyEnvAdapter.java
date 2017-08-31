package com.github.cbuschka.proxyenv;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractEnvVarProxyEnvAdapter implements EnvAdapter
{
	protected Function<String, String> envAccessor;
	protected HostWithPortParser hostWithPortParser = new HostWithPortParser();
	protected UnixNonProxyHostsParser nonProxyHostsParser = new UnixNonProxyHostsParser();

	protected AbstractEnvVarProxyEnvAdapter()
	{
		this((e) -> System.getenv(e));
	}

	protected AbstractEnvVarProxyEnvAdapter(Function<String, String> envAccessor)
	{
		this.envAccessor = envAccessor;
	}

	@Override
	public abstract boolean handles();

	public abstract ProxyConfig extract();

	protected HostWithPort getHostWithPort(String envVarName, int defaultPort)
	{
		String value = this.envAccessor.apply(envVarName);
		if (value == null)
		{
			return null;
		}

		return hostWithPortParser.parse(value, defaultPort);
	}

	protected List<NonProxyHost> getNonProxyHosts()
	{
		String nonProxyHostsValue = this.envAccessor.apply("no_proxy");
		if (nonProxyHostsValue == null || nonProxyHostsValue.trim().isEmpty())
		{
			return Collections.emptyList();
		}

		return nonProxyHostsParser.parse(nonProxyHostsValue);
	}

}
