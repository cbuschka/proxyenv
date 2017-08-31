package com.github.cbuschka.proxyenv;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class UnixProxyEnvAdapter
{
	private Function<String, String> envAccessor;

	private UnixNonProxyHostsParser nonProxyHostsParser = new UnixNonProxyHostsParser();

	private HostWithPortParser hostWithPortParser = new HostWithPortParser();

	protected UnixProxyEnvAdapter()
	{
		this((e) -> System.getenv(e));
	}

	protected UnixProxyEnvAdapter(Function<String, String> envAccessor)
	{
		this.envAccessor = envAccessor;
	}

	public ProxyConfig extract()
	{
		ProxyConfig proxyConfig = new ProxyConfig();
		List<NonProxyHost> nonProxyHosts = getNonProxyHosts();
		proxyConfig.setHttpProxyHost(getHostWithPort("http_proxy", 80));
		proxyConfig.setHttpNonProxyHosts(nonProxyHosts);
		if (proxyConfig.getHttpProxyHost() != null)
		{
			proxyConfig.setHttpNonProxyHosts(nonProxyHosts);
		}
		proxyConfig.setHttpsProxyHost(getHostWithPort("https_proxy", 443));
		proxyConfig.setFtpProxyHost(getHostWithPort("ftp_proxy", 443));
		if (proxyConfig.getFtpProxyHost() != null)
		{
			proxyConfig.setFtpNonProxyHosts(nonProxyHosts);
		}
		return proxyConfig;
	}

	protected HostWithPort getHostWithPort(String envVarName, int defaultPort)
	{
		String value = this.envAccessor.apply(envVarName);
		if (value == null)
		{
			return null;
		}

		return hostWithPortParser.parse(value, defaultPort);
	}

	public List<NonProxyHost> getNonProxyHosts()
	{
		String nonProxyHostsValue = this.envAccessor.apply("no_proxy");
		if (nonProxyHostsValue == null || nonProxyHostsValue.trim().isEmpty())
		{
			return Collections.emptyList();
		}

		return nonProxyHostsParser.parse(nonProxyHostsValue);
	}

}
