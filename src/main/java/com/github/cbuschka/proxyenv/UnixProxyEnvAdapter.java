package com.github.cbuschka.proxyenv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class UnixProxyEnvAdapter implements EnvAdapter
{
	private OsDetector osDetector = new OsDetector();
	private Function<String, String> envAccessor;
	private HostWithPortParser hostWithPortParser = new HostWithPortParser();

	public UnixProxyEnvAdapter()
	{
		this((e) -> System.getenv(e));
	}

	public UnixProxyEnvAdapter(Function<String, String> envAccessor)
	{
		this.envAccessor = envAccessor;
	}

	@Override
	public boolean handles()
	{
		return osDetector.isUnixoid();
	}

	public ProxyConfig extract()
	{
		HostWithPort httpProxyHost = getHttpProxy();
		HostWithPort httpsProxyHost = getHttpsProxy();
		HostWithPort ftpProxyHost = getFtpProxy();
		List<NonProxyHost> nonProxyHosts = getNonProxyHosts();

		return new ProxyConfig(httpProxyHost, httpsProxyHost, nonProxyHosts, ftpProxyHost, nonProxyHosts);
	}

	public HostWithPort getHttpProxy()
	{
		return getHostWithPort("http_proxy", 80);
	}

	public HostWithPort getHttpsProxy()
	{
		return getHostWithPort("https_proxy", 443);
	}

	public HostWithPort getFtpProxy()
	{
		return getHostWithPort("ftp_proxy", 80);
	}

	public List<NonProxyHost> getNonProxyHosts()
	{
		String nonProxyHostsValue = this.envAccessor.apply("no_proxy");
		if (nonProxyHostsValue == null || nonProxyHostsValue.trim().isEmpty())
		{
			return Collections.emptyList();
		}

		List<NonProxyHost> nonProxyHosts = new ArrayList<>();
		String[] parts = nonProxyHostsValue.split(",");
		for (String part : parts)
		{
			nonProxyHosts.add(new NonProxyHost(part));
		}

		return nonProxyHosts;
	}

	private HostWithPort getHostWithPort(String envVarName, int defaultPort)
	{
		String value = this.envAccessor.apply(envVarName);
		if (value == null)
		{
			return null;
		}

		return hostWithPortParser.parse(value, defaultPort);
	}
}
