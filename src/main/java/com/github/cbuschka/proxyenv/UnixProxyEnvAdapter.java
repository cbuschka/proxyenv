package com.github.cbuschka.proxyenv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class UnixProxyEnvAdapter extends AbstractEnvVarProxyEnvAdapter implements EnvAdapter
{
	private OsDetector osDetector = new OsDetector();

	public UnixProxyEnvAdapter()
	{
		super();
	}

	public UnixProxyEnvAdapter(Function<String, String> envAccessor)
	{
		super(envAccessor);
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
		return super.getNonProxyHosts();
	}
}
