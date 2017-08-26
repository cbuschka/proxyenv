package com.github.cbuschka.proxyenv;

import java.util.Collections;
import java.util.function.Function;

public class WindowsProxyEnvAdapter extends AbstractEnvVarProxyEnvAdapter implements EnvAdapter
{
	private OsDetector osDetector = new OsDetector();

	public WindowsProxyEnvAdapter()
	{
		super();
	}

	public WindowsProxyEnvAdapter(Function<String, String> envAccessor)
	{
		super(envAccessor);
	}

	@Override
	public boolean handles()
	{
		return osDetector.isWindows();
	}

	public ProxyConfig extract()
	{
		HostWithPort httpProxyHost = getHttpProxy();
		HostWithPort httpsProxyHost = getHttpsProxy();

		return new ProxyConfig(httpProxyHost, httpsProxyHost, Collections.emptyList(), null, Collections.emptyList());
	}

	public HostWithPort getHttpProxy()
	{
		return getHostWithPort("http_proxy", 80);
	}

	public HostWithPort getHttpsProxy()
	{
		return getHostWithPort("https_proxy", 443);
	}
}
